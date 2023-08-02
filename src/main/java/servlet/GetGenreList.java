package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import model.Genre;
import model.Status;
import model.URL;

/**
 * Servlet implementation class GetGenreList
 */
@WebServlet("/GetGenreList")
public class GetGenreList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetGenreList() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		HttpSession session = request.getSession();
		String status = "";
		String url;
		if (session != null && !session.isNew()) {
			String token = (String) session.getAttribute("token");

			if (token == null || token.isEmpty()) {
				status = Status.unauthorized;
				url = URL.signOut;
			} else {
				PrintWriter out = response.getWriter();
				Client client = ClientBuilder.newClient();
				String restUrl = URL.baseURL + "/getAllGenre";
				WebTarget target = client.target(restUrl);
				Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON); // media type as JSON
																									// data
				Response resp = invocationBuilder.get(); // perform HTTP GET method
				System.out.println("status: " + resp.getStatus());

				// https://stackoverflow.com/questions/18086621/read-response-body-in-jax-rs-client-from-a-post-request
				if (resp.getStatus() == Response.Status.OK.getStatusCode()) {
					System.out.println("success");

					// https://www.logicbig.com/tutorials/java-ee-tutorial/jax-rs/generic-entity.html

					ArrayList<Genre> genreList = resp.readEntity(new GenericType<ArrayList<Genre>>() {
					});
					if (genreList == null) {
						status = Status.serverError;
					} else {
						System.out.println(genreList.size());
						for (Genre genre : genreList) {
							System.out.println(genre.getGenreID());
							out.print("<br>GenreID: " + genre.getGenreID());
							out.print("<br>Genre: " + genre.getGenre());
						}
						// write to request object for forwarding to target page
						request.setAttribute("genreList", genreList);
					}
					System.out.println("......requestObj set ... forwarding ..");
					url = URL.genreList;

				} else {
					System.out.println("failed");
					url = URL.genreList;
					status = Status.serverError;
				}

			}
		} else {
			status = Status.unauthorized;
			url = URL.signOut;
		}
		request.setAttribute("status", status);
		RequestDispatcher rd = request.getRequestDispatcher(url);
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

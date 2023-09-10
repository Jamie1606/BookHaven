// Author		: Thu Htet San
// Admin No		: 2235022
// Class		: DIT/FT/2A/02
// Group		: 10
// Date			: 3.8.2023
// Description	: get genre list

package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.http.HttpHeaders;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import model.Genre;
import model.Member;
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
				Client client = ClientBuilder.newClient();
				WebTarget target = client.target(URL.baseURL).path("getAllGenre");
				Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON); 
				invocationBuilder.header(HttpHeaders.AUTHORIZATION, "Bearer " + token);
				
				Response resp = invocationBuilder.get(); // perform HTTP GET method
				System.out.println("status: " + resp.getStatus());

				// https://stackoverflow.com/questions/18086621/read-response-body-in-jax-rs-client-from-a-post-request
				if (resp.getStatus() == Response.Status.OK.getStatusCode()) {
					System.out.println("success");

					// https://www.logicbig.com/tutorials/java-ee-tutorial/jax-rs/generic-entity.html
					String json = resp.readEntity(String.class);
					ObjectMapper obj = new ObjectMapper();
					ArrayList<Genre> genreList = obj.readValue(json, new TypeReference<ArrayList<Genre>>() {});

					if (genreList == null || genreList.isEmpty()) {
						System.out.println("..... Server error in GetGenreList servlet .....");
						status = Status.serverError;
					} else {
/*						System.out.println(genreList.size());
						for (Genre genre : genreList) {
							System.out.println(genre.getGenreID());
							out.print("<br>GenreID: " + genre.getGenreID());
							out.print("<br>Genre: " + genre.getGenre());
						}
*/
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
		request.getRequestDispatcher(url).forward(request, response);
		return;
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

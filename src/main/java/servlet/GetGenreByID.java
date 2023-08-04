// Author		: Thu Htet San
// Admin No		: 2235022
// Class		: DIT/FT/2A/02
// Group		: 10
// Date			: 4.8.2023
// Description	: get genre by ID

package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpHeaders;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import model.Genre;
import model.Member;
import model.Status;
import model.URL;

/**
 * Servlet implementation class GetGenreByID
 */
@WebServlet("/GetGenreByID/*")
public class GetGenreByID extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetGenreByID() {
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
		String url = URL.genreList;
		boolean condition = true;
		String status = "";
		String id = "";

		if (session != null && !session.isNew()) {
			String token = (String) session.getAttribute("token");
			if (token == null || token.isEmpty()) {
				status = Status.unauthorized;
				url = URL.signOut;
			} else {

				try {
					String requestURi = (String) request.getRequestURI();
					String[] parts = requestURi.split("/");
					id = parts[parts.length - 1];
					id = id.trim();
					Integer.parseInt(id);
				} catch (Exception e) {
					e.printStackTrace();
					status = Status.invalidRequest;
					condition = false;
					System.out.println("..... Invalid data request in GetGenreByID servlet .....");
				}

				if (condition) {
					Client client = ClientBuilder.newClient();
					WebTarget target = client.target(URL.baseURL).path("getGenre").path("{id}").resolveTemplate("id",
							id);
					Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
					invocationBuilder.header(HttpHeaders.AUTHORIZATION, "Bearer " + token);
					Response resp = invocationBuilder.get();

					if (resp.getStatus() == Response.Status.OK.getStatusCode()) {

						String json = resp.readEntity(String.class);
						ObjectMapper obj = new ObjectMapper();
						Genre genre = obj.readValue(json, new TypeReference<Genre>() {
						});

						if (genre != null) {
							request.setAttribute("genre", genre);
							request.setAttribute("update", "true");
							url = URL.genreRegistration;
						} else {
							System.out.println("..... No genre in GetGenreByID servlet");
							status = Status.invalidData;
						}
					} else if (resp.getStatus() == Response.Status.BAD_REQUEST.getStatusCode()) {
						status = Status.invalidData;
					} else if (resp.getStatus() == Response.Status.FORBIDDEN.getStatusCode()) {
						status = Status.unauthorized;
						url = URL.signOut;
					} else {
						System.out.println("..... Error in GetGenreByID servlet .....");
						status = Status.serverError;
					}
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

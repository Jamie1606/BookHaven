// Author		: Thu Htet San
// Admin No		: 2235022
// Class		: DIT/FT/2A/02
// Group		: 10
// Date			: 3.8.2023
// Description	: create genre

package servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import model.Genre;
import model.Status;
import model.URL;

/**
 * Servlet implementation class CreateGenre
 */
@WebServlet("/CreateGenre")
public class CreateGenre extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CreateGenre() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		String status = "";
		String url = URL.genreRegistration;
		boolean condition = true;
		if (session != null && !session.isNew()) {
			String token = (String) session.getAttribute("token");

			if (token == null || token.isEmpty()) {
				status = Status.unauthorized;
				url = URL.signOut;
			} else {

				Genre genre = new Genre();
				String genreName = request.getParameter("genre");
				try {
					genre.setGenre(genreName.trim());
				} catch (Exception e) {
					e.printStackTrace();
					status = Status.invalidData;
					System.out.println("..... Invalid genre data in CreateGenre servlet .....");
					condition = false;
				}
				if (condition) {
					Client client = ClientBuilder.newClient();
					WebTarget target = client.target(URL.baseURL).path("/createGenre");
					Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
					invocationBuilder.header(HttpHeaders.AUTHORIZATION, "Bearer " + token);

					ObjectMapper obj = new ObjectMapper();
					String json = obj.writeValueAsString(genre);// data
					Response resp = invocationBuilder.post(Entity.json(json)); // perform HTTP POST method
					System.out.println("status: " + resp.getStatus());

					if (resp.getStatus() == Response.Status.OK.getStatusCode()) {
						System.out.println("success");

						// https://www.logicbig.com/tutorials/java-ee-tutorial/jax-rs/generic-entity.html

						Integer row = resp.readEntity(Integer.class);
						if(row == 1) {
							status = Status.insertSuccess;
						}
						else {
							System.out.println("..... Invalid genre data in CreateGenre servlet .....");
							status = Status.invalidData;
						}

					} else if(resp.getStatus() == Response.Status.FORBIDDEN.getStatusCode()) {
						status = Status.unauthorized;
						url = URL.signOut;
					}else {
						//url = URL.genreRegistration;
						status = Status.serverError;
					}
				}
			}
		} else {
			status = Status.unauthorized;
			url = URL.signOut;
		}
		request.setAttribute("status", status);
		RequestDispatcher rd = request.getRequestDispatcher(url);
		rd.forward(request, response);
		return;
	}

}

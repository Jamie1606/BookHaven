// Author		: Thu Htet San
// Admin No		: 2235022
// Class		: DIT/FT/2A/02
// Group		: 10
// Date			: 4.8.2023
// Description	: update genre

package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import model.Genre;
import model.Status;
import model.URL;

/**
 * Servlet implementation class UpdateGenre
 */
@WebServlet("/UpdateGenre")
public class UpdateGenre extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UpdateGenre() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		HttpSession session = request.getSession();
		boolean condition = true;
		String status = "";
		String url = URL.genreList;

		if (session != null && !session.isNew()) {
			String token = (String) session.getAttribute("token");

			if (token == null || token.isEmpty()) {
				status = Status.unauthorized;
				url = URL.signOut;
			} else {
				Genre genre = new Genre();
				String genreID = request.getParameter("genreID");
				String genreName = request.getParameter("genre");
				try {
					genre.setGenre(genreName.trim());
					genre.setGenreID(Integer.parseInt(genreID.trim()));
				} catch (Exception e) {
					e.printStackTrace();
					status = Status.invalidData;
					System.out.println("..... Invalid genre data in CreateGenre servlet .....");
					condition = false;
				}
				if (condition) {

					Client client = ClientBuilder.newClient();
					WebTarget target = client.target(URL.baseURL).path("updateGenre");
					Invocation.Builder invocationBuilder = target.request();
					invocationBuilder.header(HttpHeaders.AUTHORIZATION, "Bearer " + token);
					ObjectMapper obj = new ObjectMapper();
					String json = obj.writeValueAsString(genre);
					Response resp = invocationBuilder.put(Entity.json(json));

					if (resp.getStatus() == Response.Status.OK.getStatusCode()) {

						Integer row = resp.readEntity(Integer.class);

						if (row == 1) {
							status = Status.updateSuccess;
						} else {
							System.out.println("..... Invalid genre id in GenreMember servlet .....");
							status = Status.invalidData;
						}
					} else if (resp.getStatus() == Response.Status.FORBIDDEN.getStatusCode()) {
						status = Status.unauthorized;
						url = URL.signOut;
					} else {
						System.out.println("..... Error in UpdateMember servlet .....");
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
}

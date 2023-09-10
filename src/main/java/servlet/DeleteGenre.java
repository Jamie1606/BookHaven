// Author		: Thu Htet San
// Admin No		: 2235022
// Class		: DIT/FT/2A/02
// Group		: 10
// Date			: 4.8.2023
// Description	: delete genre by ID

package servlet;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import model.Status;
import model.URL;

/**
 * Servlet implementation class DeleteGenre
 */
@WebServlet("/DeleteGenre/*")
public class DeleteGenre extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteGenre() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		String url = URL.genreList;
		boolean condition = true;
		String status = "";
		String id = "";
		
		if(session != null && !session.isNew()) {
			String token = (String) session.getAttribute("token");
			
			if(token == null || token.isEmpty()) {
				status = Status.unauthorized;
				url = URL.signOut;
			}
			else {
				try {
					String requestURi = (String) request.getRequestURI();
					String[] parts = requestURi.split("/");
					id = parts[parts.length - 1];
					id = id.trim();
					Integer.parseInt(id);
				}
				catch(Exception e) {
					e.printStackTrace();
					condition = false;
					status = Status.invalidRequest;
					System.out.println("..... Invalid delete request in DeleteGenre servlet .....");
				}
				
				if(condition) {
					
					Client client = ClientBuilder.newClient();
					WebTarget target = client.target(URL.baseURL).path("deleteGenre").path("{id}").resolveTemplate("id", id);
					Invocation.Builder invocationBuilder = target.request();
					invocationBuilder.header(HttpHeaders.AUTHORIZATION, "Bearer " + token);
					Response resp = invocationBuilder.delete();
					
					if(resp.getStatus() == Response.Status.OK.getStatusCode()) {	
						Integer row = resp.readEntity(Integer.class);
						System.out.println(row);
						if(row == 1) {
							status = Status.deleteSuccess;
						}
						else {
							System.out.println("..... Genre not deleted in DeleteGenre servlet .....");
							status = Status.invalidRequest;
						}
					}
					else if(resp.getStatus() == Response.Status.FORBIDDEN.getStatusCode()) {
						status = Status.unauthorized;
						url = URL.signOut;
					}
					else {
						System.out.println("..... Error in DeleteGenre servlet .....");
						status = Status.serverError;
					}
				}
			}
		}
		else {
			status = Status.unauthorized;
			url = URL.signOut;
		}
			
		request.setAttribute("status", status);
		request.getRequestDispatcher(url).forward(request, response);
		return;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

}

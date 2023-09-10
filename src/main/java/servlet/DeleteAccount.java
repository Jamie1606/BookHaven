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
 * Servlet implementation class DeleteAccount
 */
@WebServlet("/DeleteAccount/*")
public class DeleteAccount extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteAccount() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("..... In delete mb servlet .....");
		HttpSession session = request.getSession();
		String url = URL.signOut;
		boolean condition = true;
		String status = "";
		String id = "";
		
		if(session != null && !session.isNew()) {
			String token = (String) session.getAttribute("token");
			
			if(token == null || token.isEmpty()) {
				status = Status.unauthorized;

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
					System.out.println("..... Invalid delete request in DeleteAccount servlet .....");
				}
				
				if(condition) {
					
					Client client = ClientBuilder.newClient();
					WebTarget target = client.target(URL.baseURL).path("deleteMember").path("{id}").resolveTemplate("id", id);
					Invocation.Builder invocationBuilder = target.request();
					invocationBuilder.header(HttpHeaders.AUTHORIZATION, "Bearer " + token);
					Response resp = invocationBuilder.delete();
					
					if(resp.getStatus() == Response.Status.OK.getStatusCode()) {	
						Integer row = resp.readEntity(Integer.class);	
						if(row == 1) {
							status = Status.deleteSuccess;
						}
						else {
							System.out.println("..... Member not deleted in DeleteAccount servlet .....");
							status = Status.invalidData;
						}
					}
					else if(resp.getStatus() == Response.Status.FORBIDDEN.getStatusCode()) {
						status = Status.unauthorized;
					}
					else {
						System.out.println("..... Error in DeleteAccount servlet .....");
						status = Status.serverError;
					}
				}
			}
		}
		else {
			status = Status.unauthorized;
		}
			
		request.setAttribute("status", status);
		request.getRequestDispatcher(url).forward(request, response);
		return;
	}

}

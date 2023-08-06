// Author		: Zay Yar Tun
// Admin No		: 2235035
// Class		: DIT/FT/2A/02
// Group		: 10
// Date			: 1.8.2023
// Description	: update review status

package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;

import model.Status;
import model.URL;

/**
 * Servlet implementation class UpdateReviewStatus
 */
@WebServlet("/UpdateReviewStatus/*")
public class UpdateReviewStatus extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public UpdateReviewStatus() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		String url = URL.reviewList;
		boolean condition = true;
		String status = "";
		String reviewstatus = "";
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
					id = parts[parts.length - 2];
					reviewstatus = parts[parts.length - 1];
					Integer.parseInt(id);
					reviewstatus = reviewstatus.trim();
					if(!reviewstatus.equals("approved") && !reviewstatus.equals("denied")) {
						throw new Error();
					}
				}
				catch(Exception e) {
					e.printStackTrace();
					System.out.println(".... Invalid request in UpdateReviewStatus servlet .....");
					status = Status.invalidRequest;
					condition = false;
				}
				
				if(condition) {
					
					Client client = ClientBuilder.newClient();
					WebTarget target = client.target(URL.baseURL).path("updateReview")
							.path("{id}").resolveTemplate("id", id)
							.path("{status}").resolveTemplate("status", reviewstatus);
					Invocation.Builder invocationBuilder = target.request();
					invocationBuilder.header(HttpHeaders.AUTHORIZATION, "Bearer " + token);
					
					Response resp = invocationBuilder.put(Entity.json(""));
					
					if(resp.getStatus() == Response.Status.OK.getStatusCode()) {
						
						Integer row = resp.readEntity(Integer.class);
						
						if(row == 1) {
							status = Status.updateSuccess;
						}
						else {
							System.out.println("..... Review not updated in UpdateReviewStatus servlet .....");
							status = Status.invalidData;
						}
					}
					else if(resp.getStatus() == Response.Status.FORBIDDEN.getStatusCode()) {
						status = Status.unauthorized;
						url = URL.signOut;
					}
					else {
						System.out.println("..... Error in UpdateReviewStatus servlet .....");
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
}

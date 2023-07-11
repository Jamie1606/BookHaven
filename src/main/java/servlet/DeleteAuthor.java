// Author		: Zay Yar Tun
// Admin No		: 2235035
// Class		: DIT/FT/2A/02
// Group		: 10
// Date			: 11.7.2023
// Description	: this is to store admin data from database

package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Response;

/**
 * Servlet implementation class DeleteAuthor
 */
@WebServlet("/DeleteAuthor/*")
public class DeleteAuthor extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public DeleteAuthor() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String requestURi = (String) request.getRequestURI();
		String[] parts = requestURi.split("/");
		if(parts.length == 0) {
			
		}
		
		String id = parts[parts.length - 1];
		
		Client client = ClientBuilder.newClient();
		String restUrl = "http://localhost:8081/bookhaven/api";
		WebTarget target = client.target(restUrl).path("deleteAuthor").path("{id}").resolveTemplate("id", id);
		Invocation.Builder invocationBuilder = target.request();
		Response resp = invocationBuilder.delete();
		String url = "/signout.jsp";
		
		if(resp.getStatus() == Response.Status.OK.getStatusCode()) {	
			Integer row = resp.readEntity(Integer.class);	
			if(row == 1) {
				
			}
			else {
				
			}
			request.setAttribute("status", "success");
			url = "/GetAuthorList";
		}
		else {
			System.out.println("..... Error in DeleteAuthor Servlet .....");
			url = "/GetAuthorList";
			request.setAttribute("err", "NotFound");
			
		}
		request.getRequestDispatcher(url).forward(request, response);
		return;
	}

}

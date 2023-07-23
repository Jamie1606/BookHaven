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

import model.URL;

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
		String url = URL.authorList;
		boolean condition = true;
		String status = "";
		String id = "";
		
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
			status = "invalid";
			System.out.println("..... Invalid delete request in DeleteAuthor servlet .....");
		}
		
		if(condition) {
			Client client = ClientBuilder.newClient();
			WebTarget target = client.target(URL.baseURL).path("deleteAuthor").path("{id}").resolveTemplate("id", id);
			Invocation.Builder invocationBuilder = target.request();
			Response resp = invocationBuilder.delete();
			
			if(resp.getStatus() == Response.Status.OK.getStatusCode()) {	
				Integer row = resp.readEntity(Integer.class);	
				if(row == 1) {
					status = "deletesuccess";
				}
				else {
					System.out.println("..... Author not deleted in DeleteAuthor servlet .....");
					status = "invalid";
				}
			}
			else {
				System.out.println("..... Error in DeleteAuthor servlet .....");
				status = "deleteservererror";
			}
		}
			
		request.setAttribute("status", status);
		request.getRequestDispatcher(url).forward(request, response);
		return;
	}

}

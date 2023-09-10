// Author		: Zay Yar Tun
// Admin No		: 2235035
// Class		: DIT/FT/2A/02
// Group		: 10
// Date			: 27.7.2023
// Description	: get author by author id

package servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import model.URL;
import model.Author;
import model.Status;

/**
 * Servlet implementation class GetAuthorByID
 */
@WebServlet("/GetAuthorByID/*")
public class GetAuthorByID extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public GetAuthorByID() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		String url = URL.authorList;
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
					status = Status.invalidRequest;
					condition = false;
					System.out.println("..... Invalid data request in GetAuthorByID servlet .....");
				}
				
				if(condition) {
					
					Client client = ClientBuilder.newClient();
					WebTarget target = client.target(URL.baseURL).path("getAuthor").path("{id}").resolveTemplate("id", id);
					Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
					Response resp = invocationBuilder.get();
					
					if(resp.getStatus() == Response.Status.OK.getStatusCode()) {
						
						String json = resp.readEntity(String.class);
						ObjectMapper obj = new ObjectMapper();
						Author author = obj.readValue(json, new TypeReference<Author>() {});
						
						if(author != null) {				
							request.setAttribute("author", author);
							request.setAttribute("update", "true");
							url = URL.authorRegistration;
						}
						else {
							System.out.println("..... No author in GetAuthorByID servlet");
							status = Status.invalidData;
						}
					}
					else {
						System.out.println("..... Error in GetAuthorByID servlet .....");
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

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
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import model.URL;
import model.Author;
import model.TestReg;

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
		String requestURi = (String) request.getRequestURI();
		String[] parts = requestURi.split("/");
		String url = URL.authorList;
		boolean condition = true;
		
		if(parts.length == 0) {
			System.out.println("..... Invalid data request in GetAuthorByID servlet .....");
			request.setAttribute("status", "invalid");
			condition = false;
		}
		
		String id = parts[parts.length - 1];
		if(id == null || !TestReg.matchInteger(id)) {
			System.out.println("..... Invalid author id in GetAuthorByID servlet .....");
			request.setAttribute("status", "invalid");
			condition = false;
		}
		
		if(condition) {
			Client client = ClientBuilder.newClient();
			WebTarget target = client.target(URL.baseURL).path("getAuthor").path("{id}").resolveTemplate("id", id);
			Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
			Response resp = invocationBuilder.get();
			
			if(resp.getStatus() == Response.Status.OK.getStatusCode()) {
				Author author = resp.readEntity(new GenericType<Author>() {});
				if(author != null) {				
					request.setAttribute("author", author);
					request.setAttribute("update", "true");
					url = URL.authorRegistration;
				}
				else {
					System.out.println("..... No author in GetAuthorByID servlet");
					url = URL.authorList;
					request.setAttribute("status", "invalid");
				}
			}
			else {
				System.out.println("..... Error in GetAuthorByID servlet .....");
				url = URL.authorList;
				request.setAttribute("status", "updateservererror");
			}
		}
		
		request.getRequestDispatcher(url).forward(request, response);
		return;
	}
}

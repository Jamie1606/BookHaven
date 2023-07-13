package servlet;

import java.io.IOException;
import java.io.PrintWriter;

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
import model.Author;

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
		if(parts.length == 0) {
			
		}
		
		String id = parts[parts.length - 1];
		
		Client client = ClientBuilder.newClient();
		String restUrl = "http://localhost:8081/bookhaven/api";
		WebTarget target = client.target(restUrl).path("getAuthor").path("{id}").resolveTemplate("id", id);
		Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
		Response resp = invocationBuilder.get();
		
		String url = "/signout.jsp";
		if(resp.getStatus() == Response.Status.OK.getStatusCode()) {
			Author author = resp.readEntity(new GenericType<Author>() {});
			if(author != null) {				
				request.setAttribute("author", author);
				request.setAttribute("update", "true");
				url = "/admin/authorRegistration.jsp";
			}
			else {
				System.out.println("No user");
				url = "/GetAuthorList";
				request.setAttribute("err", "NotFound");
			}
		}
		else {
			System.out.println("failed");
			url = "/GetAuthorList";
			request.setAttribute("err", "NotFound");
		}
		request.getRequestDispatcher(url).forward(request, response);
	}
}

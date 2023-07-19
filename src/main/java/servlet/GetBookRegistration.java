package servlet;

import java.io.IOException;
import java.util.ArrayList;

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
import model.Genre;
import model.URL;

/**
 * Servlet implementation class GetBookRegistration
 */
@WebServlet("/GetBookRegistration")
public class GetBookRegistration extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public GetBookRegistration() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(URL.baseURL).path("getAllAuthor");
		Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
		Response resp = invocationBuilder.get();
		String url = URL.bookRegistration;
		
		if(resp.getStatus() == Response.Status.OK.getStatusCode()) {			
			ArrayList<Author> authorList = resp.readEntity(new GenericType<ArrayList<Author>>() {});	
			if(authorList == null) {
				System.out.println("..... Author null error in GetBookRegistration servlet .....");
				request.setAttribute("status", "servererror");
			}
			else {
				request.setAttribute("authorList", authorList);
				request.setAttribute("servlet", "true");
			}
		}
		else {
			System.out.println("..... Author error in GetBookRegistration servlet .....");
			request.setAttribute("status", "servererror");
		}
		
		target = client.target(URL.baseURL).path("getAllGenre");
		invocationBuilder = target.request(MediaType.APPLICATION_JSON);
		resp = invocationBuilder.get();
		
		if(resp.getStatus() == Response.Status.OK.getStatusCode()) {			
			ArrayList<Genre> genreList = resp.readEntity(new GenericType<ArrayList<Genre>>() {});	
			if(genreList == null) {
				System.out.println("..... Genre null error in GetBookRegistration servlet .....");
				request.setAttribute("status", "servererror");
			}
			else {
				request.setAttribute("genreList", genreList);
				request.setAttribute("servlet", "true");
			}
		}
		else {
			System.out.println("..... Genre error in GetBookRegistration servlet .....");
			request.setAttribute("status", "servererror");
		}
		
		request.getRequestDispatcher(url).forward(request, response);
		return;
	}
}

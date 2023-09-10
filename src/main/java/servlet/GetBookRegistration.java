// Author		: Zay Yar Tun
// Admin No		: 2235035
// Class		: DIT/FT/2A/02
// Group		: 10
// Date			: 29.7.2023
// Description	: get book registration data (genre and author)

package servlet;

import java.io.IOException;
import java.util.ArrayList;

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

import model.Author;
import model.Genre;
import model.Status;
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
		
		HttpSession session = request.getSession();
		String status = "";
		String url = URL.bookRegistration;
		
		if(session != null && !session.isNew()) {
			String token = (String) session.getAttribute("token");
			
			if(token == null || token.isEmpty()) {
				status = Status.unauthorized;
				url = URL.signOut;
			}
			else {
				Client client = ClientBuilder.newClient();
				WebTarget target = client.target(URL.baseURL).path("getAllAuthor");
				Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
				Response resp = invocationBuilder.get();
				ObjectMapper obj = new ObjectMapper();
				
				if(resp.getStatus() == Response.Status.OK.getStatusCode()) {		
					
					String json = resp.readEntity(String.class);
					ArrayList<Author> authorList = obj.readValue(json, new TypeReference<ArrayList<Author>>() {});
					
					if(authorList == null) {
						System.out.println("..... Author null error in GetBookRegistration servlet .....");
						status = Status.serverError;
					}
					else {
						request.setAttribute("authorList", authorList);
						status = Status.servletStatus;
					}
				}
				else {
					System.out.println("..... Author error in GetBookRegistration servlet .....");
					status = Status.serverError;
				}
				
				target = client.target(URL.baseURL).path("getAllGenre");
				invocationBuilder = target.request(MediaType.APPLICATION_JSON);
				resp = invocationBuilder.get();
				
				if(resp.getStatus() == Response.Status.OK.getStatusCode()) {			
					
					String json = resp.readEntity(String.class);
					ArrayList<Genre> genreList = obj.readValue(json, new TypeReference<ArrayList<Genre>>() {});
					
					if(genreList == null) {
						System.out.println("..... Genre null error in GetBookRegistration servlet .....");
						status = Status.serverError;
					}
					else {
						request.setAttribute("genreList", genreList);
						status = Status.servletStatus;
					}
				}
				else {
					System.out.println("..... Genre error in GetBookRegistration servlet .....");
					status = Status.serverError;
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

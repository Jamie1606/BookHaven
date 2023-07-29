// Author		: Zay Yar Tun
// Admin No		: 2235035
// Class		: DIT/FT/2A/02
// Group		: 10
// Date			: 27.7.2023
// Description	: get book by isbn

package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import model.Book;
import model.Status;
import model.URL;

/**
 * Servlet implementation class GetBookByISBN
 */
@WebServlet("/GetBookByISBN/*")
public class GetBookByISBN extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public GetBookByISBN() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		String url = URL.bookList;
		boolean condition = true;
		String status = "";
		String isbn = "";
		
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
					isbn = parts[parts.length - 1];
					isbn = isbn.trim();
				}
				catch(Exception e) {
					e.printStackTrace();
					System.out.println("..... Invalid isbn in GetBookByISBN servlet .....");
					status = Status.invalidData;
					condition = false;
				}
				
				if(condition) {
					
					Client client = ClientBuilder.newClient();
					WebTarget target = client.target(URL.baseURL).path("getBook").path("details").path("{isbn}").resolveTemplate("isbn", isbn);
					Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
					Response resp = invocationBuilder.get();
					
					if(resp.getStatus() == Response.Status.OK.getStatusCode()) {
						
						String json = resp.readEntity(String.class);
						ObjectMapper obj = new ObjectMapper();
						Book book = obj.readValue(json, new TypeReference<Book>() {});
						
						if(book != null) {				
							request.setAttribute("book", book);
							request.setAttribute("update", "true");
							url = URL.getBookRegistrationServlet;
						}
						else {
							System.out.println("..... No book in GetBookByISBN servlet .....");
							status = Status.invalidData;
						}
					}
					else {
						System.out.println("..... Error in GetBookByISBN servlet .....");
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

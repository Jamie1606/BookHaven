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
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import model.Status;
import model.URL;

/**
 * Servlet implementation class DeleteBook
 */
@WebServlet("/DeleteBook/*")
public class DeleteBook extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteBook() {
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
					condition = false;
					System.out.println("..... Invalid isbn in DeleteBook servlet .....");
					status = Status.invalidRequest;
				}
				
				
				if(condition) {
					
					Client client = ClientBuilder.newClient();
					WebTarget target = client.target(URL.baseURL).path("deleteBook").path("{isbn}").resolveTemplate("isbn", isbn);
					Invocation.Builder invocationBuilder = target.request();
					invocationBuilder.header(HttpHeaders.AUTHORIZATION, "Bearer " + token);
					Response resp = invocationBuilder.delete();
					
					if(resp.getStatus() == Response.Status.OK.getStatusCode()) {	
						Integer row = resp.readEntity(Integer.class);
						if(row == 1) {
							status = Status.deleteSuccess;
						}
						else {
							System.out.println("..... Book not deleted in DeleteBook servlet .....");
							status = Status.invalidData;
						}
					}
					else if(resp.getStatus() == Response.Status.FORBIDDEN.getStatusCode()) {
						status = Status.unauthorized;
						url = URL.signOut;
					}
					else {
						System.out.println("..... Error in DeleteBook servlet .....");
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

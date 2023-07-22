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

import model.Functions;
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
		String requestURi = (String) request.getRequestURI();
		String[] parts = requestURi.split("/");
		String url = URL.bookList;
		boolean condition = true;
		
		if(parts.length == 0) {
			System.out.println("..... Invalid delete request in DeleteBook servlet .....");
			request.setAttribute("status", "invalid");
			condition = false;
		}
		
		String isbn = parts[parts.length - 1];
		
		if(!Functions.checkFormData(isbn)) {
			System.out.println("..... Invalid isbn in DeleteBook servlet .....");
			request.setAttribute("status", "invalid");
			condition = false;
		}
		
		if(condition) {
			Client client = ClientBuilder.newClient();
			WebTarget target = client.target(URL.baseURL).path("deleteBook").path("{isbn}").resolveTemplate("isbn", isbn);
			Invocation.Builder invocationBuilder = target.request();
			Response resp = invocationBuilder.delete();
			
			if(resp.getStatus() == Response.Status.OK.getStatusCode()) {	
				Integer row = resp.readEntity(Integer.class);
				if(row == 1) {
					request.setAttribute("status", "deletesuccess");
				}
				else {
					System.out.println("..... Book not deleted in DeleteBook servlet .....");
					request.setAttribute("status", "invalid");
				}
			}
			else {
				System.out.println("..... Error in DeleteAuthor servlet .....");
				request.setAttribute("status", "deleteservererror");
			}
		}
			
		request.getRequestDispatcher(url).forward(request, response);
		return;
	}
}

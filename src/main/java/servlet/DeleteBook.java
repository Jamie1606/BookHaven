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
		String url = URL.bookList;
		boolean condition = true;
		String status = "";
		String isbn = "";
		
		
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
			status = "invalid";
		}
		
		
		if(condition) {
			Client client = ClientBuilder.newClient();
			WebTarget target = client.target(URL.baseURL).path("deleteBook").path("{isbn}").resolveTemplate("isbn", isbn);
			Invocation.Builder invocationBuilder = target.request();
			Response resp = invocationBuilder.delete();
			
			if(resp.getStatus() == Response.Status.OK.getStatusCode()) {	
				Integer row = resp.readEntity(Integer.class);
				if(row == 1) {
					status = "deletesuccess";
				}
				else {
					System.out.println("..... Book not deleted in DeleteBook servlet .....");
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

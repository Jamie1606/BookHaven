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
 * Servlet implementation class GetBookByBookTitle
 */
@WebServlet("/GetBookByBookTitle/*")
public class GetBookByBookTitle extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetBookByBookTitle() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String url = URL.search;
		boolean condition = true;
		String status = "";
		String title = "";

				try {
					String requestURi = (String) request.getRequestURI();
					String[] parts = requestURi.split("/");
					title = parts[parts.length - 1];
					title = title.trim();
				}
				catch(Exception e) {
					e.printStackTrace();
					System.out.println("..... Invalid title in GetBookByBookTitle servlet .....");
					status = Status.invalidRequest;
					condition = false;
				}
				
				if(condition) {
					
					Client client = ClientBuilder.newClient();
					WebTarget target = client.target(URL.baseURL).path("getBook").path("title").path("{bookTitle}").resolveTemplate("bookTitle", title);
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
							System.out.println("..... No book in GetBookByBookTitle servlet .....");
							status = Status.invalidData;
						}
					} else if (resp.getStatus() == Response.Status.BAD_REQUEST.getStatusCode()) {
						status = Status.invalidData;
					} 
					else {
						System.out.println("..... Error in GetBookByBookTitle servlet .....");
						status = Status.serverError;
					}
				}
			
		
		
		request.setAttribute("status", status);
		request.getRequestDispatcher(url).forward(request, response);
		return;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

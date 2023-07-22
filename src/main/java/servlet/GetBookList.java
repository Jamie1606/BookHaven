package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import model.Book;
import model.URL;

/**
 * Servlet implementation class GetBookList
 */
@WebServlet("/GetBookList")
public class GetBookList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public GetBookList() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(URL.baseURL).path("getAllBook");
		Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
		Response resp = invocationBuilder.get();
		String url = URL.bookList;
		
		if(resp.getStatus() == Response.Status.OK.getStatusCode()) {	
			String json = resp.readEntity(String.class);
			ObjectMapper obj = new ObjectMapper();
			ArrayList<Book> bookList = obj.readValue(json, new TypeReference<ArrayList<Book>>() {});
			if(bookList == null || bookList.isEmpty()) {
				System.out.println("..... Server error in GetBookList servlet .....");
				request.setAttribute("status", "servererror");
			}
			else {
				request.setAttribute("bookList", bookList);
				request.setAttribute("servlet", "true");
			}
		}
		else {
			System.out.println("..... Error in GetBookList servlet .....");
			request.setAttribute("status", "servererror");
		}
		
		request.getRequestDispatcher(url).forward(request, response);
		return;
	}
}

// Author		: Zay Yar Tun
// Admin No		: 2235035
// Class		: DIT/FT/2A/02
// Group		: 10
// Date			: 29.7.2023
// Description	: get book list

package servlet;

import java.io.IOException;
import java.util.ArrayList;

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
 * Servlet implementation class GetBookList
 */
@WebServlet("/GetBookList")
public class GetBookList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public GetBookList() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		String url = URL.bookList;
		String status = "";
		if(request.getParameter("report")!=null) {
			if((request.getParameter("report")).equals("customer")) {
			url = URL.customersOfBook;}
		}
		if(session != null && !session.isNew()) {
			String token = (String) session.getAttribute("token");
			
			if(token == null || token.isEmpty()) {
				status = Status.unauthorized;
				url = URL.signOut;
			}
			else {
				Client client = ClientBuilder.newClient();
				WebTarget target = client.target(URL.baseURL).path("getAllBook");
				Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
				Response resp = invocationBuilder.get();
				
				if(resp.getStatus() == Response.Status.OK.getStatusCode()) {	
					
					String json = resp.readEntity(String.class);
					ObjectMapper obj = new ObjectMapper();
					ArrayList<Book> bookList = obj.readValue(json, new TypeReference<ArrayList<Book>>() {});
					
					if(bookList == null || bookList.isEmpty()) {
						System.out.println("..... Server error in GetBookList servlet .....");
						status = Status.serverError;
					}
					else {
						request.setAttribute("bookList", bookList);
						status = Status.servletStatus;
					}
				}
				else {
					System.out.println("..... Error in GetBookList servlet .....");
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

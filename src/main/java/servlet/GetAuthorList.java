// Author		: Zay Yar Tun
// Admin No		: 2235035
// Class		: DIT/FT/2A/02
// Group		: 10
// Date			: 11.7.2023
// Description	: this is to store admin data from database

package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
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
 * Servlet implementation class GetAuthorList
 */
@WebServlet("/GetAuthorList")
public class GetAuthorList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public GetAuthorList() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Client client = ClientBuilder.newClient();
		String restUrl = "http://localhost:8081/bookhaven/api";
		WebTarget target = client.target(restUrl).path("getAllAuthor");
		Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
		Response resp = invocationBuilder.get();
		String url = "/signout.jsp";
		
		if(resp.getStatus() == Response.Status.OK.getStatusCode()) {			
			ArrayList<Author> authorList = resp.readEntity(new GenericType<ArrayList<Author>>() {});	
			if(authorList == null) {
				
			}
			request.setAttribute("authorList", authorList);
			request.setAttribute("servlet", "true");
			url = "/admin/authorList.jsp";
		}
		else {
			System.out.println("..... Error in GetAuthorList Servlet .....");
			url = "/admin/authorList.jsp";
			request.setAttribute("err", "NotFound");
			
		}
		request.getRequestDispatcher(url).forward(request, response);
		return;
	}
}

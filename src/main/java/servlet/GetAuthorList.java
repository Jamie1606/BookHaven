// Author		: Zay Yar Tun
// Admin No		: 2235035
// Class		: DIT/FT/2A/02
// Group		: 10
// Date			: 11.7.2023
// Description	: this is to store admin data from database

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
import model.URL;

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
		WebTarget target = client.target(URL.baseURL).path("getAllAuthor");
		Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
		Response resp = invocationBuilder.get();
		String url = URL.authorList;
		
		if(resp.getStatus() == Response.Status.OK.getStatusCode()) {			
			ArrayList<Author> authorList = resp.readEntity(new GenericType<ArrayList<Author>>() {});	
			if(authorList == null) {
				System.out.println("..... Server error in GetAuthorList servlet .....");
				request.setAttribute("status", "servererror");
				url = URL.authorList;
			}
			else {
				request.setAttribute("authorList", authorList);
				request.setAttribute("servlet", "true");
				url = URL.authorList;
			}
		}
		else {
			System.out.println("..... Error in GetAuthorList servlet .....");
			request.setAttribute("status", "servererror");
			url = URL.authorList;
		}
		
		request.getRequestDispatcher(url).forward(request, response);
		return;
	}
}

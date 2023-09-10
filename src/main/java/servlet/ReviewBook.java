// Author		: Zay Yar Tun
// Admin No		: 2235035
// Class		: DIT/FT/2A/02
// Group		: 10
// Date			: 1.8.2023
// Description	: give book review

package servlet;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import model.InvalidErrorException;
import model.Review;
import model.Status;
import model.URL;

/**
 * Servlet implementation class ReviewBook
 */
@WebServlet("/ReviewBook")
@MultipartConfig(location = "", maxFileSize = 10485760, maxRequestSize = 20971520, fileSizeThreshold = 1048576)
public class ReviewBook extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ReviewBook() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		String status = "";
		String url = URL.signOut;
		boolean condition = true;
		Review reviewObj = new Review();
		
		if(session != null && !session.isNew()) {
			String token = (String) session.getAttribute("token");
			
			if(token == null || token.isEmpty()) {
				status = Status.unauthorized;
			}
			else {
				
				String rating = request.getParameter("rating");
				String review = request.getParameter("review");
				String orderid = request.getParameter("orderid");
				String isbn = request.getParameter("isbn");
				
				try {
					if(review.trim().length() > 255) {
						throw new InvalidErrorException();
					}
					
					reviewObj.setISBNNo(isbn.trim());
					reviewObj.setDescription(review.trim());
					reviewObj.setRating(Short.parseShort(rating));
					reviewObj.setStatus("pending");
				}
				catch(InvalidErrorException e) {
					status = Status.invalidData;
					System.out.println("..... Invalid data in ReviewBook servlet .....");
					condition = false;
				}
				catch(Exception e) {
					e.printStackTrace();
					status = Status.invalidData;
					System.out.println("..... Invalid data in ReviewBook servlet .....");
					condition = false;
				}
				
				if(condition) {
					
					Client client = ClientBuilder.newClient();
					WebTarget target = client.target(URL.baseURL).path("makeReview")
							.path("{orderid}").resolveTemplate("orderid", orderid)
							.path("{isbn}").resolveTemplate("isbn", isbn);
					Invocation.Builder invocationBuilder = target.request();
					invocationBuilder.header(HttpHeaders.AUTHORIZATION, "Bearer " + token);
					
					ObjectMapper obj = new ObjectMapper();
					String json = obj.writeValueAsString(reviewObj);
					Response resp = invocationBuilder.post(Entity.json(json));
		
					if (resp.getStatus() == Response.Status.OK.getStatusCode()) {
						Integer row = resp.readEntity(Integer.class);
						if (row == 1) {
							status = Status.ok;
						} 
						else {
							System.out.println("..... Invalid review data in ReviewBook servlet .....");
							status = Status.invalidData;
						}
					} 
					else if(resp.getStatus() == Response.Status.FORBIDDEN.getStatusCode()) {
						status = Status.unauthorized;
						url = URL.signOut;
					}
					else {
						System.out.println("..... Error in ReviewBook servlet .....");
						status = Status.serverError;
					}
				}
			}
		}
		else {
			status = Status.unauthorized;
		}
		
		if(status.equals(Status.unauthorized)) {
			request.setAttribute("status",status);
			request.getRequestDispatcher(url).forward(request, response);
			return;
		}
		else {
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(status);
		}	
		
	}

}

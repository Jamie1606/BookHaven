package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpHeaders;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import model.Review;
import model.Status;
import model.URL;

/**
 * Servlet implementation class GetReviewList
 */
@WebServlet("/GetReviewList")
public class GetReviewList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public GetReviewList() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		String url = URL.reviewList;
		String status = "";
		
		if(session != null && !session.isNew()) {
			String token = (String) session.getAttribute("token");
			
			if(token == null || token.isEmpty()) {
				status = Status.unauthorized;
				url = URL.signOut;
			}
			else {
				Client client = ClientBuilder.newClient();
				WebTarget target = client.target(URL.baseURL).path("getAllReview");
				Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
				invocationBuilder.header(HttpHeaders.AUTHORIZATION, "Bearer " + token);
				Response resp = invocationBuilder.get();
				
				if(resp.getStatus() == Response.Status.OK.getStatusCode()) {		
					
					String json = resp.readEntity(String.class);
					ObjectMapper obj = new ObjectMapper();
					ArrayList<Review> reviewList = obj.readValue(json, new TypeReference<ArrayList<Review>>() {});
					
					if(reviewList == null) {
						System.out.println("..... Server error in GetReviewList servlet .....");
						status = Status.serverError;
					}
					else {
						request.setAttribute("reviewList", reviewList);
					}
				}
				else if(resp.getStatus() == Response.Status.FORBIDDEN.getStatusCode()) {
					status = Status.unauthorized;
					url = URL.signOut;
				}
				else {
					System.out.println("..... Error in GetReviewList servlet .....");
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

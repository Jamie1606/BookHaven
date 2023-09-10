// Author		: Zay Yar Tun
// Admin No		: 2235035
// Class		: DIT/FT/2A/02
// Group		: 10
// Date			: 1.8.2023
// Description	: get member order lists

package servlet;

import java.io.IOException;
import java.util.ArrayList;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.http.HttpHeaders;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import model.Order;
import model.Status;
import model.URL;

/**
 * Servlet implementation class GetMemberOrder
 */
@WebServlet("/GetMemberOrder/*")
public class GetMemberOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public GetMemberOrder() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		String status = "";
		String url = URL.signOut;
		boolean condition = true;
		ArrayList<Order> orderList = new ArrayList<Order>();
		String json = "";
		String orderDate = "";
		ObjectMapper obj = new ObjectMapper();
		String orderStatus = "";
		
		if(session != null && !session.isNew()) {
			String token = (String) session.getAttribute("token");
			
			if(token == null || token.isEmpty()) {
				status = Status.unauthorized;
			}
			else {
				try {
					String requestURi = (String) request.getRequestURI();
					String[] parts = requestURi.split("/");
					orderDate = parts[parts.length - 2];
					orderStatus = parts[parts.length - 1];
					orderDate = orderDate.trim();
					orderStatus = orderStatus.trim();
				}
				catch(Exception e) {
					e.printStackTrace();
					condition = false;
					System.out.println("..... Invalid update request in UpdateBook servlet .....");
					json = "{\"order\": \"null\"}";
				}
				
				if(condition) {
					Client client = ClientBuilder.newClient();
					WebTarget target = client.target(URL.baseURL).path("getMemberOrders").path("{date}").resolveTemplate("date", orderDate)
							.path("{status}").resolveTemplate("status", orderStatus);
					Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
					invocationBuilder.header(HttpHeaders.AUTHORIZATION, "Bearer " + token);
					Response resp = invocationBuilder.get();
					
					if(resp.getStatus() == Response.Status.OK.getStatusCode()) {	
						
						json = resp.readEntity(String.class);
						orderList = obj.readValue(json, new TypeReference<ArrayList<Order>>() {});
					}
					else if(resp.getStatus() == Response.Status.FORBIDDEN.getStatusCode()) {
						status = Status.unauthorized;
					}
					else {
						System.out.println("..... Error in GetBookList servlet .....");
						json = "{\"order\": \"null\"}";
					}
				}
			}
		}
		else {
			status = Status.unauthorized;
		}
		
		if(status.equals(Status.unauthorized)) {
			request.setAttribute(status, "Status");
			url = URL.signOut;
			request.getRequestDispatcher(url).forward(request, response);
			return;
		}
		else {
			json = obj.writeValueAsString(orderList);
			response.setContentType("application/json");
			response.getWriter().write(json);
		}
	}
}

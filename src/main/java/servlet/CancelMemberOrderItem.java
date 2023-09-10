// Author		: Zay Yar Tun
// Admin No		: 2235035
// Class		: DIT/FT/2A/02
// Group		: 10
// Date			: 1.8.2023
// Description	: cancel member order item

package servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.http.HttpHeaders;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import model.OrderItem;
import model.Status;
import model.URL;

/**
 * Servlet implementation class CancelMemberOrderItem
 */
@WebServlet("/CancelMemberOrderItem/*")
public class CancelMemberOrderItem extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public CancelMemberOrderItem() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		String status = "";
		String url = URL.signOut;
		boolean condition = true;
		String isbn = "";
		String orderid = "";
		String json = "";
		OrderItem item = new OrderItem();
		
		if(session != null && !session.isNew()) {
			String token = (String) session.getAttribute("token");
			
			if(token == null || token.isEmpty()) {
				status = Status.unauthorized;
			}
			else {
				try {
					String requestURi = (String) request.getRequestURI();
					String[] parts = requestURi.split("/");
					isbn = parts[parts.length - 1];
					orderid = parts[parts.length - 2];
					isbn = isbn.trim();
					orderid = orderid.trim();
					item.setIsbnno(isbn);
					item.setOrderid(Integer.parseInt(orderid));
					item.setStatus("cancelled");
				}
				catch(Exception e) {
					e.printStackTrace();
					condition = false;
					json = "{\"status\": \"" + Status.invalidData + "\"}";
					System.out.println("..... Invalid request in CancelMemberOrderItem servlet .....");
				}
				
				if(condition) {
					
					Client client = ClientBuilder.newClient();
					WebTarget target = client.target(URL.baseURL).path("cancelMemberOrderItem");
					Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
					invocationBuilder.header(HttpHeaders.AUTHORIZATION, "Bearer " + token);
					
					ObjectMapper obj = new ObjectMapper();
					json = obj.writeValueAsString(item);
					Response resp = invocationBuilder.put(Entity.json(json));
					
					if(resp.getStatus() == Response.Status.OK.getStatusCode()) {
						
						int row = resp.readEntity(Integer.class);
						
						if(row == 1) {
							json = "{\"status\": \"" + Status.ok + "\"}";
						}
						else {
							json = "{\"status\": \"" + Status.invalidData + "\"}";
						}
					}
					else if(resp.getStatus() == Response.Status.FORBIDDEN.getStatusCode()) {
						status = Status.unauthorized;
					}
					else {
						System.out.println("..... Error in CancelMemberOrderItem servlet .....");
						json = "{\"status\": \"" + Status.serverError + "\"}";
					}
				}
			}
		}
		else {
			status = Status.unauthorized;
		}
		
		if(status.equals(Status.unauthorized)) {
			request.setAttribute("status", status);
			request.getRequestDispatcher(url).forward(request, response);
			return;
		}
		else {
			response.setContentType("application/json");
			response.getWriter().write(json);
		}
	}
}

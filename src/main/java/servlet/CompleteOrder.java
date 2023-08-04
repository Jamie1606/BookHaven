package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
 * Servlet implementation class CompleteOrder
 */
@WebServlet("/CompleteOrder/*")
public class CompleteOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public CompleteOrder() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		String status = "";
		String url = URL.orderDetailList + "?id=";
		boolean condition = true;
		String isbn = "";
		String orderid = "";
		OrderItem item = new OrderItem();
		
		if(session != null && !session.isNew()) {
			String token = (String) session.getAttribute("token");
			
			if(token == null || token.isEmpty()) {
				status = Status.unauthorized;
				url = URL.signOut;
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
					item.setStatus("delivered");
					url += orderid;
				}
				catch(Exception e) {
					e.printStackTrace();
					condition = false;
					status = Status.invalidRequest;
					System.out.println("..... Invalid request in CompleteOrder servlet .....");
				}
				
				if(condition) {
					
					Client client = ClientBuilder.newClient();
					WebTarget target = client.target(URL.baseURL).path("completeOrderItem");
					Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
					invocationBuilder.header(HttpHeaders.AUTHORIZATION, "Bearer " + token);
					
					ObjectMapper obj = new ObjectMapper();
					String json = obj.writeValueAsString(item);
					Response resp = invocationBuilder.put(Entity.json(json));
					
					if(resp.getStatus() == Response.Status.OK.getStatusCode()) {
						
						int row = resp.readEntity(Integer.class);
						
						if(row == 1) {
							status = Status.updateSuccess;
						}
						else {
							status = Status.invalidData;
						}
					}
					else if(resp.getStatus() == Response.Status.FORBIDDEN.getStatusCode()) {
						status = Status.unauthorized;
						url = URL.signOut;
					}
					else {
						System.out.println("..... Error in CompleteOrder servlet .....");
						status = Status.serverError;
					}
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

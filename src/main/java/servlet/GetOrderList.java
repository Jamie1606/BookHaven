// Author		: Zay Yar Tun
// Admin No		: 2235035
// Class		: DIT/FT/2A/02
// Group		: 10
// Date			: 29.7.2023
// Description	: get order list

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
 * Servlet implementation class GetOrderList
 */
@WebServlet("/GetOrderList")
public class GetOrderList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public GetOrderList() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		String url = URL.orderList;
		String status = "";
		
		if(session != null && !session.isNew()) {
			String token = (String) session.getAttribute("token");
			
			if(token == null || token.isEmpty()) {
				status = Status.unauthorized;
				url = URL.signOut;
			}
			else {
				Client client = ClientBuilder.newClient();
				WebTarget target = client.target(URL.baseURL).path("getAllOrders");
				Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
				invocationBuilder.header(HttpHeaders.AUTHORIZATION, "Bearer " + token);
				Response resp = invocationBuilder.get();
				
				if(resp.getStatus() == Response.Status.OK.getStatusCode()) {		
					
					String json = resp.readEntity(String.class);
					ObjectMapper obj = new ObjectMapper();
					ArrayList<Order> orderList = obj.readValue(json, new TypeReference<ArrayList<Order>>() {});
					
					if(orderList == null) {
						System.out.println("..... Server error in GetOrderList servlet .....");
						status = Status.serverError;
					}
					else {
						request.setAttribute("orderList", orderList);
					}
				}
				else if(resp.getStatus() == Response.Status.FORBIDDEN.getStatusCode()) {
					status = Status.unauthorized;
					url = URL.signOut;
				}
				else {
					System.out.println("..... Error in GetOrderList servlet .....");
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

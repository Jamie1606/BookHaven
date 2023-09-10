// Author		: Zay Yar Tun
// Admin No		: 2235035
// Class		: DIT/FT/2A/02
// Group		: 10
// Date			: 1.8.2023
// Description	: get member order detail list

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

import model.OrderItem;
import model.Status;
import model.URL;

/**
 * Servlet implementation class GetOrderDetailList
 */
@WebServlet("/GetOrderDetailList/*")
public class GetOrderDetailList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public GetOrderDetailList() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		String status = "";
		String url = URL.orderList;
		boolean condition = true;
		String orderid = "";
		
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
					orderid = parts[parts.length - 1];
					orderid = orderid.trim();
				}
				catch(Exception e) {
					e.printStackTrace();
					condition = false;
					status = Status.invalidRequest;
					System.out.println("..... Invalid request in GetOrderDetailList servlet .....");
				}
				
				if(condition) {
					
					Client client = ClientBuilder.newClient();
					WebTarget target = client.target(URL.baseURL).path("getOrderItem")
							.path("{id}").resolveTemplate("id", orderid);
					Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
					invocationBuilder.header(HttpHeaders.AUTHORIZATION, "Bearer " + token);
					Response resp = invocationBuilder.get();
					
					if(resp.getStatus() == Response.Status.OK.getStatusCode()) {
						
						String json = resp.readEntity(String.class);
						ObjectMapper obj = new ObjectMapper();
						ArrayList<OrderItem> itemList = obj.readValue(json, new TypeReference<ArrayList<OrderItem>>() {});
						
						if(itemList == null) {
							status = Status.invalidData;
						}
						else {
							request.setAttribute("itemList", itemList);
							url = URL.orderDetailList;
						}
					}
					else if(resp.getStatus() == Response.Status.FORBIDDEN.getStatusCode()) {
						status = Status.unauthorized;
						url = URL.signOut;
					}
					else {
						System.out.println("..... Error in GetOrderDetailList servlet .....");
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

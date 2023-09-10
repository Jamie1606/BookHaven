// Author		: Zay Yar Tun
// Admin No		: 2235035
// Class		: DIT/FT/2A/02
// Group		: 10
// Date			: 29.7.2023
// Description	: make new order

package servlet;

import java.io.IOException;
import java.util.ArrayList;

import jakarta.servlet.ServletException;
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
import model.Book;
import model.InvalidErrorException;
import model.Order;
import model.OrderItem;
import model.Status;
import model.URL;

/**
 * Servlet implementation class MakeOrder
 */
@WebServlet("/MakeOrder")
public class MakeOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public MakeOrder() {
        super();
    }


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		boolean condition = true;
		String url = URL.cart;
		String status = "";
		Order order = null;
		
		if(session != null && !session.isNew()) {
			String token = (String) session.getAttribute("token");
			
			if(token == null || token.isEmpty()) {
				url = URL.signOut;
				status = Status.unauthorized;
			}
			else {
				String stripeToken = request.getParameter("stripeToken");	
				String deliveryAddress = request.getParameter("delivery-address");
				
				try {
					order = new Order();
					order.setToken(stripeToken.trim());
					order.setDeliveryaddress(deliveryAddress);
					
					ArrayList<Integer> cartQty = (ArrayList<Integer>) session.getAttribute("cart-qty");
					ArrayList<Book> cart = (ArrayList<Book>) session.getAttribute("cart");
					
					if(cart == null || cartQty == null || cart.isEmpty() || cartQty.isEmpty()) {
						throw new InvalidErrorException();
					}
					
					double total = 0;
					ArrayList<OrderItem> orderitems = new ArrayList<OrderItem>();
					for(int i = 0; i < cart.size(); i++) {
						double price = cart.get(i).getPrice() * cartQty.get(i);
						total += price;
						OrderItem item = new OrderItem();
						item.setIsbnno(cart.get(i).getISBNNo());
						item.setAmount(price);
						item.setQty(cartQty.get(i));
						item.setStatus("pending");
						orderitems.add(item);
					}
					order.setOrderitems(orderitems);
					order.setAmount(total);
					order.setGst(8);
					order.setTotalamount((total * 0.08) + total);
					order.setOrderstatus("pending");
				}
				catch(InvalidErrorException e) {
					System.out.println("..... Invalid payment in MakeOrder servlet .....");
					condition = false;
					status = Status.invalidData;
				}
				catch(Exception e) {
					e.printStackTrace();
					System.out.println("..... Invalid payment in MakeOrder servlet .....");
					condition = false;
					status = Status.invalidData;
				}
				
				if(condition) {
					
					Client client = ClientBuilder.newClient();
					WebTarget target = client.target(URL.baseURL).path("makeOrder");
					Invocation.Builder invocationBuilder = target.request();
					invocationBuilder.header(HttpHeaders.AUTHORIZATION, "Bearer " + token);
					
					ObjectMapper obj = new ObjectMapper();
					String json = obj.writeValueAsString(order);
					Response resp = invocationBuilder.post(Entity.json(json));
		
					if (resp.getStatus() == Response.Status.OK.getStatusCode()) {
						Integer row = resp.readEntity(Integer.class);
						if (row == 1) {
							status = Status.insertSuccess;
							session.removeAttribute("cart");
							session.removeAttribute("cart-qty");
						} else if (row == -1) {
							System.out.println("..... Payment failure in MakeOrder servlet .....");
							status = Status.fail;
						} else {
							System.out.println("..... Invalid order data in MakeOrder servlet .....");
							status = Status.invalidData;
						}
					} 
					else if(resp.getStatus() == Response.Status.FORBIDDEN.getStatusCode()) {
						status = Status.unauthorized;
						url = URL.signOut;
					}
					else {
						System.out.println("..... Error in MakeOrder servlet .....");
						status = Status.serverError;
					}
				}
			}
		}
		else {
			url = URL.signOut;
			status = Status.unauthorized;
		}
		
		request.setAttribute("status", status);
		request.getRequestDispatcher(url).forward(request, response);
		return;
	}
}

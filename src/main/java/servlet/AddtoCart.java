package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import model.Book;
import model.Status;
import model.URL;

/**
 * Servlet implementation class AddtoCart
 */
@WebServlet("/AddtoCart/*")
public class AddtoCart extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AddtoCart() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		String status = "";
		String url = URL.signOut;
		boolean condition = true;
		String isbn = "";
		String qty = "";
		int intQty = 0;
		String json = "";
		
		if(session != null && !session.isNew()) {
			String token = (String) session.getAttribute("token");
			
			if(token == null || token.isEmpty()) {
				status = Status.unauthorized;
			}
			else {
				try {
					String requestURi = (String) request.getRequestURI();
					String[] parts = requestURi.split("/");
					isbn = parts[parts.length - 2];
					qty = parts[parts.length - 1];
					isbn = isbn.trim();
					intQty = Integer.parseInt(qty.trim());
				}
				catch(Exception e) {
					e.printStackTrace();
					condition = false;
					json = "{\"status\": \"" + Status.invalidData + "\"}";
					System.out.println("..... Invalid request in AddtoCart servlet .....");
				}
				
				if(condition) {
					
					Client client = ClientBuilder.newClient();
					WebTarget target = client.target(URL.baseURL).path("getBook").path("details").path("{isbn}").resolveTemplate("isbn", isbn);
					Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
					Response resp = invocationBuilder.get();
					
					if(resp.getStatus() == Response.Status.OK.getStatusCode()) {
						
						json = resp.readEntity(String.class);
						ObjectMapper obj = new ObjectMapper();
						Book book = obj.readValue(json, new TypeReference<Book>() {});
						
						if(book != null) {			
							ArrayList<Book> cart = (ArrayList<Book>) session.getAttribute("cart");
							ArrayList<Integer> cartQty = (ArrayList<Integer>) session.getAttribute("cart-qty");
							
							if(cart != null && cartQty != null) {
								if(cart.size() != cartQty.size() ) {
									json = "{\"status\": \"" + Status.invalidData + "\"}";
									session.removeAttribute("cart");
									session.removeAttribute("cart-qty");
								}
								else {
									int index = -1;
									for(int i = 0; i < cart.size(); i++) {
										if(cart.get(i).getISBNNo().equals(book.getISBNNo())) {
											index = i;
											break;
										}
									}
									if(index == -1) {
										if(book.getQty() >= intQty) {
											cartQty.add(intQty);
											cart.add(book);
											session.setAttribute("cart-qty", cartQty);
											session.setAttribute("cart", cart);
											json = "{\"status\": \"" + Status.ok + "\"}";
										}
										else {
											json = "{\"status\": \"" + Status.maxProduct + "\"}";
										}
									}
									else {
										int totalQty = intQty + cartQty.get(index);
										if(book.getQty() >= totalQty) {
											if(totalQty <= 0) {
												cart.remove(index);
												cartQty.remove(index);
											}
											else {
												cartQty.set(index, totalQty);
											}
											
											session.setAttribute("cart-qty", cartQty);
											session.setAttribute("cart", cart);
											json = "{\"status\": \"" + Status.ok + "\"}";
										}
										else {
											json = "{\"status\": \"" + Status.maxProduct + "\"}";
										}
									}
								}
							}
							else {
								cart = new ArrayList<Book>();
								cartQty = new ArrayList<Integer>();
								if(book.getQty() >= intQty) {
									cartQty.add(intQty);
									cart.add(book);
									session.setAttribute("cart-qty", cartQty);
									session.setAttribute("cart", cart);
									json = "{\"status\": \"" + Status.ok + "\"}";
								}
								else {
									json = "{\"status\": \"" + Status.maxProduct + "\"}";
								}
							}
						}
						else {
							System.out.println("..... No book in AddtoCart servlet .....");
							json = "{\"status\": \"" + Status.invalidData + "\"}";
						}
					}
					else {
						System.out.println("..... Error in AddtoCart servlet .....");
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

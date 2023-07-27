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
 * Servlet implementation class RemovefromCart
 */
@WebServlet("/RemovefromCart/*")
public class RemovefromCart extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public RemovefromCart() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		String status = "";
		String url = URL.cart;
		String isbn = "";
		
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
					isbn = isbn.trim();
					ArrayList<Book> cart = (ArrayList<Book>) session.getAttribute("cart");
					if(cart != null) {
						for(int i = 0; i < cart.size(); i++) {
							if(cart.get(i).getISBNNo().equals(isbn)) {
								cart.remove(i);
								break;
							}
						}
						if(cart.size() == 0) {
							session.removeAttribute("cart");
						}
						else {
							session.setAttribute("cart", cart);
						}
					}
					status = Status.deleteSuccess;
				}
				catch(Exception e) {
					e.printStackTrace();
					status = Status.invalidData;
					System.out.println("..... Invalid request in RemovefromCart servlet .....");
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

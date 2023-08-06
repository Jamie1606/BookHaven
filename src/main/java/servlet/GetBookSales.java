// Author		: Thu HtetSan
// Admin No		: 2235035
// Class		: DIT/FT/2A/02
// Group		: 10
// Date			: 5.8.2023
// Description	: get book sales

package servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

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
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import model.Order;
import model.Status;
import model.URL;

/**
 * Servlet implementation class GetBookSales
 */
@WebServlet("/GetBookSales")
public class GetBookSales extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetBookSales() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		String url = URL.bookSales;
		String status = "";
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		System.out.println(request.getParameter("startDate"));
		System.out.println(endDate);
		if(startDate == null || endDate == null) {
			/*
			 * LocalDate eDate=LocalDate.now(); LocalDate sDate = eDate.minusDays(7);
			 * DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); //
			 * Format the dates startDate = sDate.format(formatter); endDate =
			 * eDate.format(formatter);
			 */
			
			LocalDate currentDate = LocalDate.now();
	        
	        // Define the desired format
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	        
	        // Format the current date using the defined format
	        String formattedDate = currentDate.format(formatter);

//			startDate = (new Date()).toString();
//			endDate = (new Date()).toString();
	        
	        startDate = formattedDate;
	        endDate = formattedDate;
			request.setAttribute("startDate",startDate);
			request.setAttribute("endDate",endDate);
			System.out.println("startDate:"+startDate);
			System.out.println("endDate:"+endDate);
			
		}
		if(session != null && !session.isNew()) {
			String token = (String) session.getAttribute("token");
			
			if(token == null || token.isEmpty()) {
				status = Status.unauthorized;
				url = URL.signOut;
			}
			else {
				Client client = ClientBuilder.newClient();
				WebTarget target = client.target(URL.baseURL).path("getBookByDate").path("{startDate}")
                        .resolveTemplate("startDate", startDate).path("{endDate}")
                        .resolveTemplate("endDate", endDate);
				Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
				invocationBuilder.header(HttpHeaders.AUTHORIZATION, "Bearer " + token);
				Response resp = invocationBuilder.get();
				System.out.println(resp.getStatus() );
				if(resp.getStatus() == Response.Status.OK.getStatusCode()) {	
					
					String json = resp.readEntity(String.class);
					ObjectMapper obj = new ObjectMapper();
					ArrayList<Order> orderList = obj.readValue(json, new TypeReference<ArrayList<Order>>() {});
						/*
						 * for (Order order : orderList) { ArrayList<OrderItem> orderItemList =
						 * order.getOrderitems(); for (OrderItem orderItem : orderItemList) { Book book
						 * = orderItem.getBook(); bookList.add(book); }
						 * //order.setOrderitems(orderItemList); }
						 */
						//request.setAttribute("bookList", bookList);
						request.setAttribute("orderList", orderList);
						status = Status.servletStatus;
				}
				else {
					System.out.println("..... Error in GetBookSales servlet .....");
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

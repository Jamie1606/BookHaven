// Author		: Thu HtetSan
// Admin No		: 2235035
// Class		: DIT/FT/2A/02
// Group		: 10
// Date			: 5.8.2023
// Description	: get least selling book list

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
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import model.Book;
import model.Status;
import model.URL;

/**
 * Servlet implementation class GetLeastSellingBookList
 */
@WebServlet("/GetLeastSellingBookList")
public class GetLeastSellingBookList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetLeastSellingBookList() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		//String url = URL.leastSellingBookList;
		String url="";
		boolean condition = true;
		String status = "";
		String limit = "";

		if (session != null && !session.isNew()) {
			String token = (String) session.getAttribute("token");
			if (token == null || token.isEmpty()) {
				status = Status.unauthorized;
				url = URL.signOut;
			} else {
				try {
					String requestURi = (String) request.getRequestURI();
					String[] parts = requestURi.split("/");
					limit = parts[parts.length - 1];
					limit = limit.trim();
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("..... Invalid limit in GetLeastSellingBookList servlet .....");
					status = Status.invalidRequest;
					condition = false;
				}

				if (condition) {
					Client client = ClientBuilder.newClient();
					WebTarget target = client.target(URL.baseURL).path("getLeastSelling").path("{limit}")
							.resolveTemplate("limit", "10");
					Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
					invocationBuilder.header(HttpHeaders.AUTHORIZATION, "Bearer " + token);
					Response resp = invocationBuilder.get();
					System.out.println("..... Status ....." + resp.getStatus());
					if (resp.getStatus() == Response.Status.OK.getStatusCode()) {

						String json = resp.readEntity(String.class);
						ObjectMapper obj = new ObjectMapper();
						ArrayList<Book> bookList = obj.readValue(json, new TypeReference<ArrayList<Book>>() {
						});

						if (bookList == null || bookList.isEmpty()) {
							System.out.println("..... Server error in GetLeastSellingBookList servlet .....");
							status = Status.serverError;
						} else {
							request.setAttribute("bookList", bookList);
							status = Status.servletStatus;
						}
					} else {
						System.out.println("..... Error in GetLeastSellingBookList servlet .....");
						status = Status.serverError;
					}
				}
			}
		} else {
			status = Status.unauthorized;
			url = URL.signOut;
		}

		request.setAttribute("status", status);
		request.getRequestDispatcher(url).forward(request, response);
		return;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
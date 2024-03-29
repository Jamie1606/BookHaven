// Author		: Thu Htet San
// Admin No		: 2235022
// Class		: DIT/FT/2A/02
// Group		: 10
// Date			: 6.8.2023
// Description	: get top customer list

package servlet;

import java.io.IOException;
import java.util.ArrayList;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import model.Member;
import model.Status;
import model.URL;

/**
 * Servlet implementation class GetTopCustomerList
 */
@WebServlet("/GetTopCustomerList")
public class GetTopCustomerList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetTopCustomerList() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		String url = URL.topCustomerList;
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
					System.out.println("..... Invalid limit in GetTopCustomerList servlet .....");
					status = Status.invalidRequest;
					condition = false;
				}

				if (condition) {
					Client client = ClientBuilder.newClient();
					WebTarget target = client.target(URL.baseURL).path("getTopCustomers").path("{limit}")
							.resolveTemplate("limit", "10");
					Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
					invocationBuilder.header(HttpHeaders.AUTHORIZATION, "Bearer " + token);
					Response resp = invocationBuilder.get();
					System.out.println("..... Status ....." + resp.getStatus());
					if (resp.getStatus() == Response.Status.OK.getStatusCode()) {

						String json = resp.readEntity(String.class);
						ObjectMapper obj = new ObjectMapper();
						ArrayList<Member> memberList = obj.readValue(json, new TypeReference<ArrayList<Member>>() {
						});

						if (memberList == null || memberList.isEmpty()) {
							System.out.println("..... Server error in GetTopCustomerList servlet .....");
							status = Status.serverError;
						} else {
							request.setAttribute("memberList", memberList);
							status = Status.servletStatus;
						}
					} else {
						System.out.println("..... Error in GetTopCustomerList servlet .....");
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
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

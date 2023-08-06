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
import model.Member;
import model.Status;
import model.URL;

/**
 * Servlet implementation class GetCustomerListByBookID
 */
@WebServlet("/GetCustomerListByBookID/*")
public class GetCustomerListByBookID extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetCustomerListByBookID() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		String url = URL.getBookListServlet + "?report=customer";
		boolean condition = true;
		String status = "";
		String isbn = "";

		if (session != null && !session.isNew()) {
			String token = (String) session.getAttribute("token");
			if (token == null || token.isEmpty()) {
				status = Status.unauthorized;
				url = URL.signOut;
			} else {
				try {
					String requestURi = (String) request.getRequestURI();
					String[] parts = requestURi.split("/");
					isbn = parts[parts.length - 1];
					isbn = isbn.trim();
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("..... Invalid isbn in GetCustomerListByBookID servlet .....");
					status = Status.invalidRequest;
					condition = false;
				}

				if (condition) {
					Client client = ClientBuilder.newClient();
					WebTarget target = client.target(URL.baseURL).path("getMemberListByISBN").path("{isbn}")
							.resolveTemplate("isbn", isbn);
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
							System.out.println("..... Server error in GetCustomerListByBookID servlet .....");
							status = Status.serverError;
						} else {
							request.setAttribute("memberList", memberList);
							status = Status.servletStatus;
						}
					} else {
						System.out.println("..... Error in GetCustomerListByBookID servlet .....");
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

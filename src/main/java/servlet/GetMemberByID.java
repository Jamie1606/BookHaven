// Author		: Thu Htet San
// Admin No		: 2235022
// Class		: DIT/FT/2A/02
// Group		: 10
// Date			: 4.8.2023
// Description	: get member by ID

package servlet;

import java.io.IOException;
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

import model.Member;
import model.Status;
import model.URL;

/**
 * Servlet implementation class GetMemberByID
 */
@WebServlet("/GetMemberByID/*")
public class GetMemberByID extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public GetMemberByID() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		String url = URL.memberList;
		boolean condition = true;
		String status = "";
		String id = "";
		
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
					id = parts[parts.length - 1];
					id = id.trim();
					Integer.parseInt(id);
				}
				catch(Exception e) {
					e.printStackTrace();
					status = Status.invalidRequest;
					condition = false;
					System.out.println("..... Invalid data request in GetMemberByID servlet .....");
				}
				
				if(condition) {
					
					Client client = ClientBuilder.newClient();
					WebTarget target = client.target(URL.baseURL).path("getMember").path("{id}").resolveTemplate("id", id);
					Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
					invocationBuilder.header(HttpHeaders.AUTHORIZATION, "Bearer " + token);
					Response resp = invocationBuilder.get();
					
					if(resp.getStatus() == Response.Status.OK.getStatusCode()) {
						
						String json = resp.readEntity(String.class);
						ObjectMapper obj = new ObjectMapper();
						Member member = obj.readValue(json, new TypeReference<Member>() {});
						
						if(member != null) {				
							request.setAttribute("member", member);
							request.setAttribute("update", "true");
							url = URL.memberRegistration;
						}
						else {
							System.out.println("..... No member in GetMemberByID servlet");
							status = Status.invalidData;
						}
					}
					else if(resp.getStatus() == Response.Status.BAD_REQUEST.getStatusCode()) {
						status = Status.invalidData;
					}
					else if(resp.getStatus() == Response.Status.FORBIDDEN.getStatusCode()) {
						status = Status.unauthorized;
						url = URL.signOut;
					}
					else {
						System.out.println("..... Error in GetMemberByID servlet .....");
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

// Author		: Zay Yar Tun
// Admin No		: 2235035
// Class		: DIT/FT/2A/02
// Group		: 10
// Date			: 29.7.2023
// Description	: sign in servlet

package servlet;

import java.io.IOException;
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
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import model.Status;
import model.URL;
import model.UserCredentials;

/**
 * Servlet implementation class Signin
 */
@WebServlet("/Signin")
public class Signin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Signin() {
        super();
    }


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String status = "";
		boolean condition = true;
		String url = URL.signIn;
		UserCredentials userCredentials = new UserCredentials();
		String token = null;
		
		if(session != null) {
			token = (String) session.getAttribute("token");
		}
		
		if(token == null || token.isEmpty()) {
			try {
				userCredentials.setEmail(email.trim());
				userCredentials.setPassword(password.trim());
			}
			catch(Exception e) {
				e.printStackTrace();
				status = Status.invalidData;
				condition = false;
				System.out.println("..... Invalid email and password in SignIn servlet .....");
			}
			
			if(condition) {
				
				Client client = ClientBuilder.newClient();
				WebTarget target = client.target(URL.baseURL).path("userlogin");
				Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
				ObjectMapper obj = new ObjectMapper();
				String json = obj.writeValueAsString(userCredentials);
				Response resp = invocationBuilder.post(Entity.json(json));
				
				if(resp.getStatus() == Response.Status.OK.getStatusCode()) {
					
					json = resp.readEntity(String.class);
					userCredentials = obj.readValue(json, new TypeReference<UserCredentials>() {});
					
					if(userCredentials == null) {
						status = Status.invalidData;
					}
					else {
						session.setAttribute("role", userCredentials.getRole());
						session.setAttribute("token", userCredentials.getToken());
						session.setMaxInactiveInterval(2 * 60 * 60);
						
						if(userCredentials.getRole().equals("ROLE_ADMIN")) {
							response.sendRedirect(request.getContextPath() + URL.adminHomePage);
							return;
						}
						else if(userCredentials.getRole().equals("ROLE_MEMBER")) {
							response.sendRedirect(request.getContextPath() + URL.homePage);
							return;
						}
						else {
							status = Status.unauthorized;
							url = URL.signOut;
						}
					}
				}
				else {
					status = Status.serverError;
					condition = false;
					System.out.println("..... Error in SigninServlet .....");
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

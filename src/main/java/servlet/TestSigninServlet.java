// Author		: Zay Yar Tun
// Admin No		: 2235035
// Class		: DIT/FT/2A/02
// Group		: 10
// Date			: 11.7.2023
// Description	: this is to store admin data from database

package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import model.Admin;
import model.Member;
import model.URL;
import model.UserCredentials;

/**
 * Servlet implementation class TestSigninServlet
 */
@WebServlet("/TestSigninServlet")
public class TestSigninServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public TestSigninServlet() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String status = "";
		boolean condition = true;
		String url = URL.signIn;
		HttpSession session = request.getSession();
		UserCredentials userCredentials = new UserCredentials();
		
		try {
			userCredentials.setEmail(email.trim());
			userCredentials.setPassword(password.trim());
		}
		catch(Exception e) {
			e.printStackTrace();
			status = "invalid";
			condition = false;
			System.out.println("..... Invalid email and password in SignIn servlet .....");
		}
		
		if(condition) {
			Client client = ClientBuilder.newClient();
			WebTarget target = client.target(URL.baseURL).path("userlogin");
			Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
			Response resp = invocationBuilder.post(Entity.json(userCredentials));
			
			if(resp.getStatus() == Response.Status.OK.getStatusCode()) {
				
				userCredentials = resp.readEntity(new GenericType<UserCredentials>() {});
				if(userCredentials == null) {
					status = "invalid";
				}
				else {
					condition = false;
					session.setAttribute("email", userCredentials.getEmail());
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
						session.invalidate();
						status = "invalid";
					}
				}
			}
			else {
				status = "fail";
				condition = false;
				System.out.println("..... Error in TestSigninServlet .....");
			}
		}
		
		request.setAttribute("status", status);
		request.getRequestDispatcher(url).forward(request, response);
		return;
	}
}

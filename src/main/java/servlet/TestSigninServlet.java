// Author		: Zay Yar Tun
// Admin No		: 2235035
// Class		: DIT/FT/2A/02
// Group		: 10
// Date			: 11.7.2023
// Description	: this is to store admin data from database

package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import model.Admin;
import model.URL;

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
		
		if(email != null && password != null) {
			
		}
		else {
			
		}
		Admin admin = new Admin();
		admin.setEmail(email.trim());
		admin.setPassword(password.trim());
		
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(URL.baseURL).path("loginAdmin");
		Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
		Response resp = invocationBuilder.post(Entity.json(admin));
		
		String url = "/signout.jsp";
		if(resp.getStatus() == Response.Status.OK.getStatusCode()) {
			
			admin = resp.readEntity(new GenericType<Admin>() {});
			if(admin == null) {
				request.setAttribute("status", "invalid");
				url = "/signin.jsp";
			}
			else {
				HttpSession session = request.getSession();
				session.setAttribute("id", admin.getAdminID() + "");
				session.setAttribute("role", "admin");
				session.setMaxInactiveInterval(3* 60 * 60);
				url = request.getContextPath() + "/admin/adminHomePage.jsp";
				response.sendRedirect(url);
				return;
			}
		}
		else {
			System.out.println("..... Error in TestSigninServlet .....");
			url = "/signin.jsp";
			request.setAttribute("status", "fail");
		}
		
		RequestDispatcher rd = request.getRequestDispatcher(url);
		rd.forward(request, response);
		return;
	}
}

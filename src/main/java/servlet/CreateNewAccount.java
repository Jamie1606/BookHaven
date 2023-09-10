package servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Response;
import model.InvalidErrorException;
import model.Member;
import model.Status;
import model.URL;

/**
 * Servlet implementation class CreateNewAccount
 */
@WebServlet("/CreateNewAccount")
public class CreateNewAccount extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public CreateNewAccount() {
        super();

    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		boolean condition = true;
		String status = "";
		String url = URL.signUp;
					
		Member member = null;
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String address = request.getParameter("address");
		String postalCode = request.getParameter("postalCode");
		String phone = request.getParameter("phone");
		String image = URL.defaultMemberImage;
		
		try {
			
			member = new Member();
			member.setName(name.trim());
			member.setEmail(email.trim());
			member.setPassword(password.trim());
			
			if(postalCode.length() > 6 || postalCode.length() < 6) {
				throw new InvalidErrorException();
			}
			
			if(address.trim().length() <= 0) {
				throw new InvalidErrorException();
			}
			
			address = address.trim() + " |" + postalCode.trim();
			member.setAddress(address);
			
			if(phone.length() > 8 || phone.length() < 8) {
				throw new InvalidErrorException();
			}
			member.setPhone(phone.trim());
			member.setGender('N');
			
			member.setImage(image.trim());
		}
		catch(InvalidErrorException e) {
			status = Status.invalidData;
			System.out.println("..... Invalid member data in CreateNewAccount servlet .....");
			condition = false;
		}
		catch(Exception e) {
			e.printStackTrace();
			status = Status.invalidData;
			System.out.println("..... Invalid member data in CreateNewAccount servlet .....");
			condition = false;
		}
		
		if(condition) {			
			
			Client client = ClientBuilder.newClient();
			WebTarget target = client.target(URL.baseURL).path("createMember");
			Invocation.Builder invocationBuilder = target.request();
			ObjectMapper obj = new ObjectMapper();
			String json = obj.writeValueAsString(member);
			Response resp = invocationBuilder.post(Entity.json(json));

			if (resp.getStatus() == Response.Status.OK.getStatusCode()) {
				
				Integer row = resp.readEntity(Integer.class);
				
				if (row == 1) {
					status = Status.insertSuccess;
				}
				else if(row == 0) {
					status = Status.invalidData;
				}
				else {
					System.out.println("..... Duplicate email in CreateNewAccount servlet .....");
					status = Status.duplicateEmail;
				}
			}
			else {
				System.out.println("..... Error in CreateNewAccount servlet .....");
				status = Status.serverError;
			}
		}
		
		request.setAttribute("status", status);
		request.getRequestDispatcher(url).forward(request, response);
        return;
	}

}

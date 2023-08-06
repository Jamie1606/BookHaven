package servlet;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
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
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import model.Functions;
import model.InvalidErrorException;
import model.Member;
import model.Status;
import model.URL;

/**
 * Servlet implementation class CreateMember
 */
@WebServlet("/CreateMember")
@MultipartConfig(location = "", maxFileSize = 20971520, maxRequestSize = 41943040, fileSizeThreshold = 1048576)
public class CreateMember extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public CreateMember() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		boolean condition = true;
		String status = "";
		String url = URL.memberRegistration;
		
		if(session != null && !session.isNew()) {
			String token = (String) session.getAttribute("token");
			
			if(token == null || token.isEmpty()) {
				status = Status.unauthorized;
				url = URL.signOut;
			}
			else {				
				Member member = null;
				String name = request.getParameter("name");
				String email = request.getParameter("email");
				String password = request.getParameter("password");
				String address = request.getParameter("address");
				String postalCode = request.getParameter("postalCode");
				String phone = request.getParameter("phone");
				String birthDate = request.getParameter("birthDate");
				String gender = request.getParameter("gender");
				String image = "";
				
				try {
						
					image = Functions.uploadImage(name, LocalDate.now().toString(), "member", request.getPart("image"), token);
					if(image == null) {
						image = URL.defaultMemberImage;
					}
					
					member = new Member();
					member.setName(name.trim());
					member.setEmail(email.trim());
					member.setPassword(password.trim());
					
					address = address.trim() + " |" + postalCode.trim();
					member.setAddress(address);
					
					if(phone.length() > 8) {
						throw new InvalidErrorException();
					}
					member.setPhone(phone.trim());
					
					if(birthDate != null && !birthDate.isEmpty()) {
						Date tmpBirthDate = Date.valueOf(LocalDate.parse(birthDate));
						LocalDate testBirthDate = tmpBirthDate.toLocalDate();
						long diff = ChronoUnit.DAYS.between(testBirthDate, LocalDate.now());
						if(diff < 0) {
							throw new InvalidErrorException();
						}
						
						member.setBirthDate(tmpBirthDate);
					}
					else {
						member.setBirthDate(null);
					}
						
					
					if(gender.equals("M")) {
						member.setGender('M');
					}
					else if(gender.equals("F")) {
						member.setGender('F');
					}
					else {
						member.setGender('N');
					}
					
					member.setImage(image.trim());
				}
				catch(InvalidErrorException e) {
					status = Status.invalidData;
					System.out.println("..... Invalid member data in CreateMember servlet .....");
					condition = false;
				}
				catch(Exception e) {
					e.printStackTrace();
					status = Status.invalidData;
					System.out.println("..... Invalid member data in CreateMember servlet .....");
					condition = false;
				}
				
				if(condition) {			
					
					Client client = ClientBuilder.newClient();
					WebTarget target = client.target(URL.baseURL).path("createMember");
					Invocation.Builder invocationBuilder = target.request();
					invocationBuilder.header(HttpHeaders.AUTHORIZATION, "Bearer " + token);
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
							status = Status.duplicateEmail;
						}
					}
					else if(resp.getStatus() == Response.Status.FORBIDDEN.getStatusCode()) {
						status = Status.unauthorized;
						url = URL.signOut;
					}
					else {
						System.out.println("..... Error in CreateMember servlet .....");
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

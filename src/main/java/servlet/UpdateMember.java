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
import model.Member;
import model.Status;
import model.URL;

/**
 * Servlet implementation class UpdateMember
 */
@WebServlet("/UpdateMember")
@MultipartConfig(location = "", maxFileSize = 20971520, maxRequestSize = 41943040, fileSizeThreshold = 1048576)
public class UpdateMember extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public UpdateMember() {
        super();
    }


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		boolean condition = true;
		String status = "";
		String url = URL.memberList;
		
		if(session != null && !session.isNew()) {
			String token = (String) session.getAttribute("token");
			
			if(token == null || token.isEmpty()) {
				status = Status.unauthorized;
				url = URL.signOut;
			}
			else {				
				Member member = null;
				String memberid = request.getParameter("MemberID");
				String name = request.getParameter("name");
				String email = request.getParameter("email");
				String address = request.getParameter("address");
				String postalCode = request.getParameter("postalCode");
				String phone = request.getParameter("phone");
				String birthDate = request.getParameter("birthDate");
				String gender = request.getParameter("gender");
				String image = "";
				String oldimage = request.getParameter("oldimage");
				
				try {
						
					if(oldimage != null && !oldimage.isEmpty() && !oldimage.equals(URL.defaultMemberImage)) {
						if(!Functions.deleteImage(oldimage.trim())) {
							System.out.println("..... Error in deleting old image in UpdateMember servlet .....");
						}
					}
					image = Functions.uploadImage(name, LocalDate.now().toString() + "_" + memberid, "member", request.getPart("image"), token);
					if(image == null) {
						if(oldimage == null || oldimage.isEmpty()) {
							image = URL.defaultMemberImage;
						}
						else {
							image = oldimage.trim();
						}
					}
					
					member = new Member();
					member.setMemberID(Integer.parseInt(memberid));
					member.setName(name.trim());
					member.setEmail(email.trim());
					
					address = address.trim() + " |" + postalCode.trim();
					member.setAddress(address);
					member.setPhone(phone.trim());
					
					if(birthDate != null && !birthDate.isEmpty()) {
						Date tmpBirthDate = Date.valueOf(LocalDate.parse(birthDate));
						LocalDate testBirthDate = tmpBirthDate.toLocalDate();
						long diff = ChronoUnit.DAYS.between(testBirthDate, LocalDate.now());
						if(diff < 0) {
							throw new Error();
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
				catch(Exception e) {
					e.printStackTrace();
					status = Status.invalidData;
					System.out.println("..... Invalid member data in UpdateMember servlet .....");
					condition = false;
				}
				
				if(condition) {			
					
					Client client = ClientBuilder.newClient();
					WebTarget target = client.target(URL.baseURL).path("updateMember");
					Invocation.Builder invocationBuilder = target.request();
					invocationBuilder.header(HttpHeaders.AUTHORIZATION, "Bearer " + token);
					ObjectMapper obj = new ObjectMapper();
					String json = obj.writeValueAsString(member);
					Response resp = invocationBuilder.put(Entity.json(json));
		
					if (resp.getStatus() == Response.Status.OK.getStatusCode()) {
						
						Integer row = resp.readEntity(Integer.class);
						
						if (row == 1) {
							status = Status.updateSuccess;
						}  else {
							System.out.println("..... Invalid member id in UpdateMember servlet .....");
							status = Status.invalidData;
						}
					}
					else if(resp.getStatus() == Response.Status.FORBIDDEN.getStatusCode()) {
						status = Status.unauthorized;
						url = URL.signOut;
					}
					else {
						System.out.println("..... Error in UpdateMember servlet .....");
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

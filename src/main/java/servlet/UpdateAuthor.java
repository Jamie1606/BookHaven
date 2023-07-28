// Author		: Zay Yar Tun
// Admin No		: 2235035
// Class		: DIT/FT/2A/02
// Group		: 10
// Date			: 29.7.2023
// Description	: update author

package servlet;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

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
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;

import model.URL;
import model.Author;
import model.Status;

/**
 * Servlet implementation class UpdateAuthor
 */
@WebServlet("/UpdateAuthor")
public class UpdateAuthor extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public UpdateAuthor() {
        super();
    }


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		String url = URL.authorList;
		boolean condition = true;
		String status = "";
		Author author = new Author();
		
		if(session != null && !session.isNew()) {
			String token = (String) session.getAttribute("token");
			
			if(token == null || token.isEmpty()) {
				status = Status.unauthorized;
				url = URL.signOut;
			}
			else {
				String id = request.getParameter("id");
				String name = request.getParameter("name");
				String nationality = request.getParameter("nationality");
				String birthDate = request.getParameter("birthdate");
				String biography = request.getParameter("biography");
				String link = request.getParameter("link");
				
				try {
					author.setAuthorID(Integer.parseInt(id));
					author.setName(name.trim());
					
					if(birthDate != null && !birthDate.isEmpty()) {
						Date tmpBirthDate = Date.valueOf(LocalDate.parse(birthDate));
						LocalDate testBirthDate = tmpBirthDate.toLocalDate();
						long diff = ChronoUnit.DAYS.between(testBirthDate, LocalDate.now()) / 365;
						if(diff < 5) {
							throw new Error();
						}
						else {
							author.setBirthDate(tmpBirthDate);
						}
					}
					else {
						author.setBirthDate(null);
					}
					
					if(nationality != null && !nationality.isEmpty()) {
						author.setNationality(nationality.trim());
					}
					
					if(biography != null && !biography.isEmpty()) {
						author.setBiography(biography.trim());
					}
					
					if(link != null && !link.isEmpty()) {
						author.setLink(link.trim());
					}
				}
				catch(Exception e) {
					e.printStackTrace();
					System.out.println(".... Invalid id in UpdateAuthor servlet .....");
					status = Status.invalidData;
					condition = false;
				}
				
				if(condition) {
					Client client = ClientBuilder.newClient();
					WebTarget target = client.target(URL.baseURL).path("updateAuthor");
					Invocation.Builder invocationBuilder = target.request();
					invocationBuilder.header(HttpHeaders.AUTHORIZATION, "Bearer " + token);
					
					ObjectMapper obj = new ObjectMapper();
					String json = obj.writeValueAsString(author);
					Response resp = invocationBuilder.put(Entity.json(json));
					
					if(resp.getStatus() == Response.Status.OK.getStatusCode()) {
						
						Integer row = resp.readEntity(Integer.class);
						
						if(row == 1) {
							status = Status.updateSuccess;
						}
						else {
							System.out.println("..... Author not updated in UpdateAuthor servlet .....");
							status = Status.invalidData;
						}
					}
					else if(resp.getStatus() == Response.Status.FORBIDDEN.getStatusCode()) {
						status = Status.unauthorized;
						url = URL.signOut;
					}
					else {
						System.out.println("..... Error in UpdateAuthor servlet .....");
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

// Author		: Zay Yar Tun
// Admin No		: 2235035
// Class		: DIT/FT/2A/02
// Group		: 10
// Date			: 11.7.2023
// Description	: this is to store admin data from database

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

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Response;

import model.Author;
import model.URL;

/**
 * Servlet implementation class CreateAuthor
 */
@WebServlet("/CreateAuthor")
public class CreateAuthor extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public CreateAuthor() {
        super();
    }


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String status = "";
		String url = URL.authorRegistration;
		boolean condition = true;
		Author author = new Author();
		
		
		String name = request.getParameter("name");
		String nationality = request.getParameter("nationality");
		String birthDate = request.getParameter("birthdate");
		String biography = request.getParameter("biography");
		String link = request.getParameter("link");
		
		try {
			author.setName(name.trim());
			
			if(birthDate != null) {
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
			condition = false;
			status = "invalid";
			System.out.println("..... Invalid author data in CreateAuthor servlet .....");
		}
		
		
		if(condition) {
			Client client = ClientBuilder.newClient();
			WebTarget target = client.target(URL.baseURL).path("createAuthor");
			Invocation.Builder invocationBuilder = target.request();
			Response resp = invocationBuilder.post(Entity.json(author));
			
			if(resp.getStatus() == Response.Status.OK.getStatusCode()) {
				Integer row = resp.readEntity(Integer.class);
				if(row == 1) {
					status = "insertsuccess";
				}
				else {
					System.out.println("..... Invalid author data in CreateAuthor servlet .....");
					status = "invalid";
				}
			}
			else {
				System.out.println("..... Error in CreateAuthor servlet .....");
				status = "servererror";
			}
		}
		
		request.setAttribute("status", status);
		request.getRequestDispatcher(url).forward(request, response);
		return;
	}

}

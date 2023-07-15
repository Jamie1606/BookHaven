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

import controller.TestReg;
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
		String name = request.getParameter("name");
		String nationality = request.getParameter("nationality");
		String birthDate = request.getParameter("birthdate");
		String biography = request.getParameter("biography");
		String link = request.getParameter("link");
		
		String url = URL.authorRegistration;
		boolean condition = true;
		Author author = new Author();
		
		if(name == null || name.isEmpty()) {
			System.out.println("..... Invalid author data in CreateAuthor servlet .....");
			request.setAttribute("status", "invalid");
			condition = false;
		}
		else {
			author.setName(name.trim());
		}
		
		if(birthDate != null && !birthDate.isEmpty() && TestReg.matchDate(birthDate)) {
			Date tmpBirthDate = Date.valueOf(LocalDate.parse(birthDate));
			LocalDate testBirthDate = tmpBirthDate.toLocalDate();
			long diff = ChronoUnit.DAYS.between(testBirthDate, LocalDate.now()) / 365;
			if(diff < 5) {
				System.out.println("..... Invalid author data in CreateAuthor servlet .....");
				request.setAttribute("status", "invalid");
				condition = false;
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
		
		if(condition) {
			Client client = ClientBuilder.newClient();
			WebTarget target = client.target(URL.baseURL).path("createAuthor");
			Invocation.Builder invocationBuilder = target.request();
			Response resp = invocationBuilder.post(Entity.json(author));
			
			if(resp.getStatus() == Response.Status.OK.getStatusCode()) {
				Integer row = resp.readEntity(Integer.class);
				if(row == 1) {
					request.setAttribute("status", "success");
				}
				else {
					System.out.println("..... Invalid author data in CreateAuthor servlet .....");
					request.setAttribute("status", "invalid");
				}
			}
			else {
				System.out.println("..... Error in CreateAuthor servlet .....");
				request.setAttribute("status", "servererror");
			}
		}
		
		request.getRequestDispatcher(url).forward(request, response);
		return;
	}

}

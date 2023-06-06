// Author: Zay Yar Tun
// Admin No: 2235035
// Date: 5.6.2023
// Description: middleware for author

package controller;

import org.apache.commons.text.StringEscapeUtils;
import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Author;
import model.AuthorDatabase;

/**
 * Servlet implementation class AuthorServlet
 */
@WebServlet("/admin/authors")
public class AuthorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AuthorServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		ArrayList<Author> authorlist = new ArrayList<Author>();
		AuthorDatabase author_db = new AuthorDatabase();

		boolean condition = author_db.getAuthor();
		if (condition) {
			ResultSet rs = author_db.getAuthorResult();
			try {
				while (rs.next()) {
					// sanitizing output by escaping html special characters
					authorlist.add(new Author(rs.getInt("AuthorID"), StringEscapeUtils.escapeHtml4(rs.getString("Name")), StringEscapeUtils.escapeHtml4(rs.getString("Nationality")), rs.getDate("BirthDate"), StringEscapeUtils.escapeHtml4(rs.getString("Biography")), StringEscapeUtils.escapeHtml4(rs.getString("Link"))));
				}
			}
			catch(Exception e) {
				request.setAttribute("error", "serverError");
			}
		} else {
			request.setAttribute("error", "serverError");
		}

		// set the author arraylist as an attribute
		request.setAttribute("authorList", authorlist);

		// forward the data to the jsp
		request.getRequestDispatcher("authorList.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		// values passed from author registration form
		String status = request.getParameter("status");
		String name, nationality, birthdate, biography, link;

		if (status.equalsIgnoreCase("register")) {
			name = request.getParameter("name");
			if (name != null && !name.isBlank()) {
				nationality = request.getParameter("nationality");
				birthdate = request.getParameter("birthdate");
				biography = request.getParameter("biography");
				link = request.getParameter("link");
				LocalDate date = null;

				if (birthdate != null && !birthdate.isEmpty()) {
					date = LocalDate.parse(birthdate);
				}
				
				Date birth_Date = Date.valueOf(date);
				
				AuthorDatabase author_db = new AuthorDatabase();
				if(author_db.registerAuthor(new Author(name, nationality, birth_Date, biography, link))) {
					response.sendRedirect("authorRegistration.jsp?success=true");
				}
				else {
					response.sendRedirect("authorRegistration.jsp?errCode=serverError");
				}				
			} else {
				response.sendRedirect("authorRegistration.jsp?errCode=invalid");
			}
		} else {
			response.sendRedirect("authorRegistration.jsp?errCode=invalid");
		}
	}
}

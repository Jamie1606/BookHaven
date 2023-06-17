// Author: Zay Yar Tun
// Admin No: 2235035
// Date: 8.6.2023
// Description: middleware for author

package controller;

import org.apache.commons.text.StringEscapeUtils;
import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Author;
import model.AuthorDatabase;
import model.BookDatabase;

/**
 * Servlet implementation class AuthorServlet
 */
@WebServlet(urlPatterns = { "/admin/authors", "/admin/authorUpdate/*", "/admin/authorDelete/*" })
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

		// create author_db object and clear the result
		AuthorDatabase author_db = new AuthorDatabase();
		author_db.clearAuthorResult();
		HttpSession session = request.getSession();
		Authentication auth = new Authentication();

		if (!auth.testAdmin(session)) {
			request.setAttribute("error", "unauthorized");
			request.getRequestDispatcher("/signout.jsp").forward(request, response);
			return;
		}

		String requestURi = request.getRequestURI();
		if (requestURi.contains("admin/authorUpdate")) {
			String[] parts = requestURi.split("/");
			if (parts.length == 0) {
				request.setAttribute("error", "invalid");
				request.getRequestDispatcher("/admin/authors").forward(request, response);
				return;
			} else {
				String id = parts[parts.length - 1];
				if (TestReg.matchInteger(id)) {
					if (author_db.getAuthorByID(Integer.parseInt(id))) {
						ResultSet rs = author_db.getAuthorResult();
						Author author_data = null;

						try {
							while (rs.next()) {
								// sanitizing output by escaping html special characters
								author_data = new Author(rs.getInt("AuthorID"),
										StringEscapeUtils.escapeHtml4(rs.getString("Name")),
										StringEscapeUtils.escapeHtml4(rs.getString("Nationality")),
										rs.getDate("BirthDate"),
										StringEscapeUtils.escapeHtml4(rs.getString("Biography")),
										StringEscapeUtils.escapeHtml4(rs.getString("Link")));
								break;
							}
							request.setAttribute("author", author_data);
							request.setAttribute("status", "update");
							request.getRequestDispatcher("/admin/authorRegistration.jsp").forward(request, response);
							return;
						} catch (Exception e) {
							request.setAttribute("error", "serverError");
							request.getRequestDispatcher("/admin/authors").forward(request, response);
							return;
						}
					} else {
						request.setAttribute("error", "invalid");
						request.getRequestDispatcher("/admin/authors").forward(request, response);
						return;
					}
				} else {
					request.setAttribute("error", "invalid");
					request.getRequestDispatcher("/admin/authors").forward(request, response);
					return;
				}
			}
		} else if (requestURi.contains("admin/authorDelete")) {
			doDelete(request, response);
		} else if (requestURi.endsWith("admin/authors")) {

			ArrayList<Author> authorList = new ArrayList<Author>();

			if (author_db.getAuthor()) {
				ResultSet rs = author_db.getAuthorResult();
				try {
					while (rs.next()) {
						// sanitizing output by escaping html special characters
						authorList.add(new Author(rs.getInt("AuthorID"),
								StringEscapeUtils.escapeHtml4(rs.getString("Name")),
								StringEscapeUtils.escapeHtml4(rs.getString("Nationality")), rs.getDate("BirthDate"),
								StringEscapeUtils.escapeHtml4(rs.getString("Biography")),
								StringEscapeUtils.escapeHtml4(rs.getString("Link"))));
					}
				} catch (Exception e) {
					request.setAttribute("error", "serverRetrieveError");
				}
			} else {
				request.setAttribute("error", "serverRetrieveError");
			}

			// set the author arraylist as an attribute
			request.setAttribute("authorList", authorList);
			request.setAttribute("servlet", "true");

			// forward the data to the jsp
			request.getRequestDispatcher("/admin/authorList.jsp").forward(request, response);
			return;
		} else {
			request.setAttribute("error", "unauthorized");
			request.getRequestDispatcher("/signout.jsp").forward(request, response);
			return;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		Authentication auth = new Authentication();
		HttpSession session = request.getSession();

		if (!auth.testAdmin(session)) {
			request.setAttribute("error", "unauthorized");
			request.getRequestDispatcher("/signout.jsp").forward(request, response);
			return;
		}
		
		// values passed from author registration form
		String status = request.getParameter("status");
		String name, nationality, birthdate, biography, link;

		if(status == null) {
			response.sendRedirect("authorRegistration.jsp?errCode=unauthorized");
		}
		if (status.equals("register")) {
			name = request.getParameter("name");
			if (name != null && !name.isBlank()) {
				nationality = request.getParameter("nationality");
				if(nationality != null) {
					nationality = nationality.trim();
				}
				birthdate = request.getParameter("birthdate");
				biography = request.getParameter("biography");
				if(biography != null) {
					biography = biography.trim();
				}
				link = request.getParameter("link");
				if(link != null) {
					link = link.trim();
				}
				Date birth_Date = null;

				if (birthdate != null && !birthdate.isEmpty() && TestReg.matchDate(birthdate)) {
					birth_Date = Date.valueOf(LocalDate.parse(birthdate));
					LocalDate testDate = birth_Date.toLocalDate();
					LocalDate currentDate = LocalDate.now();
					long diff = ChronoUnit.DAYS.between(testDate, currentDate) / 365;
					if(diff < 5) {
						response.sendRedirect("authorRegistration.jsp?errCode=invalid");
						return;
					}
				}

				AuthorDatabase author_db = new AuthorDatabase();
				author_db.clearAuthorResult();

				if (author_db.registerAuthor(new Author(name, nationality, birth_Date, biography, link))) {
					response.sendRedirect("authorRegistration.jsp?success=register");
				} else {
					response.sendRedirect("authorRegistration.jsp?errCode=serverError");
				}
			} else {
				response.sendRedirect("authorRegistration.jsp?errCode=invalid");
			}
		} else if (status.equals("update")) {
			doPut(request, response);
		} else {
			response.sendRedirect("authorRegistration.jsp?errCode=invalid");
		}
	}

	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		Authentication auth = new Authentication();
		HttpSession session = request.getSession();
		if (!auth.testAdmin(session)) {
			request.setAttribute("error", "unauthorized");
			request.getRequestDispatcher("/signout.jsp").forward(request, response);
			return;
		}
		
		// values passed from author registration form
		String status = request.getParameter("status");
		String id, name, nationality, birthdate, biography, link;

		if (status.equals("update")) {
			name = request.getParameter("name");
			id = request.getParameter("id");

			if (id != null && !id.isBlank() && TestReg.matchInteger(id) && name != null && !name.isBlank()) {
				nationality = request.getParameter("nationality");
				if(nationality != null) {
					nationality = nationality.trim();
				}
				birthdate = request.getParameter("birthdate");
				biography = request.getParameter("biography");
				if(biography != null) {
					biography = biography.trim();
				}
				link = request.getParameter("link");
				if(link != null) {
					link = link.trim();
				}
				Date birth_Date = null;
				int authorID = Integer.parseInt(id);

				if (birthdate != null && !birthdate.isEmpty() && TestReg.matchDate(birthdate)) {
					birth_Date = Date.valueOf(LocalDate.parse(birthdate));
				}

				AuthorDatabase author_db = new AuthorDatabase();
				author_db.clearAuthorResult();

				if (author_db.getAuthorByID(authorID)) {
					ResultSet rs = author_db.getAuthorResult();
					int count = 0;
					try {
						while (rs.next()) {
							count++;
						}
					} catch (Exception e) {
						response.sendRedirect("authorRegistration.jsp?errCode=serverError");
					}
					if (count == 1) {
						if (author_db
								.updateAuthor(new Author(authorID, name, nationality, birth_Date, biography, link))) {
							response.sendRedirect("authorRegistration.jsp?success=update");
						} else {
							response.sendRedirect("authorRegistration.jsp?errCode=serverError");
						}
					} else {
						response.sendRedirect("authorRegistration.jsp?errCode=invalid");
					}
				}
			} else {
				response.sendRedirect("authorRegistration.jsp?errCode=invalid");
			}
		} else {
			response.sendRedirect("authorRegistration.jsp?errCode=invalid");
		}
	}

	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Authentication auth = new Authentication();
		HttpSession session = request.getSession();
		if (!auth.testAdmin(session)) {
			request.setAttribute("error", "unauthorized");
			request.getRequestDispatcher("/signout.jsp").forward(request, response);
			return;
		}
		
		AuthorDatabase author_db = new AuthorDatabase();
		BookDatabase book_db = new BookDatabase();
		String requestURi = request.getRequestURI();
		String[] parts = requestURi.split("/");

		if (parts.length == 0) {
			request.setAttribute("error", "invalid");
			request.getRequestDispatcher("/admin/authors").forward(request, response);
			return;
		} else {
			String id = parts[parts.length - 1];
			if (TestReg.matchInteger(id)) {
				if (book_db.deleteBookAuthor(Integer.parseInt(id), null)) {
					if (author_db.deleteAuthor(Integer.parseInt(id))) {
						request.setAttribute("success", "delete");
						request.getRequestDispatcher("/admin/authorList.jsp").forward(request, response);
						return;
					} else {
						request.setAttribute("error", "serverError");
						request.getRequestDispatcher("/admin/authorList.jsp").forward(request, response);
						return;
					}
				} else {
					request.setAttribute("error", "serverError");
					request.getRequestDispatcher("/admin/authorList.jsp").forward(request, response);
					return;
				}
			} else {
				request.setAttribute("error", "invalid");
				request.getRequestDispatcher("/admin/authorList.jsp").forward(request, response);
				return;
			}
		}
	}
}

// Author: Zay Yar Tun
// Admin No: 2235035
// Class: DIT/FT/2A/02
// Group: 10
// Date: 8.6.2023
// Description: middleware for author

package controller;

import org.apache.commons.text.StringEscapeUtils;

import com.google.gson.Gson;

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
import model.Book;
import model.BookDatabase;

/**
 * Servlet implementation class AuthorServlet
 */

// admin/authors => showing author list in admin site
// admin/authorUpdate/{id} => getting author data by author id in admin site
// admin/authorDelete/{id} => deleting author data by author id by admin 
// author/{id} => getting author detail by author id in member site 

@WebServlet(urlPatterns = { "/admin/authors", "/admin/authorUpdate/*", "/admin/authorDelete/*", "/author/*" })
public class AuthorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AuthorServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// create author_db object and clear the result
		AuthorDatabase author_db = new AuthorDatabase();
		
		HttpSession session = request.getSession();
		Authentication auth = new Authentication();

		String requestURi = request.getRequestURI();
		if (requestURi.contains(request.getContextPath() + "/admin/authorUpdate")) {
			
			// check if the user is admin and authorized
			if (!auth.testAdmin(session)) {
				request.setAttribute("error", "unauthorized");
				request.getRequestDispatcher("/signout.jsp").forward(request, response);
				System.out.println("..... Unauthorized in authorUpdate .....");
				return;
			}

			// split request url to get author id
			// author id is in the last part of url e.g. admin/authorUpdate/1
			String[] parts = requestURi.split("/");
			if (parts.length == 0) {
				request.setAttribute("error", "invalidrequest");
				request.getRequestDispatcher("/admin/authors").forward(request, response);
				System.out.println("..... Invalid route in authorUpdate .....");
				return;
			} else {
				String id = parts[parts.length - 1];
				
				// test whether author id is valid (integer, not negative, not string)
				if (TestReg.matchInteger(id)) {
					try {
						Author author_data = author_db.getAuthorByID(Integer.parseInt(id));
						
						// check if there is author data or null value
						// null value => invalid id given by user
						if(author_data == null) {
							request.setAttribute("error", "noauthor");
							request.getRequestDispatcher("/admin/authorList.jsp").forward(request, response);
							System.out.println("..... No author with id in authorUpdate .....");
							return;
						}
						else {
							request.setAttribute("author", author_data);
							request.setAttribute("status", "update");
							request.getRequestDispatcher("/admin/authorRegistration.jsp").forward(request, response);
							return;
						}
					}
					catch(Exception e) {
						e.printStackTrace();
						request.setAttribute("error", "servererror");
						request.getRequestDispatcher("/admin/authors").forward(request, response);
						System.out.println("..... Error in catch block in authorUpdate .....");
						return;
					}
				} else {
					request.setAttribute("error", "invalidid");
					request.getRequestDispatcher("/admin/authors").forward(request, response);
					System.out.println("..... Invalid author id in authorUpdate .....");
					return;
				}
			}
		} else if (requestURi.contains(request.getContextPath() + "/admin/authorDelete")) {
			
			// check if the user is admin and authorized
			if (!auth.testAdmin(session)) {
				System.out.println("..... Unauthorized in authorDelete .....");
				request.setAttribute("error", "unauthorized");
				request.getRequestDispatcher("/signout.jsp").forward(request, response);
				return;
			}

			doDelete(request, response);
		} else if (requestURi.endsWith(request.getContextPath() + "/admin/authors")) {
			
			// check if the user is admin and authorized
			if (!auth.testAdmin(session)) {
				System.out.println("..... Unauthorized in admin/authors .....");
				request.setAttribute("error", "unauthorized");
				request.getRequestDispatcher("/signout.jsp").forward(request, response);
				return;
			}

			ArrayList<Author> authorList = new ArrayList<Author>();
			
			try {
				authorList = author_db.getAuthor();
				if(authorList.size() == 0) {
					request.setAttribute("error", "serverretrieveerror");
					System.out.println("..... Error in retrieving author in admin/authors .....");
				}
			}
			catch(Exception e) {
				e.printStackTrace();
				request.setAttribute("error", "servererror");
				System.out.println("..... Database error in admin/authors .....");
			}

			// set the author arraylist as an attribute
			request.setAttribute("authorList", authorList);
			request.setAttribute("servlet", "true");

			// forward the data to the jsp
			request.getRequestDispatcher("/admin/authorList.jsp").forward(request, response);
			return;
		} 
		// *change structure*
		else if (requestURi.contains(request.getContextPath() + "/author/")) {
			BookDatabase book_db = new BookDatabase();
			String status = "";
			String[] parts = requestURi.split("/");
			ArrayList<Author> authorList = new ArrayList<Author>();
			ArrayList<Book> bookList = new ArrayList<Book>();
			
			if (parts.length == 0) {
				status = "invalid";
			} else {

				String id = parts[parts.length - 1];
				if (TestReg.matchInteger(id)) {
					try {
						Author author = author_db.getAuthorByID(Integer.parseInt(id));
						if (author != null) {
							authorList.add(author);
							boolean condition = book_db.getBookByAuthorID(author.getAuthorID());
							if (condition) {
								ResultSet book_rs = book_db.getBookResult();
								while (book_rs.next()) {
									bookList.add(new Book(StringEscapeUtils.escapeHtml4(book_rs.getString("ISBNNo")),
											StringEscapeUtils.escapeHtml4(book_rs.getString("Title")),
											StringEscapeUtils.escapeHtml4(book_rs.getString("Image")),
											StringEscapeUtils.escapeHtml4(book_rs.getString("Status"))));
								}
								status = "success";
							} else {
								status = "serverError";
							}
						}
						else {
							status = "serverError";
						}
					}
					catch(Exception e) {
						e.printStackTrace();
						status = "serverError";
					}
				} else {
					status = "invalid";
				}
			}
			Gson gson = new Gson();
			JSONObjects<Book> obj = new JSONObjects<>(bookList, authorList, status);
			String json = gson.toJson(obj);
			response.setContentType("application/json");
			response.getWriter().write(json);
			return;
			
		} else {
			System.out.println("..... Invalid route for author servlet .....");
			request.getRequestDispatcher("/errorPage.jsp").forward(request, response);
			return;
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Authentication auth = new Authentication();
		HttpSession session = request.getSession();

		if (!auth.testAdmin(session)) {
			System.out.println("..... Unauthorized in doPost in author servlet .....");
			request.setAttribute("error", "unauthorized");
			request.getRequestDispatcher("/signout.jsp").forward(request, response);
			return;
		}

		// values passed from author registration form
		String status = request.getParameter("status");
		String name, nationality, birthdate, biography, link;

		// status is to check whether it is create or update
		if (status == null) {
			System.out.println("..... Unavailable status in doPost in author servlet .....");
			response.sendRedirect("authorRegistration.jsp?error=unauthorized");
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

				// check if birth date is in correct format
				if (birthdate != null && !birthdate.isEmpty() && TestReg.matchDate(birthdate)) {
					birth_Date = Date.valueOf(LocalDate.parse(birthdate));
					LocalDate testDate = birth_Date.toLocalDate();
					LocalDate currentDate = LocalDate.now();
					long diff = ChronoUnit.DAYS.between(testDate, currentDate) / 365;
					if (diff < 5) {
						response.sendRedirect("authorRegistration.jsp?error=invalid");
						return;
					}
				}

				AuthorDatabase author_db = new AuthorDatabase();
				
				try {
					if (author_db.registerAuthor(new Author(name, nationality, birth_Date, biography, link)) == 1) {
						response.sendRedirect("authorRegistration.jsp?success=register");
					} else {
						System.out.println("..... Insert failed in doPost in author servlet .....");
						response.sendRedirect("authorRegistration.jsp?error=servererror");
					}
				}
				catch(Exception e) {
					e.printStackTrace();
					System.out.println("..... Database error catch block in doPost in author servlet .....");
					response.sendRedirect("authorRegistration.jsp?error=servererror");
				}
			} else {
				System.out.println("..... Invalid name in doPost in author servlet .....");
				response.sendRedirect("authorRegistration.jsp?error=invalid");
			}
		} else if (status.equals("update")) {
			doPut(request, response);
		} else {
			System.out.println("..... Invalid status in doPost in author servlet .....");
			response.sendRedirect("authorRegistration.jsp?error=invalid");
		}
	}

	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Authentication auth = new Authentication();
		HttpSession session = request.getSession();
		if (!auth.testAdmin(session)) {
			System.out.println("..... Unauthorized in doPut in author servlet .....");
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
				
				try {
					Author author = author_db.getAuthorByID(authorID);
					if(author == null) {
						System.out.println("..... No author with id in doPut in author servlet .....");
						response.sendRedirect("authorRegistration.jsp?error=noauthor");
					}
					else {
						if (author_db.updateAuthor(new Author(authorID, name, nationality, birth_Date, biography, link)) == 1) {
							response.sendRedirect("authorRegistration.jsp?success=update");
						} else {
							System.out.println("..... Update fail in doPut in author servlet .....");
							response.sendRedirect("authorRegistration.jsp?error=updatefail");
						}
					}
				}
				catch(Exception e) {
					e.printStackTrace();
					System.out.println("..... Database error in doPut in author servlet .....");
					response.sendRedirect("authorRegistration.jsp?error=servererror");
				}
			} else {
				System.out.println("..... Invalid data in doPut in author servlet .....");
				response.sendRedirect("authorRegistration.jsp?error=invalid");
			}
		} else {
			System.out.println("..... Invalid status in doPut in author servlet .....");
			response.sendRedirect("authorRegistration.jsp?error=invalid");
		}
	}

	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Authentication auth = new Authentication();
		HttpSession session = request.getSession();
		if (!auth.testAdmin(session)) {
			System.out.println("..... Unauthorized in doDelete in author servlet .....");
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
					try {
						if (author_db.deleteAuthor(Integer.parseInt(id)) == 1) {
							request.setAttribute("success", "delete");
							request.getRequestDispatcher("/admin/authorList.jsp").forward(request, response);
							System.out.println("..... Success in authorDelete .....");
							return;
						} else {
							request.setAttribute("error", "invalid");
							request.getRequestDispatcher("/admin/authorList.jsp").forward(request, response);
							System.out.println("..... Invalid author id in authorDelete .....");
							return;
						}
					}
					catch(Exception e) {
						e.printStackTrace();
						System.out.println("..... Error in catch block in authorDelete .....");
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

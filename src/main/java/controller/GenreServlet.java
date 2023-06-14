//Author 	  : Thu Htet San
//Admin No    : 2235022
//Class       : DIT/FT/2A/02
//Date		  : 8.6.2023
//Description : Genre Servlet

package controller;

import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.text.StringEscapeUtils;

import com.google.gson.Gson;

import model.Author;
import model.AuthorDatabase;
//[IMPORT FROM MODEL]
import model.Book;
import model.BookDatabase;
import model.Genre;
import model.GenreDatabase;

/**
 * Servlet implementation class GenreServlet
 */
@WebServlet(urlPatterns = { "/genres/all", "/genres/books/*", "/admin/genreRegistration", "/admin/genres",
		"/admin/genreUpdate/*", "/admin/genreDelete/*" })
public class GenreServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GenreServlet() {
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

		// response.getWriter().append("Served at: ").append(request.getContextPath());

		String requestURi = request.getRequestURI();
		if (requestURi.endsWith("/genres/all")) {
			// [DEFINE] database and resultSet arrayList(Genre)
			GenreDatabase genre_db = new GenreDatabase();
			ArrayList<Genre> genreList = new ArrayList<Genre>();
			// [TRUE-database execution successful]
			// [FALSE-fail]
			boolean condition = genre_db.getGenre();
			if (condition) {

				// [ASSIGN RESULT]
				ResultSet rs = genre_db.getGenreResult();
				try {
					while (rs.next()) {
						// ESCAPE:StringEscapeUtils.escapeHtml4
						genreList.add(
								new Genre(rs.getInt("GenreID"), StringEscapeUtils.escapeHtml4(rs.getString("Genre"))));
					}
				} catch (SQLException e) {
					request.setAttribute("errCode", "serverError");
				}
			} else {
				request.setAttribute("errCode", "serverError");
			}

			Gson gson = new Gson();
			JSONObjects<Genre> obj = new JSONObjects<>(genreList, "true");
			String json = gson.toJson(obj);
			response.setContentType("application/json");
			response.getWriter().write(json);
			return;
		} else if (requestURi.contains("/genres/books/")) {

			String[] parts = requestURi.split("/");
			if (parts.length == 0) {
				// invalid do something
				return;
			} else {
				String id = parts[parts.length - 1];

				// [DEFINE] database
				BookDatabase book_db = new BookDatabase();
				book_db.clearBookResult();

				if (TestReg.matchInteger(id)) {
					int genreID = Integer.parseInt(id);
					ArrayList<Book> bookList = new ArrayList<Book>();
					// [TRUE-database execution successful]
					// [FALSE-fail]

					if (book_db.getBookByGenreID(genreID)) {
						// [ASSIGN RESULT]
						ResultSet rs = book_db.getBookResult();
						try {
							while (rs.next()) {
								// ESCAPE:StringEscapeUtils.escapeHtml4
								bookList.add(new Book(StringEscapeUtils.escapeHtml4(rs.getString("ISBNNo")),
										StringEscapeUtils.escapeHtml4(rs.getString("Title")), rs.getDouble("Price"),
										StringEscapeUtils.escapeHtml4(rs.getString("Description")),
										StringEscapeUtils.escapeHtml4(rs.getString("Image"))));
							}
						} catch (Exception e) {
							System.out.println(e);
							// TODO Auto-generated catch block
							request.setAttribute("errCode", "serverError");
						}
					} else {
						request.setAttribute("errCode", "serverError");
					}

					Gson gson = new Gson();
					JSONObjects<Book> obj = new JSONObjects<>(bookList, "true");
					String json = gson.toJson(obj);
					response.setContentType("application/json");
					response.getWriter().write(json);
					return;
				} else {
					// invalid id
				}
			}
		} else if (requestURi.contains("admin/genres")) {

			// [CKECK AUTHENTICATION]
			HttpSession session = request.getSession();
			Authentication auth = new Authentication();
			if (!auth.testAdmin(session)) {
				request.setAttribute("error", "unauthorized");
				request.getRequestDispatcher("/admin/genreList.jsp").forward(request, response);
				return;
			}
			// [CKECK AUTHENTICATION-END]

			// [DEFINE] database and resultSet arrayList(Genre)
			GenreDatabase genre_db = new GenreDatabase();
			ArrayList<Genre> genreList = new ArrayList<Genre>();

			boolean condition = genre_db.getGenre();
			if (condition) {
				ResultSet rs = genre_db.getGenreResult();
				try {
					while (rs.next()) {
						// sanitizing output by escaping html special characters
						genreList.add(
								new Genre(rs.getInt("GenreID"), StringEscapeUtils.escapeHtml4(rs.getString("Genre"))));
					}
				} catch (Exception e) {
					request.setAttribute("error", "serverRetrieveError");
				}
			} else {
				request.setAttribute("error", "serverRetrieveError");
			}

			// set the author arraylist as an attribute
			request.setAttribute("genreList", genreList);
			request.setAttribute("servlet", "true");

			// forward the data to the jsp
			request.getRequestDispatcher("/admin/genreList.jsp").forward(request, response);
			return;
		}
		if (requestURi.contains("admin/genreUpdate")) {

			// [CKECK AUTHENTICATION]
			HttpSession session = request.getSession();
			Authentication auth = new Authentication();
			if (!auth.testAdmin(session)) {
				request.setAttribute("error", "unauthorized");
				request.getRequestDispatcher("/admin/genreList.jsp").forward(request, response);
				return;
			}
			// [CKECK AUTHENTICATION-END]

			String[] parts = requestURi.split("/");
			if (parts.length == 0) {
				request.setAttribute("error", "invalid");
				request.getRequestDispatcher("/admin/authors").forward(request, response);
				return;
			} else {
				// [DEFINE] database and resultSet arrayList(Genre)
				GenreDatabase genre_db = new GenreDatabase();
				String id = parts[parts.length - 1];
				if (TestReg.matchInteger(id)) {
					if (genre_db.getGenreByID(Integer.parseInt(id))) {
						ResultSet rs = genre_db.getGenreResult();
						Genre genreData = null;

						try {
							while (rs.next()) {
								// sanitizing output by escaping html special characters
								genreData = new Genre(rs.getInt("GenreID"),
										StringEscapeUtils.escapeHtml4(rs.getString("Genre")));
								break;
							}

							request.setAttribute("genre", genreData); // [SEND TO registration form]
							request.setAttribute("status", "update");
							request.getRequestDispatcher("/admin/genreRegistration.jsp").forward(request, response);
							return;
						} catch (Exception e) {
							request.setAttribute("error", "serverError");
							request.getRequestDispatcher("/admin/genres").forward(request, response);
							return;
						}
					} else {
						request.setAttribute("error", "invalid");
						request.getRequestDispatcher("/admin/genres").forward(request, response);
						return;
					}
				} else {
					request.setAttribute("error", "invalid");
					request.getRequestDispatcher("/admin/genres").forward(request, response);
					return;
				}
			}
		} else if (requestURi.contains("admin/genreDelete")) {

			// [CKECK AUTHENTICATION]
			HttpSession session = request.getSession();
			Authentication auth = new Authentication();
			if (!auth.testAdmin(session)) {
				request.setAttribute("error", "unauthorized");
				request.getRequestDispatcher("/admin/genreList.jsp").forward(request, response);
				return;
			}
			// [CKECK AUTHENTICATION-END]
			doDelete(request, response);
		} else {
			// invalid do something
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		// [CHECK AUTHENTICATION]
		Authentication auth = new Authentication();
		HttpSession session = request.getSession();
		if (!auth.testAdmin(session)) {
			request.setAttribute("error", "unauthorized");
			request.getRequestDispatcher("/admin/authorList.jsp").forward(request, response);
			return;
		}
		// [CHECK AUTHENTICATION-END]

		// values passed from genre registration form
		String status = request.getParameter("status");
		String genre = request.getParameter("genre");

		if (status.equals("register")) {

			// [REGISTER GENRE]
			if (genre != null && !genre.isBlank()) {

				GenreDatabase genre_db = new GenreDatabase();

				if (genre_db.addGenre(new Genre(StringEscapeUtils.escapeHtml4(genre)))) {
					response.sendRedirect("genreRegistration.jsp?success=register");
				} else {
					response.sendRedirect("genreRegistration.jsp?errCode=serverError");
				}
			} else {
				response.sendRedirect("genreRegistration.jsp?errCode=invalid");
			}
		} else if (status.equals("update")) {

			// [UPDATE GENRE]
			doPut(request, response);
		} else {
			response.sendRedirect("genreRegistration.jsp?errCode=invalid");
		}
	}

	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		Authentication auth = new Authentication();
		HttpSession session = request.getSession();
		if (!auth.testAdmin(session)) {
			request.setAttribute("error", "unauthorized");
			request.getRequestDispatcher("/admin/genreList.jsp").forward(request, response);
			return;
		}

		// values passed from author registration form
		String status = request.getParameter("status");
		String genreID, genre;

		System.out.println("status: " + status);
		if (status.equals("update")) {
			genreID = request.getParameter("genreID");
			genre = request.getParameter("genre");

			System.out.println("genreID: " + genreID);
			if (genreID != null && !genreID.isBlank() && TestReg.matchInteger(genreID) && genre != null
					&& !genre.isBlank()) {

				GenreDatabase genre_db = new GenreDatabase();
				genre_db.clearGenreResult();

				if (genre_db.getGenreByID(Integer.parseInt(genreID))) {
					ResultSet rs = genre_db.getGenreResult();
					int count = 0;
					try {
						while (rs.next()) {
							count++;
						}
					} catch (Exception e) {
						response.sendRedirect("genreRegistration.jsp?errCode=serverError");
					}

					System.out.println("count: " + count);
					if (count == 1) {
						if (genre_db.updateGenre(
								new Genre(Integer.parseInt(genreID), StringEscapeUtils.escapeHtml4(genre)))) {
							response.sendRedirect("genreRegistration.jsp?success=update");
						} else {
							response.sendRedirect("genreRegistration.jsp?errCode=serverError");
						}
					} else {
						response.sendRedirect("genreRegistration.jsp?errCode=invalid");
					}
				}
			} else {
				response.sendRedirect("genreRegistration.jsp?errCode=invalid");
			}
		} else {
			response.sendRedirect("genreRegistration.jsp?errCode=invalid");
		}
	}

	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Authentication auth = new Authentication();
		HttpSession session = request.getSession();
		if (!auth.testAdmin(session)) {
			request.setAttribute("error", "unauthorized");
			request.getRequestDispatcher("/admin/genreList.jsp").forward(request, response);
			return;
		}

		GenreDatabase genre_db = new GenreDatabase();
		BookDatabase book_db = new BookDatabase();
		String requestURi = request.getRequestURI();
		String[] parts = requestURi.split("/");

		if (parts.length == 0) {
			request.setAttribute("error", "invalid");
			request.getRequestDispatcher("/admin/genres").forward(request, response);
			return;
		} else {
			String id = parts[parts.length - 1];
			if (TestReg.matchInteger(id)) {
				if (book_db.deleteBookGenre(Integer.parseInt(id), null)) {
					if (genre_db.deleteGenre(Integer.parseInt(id))) {
						request.setAttribute("success", "delete");
						request.getRequestDispatcher("/admin/genres").forward(request, response);
						return;
					} else {
						request.setAttribute("error", "serverError");
						request.getRequestDispatcher("/admin/genres").forward(request, response);
						return;
					}
				} else {
					request.setAttribute("error", "serverError");
					request.getRequestDispatcher("/admin/genres").forward(request, response);
					return;
				}
			} else {
				request.setAttribute("error", "invalid");
				request.getRequestDispatcher("/admin/genres").forward(request, response);
				return;
			}
		}
	}

}

package controller;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.text.StringEscapeUtils;

import model.Author;
import model.AuthorDatabase;
import model.Genre;
import model.GenreDatabase;

/**
 * Servlet implementation class BookServlet
 */
@WebServlet(urlPatterns = {"/admin/books", "/admin/bookRegistration", "/admin/bookList"})
public class BookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BookServlet() {
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
		ArrayList<Genre> genrelist = new ArrayList<Genre>();
		AuthorDatabase author_db = new AuthorDatabase();
		GenreDatabase genre_db = new GenreDatabase();

		// get author data from database and put data in arraylist
		boolean condition = author_db.getAuthor();
		if (condition) {
			ResultSet rs = author_db.getAuthorResult();
			try {
				while (rs.next()) {
					// sanitizing output by escaping html special characters
					authorlist
							.add(new Author(rs.getInt("AuthorID"), StringEscapeUtils.escapeHtml4(rs.getString("Name")),StringEscapeUtils.escapeHtml4(rs.getString("Nationality")), rs.getDate("BirthDate"), StringEscapeUtils.escapeHtml4(rs.getString("Biography")), StringEscapeUtils.escapeHtml4(rs.getString("Link"))));
				}
			} catch (Exception e) {
				request.setAttribute("error", "serverError");
			}
		} else {
			request.setAttribute("error", "serverError");
		}

		// get genre data from database and put data in arraylist
		condition = genre_db.getGenre();
		if (condition) {
			ResultSet rs = genre_db.getGenreResult();
			try {
				while (rs.next()) {
					// sanitizing output by escaping html special characters
					genrelist
							.add(new Genre(rs.getInt("GenreID"), StringEscapeUtils.escapeHtml4(rs.getString("Genre"))));
				}
			} catch (Exception e) {
				request.setAttribute("error", "serverError");
			}
		} else {
			request.setAttribute("error", "serverError");
		}

		// set the author arraylist as an attribute
		request.setAttribute("authorList", authorlist);
		request.setAttribute("genrelist", genrelist);

		String targetPage = "";
		// forward the data to the jsp
		System.out.println(request.getRequestURI());
		if (request.getRequestURI().endsWith("bookRegistration")) {
			targetPage = "bookRegistration.jsp";
		} else if (request.getRequestURI().endsWith("bookList")) {
			targetPage = "bookList.jsp";
		}
		else {
			targetPage = "adminHomePage.jsp";
		}
		request.getRequestDispatcher(targetPage).forward(request, response);
		return;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

	}

}

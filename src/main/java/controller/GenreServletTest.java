package controller;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.text.StringEscapeUtils;

import com.google.gson.Gson;

import model.Book;
import model.BookDatabase;
import model.Genre;
import model.GenreDatabase;

/**
 * Servlet implementation class GenreServletTest
 */
@WebServlet(urlPatterns = { "/genres/all", "/genres/books/*" })
public class GenreServletTest extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GenreServletTest() {
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
						genreList.add(new Genre(rs.getInt("GenreID"),
								(StringEscapeUtils.escapeHtml4(rs.getString("Genre")))));
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
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
	
				if(TestReg.matchInteger(id)) {
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
								bookList.add(new Book(rs.getString("ISBNNo"), rs.getString("Title"), rs.getDouble("Price"),
										rs.getString("Description"), rs.getString("Image")));
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
				}
				else {
					// invalid id
				}
			}
		}
		else {
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

	}

}

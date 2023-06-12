//Author 	  : Thu Htet San
//Admin No    : 2235022
//Class       : DIT/FT/2A/02
//Date		  : 8.6.2023
//Description : Genre Servlet

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

//[IMPORT FROM MODEL]
import model.Book;
import model.BookDatabase;
import model.Genre;
import model.GenreDatabase;

/**
 * Servlet implementation class GenreServlet
 */
@WebServlet("/GenreServlet")
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

		System.out.print("in servlet\n");
		String formName = request.getParameter("formName");
		if (formName != null) {
			if (formName.equals("genreListForm")) {
				System.out.print("in genreListForms\n");
				
				// [DEFINE] database
				GenreDatabase genre_db = new GenreDatabase();
				BookDatabase book_db = new BookDatabase();
				book_db.clearBookResult();
				
				int genreID = Integer.valueOf(request.getParameter("genre"));
				ArrayList<Book> bookList = new ArrayList<Book>();
				// [TRUE-database execution successful]
				// [FALSE-fail]

				if (book_db.getBookByGenreID(genreID)) {
					System.out.println("getBook success");
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

				// [SEND arrayList to bookGenre.jsp]
				request.setAttribute("bookList", bookList);
				request.setAttribute("result", true);
				request.getRequestDispatcher("bookGenre.jsp").forward(request, response);
				return;
			}
		}

		// [DEFINE] database
		GenreDatabase genre_db = new GenreDatabase();
		// [DEFINE] resultSet arrayList(Genre)
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
							new Genre(rs.getInt("GenreID"), (StringEscapeUtils.escapeHtml4(rs.getString("Genre")))));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				request.setAttribute("errCode", "serverError");
			}
		} else {
			request.setAttribute("errCode", "serverError");
		}

		// [SEND arrayList to bookGenre.jsp]
		request.setAttribute("genreList", genreList);
		request.setAttribute("status", true);
		request.getRequestDispatcher("bookGenre.jsp").forward(request, response);
		return;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.sql.ResultSet;
import org.apache.commons.text.StringEscapeUtils;

import model.SearchDatabase;
import model.Author;
import model.Book;

/**
 * Servlet implementation class SearchServlet
 */
@WebServlet(urlPatterns = { "/search/book/*", "/search/author/*" })
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SearchServlet() {
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
		SearchDatabase search_db = new SearchDatabase();
		String requestURi = request.getRequestURI();
		if (requestURi.contains("/search/book")) {
			ArrayList<Book> bookList = new ArrayList<Book>();
			String[] parts = requestURi.split("/");
			if (parts.length == 0) {
				request.setAttribute("errCode", "invalid");
				request.getRequestDispatcher("/search.jsp").forward(request, response);
			} else {
				String searchValue = parts[parts.length - 1];
				searchValue.trim();
				if (searchValue != null && !searchValue.isBlank()) {
					boolean condition = search_db.searchBook(searchValue);
					if (condition) {
						ResultSet rs = search_db.getSearchResultSet();
						try {
							while (rs.next()) {
								bookList.add(new Book(StringEscapeUtils.escapeHtml4(rs.getString("ISBNNo")),
										StringEscapeUtils.escapeHtml4(rs.getString("Title")), rs.getDouble("Price"),
										StringEscapeUtils.escapeHtml4(rs.getString("Description")),
										StringEscapeUtils.escapeHtml4(rs.getString("Image"))));
							}
						} catch (Exception e) {
							request.setAttribute("errCode", "serverError");
							request.getRequestDispatcher("/search.jsp").forward(request, response);
						}
					}
				}
			}

			request.setAttribute("bookList", bookList);
			request.getRequestDispatcher("/search.jsp").forward(request, response);
		} else if (requestURi.contains("/search/author")) {
			ArrayList<Author> authorList = new ArrayList<Author>();
			String[] parts = requestURi.split("/");
			if (parts.length == 0) {
				request.setAttribute("errCode", "invalid");
				request.getRequestDispatcher("/search.jsp").forward(request, response);
			} else {
				String searchValue = parts[parts.length - 1];
				searchValue.trim();
				if (searchValue != null && !searchValue.isBlank()) {
					boolean condition = search_db.searchAuthor(searchValue);
					if (condition) {
						ResultSet rs = search_db.getSearchResultSet();
						try {
							while (rs.next()) {
								authorList.add(new Author(rs.getInt("AuthorID"),
										StringEscapeUtils.escapeHtml4(rs.getString("Name")),
										StringEscapeUtils.escapeHtml4(rs.getString("Nationality")),
										rs.getDate("BirthDate"),
										StringEscapeUtils.escapeHtml4(rs.getString("Biography")),
										StringEscapeUtils.escapeHtml4(rs.getString("Link"))));
							}
						} catch (Exception e) {
							request.setAttribute("errCode", "serverError");
							request.getRequestDispatcher("/search.jsp").forward(request, response);
						}
					}
				}
			}

			request.setAttribute("authorList", authorList);
			request.getRequestDispatcher("/search.jsp").forward(request, response);
		} else {
			request.setAttribute("errCode", "invalid");
			request.getRequestDispatcher("/search.jsp").forward(request, response);
		}

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

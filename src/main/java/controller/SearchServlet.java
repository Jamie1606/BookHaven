//Author 	  : Thu Htet San
//Admin No    : 2235022
//Class       : DIT/FT/2A/02
//Date		  : 15.6.2023
//Description : middleware for searching Books/Authors

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

import com.google.gson.Gson;

import model.SearchDatabase;
import model.Author;
import model.Book;
import model.Genre;

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
			String status = "";
			ArrayList<Book> bookList = new ArrayList<Book>();
			String[] parts = requestURi.split("/");
			if (parts.length == 0) {
				status = "invalid";
			} else {		
				String searchValue = parts[parts.length - 1];
				searchValue = searchValue.trim();
				if (searchValue != null && !searchValue.isBlank()) {
					boolean condition = search_db.searchBook(searchValue);
					if (condition) {
						ResultSet rs = search_db.getSearchResultSet();
						try {
							while (rs.next()) {
								bookList.add(new Book(StringEscapeUtils.escapeHtml4(rs.getString("ISBNNo")),
										StringEscapeUtils.escapeHtml4(rs.getString("Title")),
										StringEscapeUtils.escapeHtml4(rs.getString("Image")),
										StringEscapeUtils.escapeHtml4(rs.getString("Status"))));
							}
							status = "success";
						} catch (Exception e) {
							status = "serverError";
						}
					} else {
						status = "serverError";
					}
				} else {
					status = "invalid";
				}
			}
			Gson gson = new Gson();
			JSONObjects<Book> obj = new JSONObjects<>(bookList, status);
			String json = gson.toJson(obj);
			response.setContentType("application/json");
			response.getWriter().write(json);
			return;
		} else if (requestURi.contains("/search/author")) {
			String status = "";
			ArrayList<Book> bookList = new ArrayList<Book>();
			ArrayList<Author> authorList = new ArrayList<Author>();
			String[] parts = requestURi.split("/");
			if (parts.length == 0) {
				status = "invalid";
			} else {
				String searchValue = parts[parts.length - 1];
				searchValue.trim();
				if (searchValue != null && !searchValue.isBlank()) {
					boolean condition = search_db.searchBookByAuthor(searchValue);
					if (condition) {
						ResultSet rs = search_db.getSearchResultSet();
						try {
							while (rs.next()) {
								authorList.add(new Author(rs.getInt("AuthorID"),
										StringEscapeUtils.escapeHtml4(rs.getString("Name"))));
								bookList.add(new Book(StringEscapeUtils.escapeHtml4(rs.getString("ISBNNo")),
										StringEscapeUtils.escapeHtml4(rs.getString("Title")),
										StringEscapeUtils.escapeHtml4(rs.getString("Image")),
										StringEscapeUtils.escapeHtml4(rs.getString("Status"))));
							}
							status = "success";
						} catch (Exception e) {
							status = "serverError";
						}
					} else {
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

// Author: Zay Yar Tun
// Admin No: 2235035
// Date: 7.6.2023
// Description: middleware for book

package controller;

import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.text.StringEscapeUtils;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;

import model.Author;
import model.AuthorDatabase;
import model.Book;
import model.BookDatabase;
import model.Genre;
import model.GenreDatabase;

/**
 * Servlet implementation class BookServlet
 */
@WebServlet(urlPatterns = { "/admin/books", "/admin/bookRegistration" })
/**
 * Servlet implementation class BookServlet
 */
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		HttpSession session = request.getSession();
		Authentication auth = new Authentication();

		if (!auth.testAdmin(session)) {
			request.setAttribute("error", "unauthorized");
			request.getRequestDispatcher("/admin/bookRegistration.jsp").forward(request, response);
			return;
		}
		
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
							.add(new Author(rs.getInt("AuthorID"), StringEscapeUtils.escapeHtml4(rs.getString("Name")),
									StringEscapeUtils.escapeHtml4(rs.getString("Nationality")), rs.getDate("BirthDate"),
									StringEscapeUtils.escapeHtml4(rs.getString("Biography")),
									StringEscapeUtils.escapeHtml4(rs.getString("Link"))));
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
		request.setAttribute("genreList", genrelist);
		request.setAttribute("servlet", "true");

		String targetPage = "";
		// forward the data to the jsp
		if (request.getRequestURI().endsWith("admin/bookRegistration")) {
			targetPage = "/admin/bookRegistration.jsp";
		} else if (request.getRequestURI().endsWith("admin/books")) {
			targetPage = "/admin/bookList.jsp";
		} else {
			targetPage = "/admin/adminHomePage.jsp";
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

		HttpSession session = request.getSession();
		Authentication auth = new Authentication();

		if (!auth.testAdmin(session)) {
			request.setAttribute("error", "unauthorized");
			request.getRequestDispatcher("/admin/bookRegistration.jsp").forward(request, response);
			return;
		}
		
		ArrayList<String> authors = new ArrayList<String>();
		ArrayList<String> genres = new ArrayList<String>();

		// check if the request is multipart/form-data
		if (ServletFileUpload.isMultipartContent(request)) {
			try {
				// create handler for file upload
				ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());

				// parse the multipar request
				List<FileItem> items = upload.parseRequest(new ServletRequestContext(request));

				Map<String, String> fields = new HashMap<>();

				// process the form fields and file uploads
				for (FileItem item : items) {
					if (item.isFormField()) {
						// regular form field
						String fieldName = item.getFieldName();
						String fieldValue = item.getString();

						if (fieldName.equals("author")) {
							authors.add(fieldValue);
						} else if (fieldName.equals("genre")) {
							genres.add(fieldValue);
						} else {
							fields.put(fieldName, fieldValue);
						}
					} else {
						// file upload
					}
				}

				// getting value from fields
				String isbn = fields.get("isbn");
				String title = fields.get("title");
				String page = fields.get("page");
				String price = fields.get("price");
				String qty = fields.get("qty");
				String publisher = fields.get("publisher");
				String publicationdate = fields.get("publicationdate");
				String status = fields.get("status");
				String description = fields.get("description");

				// checking null and empty values
				if (isbn != null && !isbn.isEmpty() && title != null && !title.isEmpty() && page != null
						&& !page.isEmpty() && price != null && !price.isEmpty() && qty != null && !qty.isEmpty()
						&& publisher != null && !publisher.isEmpty() && publicationdate != null
						&& !publicationdate.isEmpty() && status != null && !status.isEmpty() && authors.size() != 0
						&& genres.size() != 0) {

					// test with regular expressions
					if (TestReg.matchISBN(isbn) && TestReg.matchInteger(page) && TestReg.matchDecimal(price)
							&& TestReg.matchInteger(qty) && TestReg.matchDate(publicationdate)
							&& TestReg.matchIntegerArrayList(authors) && TestReg.matchIntegerArrayList(genres)) {

						Date publicationDate = Date.valueOf(LocalDate.parse(publicationdate));

						BookDatabase book_db = new BookDatabase();
						book_db.clearBookResult();
						
						if(book_db.getBookByISBN(isbn)) {
							ResultSet rs = book_db.getBookResult();
							try {
								int count = 0;
								while(rs.next()) {
									count++;
								}
								if(count > 0) {
									request.setAttribute("error", "duplicate");
									request.getRequestDispatcher("/admin/bookRegistration.jsp").forward(request, response);
									return;
								}
							}
							catch(Exception e) {
								request.setAttribute("error", "serverError");
								request.getRequestDispatcher("/admin/bookRegistration.jsp").forward(request, response);
								return;
							}
							if(book_db.registerBook(new Book(isbn, title, Integer.parseInt(page), Double.parseDouble(price), publisher,
									publicationDate, Integer.parseInt(qty), description, "", "", status))) {
								if(book_db.registerBookAuthor(authors, isbn)) {
									if(book_db.registerBookGenre(genres, isbn)) {
										request.setAttribute("success", "register");
										request.getRequestDispatcher("/admin/bookRegistration.jsp").forward(request, response);
										return;
									}
									else {
										book_db.deleteBookGenre(0, isbn);
										book_db.deleteBookAuthor(0, isbn);
										request.setAttribute("error", "genreError");
										request.getRequestDispatcher("/admin/bookRegistration.jsp").forward(request, response);
										return;
									}
								}
								else {
									book_db.deleteBookAuthor(0, isbn);
									request.setAttribute("error", "authorError");
									request.getRequestDispatcher("/admin/bookRegistration.jsp").forward(request, response);
									return;
								}
							}
							else {
								request.setAttribute("error", "serverError");
								request.getRequestDispatcher("/admin/bookRegistration.jsp").forward(request, response);
								return;
							}
						}
						else {
							request.setAttribute("error", "serverError");
							request.getRequestDispatcher("/admin/bookRegistration.jsp").forward(request, response);
							return;
						}
					} else {
						request.setAttribute("error", "invalid");
						request.getRequestDispatcher("/admin/bookRegistration.jsp").forward(request, response);
						return;
					}
				} else {
					request.setAttribute("error", "invalid");
					request.getRequestDispatcher("/admin/bookRegistration.jsp").forward(request, response);
					return;
				}
			} catch (Exception e) {
				request.setAttribute("error", "upload");
				request.getRequestDispatcher("/admin/bookRegistration.jsp").forward(request, response);
				return;
			}
		}
	}

}

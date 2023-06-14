// Author: Zay Yar Tun
// Admin No: 2235035
// Date: 7.6.2023
// Description: middleware for book

package controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.apache.commons.text.StringEscapeUtils;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;

import com.google.gson.Gson;

import model.Author;
import model.AuthorDatabase;
import model.Book;
import model.BookDatabase;
import model.Genre;
import model.GenreDatabase;

/**
 * Servlet implementation class BookServlet
 */
@WebServlet(urlPatterns = { "/admin/books", "/book/*", "/books/latestrelease", "/books/latest", "/admin/bookRegistration",
		"/admin/bookUpdate/*", "/admin/bookDelete/*" })
/**
 * Servlet implementation class BookServlet
 */
@MultipartConfig(location = "/tmp", maxFileSize = 20971520, maxRequestSize = 41943040, fileSizeThreshold = 1048576)
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

		HttpSession session = request.getSession();
		Authentication auth = new Authentication();

		ArrayList<Author> authorList = new ArrayList<Author>();
		ArrayList<Genre> genreList = new ArrayList<Genre>();
		ArrayList<Book> bookList = new ArrayList<Book>();

		BookDatabase book_db = new BookDatabase();
		AuthorDatabase author_db = new AuthorDatabase();
		GenreDatabase genre_db = new GenreDatabase();
		book_db.clearBookResult();
		author_db.clearAuthorResult();

		request.setAttribute("servlet", "true");

		String targetPage = "";
		String requestURi = request.getRequestURI();
		if(requestURi.contains("/book/")) {			
			String status = "";
			String[] parts = requestURi.split("/");
			if (parts.length == 0) {
				status = "invalid";
			} else {
				String id = parts[parts.length - 1];
				if (TestReg.matchISBN(id)) {
					if(book_db.getBookByISBN(id)) {
						ResultSet rs = book_db.getBookResult();
						try {
							while(rs.next()) {
								bookList.add(new Book(StringEscapeUtils.escapeHtml4(rs.getString("ISBNNo")),
										StringEscapeUtils.escapeHtml4(rs.getString("Title")), rs.getInt("Page"),
										rs.getDouble("Price"), StringEscapeUtils.escapeHtml4(rs.getString("Publisher")),
										rs.getDate("PublicationDate"), rs.getInt("Qty"), rs.getShort("Rating"),
										StringEscapeUtils.escapeHtml4(rs.getString("Description")),
										StringEscapeUtils.escapeHtml4(rs.getString("Image")),
										StringEscapeUtils.escapeHtml4(rs.getString("Image3D")),
										StringEscapeUtils.escapeHtml4(rs.getString("Status"))));
							}
						}
						catch(Exception e) {
							status = "serverError";
						}
						if(bookList.size() != 1) {
							status = "invalid";
						}
						else {
							book_db.clearBookResult();
							if(book_db.getGenreByISBN(id)) {
								rs = book_db.getBookResult();
								try {
									while(rs.next()) {
										genreList.add(new Genre(rs.getInt("GenreID"), StringEscapeUtils.escapeHtml4(rs.getString("Genre"))));
									}
								}
								catch(Exception e) {
									status = "serverError";
								}
							}
							if(genreList.size() == 0) {
								status = "invalid";
							}
							else {
								book_db.clearBookResult();
								if(book_db.getAuthorByISBN(id)) {
									rs = book_db.getBookResult();
									try {
										while(rs.next()) {
											authorList.add(new Author(rs.getInt("AuthorID"), StringEscapeUtils.escapeHtml4(rs.getString("Name"))));
										}
									}
									catch(Exception e) {
										status = "serverError";
									}
									if(authorList.size() == 0) {
										status = "invalid";
									}
									else {
										status = "success";
									}
								}
							}
						}
					}
					else {
						status = "serverError";
					}
				}
				else {
					status = "invalid";
				}
			}
			
			Gson gson = new Gson();
			JSONObjects<Book> obj = new JSONObjects<>(bookList, authorList, genreList, status);
			String json = gson.toJson(obj);
			response.setContentType("application/json");
			response.getWriter().write(json);
			return;
		}
		else if (requestURi.endsWith("admin/bookRegistration")) {
			if (!auth.testAdmin(session)) {
				request.setAttribute("error", "unauthorized");
				request.getRequestDispatcher("/admin/bookRegistration.jsp").forward(request, response);
				return;
			}

			// get author data from database and put data in arraylist
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
					request.setAttribute("error", "serverError");
				}
			} else {
				request.setAttribute("error", "serverError");
			}

			// get genre data from database and put data in arraylist
			if (genre_db.getGenre()) {
				ResultSet rs = genre_db.getGenreResult();
				try {
					while (rs.next()) {
						// sanitizing output by escaping html special characters
						genreList.add(
								new Genre(rs.getInt("GenreID"), StringEscapeUtils.escapeHtml4(rs.getString("Genre"))));
					}
				} catch (Exception e) {
					request.setAttribute("error", "serverError");
				}
			} else {
				request.setAttribute("error", "serverError");
			}

			request.setAttribute("authorList", authorList);
			request.setAttribute("genreList", genreList);

			targetPage = "/admin/bookRegistration.jsp";
		} else if (requestURi.contains("admin/bookUpdate")) {
			if (!auth.testAdmin(session)) {
				request.setAttribute("error", "unauthorized");
				request.getRequestDispatcher("/admin/bookRegistration.jsp").forward(request, response);
				return;
			}

			ArrayList<Author> bookAuthorList = new ArrayList<Author>();
			ArrayList<Genre> bookGenreList = new ArrayList<Genre>();

			String[] parts = requestURi.split("/");
			if (parts.length == 0) {
				request.setAttribute("error", "invalid");
				request.getRequestDispatcher("/admin/books").forward(request, response);
				return;
			} else {
				String id = parts[parts.length - 1];
				if (TestReg.matchISBN(id)) {
					// get author data from database and put data in arraylist
					if (author_db.getAuthor()) {
						ResultSet rs = author_db.getAuthorResult();
						try {
							while (rs.next()) {
								// sanitizing output by escaping html special characters
								authorList.add(new Author(rs.getInt("AuthorID"),
										StringEscapeUtils.escapeHtml4(rs.getString("Name")),
										StringEscapeUtils.escapeHtml4(rs.getString("Nationality")),
										rs.getDate("BirthDate"),
										StringEscapeUtils.escapeHtml4(rs.getString("Biography")),
										StringEscapeUtils.escapeHtml4(rs.getString("Link"))));
							}
						} catch (Exception e) {
							request.setAttribute("error", "serverError");
						}
					} else {
						request.setAttribute("error", "serverError");
					}

					if (book_db.getBookAuthorByISBN(id)) {
						ResultSet rs = book_db.getBookResult();
						book_db.clearBookResult();
						try {
							while (rs.next()) {
								bookAuthorList.add(new Author(rs.getInt("AuthorID")));
							}
						} catch (Exception e) {
							request.setAttribute("error", "serverError");
						}
					}

					// get genre data from database and put data in arraylist
					if (genre_db.getGenre()) {
						ResultSet rs = genre_db.getGenreResult();
						try {
							while (rs.next()) {
								// sanitizing output by escaping html special characters
								genreList.add(new Genre(rs.getInt("GenreID"),
										StringEscapeUtils.escapeHtml4(rs.getString("Genre"))));
							}
						} catch (Exception e) {
							request.setAttribute("error", "serverError");
						}
					} else {
						request.setAttribute("error", "serverError");
					}

					if (book_db.getBookGenreByISBN(id)) {
						ResultSet rs = book_db.getBookResult();
						book_db.clearBookResult();
						try {
							while (rs.next()) {
								bookGenreList.add(new Genre(rs.getInt("GenreID")));
							}
						} catch (Exception e) {
							request.setAttribute("error", "serverError");
						}
					}

					// retrieving book data
					if (book_db.getBookByISBN(id)) {
						ResultSet rs = book_db.getBookResult();
						Book book_data = null;

						try {
							while (rs.next()) {
								// sanitizing output by escaping html special characters
								book_data = new Book(StringEscapeUtils.escapeHtml4(rs.getString("ISBNNo")),
										StringEscapeUtils.escapeHtml4(rs.getString("Title")), rs.getInt("Page"),
										rs.getDouble("Price"), StringEscapeUtils.escapeHtml4(rs.getString("Publisher")),
										rs.getDate("PublicationDate"), rs.getInt("Qty"), rs.getShort("Rating"),
										StringEscapeUtils.escapeHtml4(rs.getString("Description")),
										StringEscapeUtils.escapeHtml4(rs.getString("Image")),
										StringEscapeUtils.escapeHtml4(rs.getString("Image3D")),
										StringEscapeUtils.escapeHtml4(rs.getString("Status")));
								break;
							}
						} catch (Exception e) {
							request.setAttribute("error", "serverError");
							request.getRequestDispatcher("/admin/books").forward(request, response);
							return;
						}
						request.setAttribute("book", book_data);
						request.setAttribute("authorList", authorList);
						request.setAttribute("genreList", genreList);
						request.setAttribute("bookAuthorList", bookAuthorList);
						request.setAttribute("bookGenreList", bookGenreList);
						request.setAttribute("status", "update");
						request.getRequestDispatcher("/admin/bookRegistration.jsp").forward(request, response);
						return;
					} else {
						request.setAttribute("error", "invalid");
						request.getRequestDispatcher("/admin/books").forward(request, response);
						return;
					}
				} else {
					request.setAttribute("error", "invalid");
					request.getRequestDispatcher("/admin/books").forward(request, response);
					return;
				}
			}
		} else if (requestURi.endsWith("admin/books")) {
			if (!auth.testAdmin(session)) {
				request.setAttribute("error", "unauthorized");
				request.getRequestDispatcher("/admin/bookRegistration.jsp").forward(request, response);
				return;
			}

			boolean condition = book_db.getBook();
			if (condition) {
				ResultSet rs = book_db.getBookResult();
				try {
					while (rs.next()) {
						// sanitizing output by escaping html special characters
						bookList.add(new Book(StringEscapeUtils.escapeHtml4(rs.getString("ISBNNo")),
								StringEscapeUtils.escapeHtml4(rs.getString("Title")), rs.getInt("Page"),
								rs.getDouble("Price"), StringEscapeUtils.escapeHtml4(rs.getString("Publisher")),
								rs.getDate("PublicationDate"), rs.getInt("Qty"), rs.getShort("Rating"),
								StringEscapeUtils.escapeHtml4(rs.getString("Description")),
								StringEscapeUtils.escapeHtml4(rs.getString("Image")),
								StringEscapeUtils.escapeHtml4(rs.getString("Image3D")),
								StringEscapeUtils.escapeHtml4(rs.getString("Status"))));
					}
				} catch (Exception e) {
					request.setAttribute("error", "serverRetrieveError");
				}
			} else {
				request.setAttribute("error", "serverRetrieveError");
			}

			// set the author arraylist as an attribute
			request.setAttribute("bookList", bookList);
			request.setAttribute("servlet", "true");

			targetPage = "/admin/bookList.jsp";
		} else if (requestURi.contains("/admin/bookDelete/")) {
			if (!auth.testAdmin(session)) {
				request.setAttribute("error", "unauthorized");
				request.getRequestDispatcher("/admin/bookRegistration.jsp").forward(request, response);
				return;
			}

			doDelete(request, response);
		} else if (requestURi.endsWith("/books/latestrelease")) {
			ArrayList<Book> book = new ArrayList<Book>();
			String error = "";
			if (book_db.getLatestBook(9)) {
				ResultSet rs = book_db.getBookResult();

				try {
					while (rs.next()) {
						book.add(new Book(StringEscapeUtils.escapeHtml4(rs.getString("ISBNNo")),
								StringEscapeUtils.escapeHtml4(rs.getString("Title")), rs.getInt("Page"),
								rs.getDouble("Price"), StringEscapeUtils.escapeHtml4(rs.getString("Publisher")),
								rs.getDate("PublicationDate"), rs.getInt("Qty"), rs.getShort("Rating"),
								StringEscapeUtils.escapeHtml4(rs.getString("Description")),
								StringEscapeUtils.escapeHtml4(rs.getString("Image")),
								StringEscapeUtils.escapeHtml4(rs.getString("Image3D")),
								StringEscapeUtils.escapeHtml4(rs.getString("Status"))));
					}
				} catch (Exception e) {
					error = "serverError";
				}
			} else {
				error = "serverError";
			}
			Gson gson = new Gson();
			JSONObjects<Book> obj = new JSONObjects<>(book, error);
			String json = gson.toJson(obj);
			response.setContentType("application/json");
			response.getWriter().write(json);
			return;
		} else if (requestURi.endsWith("/books/latest")) {
			ArrayList<Book> book = new ArrayList<Book>();
			String error = "";
			if (book_db.getLatestBook(1)) {
				ResultSet rs = book_db.getBookResult();

				try {
					while (rs.next()) {
						book.add(new Book(StringEscapeUtils.escapeHtml4(rs.getString("ISBNNo")),
								StringEscapeUtils.escapeHtml4(rs.getString("Title")), rs.getInt("Page"),
								rs.getDouble("Price"), StringEscapeUtils.escapeHtml4(rs.getString("Publisher")),
								rs.getDate("PublicationDate"), rs.getInt("Qty"), rs.getShort("Rating"),
								StringEscapeUtils.escapeHtml4(rs.getString("Description")),
								StringEscapeUtils.escapeHtml4(rs.getString("Image")),
								StringEscapeUtils.escapeHtml4(rs.getString("Image3D")),
								StringEscapeUtils.escapeHtml4(rs.getString("Status"))));
					}
				} catch (Exception e) {
					error = "serverError";
				}
			} else {
				error = "serverError";
			}
			Gson gson = new Gson();
			JSONObjects<Book> obj = new JSONObjects<>(book, error);
			String json = gson.toJson(obj);
			response.setContentType("application/json");
			response.getWriter().write(json);
			return;
		}

		else {
			targetPage = "/signin.jsp";
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
		ServletContext context = request.getServletContext();
		// Get the root path of the web application
		String rootPath = context.getRealPath("/");

		// Find the position of the ".metadata" directory
		int metadataIndex = rootPath.indexOf(".metadata");

		// Remove the ".metadata" and subsequent directories
		String currentProjectPath = rootPath.substring(0, metadataIndex);

		// Append the desired path relative to the root of the project
		String imagePath = currentProjectPath + "JAD CA1 BookHaven" + File.separator + "src" + File.separator + "main"
				+ File.separator + "webapp" + File.separator + "img" + File.separator + "books" + File.separator;
		String storedImagePath = "/img/books/";

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
				String image = null;
				String image3d = null;

				// process the form fields and file uploads
				for (FileItem item : items) {
					String fieldName = item.getFieldName();
					if (item.isFormField()) {
						// regular form field
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
						String fileName = item.getName();
						InputStream fileContent = item.getInputStream(); // get image content
						String uploadPath;

						if (fieldName.equals("image") && !fileName.isEmpty()) {
							uploadPath = imagePath + "normal" + File.separator + fileName; // image upload destination
							File directory = new File(uploadPath).getParentFile();
							if (!directory.exists()) {
								directory.mkdirs();
							}
							Files.copy(fileContent, Paths.get(uploadPath), StandardCopyOption.REPLACE_EXISTING);
							image = storedImagePath + "normal/" + fileName;
						}
						if (fieldName.equals("image3d") && !fileName.isEmpty()) {
							uploadPath = imagePath + "3d" + File.separator + fileName; // image upload destination
							File directory = new File(uploadPath).getParentFile();
							if (!directory.exists()) {
								directory.mkdirs();
							}
							Files.copy(fileContent, Paths.get(uploadPath), StandardCopyOption.REPLACE_EXISTING);
							image3d = storedImagePath + "3d/" + fileName;
						}
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
				String bookstatus = "available";
				String description = fields.get("description");
				String status = fields.get("status");
				String oldimage = fields.get("oldimage");
				String oldimage3d = fields.get("oldimage3d");

				// checking null and empty values
				if (isbn != null && !isbn.isEmpty() && title != null && !title.isEmpty() && page != null
						&& !page.isEmpty() && price != null && !price.isEmpty() && qty != null && !qty.isEmpty()
						&& publisher != null && !publisher.isEmpty() && publicationdate != null
						&& !publicationdate.isEmpty() && authors.size() != 0 && status != null && !status.isEmpty()
						&& genres.size() != 0) {

					if (Integer.parseInt(qty) <= 0) {
						bookstatus = "unavailable";
					}

					// test with regular expressions
					if (TestReg.matchISBN(isbn) && TestReg.matchInteger(page) && TestReg.matchDecimal(price)
							&& TestReg.matchInteger(qty) && TestReg.matchDate(publicationdate)
							&& TestReg.matchIntegerArrayList(authors) && TestReg.matchIntegerArrayList(genres)) {

						Date publicationDate = Date.valueOf(LocalDate.parse(publicationdate));

						BookDatabase book_db = new BookDatabase();
						book_db.clearBookResult();
						int count = 0;

						if (book_db.getBookByISBN(isbn)) {
							ResultSet rs = book_db.getBookResult();
							try {
								while (rs.next()) {
									count++;
								}

							} catch (Exception e) {
								request.setAttribute("error", "serverError");
								request.getRequestDispatcher("/admin/bookRegistration.jsp").forward(request, response);
								return;
							}
						} else {
							request.setAttribute("error", "serverError");
							request.getRequestDispatcher("/admin/bookRegistration.jsp").forward(request, response);
							return;
						}

						if (status.equals("register")) {
							if (count > 0) {
								request.setAttribute("error", "duplicate");
								request.getRequestDispatcher("/admin/bookRegistration.jsp").forward(request, response);
								return;
							}
							if (book_db.registerBook(new Book(isbn, title, Integer.parseInt(page),
									Double.parseDouble(price), publisher, publicationDate, Integer.parseInt(qty),
									description, image, image3d, bookstatus))) {
								if (book_db.registerBookAuthor(authors, isbn)) {
									if (book_db.registerBookGenre(genres, isbn)) {
										request.setAttribute("success", "register");
										request.getRequestDispatcher("/admin/bookRegistration.jsp").forward(request,
												response);
										return;
									} else {
										book_db.deleteBookGenre(0, isbn);
										book_db.deleteBookAuthor(0, isbn);
										request.setAttribute("error", "genreError");
										request.getRequestDispatcher("/admin/bookRegistration.jsp").forward(request,
												response);
										return;
									}
								} else {
									book_db.deleteBookAuthor(0, isbn);
									request.setAttribute("error", "authorError");
									request.getRequestDispatcher("/admin/bookRegistration.jsp").forward(request,
											response);
									return;
								}
							} else {
								request.setAttribute("error", "serverError");
								request.getRequestDispatcher("/admin/bookRegistration.jsp").forward(request, response);
								return;
							}
						} else if (status.equals("update")) {
							if (image == null) {
								if (oldimage != null && !oldimage.isEmpty() && !oldimage.equals("null")) {
									image = oldimage;
								}
							}
							if (image3d == null) {
								if (oldimage3d != null && !oldimage3d.isEmpty() && !oldimage3d.equals("null")) {
									image3d = oldimage3d;
								}
							}
							if (count == 0) {
								request.setAttribute("error", "duplicate");
								request.getRequestDispatcher("/admin/bookRegistration.jsp").forward(request, response);
								return;
							}
							if (book_db.updateBook(new Book(isbn, title, Integer.parseInt(page),
									Double.parseDouble(price), publisher, publicationDate, Integer.parseInt(qty),
									description, image, image3d, bookstatus))) {
								book_db.deleteBookAuthor(0, isbn);
								if (book_db.registerBookAuthor(authors, isbn)) {
									book_db.deleteBookGenre(0, isbn);
									if (book_db.registerBookGenre(genres, isbn)) {
										request.setAttribute("success", "update");
										request.getRequestDispatcher("/admin/bookRegistration.jsp").forward(request,
												response);
										return;
									} else {
										book_db.deleteBookGenre(0, isbn);
										book_db.deleteBookAuthor(0, isbn);
										request.setAttribute("error", "genreError");
										request.getRequestDispatcher("/admin/bookRegistration.jsp").forward(request,
												response);
										return;
									}
								} else {
									book_db.deleteBookAuthor(0, isbn);
									request.setAttribute("error", "authorError");
									request.getRequestDispatcher("/admin/bookRegistration.jsp").forward(request,
											response);
									return;
								}
							} else {
								request.setAttribute("error", "serverError");
								request.getRequestDispatcher("/admin/bookRegistration.jsp").forward(request, response);
								return;
							}
						} else {
							request.setAttribute("error", "unauthorized");
							request.getRequestDispatcher("/admin/bookList.jsp").forward(request, response);
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
				System.out.println(e);
				request.setAttribute("error", "upload");
				request.getRequestDispatcher("/admin/bookRegistration.jsp").forward(request, response);
				return;
			}
		}
	}

	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Authentication auth = new Authentication();
		HttpSession session = request.getSession();
		if (!auth.testAdmin(session)) {
			request.setAttribute("error", "unauthorized");
			request.getRequestDispatcher("/admin/adminHomePage.jsp").forward(request, response);
			return;
		}

		BookDatabase book_db = new BookDatabase();
		book_db.clearBookResult();
		String requestURi = request.getRequestURI();
		String[] parts = requestURi.split("/");
		if (parts.length == 0) {
			request.setAttribute("error", "invalid");
			request.getRequestDispatcher("/admin/bookList.jsp").forward(request, response);
			return;
		} else {
			String id = parts[parts.length - 1];

			if (TestReg.matchISBN(id)) {
				if (book_db.deleteBookGenre(0, id)) {
					if (book_db.deleteBookAuthor(0, id)) {
						if (book_db.deleteBook(id)) {
							request.setAttribute("success", "delete");
							request.getRequestDispatcher("/admin/bookList.jsp").forward(request, response);
							return;
						} else {
							request.setAttribute("error", "serverError");
							request.getRequestDispatcher("/admin/bookList.jsp").forward(request, response);
							return;
						}
					} else {
						request.setAttribute("error", "serverError");
						request.getRequestDispatcher("/admin/bookList.jsp").forward(request, response);
						return;
					}
				} else {
					request.setAttribute("error", "serverError");
					request.getRequestDispatcher("/admin/bookList.jsp").forward(request, response);
					return;
				}
			} else {
				request.setAttribute("error", "invalid");
				request.getRequestDispatcher("/admin/bookList.jsp").forward(request, response);
				return;
			}
		}
	}
}

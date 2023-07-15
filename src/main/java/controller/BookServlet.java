//// Author: Zay Yar Tun
//// Admin No: 2235035
//// Class: DIT/FT/2A/02
//// Group: 10
//// Date: 7.6.2023
//// Description: middleware for book
//
//package controller;
//
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStream;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.nio.file.StandardCopyOption;
//import java.sql.Date;
//import java.sql.ResultSet;
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.servlet.ServletContext;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.MultipartConfig;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//import org.apache.commons.text.StringEscapeUtils;
//import org.apache.tomcat.util.http.fileupload.FileItem;
//import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
//import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
//import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;
//
//import com.google.gson.Gson;
//
//import model.Author;
//import model.AuthorDatabase;
//import model.Book;
//import model.BookDatabase;
//import model.Genre;
//import model.GenreDatabase;
//
///**
// * Servlet implementation class BookServlet
// */
//@WebServlet(urlPatterns = { "/admin/books", "/cart/remove/*", "/cart/bookdetail", "/book/qty/*", "/book/*", "/books/latestrelease",
//		"/books/latest", "/admin/bookRegistration", "/admin/bookUpdate/*", "/admin/bookDelete/*", "/book/genres/*" })
///**
// * Servlet implementation class BookServlet
// */
//@MultipartConfig(location = "/tmp", maxFileSize = 20971520, maxRequestSize = 41943040, fileSizeThreshold = 1048576)
//public class BookServlet extends HttpServlet {
//	private static final long serialVersionUID = 1L;
//
//	/**
//	 * @see HttpServlet#HttpServlet()
//	 */
//	public BookServlet() {
//		super();
//		// TODO Auto-generated constructor stub
//	}
//
//	/**
//	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
//	 *      response)
//	 */
//	protected void doGet(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//
//		HttpSession session = request.getSession();
//		Authentication auth = new Authentication();
//
//		ArrayList<Author> authorList = new ArrayList<Author>();
//		ArrayList<Genre> genreList = new ArrayList<Genre>();
//		ArrayList<Book> bookList = new ArrayList<Book>();
//
//		BookDatabase book_db = new BookDatabase();
//		AuthorDatabase author_db = new AuthorDatabase();
//		GenreDatabase genre_db = new GenreDatabase();
//		book_db.clearBookResult();
//		author_db.clearAuthorResult();
//
//		request.setAttribute("servlet", "true");
//
//		String targetPage = "";
//		String requestURi = request.getRequestURI();
//		if(requestURi.contains("/cart/remove/")) {
//			if (!auth.testMember(session)) {
//				request.setAttribute("error", "unauthorized");
//				request.getRequestDispatcher("/signout.jsp").forward(request, response);
//				return;
//			}
//			
//			String status = "";
//			String[] parts = requestURi.split("/");
//			if (parts.length == 0) {
//				status = "invalid";
//			} else {
//				String isbn = parts[parts.length - 1].trim();
//				if(isbn != null && !isbn.isEmpty() && TestReg.matchISBN(isbn)) {
//					ArrayList<Book> cart = (ArrayList<Book>)session.getAttribute("cart");
//					if(cart != null) {
//						for(int i = 0; i < cart.size(); i++) {
//							if(cart.get(i).getISBNNo().equals(isbn)) {
//								cart.remove(i);
//								break;
//							}
//						}
//					}
//					else {
//						status = "invalid";
//					}
//				}
//				else {
//					status = "invalid";
//				}
//			}
//			Gson gson = new Gson();
//			JSONObjects<Book> obj = new JSONObjects<>(status);
//			String json = gson.toJson(obj);
//			response.setContentType("application/json");
//			response.getWriter().write(json);
//			return;
//		}
//		else if (requestURi.endsWith("/cart/bookdetail")) {
//			if (!auth.testMember(session)) {
//				request.setAttribute("error", "unauthorized");
//				request.getRequestDispatcher("/signout.jsp").forward(request, response);
//				return;
//			}
//			
//			String status = "";
//			ArrayList<Book> cart = (ArrayList<Book>) session.getAttribute("cart");
//			if (cart != null) {
//				for (int i = 0; i < cart.size(); i++) {
//					book_db.clearBookResult();
//					if (book_db.getBookByISBN(cart.get(i).getISBNNo())) {
//						ResultSet rs = book_db.getBookResult();
//						try {
//							while (rs.next()) {
//								book_db.clearBookResult();
//								authorList.clear();
//								if(book_db.getAuthorByISBN(StringEscapeUtils.escapeHtml4(rs.getString("ISBNNo")))) {
//									ResultSet rs2 = book_db.getBookResult();
//									while(rs2.next()) {
//										authorList.add(new Author(rs2.getInt("AuthorID"), 											StringEscapeUtils.escapeHtml4(rs2.getString("Name"))));
//									}
//								}
//								else {
//									status = "serverError";
//									break;
//								}
//								Author[] authors = new Author[authorList.size()];
//								for(int j = 0; j < authorList.size(); j++) {
//									authors[j] = new Author(authorList.get(j).getAuthorID(), authorList.get(j).getName());
//								}
//								Book book = new Book();
//								book.setISBNNo(StringEscapeUtils.escapeHtml4(rs.getString("ISBNNo")));
//								book.setTitle(StringEscapeUtils.escapeHtml4(rs.getString("Title")));
//								book.setPage(rs.getInt("Page"));
//								book.setPrice(rs.getDouble("Price"));
//								book.setPublisher(StringEscapeUtils.escapeHtml4(rs.getString("Publisher")));
//								book.setPublicationDate(rs.getDate("PublicationDate"));
//								book.setQty(rs.getInt("Qty"));
//								book.setRating(rs.getDouble("Rating"));
//								book.setDescription(StringEscapeUtils.escapeHtml4(rs.getString("Description")));
//								book.setImage(StringEscapeUtils.escapeHtml4(rs.getString("Image")));
//								book.setImage3D(StringEscapeUtils.escapeHtml4(rs.getString("Image3D")));
//								book.setStatus(StringEscapeUtils.escapeHtml4(rs.getString("Status")));
//								book.setAuthors(authorList);
//								bookList.add(book);
//							}
//						} catch (Exception e) {
//							status = "serverError";
//							break;
//						}
//					} else {
//						status = "serverError";
//						break;
//					}
//				}
//			}
//			Gson gson = new Gson();
//			JSONObjects<Book> obj = new JSONObjects<>(bookList, status);
//			String json = gson.toJson(obj);
//			response.setContentType("application/json");
//			response.getWriter().write(json);
//			return;
//		} else if (requestURi.contains("/book/qty/")) {
//			if (!auth.testMember(session)) {
//				request.setAttribute("error", "unauthorized");
//				request.getRequestDispatcher("/signout.jsp").forward(request, response);
//				return;
//			}
//			
//			String status = "";
//			String[] parts = requestURi.split("/");
//			if (parts.length == 0 && parts.length != 5) {
//				status = "invalid";
//			} else {
//				String isbn = parts[parts.length - 2].trim();
//				if (isbn != null && !isbn.isEmpty() && TestReg.matchISBN(isbn)) {
//					String qty = parts[parts.length - 1].trim();
//					if (qty == null || !TestReg.matchNegInteger(qty)) {
//						status = "invalid";
//					} else {
//						int stockQty = book_db.getBookQtyByISBN(isbn);
//						if (stockQty == -1) {
//							status = "serverError";
//						} else if (stockQty >= Integer.parseInt(qty)) {
//							status = "ok";
//							ArrayList<Book> cart = (ArrayList<Book>) session.getAttribute("cart");
//							if (cart == null) {
//								cart = new ArrayList<Book>();
//							}
//							boolean isFound = false;
//							for(int i = 0; i < cart.size(); i++) {
//								if(cart.get(i).getISBNNo().equals(isbn)) {
//									isFound = true;
//									int preqty = cart.get(i).getQty();
//									cart.get(i).setQty(preqty + Integer.parseInt(qty));
//									if(cart.get(i).getQty() > stockQty) {
//										status = "full";
//										cart.get(i).setQty(preqty);
//									}
//									if(cart.get(i).getQty() <= 0) {
//										cart.remove(i);
//									}
//									break;
//								}
//							}
//							if(!isFound) {
//								Book book = new Book();
//								book.setISBNNo(isbn);
//								book.setQty(Integer.parseInt(qty));
//								cart.add(book);	
//							}
//							session.setAttribute("cart", cart);
//						} else {
//							status = "invalid";
//						}
//					}
//				} else {
//					status = "invalid";
//				}
//			}
//			Gson gson = new Gson();
//			JSONObjects<Book> obj = new JSONObjects<>(status);
//			String json = gson.toJson(obj);
//			response.setContentType("application/json");
//			response.getWriter().write(json);
//			return;
//		} else if (requestURi.contains("/book/genres/")) {
//			String status = "";
//			String[] parts = requestURi.split("/");
//			if (parts.length == 0 && parts.length != 5) {
//				status = "invalid";
//			} else {
//				String isbn = parts[parts.length - 2].trim();
//				if (isbn != null && !isbn.isEmpty() && TestReg.matchISBN(isbn)) {
//					String[] genres = parts[parts.length - 1].split(",");
//					if (genres == null) {
//						status = "invalid";
//					} else {
//						for (int i = 0; i < genres.length; i++) {
//							if (bookList.size() == 2) {
//								break;
//							}
//							book_db.clearBookResult();
//							if (book_db.getBookByGenre(genres[i].trim())) {
//								ResultSet rs = book_db.getBookResult();
//								try {
//									while (rs.next()) {
//										if (!StringEscapeUtils.escapeHtml4(rs.getString("ISBNNo")).equals(isbn)) {
//											Book book = new Book();
//											book.setISBNNo(StringEscapeUtils.escapeHtml4(rs.getString("ISBNNo")));
//											book.setTitle(StringEscapeUtils.escapeHtml4(rs.getString("Title")));
//											book.setPage(rs.getInt("Page"));
//											book.setPrice(rs.getDouble("Price"));
//											book.setPublisher(StringEscapeUtils.escapeHtml4(rs.getString("Publisher")));
//											book.setPublicationDate(rs.getDate("PublicationDate"));
//											book.setQty(rs.getInt("Qty"));
//											book.setRating(rs.getDouble("Rating"));
//											book.setDescription(StringEscapeUtils.escapeHtml4(rs.getString("Description")));
//											book.setImage(StringEscapeUtils.escapeHtml4(rs.getString("Image")));
//											book.setImage3D(StringEscapeUtils.escapeHtml4(rs.getString("Image3D")));
//											book.setStatus(StringEscapeUtils.escapeHtml4(rs.getString("Status")));
//											bookList.add(book);
//										}
//										if (bookList.size() == 2) {
//											break;
//										}
//									}
//								} catch (Exception e) {
//									status = "serverError";
//								}
//							}
//						}
//					}
//				} else {
//					status = "invalid";
//				}
//			}
//			Gson gson = new Gson();
//			JSONObjects<Book> obj = new JSONObjects<>(bookList, status);
//			String json = gson.toJson(obj);
//			response.setContentType("application/json");
//			response.getWriter().write(json);
//			return;
//		} else if (requestURi.contains("/book/")) {
//			String status = "";
//			String[] parts = requestURi.split("/");
//			if (parts.length == 0) {
//				status = "invalid";
//			} else {
//				String id = parts[parts.length - 1];
//				if (TestReg.matchISBN(id)) {
//					if (book_db.getBookByISBN(id)) {
//						ResultSet rs = book_db.getBookResult();
//						try {
//							while (rs.next()) {
//								Book book = new Book();
//								book.setISBNNo(StringEscapeUtils.escapeHtml4(rs.getString("ISBNNo")));
//								book.setTitle(StringEscapeUtils.escapeHtml4(rs.getString("Title")));
//								book.setPage(rs.getInt("Page"));
//								book.setPrice(rs.getDouble("Price"));
//								book.setPublisher(StringEscapeUtils.escapeHtml4(rs.getString("Publisher")));
//								book.setPublicationDate(rs.getDate("PublicationDate"));
//								book.setQty(rs.getInt("Qty"));
//								book.setRating(rs.getDouble("Rating"));
//								book.setDescription(StringEscapeUtils.escapeHtml4(rs.getString("Description")));
//								book.setImage(StringEscapeUtils.escapeHtml4(rs.getString("Image")));
//								book.setImage3D(StringEscapeUtils.escapeHtml4(rs.getString("Image3D")));
//								book.setStatus(StringEscapeUtils.escapeHtml4(rs.getString("Status")));
//								bookList.add(book);
//							}
//						} catch (Exception e) {
//							status = "serverError";
//						}
//						if (bookList.size() != 1) {
//							status = "invalid";
//						} else {
//							book_db.clearBookResult();
//							if (book_db.getGenreByISBN(id)) {
//								rs = book_db.getBookResult();
//								try {
//									while (rs.next()) {
//										genreList.add(new Genre(rs.getInt("GenreID"),
//												StringEscapeUtils.escapeHtml4(rs.getString("Genre"))));
//									}
//								} catch (Exception e) {
//									status = "serverError";
//								}
//							}
//							if (genreList.size() == 0) {
//								status = "invalid";
//							} else {
//								book_db.clearBookResult();
//								if (book_db.getAuthorByISBN(id)) {
//									rs = book_db.getBookResult();
//									try {
//										while (rs.next()) {
//											authorList.add(new Author(rs.getInt("AuthorID"),
//													StringEscapeUtils.escapeHtml4(rs.getString("Name"))));
//										}
//									} catch (Exception e) {
//										status = "serverError";
//									}
//									if (authorList.size() == 0) {
//										status = "invalid";
//									} else {
//										status = "success";
//									}
//								}
//							}
//						}
//					} else {
//						status = "serverError";
//					}
//				} else {
//					status = "invalid";
//				}
//			}
//
//			Gson gson = new Gson();
//			JSONObjects<Book> obj = new JSONObjects<>(bookList, authorList, genreList, status);
//			String json = gson.toJson(obj);
//			response.setContentType("application/json");
//			response.getWriter().write(json);
//			return;
//		} else if (requestURi.endsWith("admin/bookRegistration")) {
//			if (!auth.testAdmin(session)) {
//				request.setAttribute("error", "unauthorized");
//				request.getRequestDispatcher("/signout.jsp").forward(request, response);
//				return;
//			}
//
//			// get author data from database and put data in arraylist
//			try {
//				authorList = author_db.getAuthor();
//			}
//			catch(Exception e) {
//				e.printStackTrace();
//				request.setAttribute("error", "serverError");
//			}
//
//			// get genre data from database and put data in arraylist
//			if (genre_db.getGenre()) {
//				ResultSet rs = genre_db.getGenreResult();
//				try {
//					while (rs.next()) {
//						// sanitizing output by escaping html special characters
//						genreList.add(
//								new Genre(rs.getInt("GenreID"), StringEscapeUtils.escapeHtml4(rs.getString("Genre"))));
//					}
//				} catch (Exception e) {
//					request.setAttribute("error", "serverError");
//				}
//			} else {
//				request.setAttribute("error", "serverError");
//			}
//
//			request.setAttribute("authorList", authorList);
//			request.setAttribute("genreList", genreList);
//
//			targetPage = "/admin/bookRegistration.jsp";
//		} else if (requestURi.contains("admin/bookUpdate")) {
//			if (!auth.testAdmin(session)) {
//				request.setAttribute("error", "unauthorized");
//				request.getRequestDispatcher("/signout.jsp").forward(request, response);
//				return;
//			}
//
//			ArrayList<Author> bookAuthorList = new ArrayList<Author>();
//			ArrayList<Genre> bookGenreList = new ArrayList<Genre>();
//
//			String[] parts = requestURi.split("/");
//			if (parts.length == 0) {
//				request.setAttribute("error", "invalid");
//				request.getRequestDispatcher("/admin/books").forward(request, response);
//				return;
//			} else {
//				String id = parts[parts.length - 1];
//				if (TestReg.matchISBN(id)) {
//					// get author data from database and put data in arraylist
//					try {
//						authorList = author_db.getAuthor();
//					}
//					catch(Exception e) {
//						e.printStackTrace();
//						request.setAttribute("error", "serverError");
//					}
//
//					if (book_db.getBookAuthorByISBN(id)) {
//						ResultSet rs = book_db.getBookResult();
//						book_db.clearBookResult();
//						try {
//							while (rs.next()) {
//								bookAuthorList.add(new Author(rs.getInt("AuthorID")));
//							}
//						} catch (Exception e) {
//							request.setAttribute("error", "serverError");
//						}
//					}
//
//					// get genre data from database and put data in arraylist
//					if (genre_db.getGenre()) {
//						ResultSet rs = genre_db.getGenreResult();
//						try {
//							while (rs.next()) {
//								// sanitizing output by escaping html special characters
//								genreList.add(new Genre(rs.getInt("GenreID"),
//										StringEscapeUtils.escapeHtml4(rs.getString("Genre"))));
//							}
//						} catch (Exception e) {
//							request.setAttribute("error", "serverError");
//						}
//					} else {
//						request.setAttribute("error", "serverError");
//					}
//
//					if (book_db.getBookGenreByISBN(id)) {
//						ResultSet rs = book_db.getBookResult();
//						book_db.clearBookResult();
//						try {
//							while (rs.next()) {
//								bookGenreList.add(new Genre(rs.getInt("GenreID")));
//							}
//						} catch (Exception e) {
//							request.setAttribute("error", "serverError");
//						}
//					}
//
//					// retrieving book data
//					if (book_db.getBookByISBN(id)) {
//						ResultSet rs = book_db.getBookResult();
//						Book book_data = null;
//
//						try {
//							while (rs.next()) {
//								// sanitizing output by escaping html special characters
//								book_data = new Book();
//								book_data.setISBNNo(StringEscapeUtils.escapeHtml4(rs.getString("ISBNNo")));
//								book_data.setTitle(StringEscapeUtils.escapeHtml4(rs.getString("Title")));
//								book_data.setPage(rs.getInt("Page"));
//								book_data.setPrice(rs.getDouble("Price"));
//								book_data.setPublisher(StringEscapeUtils.escapeHtml4(rs.getString("Publisher")));
//								book_data.setPublicationDate(rs.getDate("PublicationDate"));
//								book_data.setQty(rs.getInt("Qty"));
//								book_data.setRating(rs.getDouble("Rating"));
//								book_data.setDescription(StringEscapeUtils.escapeHtml4(rs.getString("Description")));
//								book_data.setImage(StringEscapeUtils.escapeHtml4(rs.getString("Image")));
//								book_data.setImage3D(StringEscapeUtils.escapeHtml4(rs.getString("Image3D")));
//								book_data.setStatus(StringEscapeUtils.escapeHtml4(rs.getString("Status")));
//								break;
//							}
//						} catch (Exception e) {
//							request.setAttribute("error", "serverError");
//							request.getRequestDispatcher("/admin/books").forward(request, response);
//							return;
//						}
//						request.setAttribute("book", book_data);
//						request.setAttribute("authorList", authorList);
//						request.setAttribute("genreList", genreList);
//						request.setAttribute("bookAuthorList", bookAuthorList);
//						request.setAttribute("bookGenreList", bookGenreList);
//						request.setAttribute("status", "update");
//						request.getRequestDispatcher("/admin/bookRegistration.jsp").forward(request, response);
//						return;
//					} else {
//						request.setAttribute("error", "invalid");
//						request.getRequestDispatcher("/admin/books").forward(request, response);
//						return;
//					}
//				} else {
//					request.setAttribute("error", "invalid");
//					request.getRequestDispatcher("/admin/books").forward(request, response);
//					return;
//				}
//			}
//		} else if (requestURi.endsWith("admin/books")) {
//			if (!auth.testAdmin(session)) {
//				request.setAttribute("error", "unauthorized");
//				request.getRequestDispatcher("/signout.jsp").forward(request, response);
//				return;
//			}
//
//			boolean condition = book_db.getBook();
//			if (condition) {
//				ResultSet rs = book_db.getBookResult();
//				try {
//					while (rs.next()) {
//						// sanitizing output by escaping html special characters
//						Book book = new Book();
//						book.setISBNNo(StringEscapeUtils.escapeHtml4(rs.getString("ISBNNo")));
//						book.setTitle(StringEscapeUtils.escapeHtml4(rs.getString("Title")));
//						book.setPage(rs.getInt("Page"));
//						book.setPrice(rs.getDouble("Price"));
//						book.setPublisher(StringEscapeUtils.escapeHtml4(rs.getString("Publisher")));
//						book.setPublicationDate(rs.getDate("PublicationDate"));
//						book.setQty(rs.getInt("Qty"));
//						book.setRating(rs.getDouble("Rating"));
//						book.setDescription(StringEscapeUtils.escapeHtml4(rs.getString("Description")));
//						book.setImage(StringEscapeUtils.escapeHtml4(rs.getString("Image")));
//						book.setImage3D(StringEscapeUtils.escapeHtml4(rs.getString("Image3D")));
//						book.setStatus(StringEscapeUtils.escapeHtml4(rs.getString("Status")));
//						bookList.add(book);
//					}
//				} catch (Exception e) {
//					request.setAttribute("error", "serverRetrieveError");
//				}
//			} else {
//				request.setAttribute("error", "serverRetrieveError");
//			}
//
//			// set the author arraylist as an attribute
//			request.setAttribute("bookList", bookList);
//			request.setAttribute("servlet", "true");
//
//			targetPage = "/admin/bookList.jsp";
//		} else if (requestURi.contains("/admin/bookDelete/")) {
//			if (!auth.testAdmin(session)) {
//				request.setAttribute("error", "unauthorized");
//				request.getRequestDispatcher("/signout.jsp").forward(request, response);
//				return;
//			}
//
//			doDelete(request, response);
//		} else if (requestURi.endsWith("/books/latestrelease")) {
//			ArrayList<Book> book = new ArrayList<Book>();
//			String error = "";
//			if (book_db.getLatestBook(6)) {
//				ResultSet rs = book_db.getBookResult();
//
//				try {
//					while (rs.next()) {
//						Book tmp_book = new Book();
//						tmp_book.setISBNNo(StringEscapeUtils.escapeHtml4(rs.getString("ISBNNo")));
//						tmp_book.setTitle(StringEscapeUtils.escapeHtml4(rs.getString("Title")));
//						tmp_book.setPage(rs.getInt("Page"));
//						tmp_book.setPrice(rs.getDouble("Price"));
//						tmp_book.setPublisher(StringEscapeUtils.escapeHtml4(rs.getString("Publisher")));
//						tmp_book.setPublicationDate(rs.getDate("PublicationDate"));
//						tmp_book.setQty(rs.getInt("Qty"));
//						tmp_book.setRating(rs.getDouble("Rating"));
//						tmp_book.setDescription(StringEscapeUtils.escapeHtml4(rs.getString("Description")));
//						tmp_book.setImage(StringEscapeUtils.escapeHtml4(rs.getString("Image")));
//						tmp_book.setImage3D(StringEscapeUtils.escapeHtml4(rs.getString("Image3D")));
//						tmp_book.setStatus(StringEscapeUtils.escapeHtml4(rs.getString("Status")));
//						book.add(tmp_book);
//					}
//				} catch (Exception e) {
//					error = "serverError";
//				}
//			} else {
//				error = "serverError";
//			}
//			Gson gson = new Gson();
//			JSONObjects<Book> obj = new JSONObjects<>(book, error);
//			String json = gson.toJson(obj);
//			response.setContentType("application/json");
//			response.getWriter().write(json);
//			return;
//		} else if (requestURi.endsWith("/books/latest")) {
//			ArrayList<Book> book = new ArrayList<Book>();
//			String error = "";
//			if (book_db.getLatestBook(1)) {
//				ResultSet rs = book_db.getBookResult();
//
//				try {
//					while (rs.next()) {
//						Book tmp_book = new Book();
//						tmp_book.setISBNNo(StringEscapeUtils.escapeHtml4(rs.getString("ISBNNo")));
//						tmp_book.setTitle(StringEscapeUtils.escapeHtml4(rs.getString("Title")));
//						tmp_book.setPage(rs.getInt("Page"));
//						tmp_book.setPrice(rs.getDouble("Price"));
//						tmp_book.setPublisher(StringEscapeUtils.escapeHtml4(rs.getString("Publisher")));
//						tmp_book.setPublicationDate(rs.getDate("PublicationDate"));
//						tmp_book.setQty(rs.getInt("Qty"));
//						tmp_book.setRating(rs.getDouble("Rating"));
//						tmp_book.setDescription(StringEscapeUtils.escapeHtml4(rs.getString("Description")));
//						tmp_book.setImage(StringEscapeUtils.escapeHtml4(rs.getString("Image")));
//						tmp_book.setImage3D(StringEscapeUtils.escapeHtml4(rs.getString("Image3D")));
//						tmp_book.setStatus(StringEscapeUtils.escapeHtml4(rs.getString("Status")));
//						book.add(tmp_book);
//					}
//				} catch (Exception e) {
//					error = "serverError";
//				}
//			} else {
//				error = "serverError";
//			}
//			Gson gson = new Gson();
//			JSONObjects<Book> obj = new JSONObjects<>(book, error);
//			String json = gson.toJson(obj);
//			response.setContentType("application/json");
//			response.getWriter().write(json);
//			return;
//		}
//
//		else {
//			targetPage = "/signin.jsp";
//		}
//		request.getRequestDispatcher(targetPage).forward(request, response);
//		return;
//	}
//
//	/**
//	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
//	 *      response)
//	 */
//	protected void doPost(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//		// TODO Auto-generated method stub
//
//		HttpSession session = request.getSession();
//		Authentication auth = new Authentication();
//		ServletContext context = request.getServletContext();
//		// Get the root path of the web application
//		String rootPath = context.getRealPath("/");
//
//		// Find the position of the ".metadata" directory
//		int metadataIndex = rootPath.indexOf(".metadata");
//
//		// Remove the ".metadata" and subsequent directories
//		String currentProjectPath = rootPath.substring(0, metadataIndex);
//
//		// Append the desired path relative to the root of the project
//		String imagePath = currentProjectPath + "JAD CA1 BookHaven" + File.separator + "src" + File.separator + "main"
//				+ File.separator + "webapp" + File.separator + "img" + File.separator + "books" + File.separator;
//		String storedImagePath = "/img/books/";
//
//		if (!auth.testAdmin(session)) {
//			request.setAttribute("error", "unauthorized");
//			request.getRequestDispatcher("/signout.jsp").forward(request, response);
//			return;
//		}
//
//		ArrayList<String> authors = new ArrayList<String>();
//		ArrayList<String> genres = new ArrayList<String>();
//
//		// check if the request is multipart/form-data
//		if (ServletFileUpload.isMultipartContent(request)) {
//			try {
//				// create handler for file upload
//				ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());
//
//				// parse the multipar request
//				List<FileItem> items = upload.parseRequest(new ServletRequestContext(request));
//
//				Map<String, String> fields = new HashMap<>();
//				String defaultimage = storedImagePath + "normal/defaultbook.png";
//				String defaultimage3d = storedImagePath + "3d/defaultbook_3d.png";
//				String image = null;
//				String image3d = null;
//
//				// process the form fields and file uploads
//				for (FileItem item : items) {
//					String fieldName = item.getFieldName();
//					if (item.isFormField()) {
//						// regular form field
//						String fieldValue = item.getString();
//
//						if (fieldName.equals("author")) {
//							authors.add(fieldValue);
//						} else if (fieldName.equals("genre")) {
//							genres.add(fieldValue);
//						} else {
//							fields.put(fieldName, fieldValue);
//						}
//					} else {
//						// file upload
//						String fileName = item.getName();
//						InputStream fileContent = item.getInputStream(); // get image content
//						String uploadPath;
//
//						if (fieldName.equals("image") && !fileName.isEmpty()) {
//							uploadPath = imagePath + "normal" + File.separator + fileName; // image upload destination
//							File directory = new File(uploadPath).getParentFile();
//							if (!directory.exists()) {
//								directory.mkdirs();
//							}
//							Files.copy(fileContent, Paths.get(uploadPath), StandardCopyOption.REPLACE_EXISTING);
//							image = storedImagePath + "normal/" + fileName;
//						}
//						if (fieldName.equals("image3d") && !fileName.isEmpty()) {
//							uploadPath = imagePath + "3d" + File.separator + fileName; // image upload destination
//							File directory = new File(uploadPath).getParentFile();
//							if (!directory.exists()) {
//								directory.mkdirs();
//							}
//							Files.copy(fileContent, Paths.get(uploadPath), StandardCopyOption.REPLACE_EXISTING);
//							image3d = storedImagePath + "3d/" + fileName;
//						}
//					}
//				}
//
//				// getting value from fields
//				String isbn = fields.get("isbn");
//				String title = fields.get("title");
//				String page = fields.get("page");
//				String price = fields.get("price");
//				String qty = fields.get("qty");
//				String publisher = fields.get("publisher");
//				String publicationdate = fields.get("publicationdate");
//				String bookstatus = "available";
//				String description = fields.get("description");
//				if(description != null) {
//					description = description.trim();
//				}
//				String status = fields.get("status");
//				String oldimage = fields.get("oldimage");
//				String oldimage3d = fields.get("oldimage3d");
//
//				// checking null and empty values
//				if (isbn != null && !isbn.isEmpty() && title != null && !title.isEmpty() && page != null
//						&& !page.isEmpty() && price != null && !price.isEmpty() && qty != null && !qty.isEmpty()
//						&& publisher != null && !publisher.isEmpty() && publicationdate != null
//						&& !publicationdate.isEmpty() && authors.size() != 0 && status != null && !status.isEmpty()
//						&& genres.size() != 0) {
//
//					if (Integer.parseInt(qty) <= 0) {
//						bookstatus = "unavailable";
//					}
//
//					// test with regular expressions
//					if (TestReg.matchISBN(isbn) && TestReg.matchInteger(page) && TestReg.matchDecimal(price)
//							&& TestReg.matchInteger(qty) && TestReg.matchDate(publicationdate)
//							&& TestReg.matchIntegerArrayList(authors) && TestReg.matchIntegerArrayList(genres)) {
//
//						Date publicationDate = Date.valueOf(LocalDate.parse(publicationdate));
//
//						BookDatabase book_db = new BookDatabase();
//						book_db.clearBookResult();
//						int count = 0;
//
//						if (book_db.getBookByISBN(isbn)) {
//							ResultSet rs = book_db.getBookResult();
//							try {
//								while (rs.next()) {
//									count++;
//								}
//
//							} catch (Exception e) {
//								request.setAttribute("error", "serverError");
//								request.getRequestDispatcher("/admin/bookRegistration.jsp").forward(request, response);
//								return;
//							}
//						} else {
//							request.setAttribute("error", "serverError");
//							request.getRequestDispatcher("/admin/bookRegistration.jsp").forward(request, response);
//							return;
//						}
//
//						if (status.equals("register")) {
//							if (count > 0) {
//								request.setAttribute("error", "duplicate");
//								request.getRequestDispatcher("/admin/bookRegistration.jsp").forward(request, response);
//								return;
//							}
//							if(image == null) {
//								image = defaultimage;
//							}
//							if(image3d == null) {
//								image3d = defaultimage3d;
//							}
//							Book book = new Book();
//							book.setISBNNo(isbn);
//							book.setTitle(title.trim());
//							book.setPage(Integer.parseInt(page));
//							book.setPrice(Double.parseDouble(price));
//							book.setPublisher(publisher.trim());
//							book.setPublicationDate(publicationDate);
//							book.setQty(Integer.parseInt(qty));
//							book.setDescription(description);
//							book.setImage(image);
//							book.setImage3D(image3d);
//							book.setStatus(bookstatus);
//							if (book_db.registerBook(book)) {
//								if (book_db.registerBookAuthor(authors, isbn)) {
//									if (book_db.registerBookGenre(genres, isbn)) {
//										request.setAttribute("success", "register");
//										request.getRequestDispatcher("/admin/bookRegistration.jsp").forward(request,
//												response);
//										return;
//									} else {
//										book_db.deleteBookGenre(0, isbn);
//										book_db.deleteBookAuthor(0, isbn);
//										book_db.deleteBook(isbn);
//										request.setAttribute("error", "genreError");
//										request.getRequestDispatcher("/admin/bookRegistration.jsp").forward(request,
//												response);
//										return;
//									}
//								} else {
//									book_db.deleteBookAuthor(0, isbn);
//									book_db.deleteBook(isbn);
//									request.setAttribute("error", "authorError");
//									request.getRequestDispatcher("/admin/bookRegistration.jsp").forward(request,
//											response);
//									return;
//								}
//							} else {
//								request.setAttribute("error", "serverError");
//								request.getRequestDispatcher("/admin/bookRegistration.jsp").forward(request, response);
//								return;
//							}
//						} else if (status.equals("update")) {
//							if (image == null) {
//								if (oldimage != null && !oldimage.isEmpty() && !oldimage.equals("null")) {
//									image = oldimage;
//								}
//							}
//							if (image3d == null) {
//								if (oldimage3d != null && !oldimage3d.isEmpty() && !oldimage3d.equals("null")) {
//									image3d = oldimage3d;
//								}
//							}
//							if (count == 0) {
//								request.setAttribute("error", "duplicate");
//								request.getRequestDispatcher("/admin/bookRegistration.jsp").forward(request, response);
//								return;
//							}
//							Book book = new Book();
//							book.setISBNNo(isbn);
//							book.setTitle(title.trim());
//							book.setPage(Integer.parseInt(page));
//							book.setPrice(Double.parseDouble(price));
//							book.setPublisher(publisher.trim());
//							book.setPublicationDate(publicationDate);
//							book.setQty(Integer.parseInt(qty));
//							book.setDescription(description);
//							book.setImage(image);
//							book.setImage3D(image3d);
//							book.setStatus(bookstatus);
//							if (book_db.updateBook(book)) {
//								book_db.deleteBookAuthor(0, isbn);
//								if (book_db.registerBookAuthor(authors, isbn)) {
//									book_db.deleteBookGenre(0, isbn);
//									if (book_db.registerBookGenre(genres, isbn)) {
//										request.setAttribute("success", "update");
//										request.getRequestDispatcher("/admin/bookRegistration.jsp").forward(request,
//												response);
//										return;
//									} else {
//										book_db.deleteBookGenre(0, isbn);
//										book_db.deleteBookAuthor(0, isbn);
//										request.setAttribute("error", "genreError");
//										request.getRequestDispatcher("/admin/bookRegistration.jsp").forward(request,
//												response);
//										return;
//									}
//								} else {
//									book_db.deleteBookAuthor(0, isbn);
//									request.setAttribute("error", "authorError");
//									request.getRequestDispatcher("/admin/bookRegistration.jsp").forward(request,
//											response);
//									return;
//								}
//							} else {
//								request.setAttribute("error", "serverError");
//								request.getRequestDispatcher("/admin/bookRegistration.jsp").forward(request, response);
//								return;
//							}
//						} else {
//							request.setAttribute("error", "unauthorized");
//							request.getRequestDispatcher("/signout.jsp").forward(request, response);
//							return;
//						}
//					} else {
//						request.setAttribute("error", "invalid");
//						request.getRequestDispatcher("/admin/bookRegistration.jsp").forward(request, response);
//						return;
//					}
//				} else {
//					request.setAttribute("error", "invalid");
//					request.getRequestDispatcher("/admin/bookRegistration.jsp").forward(request, response);
//					return;
//				}
//			} catch (Exception e) {
//				request.setAttribute("error", "upload");
//				request.getRequestDispatcher("/admin/bookRegistration.jsp").forward(request, response);
//				return;
//			}
//		}
//	}
//
//	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//
//		Authentication auth = new Authentication();
//		HttpSession session = request.getSession();
//		if (!auth.testAdmin(session)) {
//			request.setAttribute("error", "unauthorized");
//			request.getRequestDispatcher("/signout.jsp").forward(request, response);
//			return;
//		}
//
//		BookDatabase book_db = new BookDatabase();
//		book_db.clearBookResult();
//		String requestURi = request.getRequestURI();
//		String[] parts = requestURi.split("/");
//		if (parts.length == 0) {
//			request.setAttribute("error", "invalid");
//			request.getRequestDispatcher("/admin/bookList.jsp").forward(request, response);
//			return;
//		} else {
//			String id = parts[parts.length - 1];
//
//			if (TestReg.matchISBN(id)) {
//				if (book_db.deleteBookGenre(0, id)) {
//					if (book_db.deleteBookAuthor(0, id)) {
//						if (book_db.deleteBook(id)) {
//							request.setAttribute("success", "delete");
//							request.getRequestDispatcher("/admin/bookList.jsp").forward(request, response);
//							return;
//						} else {
//							request.setAttribute("error", "serverError");
//							request.getRequestDispatcher("/admin/bookList.jsp").forward(request, response);
//							return;
//						}
//					} else {
//						request.setAttribute("error", "serverError");
//						request.getRequestDispatcher("/admin/bookList.jsp").forward(request, response);
//						return;
//					}
//				} else {
//					request.setAttribute("error", "serverError");
//					request.getRequestDispatcher("/admin/bookList.jsp").forward(request, response);
//					return;
//				}
//			} else {
//				request.setAttribute("error", "invalid");
//				request.getRequestDispatcher("/admin/bookList.jsp").forward(request, response);
//				return;
//			}
//		}
//	}
//}

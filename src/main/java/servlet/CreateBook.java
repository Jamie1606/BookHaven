// Author		: Zay Yar Tun
// Admin No		: 2235035
// Class		: DIT/FT/2A/02
// Group		: 10
// Date			: 11.7.2023
// Description	: this is to store admin data from database

package servlet;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Response;

import model.Author;
import model.Book;
import model.Functions;
import model.Genre;
import model.TestReg;
import model.URL;

/**
 * Servlet implementation class CreateBook
 */
@WebServlet("/CreateBook")
@MultipartConfig(location = "/tmp", maxFileSize = 20971520, maxRequestSize = 41943040, fileSizeThreshold = 1048576)
public class CreateBook extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public CreateBook() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		final String defaultNormalImage = "book/normal/defaultBookHavenImage_normal.png";
		final String default3DImage = "book/3d/defaultBookHavenImage_3d.png";

		String isbn = request.getParameter("isbn");
		String title = request.getParameter("title");
		String page = request.getParameter("page");
		String price = request.getParameter("price");
		String qty = request.getParameter("qty");
		String publisher = request.getParameter("publisher");
		String publicationdate = request.getParameter("publicationdate");
		String description = request.getParameter("description");
		String[] authors = request.getParameterValues("author");
		String[] genres = request.getParameterValues("genre");
		String image = "";
		String image3d = "";
		boolean condition = true;

		String url = URL.bookRegistration;

		// checking all form data valid or not
		if (Functions.checkFormData(isbn, title, page, price, qty, publisher, publicationdate)) {
			if (TestReg.matchInteger(title) || !TestReg.matchInteger(page) || !TestReg.matchDecimal(price)
					|| !TestReg.matchInteger(qty) || TestReg.matchInteger(publisher)
					|| !TestReg.matchDate(publicationdate)) {
				isbn = isbn.trim();
				title = title.trim();
				page = page.trim();
				price = price.trim();
				qty = qty.trim();
				publisher = publisher.trim();
				publicationdate = publicationdate.trim();
				if (description != null) {
					description = description.trim();
				}

				if (Functions.checkISBN13(isbn)) {
					Part filePart = request.getPart("image");
					String fileName = "";
					String fileType = "";
					if (filePart == null) {
						image = defaultNormalImage;
					} else {
						fileName = filePart.getSubmittedFileName();
						int index = fileName.indexOf(".");
						if (index == -1) {
							image = defaultNormalImage;
						} else {
							fileType = fileName.substring(index).trim();
							fileName = title.toLowerCase() + "_" + isbn + "_normal" + fileType;

							if (uploadImage(fileName, "normal", filePart)) {
								image = "book/normal/" + fileName;
							} else {
								image = defaultNormalImage;
							}
						}
					}

					filePart = request.getPart("image3d");
					if (filePart == null) {
						image3d = default3DImage;
					} else {
						fileName = filePart.getSubmittedFileName();
						int index = fileName.indexOf('.');
						if (index == -1) {
							image3d = default3DImage;
						} else {
							fileType = fileName.substring(index).trim();
							fileName = title.toLowerCase() + "_" + isbn + "_3d" + fileType;

							if (uploadImage(fileName, "3d", filePart)) {
								image3d = "book/3d/" + fileName;
							} else {
								image3d = default3DImage;
							}
						}
					}

					Book book = new Book();
					book.setISBNNo(isbn);
					book.setTitle(title);
					book.setPage(Integer.parseInt(page));
					book.setPrice(Double.parseDouble(price));
					book.setQty(Integer.parseInt(qty));
					book.setPublisher(publisher);
					book.setRating(0);

					Date publicationDate = Date.valueOf(LocalDate.parse(publicationdate));
					book.setPublicationDate(publicationDate);

					book.setDescription(description);
					book.setImage(image);
					book.setImage3D(image3d);
					if (book.getQty() > 0) {
						book.setStatus("available");
					} else {
						book.setStatus("unavailable");
					}

					ArrayList<Author> authorList = new ArrayList<Author>();
					for (int i = 0; i < authors.length; i++) {
						Author author = new Author();
						if (TestReg.matchInteger(authors[i])) {
							author.setAuthorID(Integer.parseInt(authors[i]));
						} else {
							condition = false;
							request.setAttribute("status", "invalid");
							break;
						}
						authorList.add(author);
					}
					book.setAuthors(authorList);

					ArrayList<Genre> genreList = new ArrayList<Genre>();
					for (int i = 0; i < genres.length; i++) {
						Genre genre = new Genre();
						if (TestReg.matchInteger(genres[i])) {
							genre.setGenreID(Integer.parseInt(genres[i]));
						} else {
							condition = false;
							request.setAttribute("status", "invalid");
							break;
						}
						genreList.add(genre);
					}
					book.setGenres(genreList);

					if (condition) {
						Client client = ClientBuilder.newClient();
						WebTarget target = client.target(URL.baseURL).path("createBook");
						Invocation.Builder invocationBuilder = target.request();
						ObjectMapper obj = new ObjectMapper();
						String jsonBook = obj.writeValueAsString(book);
						Response resp = invocationBuilder.post(Entity.json(jsonBook));

						if (resp.getStatus() == Response.Status.OK.getStatusCode()) {
							Integer row = resp.readEntity(Integer.class);
							if (row == 1) {
								request.setAttribute("status", "insertsuccess");
							} else if (row == -1) {
								System.out.println("..... Duplicate author data in CreateAuthor servlet .....");
								request.setAttribute("status", "duplicate");
							} else {
								deleteImage(image);
								deleteImage(image3d);
								System.out.println("..... Invalid author data in CreateAuthor servlet .....");
								request.setAttribute("status", "invalid");
							}
						} else {
							System.out.println("..... Error in CreateAuthor servlet .....");
							request.setAttribute("status", "servererror");
						}
					}
				} else {
					request.setAttribute("status", "invalid");
				}
			} else {
				request.setAttribute("status", "invalid");
			}
		} else {
			request.setAttribute("status", "invalid");
		}

		request.getRequestDispatcher(url).forward(request, response);
		return;
	}

	// uploading image to spring boot
	private boolean uploadImage(String fileName, String path, Part filePart) {
		boolean condition = false;

		try {
			HttpClient httpClient = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost("http://localhost:8081/bookhaven/api/uploadImage/book/" + path);

			MultipartEntityBuilder builder = MultipartEntityBuilder.create();
			builder.addBinaryBody("image", filePart.getInputStream(), ContentType.DEFAULT_BINARY, fileName);

			HttpEntity multipartEntity = builder.build();
			httpPost.setEntity(multipartEntity);

			HttpResponse httpResponse = httpClient.execute(httpPost);
			int statusCode = httpResponse.getStatusLine().getStatusCode();

			if (statusCode == 200) {
				HttpEntity httpEntity = httpResponse.getEntity();
				String responseStr = EntityUtils.toString(httpEntity);
				condition = Boolean.parseBoolean(responseStr);
				// Image uploaded successfully
				// Handle the response if needed
			} else {
				condition = false;
				System.out.println("..... Error in uploading " + path + " book in CreateBook Servlet .....");
				// Error occurred while uploading the image
				// Handle the error response if needed
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return condition;
	}

	private boolean deleteImage(String image) {
		boolean condition = false;

		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(URL.baseURL).path("deleteImage").path("{image}").resolveTemplate("image",
				image);
		Invocation.Builder invocationBuilder = target.request();
		Response resp = invocationBuilder.delete();

		if (resp.getStatus() == Response.Status.OK.getStatusCode()) {
			condition = resp.readEntity(Boolean.class);
		} else {
			System.out.println("..... Error in deleteImage in CreateBook servlet .....");
			return false;
		}

		return condition;
	}
}

// Author		: Zay Yar Tun
// Admin No		: 2235035
// Class		: DIT/FT/2A/02
// Group		: 10
// Date			: 11.7.2023
// Description	: this is to store admin data from database

package servlet;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		Book book = null;
		ArrayList<Author> authorList = new ArrayList<Author>();
		ArrayList<Genre> genreList = new ArrayList<Genre>();
		boolean condition = true;
		String status = "";
		String url = URL.bookRegistration;
		
		
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


		try {
			isbn = isbn.trim();
			title = title.trim();
			page = page.trim();
			price = price.trim();
			qty = qty.trim();
			publisher = publisher.trim();
			publicationdate = publicationdate.trim();
			
			if(description != null) {
				description = description.trim();
			}
			
			if(Functions.checkISBN13(isbn)) {
				image = Functions.uploadImage(title, isbn, "booknormal", request.getPart("image"));
				if(image == null) {
					image = defaultNormalImage;
				}
				
				image3d = Functions.uploadImage(title, isbn, "book3d", request.getPart("image3d"));
				if(image3d == null) {
					image3d = default3DImage;
				}
				
				for (int i = 0; i < authors.length; i++) {
					Author author = new Author();
					author.setAuthorID(Integer.parseInt(authors[i]));
					authorList.add(author);
				}
				
				for (int i = 0; i < genres.length; i++) {
					Genre genre = new Genre();
					genre.setGenreID(Integer.parseInt(genres[i]));
					genreList.add(genre);
				}
				
				book = new Book();
				book.setISBNNo(isbn);
				book.setTitle(title);
				book.setPage(Integer.parseInt(page));
				book.setQty(Integer.parseInt(qty));
				book.setPrice(Double.parseDouble(price));
				book.setRating(0);
				book.setPublisher(publisher);
				book.setPublicationDate(Date.valueOf(publicationdate));
				book.setImage(image);
				book.setImage3D(image3d);
				book.setAuthors(authorList);
				book.setGenres(genreList);
				
				if(book.getQty() <= 0) {
					book.setStatus("unavailable");
				}
				else {
					book.setStatus("available");
				}
			}
			else {
				throw new Error();
			}
		}
		catch(Exception e) {
			status = "invalid";
			e.printStackTrace();
			System.out.println("..... Invalid book data in CreateBook servlet .....");
			condition = false;
		}
		
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
					status = "insertsuccess";
				} else if (row == -1) {
					System.out.println("..... Duplicate author data in CreateAuthor servlet .....");
					status = "duplicate";
				} else {
					// current deleteImage may be wrong
					//deleteImage(image);
					//deleteImage(image3d);
					System.out.println("..... Invalid author data in CreateAuthor servlet .....");
					status = "invalid";
				}
			} else {
				System.out.println("..... Error in CreateAuthor servlet .....");
				status = "servererror";
			}
		}

		request.setAttribute("status", status);
		request.getRequestDispatcher(url).forward(request, response);
		return;
	}

//	private boolean deleteImage(String image) {
//		boolean condition = false;
//
//		Client client = ClientBuilder.newClient();
//		WebTarget target = client.target(URL.baseURL).path("deleteImage").path("{image}").resolveTemplate("image",
//				image);
//		Invocation.Builder invocationBuilder = target.request();
//		Response resp = invocationBuilder.delete();
//
//		if (resp.getStatus() == Response.Status.OK.getStatusCode()) {
//			condition = resp.readEntity(Boolean.class);
//		} else {
//			System.out.println("..... Error in deleteImage in CreateBook servlet .....");
//			return false;
//		}
//
//		return condition;
//	}
}

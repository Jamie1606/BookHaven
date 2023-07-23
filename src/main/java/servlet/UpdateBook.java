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
 * Servlet implementation class UpdateBook
 */
@WebServlet("/UpdateBook/*")
@MultipartConfig(location = "/tmp", maxFileSize = 20971520, maxRequestSize = 41943040, fileSizeThreshold = 1048576)
public class UpdateBook extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public UpdateBook() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		final String defaultNormalImage = "book/normal/defaultBookHavenImage_normal.png";
		final String default3DImage = "book/3d/defaultBookHavenImage_3d.png";
		
		boolean condition = true;
		String status = "";
		ArrayList<Author> authorList = new ArrayList<Author>();
		ArrayList<Genre> genreList = new ArrayList<Genre>();
		String pathisbn = "";
		
		try {
			String requestURi = (String) request.getRequestURI();
			String[] parts = requestURi.split("/");
			pathisbn = parts[parts.length - 1];
		}
		catch(Exception e) {
			e.printStackTrace();
			condition = false;
			System.out.println("..... Invalid update request in UpdateBook servlet .....");
			status = "invalid";
		}
		
		
		Book book = null;
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
		String oldimage = request.getParameter("oldimage");
		String oldimage3d = request.getParameter("oldimage3d");
		String url = URL.bookList;
		
		try {
			pathisbn = pathisbn.trim();
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
			
			if(Functions.checkISBN13(isbn)) {
				
				image = Functions.uploadImage(title, isbn, "booknormal", request.getPart("image"));
				if(image == null) {
					if(oldimage == null || oldimage.isEmpty()) {
						image = defaultNormalImage;
					}
					else {
						image = oldimage.trim();
					}
				}

				image3d = Functions.uploadImage(title, isbn, "book3d", request.getPart("image3d"));
				if(image3d == null) {
					if(oldimage3d == null || oldimage3d.isEmpty()) {
						image3d = default3DImage;
					}
					else {
						image3d = oldimage3d.trim();
					}
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
			e.printStackTrace();
			status = "invalid";
			System.out.println("..... Invalid book data in UpdateBook servlet .....");
			condition = false;
		}
		
		if(condition) {			
			Client client = ClientBuilder.newClient();
			WebTarget target = client.target(URL.baseURL).path("updateBook").path("{isbn}").resolveTemplate("isbn", isbn);
			Invocation.Builder invocationBuilder = target.request();
			ObjectMapper obj = new ObjectMapper();
			String jsonBook = obj.writeValueAsString(book);
			Response resp = invocationBuilder.put(Entity.json(jsonBook));

			if (resp.getStatus() == Response.Status.OK.getStatusCode()) {
				Integer row = resp.readEntity(Integer.class);
				if (row == 1) {
					status = "updatesuccess";
				}  else {
					System.out.println("..... Invalid isbn in UpdateBook servlet .....");
					status = "invalid";
				}
			} else {
				System.out.println("..... Error in UpdateBook servlet .....");
				status = "servererror";
			}
		}
		
		request.setAttribute("status", status);
		request.getRequestDispatcher(url).forward(request, response);
        return;
	}
}

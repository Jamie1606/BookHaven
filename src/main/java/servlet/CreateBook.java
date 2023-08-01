// Author		: Zay Yar Tun
// Admin No		: 2235035
// Class		: DIT/FT/2A/02
// Group		: 10
// Date			: 1.8.2023
// Description	: create new book

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
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import model.Author;
import model.Book;
import model.Functions;
import model.Genre;
import model.Status;
import model.URL;

/**
 * Servlet implementation class CreateBook
 */
@WebServlet("/CreateBook")
@MultipartConfig(location = "", maxFileSize = 10485760, maxRequestSize = 20971520, fileSizeThreshold = 1048576)
public class CreateBook extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public CreateBook() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		Book book = null;
		ArrayList<Author> authorList = new ArrayList<Author>();
		ArrayList<Genre> genreList = new ArrayList<Genre>();
		boolean condition = true;
		String status = "";
		String url = URL.bookRegistration;
		
		if(session != null && !session.isNew()) {
			String token = (String) session.getAttribute("token");
			
			if(token == null || token.isEmpty()) {
				status = Status.unauthorized;
				url = URL.signOut;
			}
			else {
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
					book = new Book();
					book.setISBNNo(isbn.trim());
					
					if(Functions.checkISBN13(book.getISBNNo())) {
						
						book.setTitle(title.trim());
						book.setPage(Integer.parseInt(page.trim()));
						book.setQty(Integer.parseInt(qty.trim()));
						book.setPrice(Double.parseDouble(price.trim()));
						book.setRating(0);
						book.setPublisher(publisher.trim());
						book.setPublicationDate(Date.valueOf(publicationdate.trim()));
						if(description != null) {
							book.setDescription(description.trim());
						}
						
						if(book.getQty() <= 0) {
							book.setStatus("unavailable");
						}
						else {
							book.setStatus("available");
						}
						
						image = Functions.uploadImage(book.getTitle(), book.getISBNNo(), "booknormal", request.getPart("image"), token);
						if(image == null) {
							image = URL.defaultBookNormalImage;
						}
						
						image3d = Functions.uploadImage(book.getTitle(), book.getISBNNo(), "book3d", request.getPart("image3d"), token);
						if(image3d == null) {
							image3d = URL.defaultBook3DImage;
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
						
						book.setImage(image.trim());
						book.setImage3D(image3d.trim());			
						book.setAuthors(authorList);
						book.setGenres(genreList);
					}
					else {
						throw new Error();
					}
				}
				catch(Exception e) {
					status = Status.invalidData;
					e.printStackTrace();
					System.out.println("..... Invalid book data in CreateBook servlet .....");
					condition = false;
				}
				
				if (condition) {
					
					Client client = ClientBuilder.newClient();
					WebTarget target = client.target(URL.baseURL).path("createBook");
					Invocation.Builder invocationBuilder = target.request();
					invocationBuilder.header(HttpHeaders.AUTHORIZATION, "Bearer " + token);
					
					ObjectMapper obj = new ObjectMapper();
					String json = obj.writeValueAsString(book);
					Response resp = invocationBuilder.post(Entity.json(json));
		
					if (resp.getStatus() == Response.Status.OK.getStatusCode()) {
						Integer row = resp.readEntity(Integer.class);
						if (row == 1) {
							status = Status.insertSuccess;
						} else if (row == -1) {
							System.out.println("..... Duplicate book data in CreateBook servlet .....");
							status = Status.duplicateData;
						} else {
							// current deleteImage may be wrong
							//deleteImage(image);
							//deleteImage(image3d);
							System.out.println("..... Invalid book data in CreateBook servlet .....");
							status = Status.invalidData;
						}
					} 
					else if(resp.getStatus() == Response.Status.FORBIDDEN.getStatusCode()) {
						status = Status.unauthorized;
						url = URL.signOut;
					}
					else {
						System.out.println("..... Error in CreateBook servlet .....");
						status = Status.serverError;
					}
				}
			}	
		}
		else {
			status = Status.unauthorized;
			url = URL.signOut;
		}	

		request.setAttribute("status", status);
		request.getRequestDispatcher(url).forward(request, response);
		return;
	}
	
//	private boolean deleteImage(String image) {
//	boolean condition = false;
//
//	Client client = ClientBuilder.newClient();
//	WebTarget target = client.target(URL.baseURL).path("deleteImage").path("{image}").resolveTemplate("image",
//			image);
//	Invocation.Builder invocationBuilder = target.request();
//	Response resp = invocationBuilder.delete();
//
//	if (resp.getStatus() == Response.Status.OK.getStatusCode()) {
//		condition = resp.readEntity(Boolean.class);
//	} else {
//		System.out.println("..... Error in deleteImage in CreateBook servlet .....");
//		return false;
//	}
//
//	return condition;
//}
}

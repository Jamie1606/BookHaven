package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Response;
import model.Functions;
import model.TestReg;
import model.URL;

/**
 * Servlet implementation class UpdateBook
 */
@WebServlet("/UpdateBook/*")
public class UpdateBook extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public UpdateBook() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		final String defaultNormalImage = "book/normal/defaultBookHavenImage_normal.png";
		final String default3DImage = "book/3d/defaultBookHavenImage_3d.png";
		
		String requestURi = (String) request.getRequestURI();
		String[] parts = requestURi.split("/");
		boolean condition = false;
		String status = "";
		
		if(parts.length == 0) {
			System.out.println("..... Invalid data request in UpdateBook servlet .....");
			status = "invalid";
		}
		
		String pathisbn = parts[parts.length - 1];

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

		if (Functions.checkFormData(isbn, title, page, price, qty, publisher, publicationdate, pathisbn)) {
			
			if (TestReg.matchInteger(title) || !TestReg.matchInteger(page) || !TestReg.matchDecimal(price)
					|| !TestReg.matchInteger(qty) || TestReg.matchInteger(publisher)
					|| !TestReg.matchDate(publicationdate)) {
				status = "invalid";
				System.out.println("..... Invalid book data in UpdateBook servlet .....");
			} else {
				isbn = isbn.trim();
				title = title.trim();
				page = page.trim();
				price = price.trim();
				qty = qty.trim();
				publisher = publisher.trim();
				publicationdate = publicationdate.trim();
				pathisbn = pathisbn.trim();
				
				if (description != null) {
					description = description.trim();
				}
				
				if(Functions.checkISBN13(isbn) && Functions.checkISBN13(pathisbn)) {
					if(oldimage == null || oldimage.isEmpty()) {
						oldimage = defaultNormalImage;
					}
					else {
						oldimage = oldimage.trim();
					}
					
					if(oldimage3d == null || oldimage3d.isEmpty()) {
						oldimage3d = default3DImage;
					}
					else {
						oldimage3d = oldimage3d.trim();
					}
					
					image = Functions.uploadImage(title, isbn, "booknormal", request.getPart("image"));
					if(image == null) {
						image = oldimage;
					}

					image3d = Functions.uploadImage(title, isbn, "book3d", request.getPart("image3d"));
					if(image3d == null) {
						image3d = oldimage3d;
					}
					
					
					
				}
				else {
					status = "invalid";
					System.out.println("..... Invalid isbn in UpdateBook servlet .....");
				}
			}
		}
		else {
			status = "invalid";
			System.out.println("..... Null or empty data in UpdateBook servlet .....");
		}
		
		if(condition) {
			
		}
		
		request.setAttribute("status", status);
		request.getRequestDispatcher(url).forward(request, response);
        return;
	}
}

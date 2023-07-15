// Author		: Zay Yar Tun
// Admin No		: 2235035
// Class		: DIT/FT/2A/02
// Group		: 10
// Date			: 11.7.2023
// Description	: this is to store admin data from database

package servlet;

import java.io.IOException;

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

import controller.TestReg;
import model.Book;

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

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String defaultNormalImage = "book/normal/defaultBookHavenImage_normal.png";
		final String default3DImage = "book/3d/defaultBookHavenImage_3d.png";
		
		
		String isbn = request.getParameter("isbn");
		String title = request.getParameter("title");
		String page = request.getParameter("page");
		String price = request.getParameter("price");
		String qty = request.getParameter("qty");
		String publisher = request.getParameter("publisher");
		String publicationdate = request.getParameter("publicationdate");
		String bookstatus = "available";
		String description = request.getParameter("description");
		String image = "";
		String image3d = "";
		
		String url = "/admin/bookRegistration.jsp";
		
		// checking all form data valid or not
		if(checkFormData(isbn, title, page, price, qty, publisher, publicationdate, description)) {
			
			Part filePart = request.getPart("image");
			String fileName = "";
			String fileType = "";
			if(filePart == null) {
				image = defaultNormalImage;
			}
			else {
				fileName = filePart.getSubmittedFileName();
		        fileType = fileName.substring(fileName.indexOf('.')).trim();
		        fileName = title.toLowerCase() + "_normal" + fileType;
		        
		        if(uploadImage(fileName, "normal", filePart)) {
		        	image = "book/normal/" + fileName;
		        }
		        else {
		        	image = defaultNormalImage;
		        }
			}
			
			
			filePart = request.getPart("image3d");
			if(filePart == null) {
				image3d = default3DImage;
			}
			else {
				fileName = filePart.getSubmittedFileName();
		        fileType = fileName.substring(fileName.indexOf('.')).trim();
		        fileName = title.toLowerCase() + "_3d" + fileType;
		        
		        if(uploadImage(fileName, "3d", filePart)) {
		        	image3d = "book/3d/" + fileName;
		        }
		        else {
		        	image3d = default3DImage;
		        }
			}
			
			Book book = new Book();
			
		}
		else {
			request.setAttribute("error", "");
		}
	        
        request.getRequestDispatcher(url).forward(request, response);
        return;
	}
	
	private boolean checkFormData(String isbn, String title, String page, String price, String qty, String publisher, String publicationdate, 
			String description) {
		
		boolean condition = false;
		
		if(isbn == null || title == null || page == null || price == null || qty == null || publisher == null || publicationdate == null) {
			condition = false;
		}
		else {
			if(isbn.isEmpty() || title.isEmpty() || page.isEmpty() || price.isEmpty() || qty.isEmpty() || publisher.isEmpty() || 
					publicationdate.isEmpty()) {
				condition = false;
			}
			else {
				if(!TestReg.matchISBN(isbn) || TestReg.matchInteger(title) || !TestReg.matchInteger(page) || !TestReg.matchDecimal(price) || 
						!TestReg.matchInteger(qty) || TestReg.matchInteger(publisher) || !TestReg.matchDate(publicationdate)) {
					condition = false;
				}
				else {
					condition = true;
				}
			}
		}
		
		return condition;
	}
	
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
	        	condition = true;
	            // Image uploaded successfully
	            // Handle the response if needed
	        } else {
	        	condition = false;
	        	System.out.println("..... Error in uploading " + path + " book in CreateBook Servlet .....");
	        	// Error occurred while uploading the image
	            // Handle the error response if needed
	        }
		}
		catch(Exception e) {
			e.printStackTrace();
		}
			
		return condition;
	}
}

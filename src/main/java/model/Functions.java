// Author		: Zay Yar Tun
// Admin No		: 2235035
// Class		: DIT/FT/2A/02
// Group		: 10
// Date			: 1.8.2023
// Description	: function collections

package model;



import jakarta.servlet.http.Part;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Response;

public class Functions {
		
	/**
	 * 
	 * Check ISBN 13
	 * 
	 * @param isbn accepts all kinds of formats (must include 13 numbers)
	 * @return True if isbn provided is valid
	 * 	
	 */
	public static boolean checkISBN13(String isbn) {
		boolean condition = false;
		
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(URL.baseURL).path("checkISBN13").path("{isbn}").resolveTemplate("isbn", isbn);
		Invocation.Builder invocationBuilder = target.request();
		Response resp = invocationBuilder.get();
		
		if(resp.getStatus() == Response.Status.OK.getStatusCode()) {			
			condition = resp.readEntity(Boolean.class);
		}
		else {
			System.out.println("..... Error in checkISBN13 in Functions .....");
			return false;
		}
		
		return condition;
	}
	
	
	/**
	 * 
	 * Upload image to S3
	 * 
	 * @param fileName is the name of the file after uploaded
	 * @param id will be added to the fileName, e.g. hello_12345. It should be unique, e.g. datetime
	 * @param type 3 types are available: booknormal, book3d, member
	 * @param filePart is the file you want to upload to s3
	 * @param token must be provided
	 * @return imagepath if the image is successfully uploaded
	 * @return null if the image upload is failed
	 * 
	 */
	public static String uploadImage(String fileName, String id, String type, Part filePart, String token) {
		String returnStr = null;
		
		if(token == null || token.isEmpty()) {
			return null;
		}
		
		try {
			if(filePart == null) {
				return null;
			}
			
			String submittedFileName = filePart.getSubmittedFileName();
			int index = submittedFileName.indexOf(".");
			if(index == -1) {
				return null;
			}
			fileName = fileName.toLowerCase() + "_" + id + submittedFileName.substring(index).trim();
			
			HttpClient httpClient = HttpClients.createDefault();
			String url = URL.baseURL;
			String imagepath = "";
			if(type.equals("booknormal")) {
				url += URL.bookNormalImageUpload;
				imagepath = "book/normal/";
			}
			else if(type.equals("book3d")) {
				url += URL.book3DImageUpload;
				imagepath = "book/3d/";
			}
			else if(type.equals("member")) {
				url += URL.memberImageUpload;
				imagepath = "member/";
			}
			else {
				return null;
			}
			
			HttpPost httpPost = new HttpPost(url);
			httpPost.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);

			MultipartEntityBuilder builder = MultipartEntityBuilder.create();
			builder.addBinaryBody("image", filePart.getInputStream(), ContentType.DEFAULT_BINARY, fileName);

			HttpEntity multipartEntity = builder.build();
			httpPost.setEntity(multipartEntity);

			HttpResponse httpResponse = httpClient.execute(httpPost);
			int statusCode = httpResponse.getStatusLine().getStatusCode();

			if (statusCode == 200) {
				HttpEntity httpEntity = httpResponse.getEntity();
				String responseStr = EntityUtils.toString(httpEntity);
				boolean condition = Boolean.parseBoolean(responseStr);
				if(condition) {
					returnStr = imagepath + fileName;
				}
				// Image uploaded successfully
				// Handle the response if needed
			} 
			else {
				System.out.println("..... Error in uploading " + type + " image in Functions .....");
				return null;
				// Error occurred while uploading the image
				// Handle the error response if needed
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return returnStr;
	}
	
	
	/**
	 * Delete image from s3 (cannot be used now)
	 * 
	 * @param image Image should include s3 path, where image is stored. e.g. book/image.jpg
	 * @return True if the image is successfully deleted
	 *  
	 */
	public static boolean deleteImage(String image, String token) {
		
		boolean condition = false;
		if(image == null || image.isEmpty()) {
			return false;
		}
		String type = "";
		String[] parts = image.split("/");
		if(parts.length == 3) {
			if(parts[1].equals("3d")) {
				type = "book3d";
			}
			else if(parts[1].equals("normal")) {
				type = "booknormal";
			}
			else {
				return false;
			}
		}
		else if(parts.length == 2) {
			type = "member";
		}
		else {
			return false;
		}
		
		image = parts[parts.length - 1];
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(URL.baseURL).path("deleteImage")
				.path("{type}").resolveTemplate("type", type)
				.path("{image}").resolveTemplate("image", image);
		Invocation.Builder invocationBuilder = target.request();
		invocationBuilder.header(jakarta.ws.rs.core.HttpHeaders.AUTHORIZATION, "Bearer " + token);
		Response resp = invocationBuilder.delete();
		
		if(resp.getStatus() == Response.Status.OK.getStatusCode()) {			
			condition = resp.readEntity(Boolean.class);
		}
		else {
			System.out.println("..... Error in deleteImage in Functions.....");
			return false;
		}
			
		return condition;
	}
}
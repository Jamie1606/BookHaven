package model;

public class URL {
	
	public static final String baseURL = "http://localhost:8081/bookhaven/api";
	
	// jsp pages
	public static final String authorList = "/admin/authorList.jsp";
	public static final String adminHomePage = "/admin/adminHomePage.jsp";
	public static final String authorRegistration = "/admin/authorRegistration.jsp";
	
	// servlets
	public static final String getAuthorListServlet = "/GetAuthorList";
	public static final String deleteAuthorServlet = "/DeleteAuthor/";
}
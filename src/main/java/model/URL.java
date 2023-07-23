// Author		: Zay Yar Tun, Thu Htet San
// Admin No		: 2235035, 2235022
// Class		: DIT/FT/2A/02
// Group		: 10
// Date			: 15.7.2023
// Description	: url

package model;

public class URL {
	
	// backend api
	public static final String baseURL = "http://localhost:8081/bookhaven/api";
	public static final String bookNormalImageUpload = "/uploadImage/book/normal";
	public static final String book3DImageUpload = "/uploadImage/book/3d";
	public static final String memberImageUpload = "/uploadImage/member";
	public static final String getLatestBook = "/getLatest/";
	public static final String s3ImageLink = "http://s3.amazonaws.com/bookhavenjad10/";
	public static final String getOneBookDetail = "/getBook/details/";
	public static final String getRelatedBook = "/getRelated/";
	
	
	// admin jsp pages
	public static final String authorList = "/admin/authorList.jsp";
	public static final String adminHomePage = "/admin/adminHomePage.jsp";
	public static final String authorRegistration = "/admin/authorRegistration.jsp";
	public static final String bookRegistration = "/admin/bookRegistration.jsp";
	public static final String bookList = "/admin/bookList.jsp";
	
	
	// author servlets
	public static final String getAuthorListServlet = "/GetAuthorList";
	public static final String deleteAuthorServlet = "/DeleteAuthor/";
	public static final String getAuthorByIDServlet = "/GetAuthorByID/";
	public static final String createAuthorServlet = "/CreateAuthor";
	public static final String updateAuthorServlet = "/UpdateAuthor";
	
	
	// book servlets
	public static final String getBookRegistrationServlet = "/GetBookRegistration";
	public static final String createBookServlet = "/CreateBook";
	public static final String getBookListServlet = "/GetBookList";
	public static final String updateBookServlet = "/UpdateBook/";
	public static final String getBookByISBNServlet = "/GetBookByISBN/";
	public static final String deleteBookServlet = "/DeleteBook/";
	
	// customer jsp pages
	public static final String bookDetail = "/bookDetail.jsp";
}
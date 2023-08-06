// Author		: Zay Yar Tun, Thu Htet San
// Admin No		: 2235035, 2235022
// Class		: DIT/FT/2A/02
// Group		: 10
// Date			: 1.8.2023
// Description	: url collections

package model;

public class URL {
	
	// backend api
//	public static final String baseURL = "http://localhost:8081/bookhaven/api";
	public static final String baseURL = "http://35.175.15.211:8081/bookhaven/api";
	public static final String bookNormalImageUpload = "/uploadImage/book/normal";
	public static final String book3DImageUpload = "/uploadImage/book/3d";
	public static final String memberImageUpload = "/uploadImage/member";
	public static final String getLatestBook = "/getLatest/";
	public static final String getOneBookDetail = "/getBook/details/";
	public static final String getRelatedBook = "/getRelated/";
	public static final String getBookByAuthorID = "/getBookByAuthorID/";
	public static final String getAuthor = "/getAuthor/";
	public static final String getBestSeller = "/getBestSeller/";
	public static final String getTopRated = "/getTopRated/";
	public static final String imageLink = "http://s3.us-east-1.amazonaws.com/bookhavenjad10/";
	public static final String getReviewByISBN = "/getReview/";
	public static final String searchBookByTitle = "/getBook/title/";
	public static final String searchBookByAuthor = "/getBook/author/";
	public static final String getAllGenreList = "/getAllGenre";
	public static final String getBookByGenreID = "/getBookByGenreID/";
	
	
	
	// admin jsp pages
	public static final String authorList = "/admin/authorList.jsp";
	public static final String adminHomePage = "/admin/adminHomePage.jsp";
	public static final String authorRegistration = "/admin/authorRegistration.jsp";
	public static final String bookRegistration = "/admin/bookRegistration.jsp";
	public static final String bookList = "/admin/bookList.jsp";
	public static final String orderList = "/admin/orderList.jsp";
	public static final String orderDetailList = "/admin/orderDetailList.jsp";
	public static final String memberList = "/admin/memberList.jsp";
	public static final String memberRegistration = "/admin/memberRegistration.jsp";
	public static final String genreList = "/admin/genreList.jsp";
	public static final String genreRegistration = "/admin/genreRegistration.jsp";
	public static final String reviewList = "/admin/reviewList.jsp";
	public static final String topCustomerList = "/admin/topCustomerList.jsp";
	public static final String bookSales = "/admin/bookSales.jsp";
	public static final String customersOfBook = "/admin/customersOfBook.jsp";
	public static final String leastSellingBookList = "/admin/leastSellingBookList.jsp";
	public static final String lowStockBookList = "/admin/lowStockBookList.jsp";
	public static final String bestSellingBookList = "/admin/bestSellingBookList.jsp";
	
	
	
	// authentication servlets
	public static final String signInServlet = "/Signin";
	
	
	
	// author servlets
	public static final String getAuthorListServlet = "/GetAuthorList";
	public static final String deleteAuthorServlet = "/DeleteAuthor/";
	public static final String getAuthorByIDServlet = "/GetAuthorByID/";
	public static final String createAuthorServlet = "/CreateAuthor";
	public static final String updateAuthorServlet = "/UpdateAuthor";
	public static final String getAuthorDetailServlet = "/GetAuthorDetail/";
	
	
	
	// book servlets
	public static final String getBookRegistrationServlet = "/GetBookRegistration";
	public static final String createBookServlet = "/CreateBook";
	public static final String getBookListServlet = "/GetBookList";
	public static final String updateBookServlet = "/UpdateBook/";
	public static final String getBookByISBNServlet = "/GetBookByISBN/";
	public static final String deleteBookServlet = "/DeleteBook/";
	public static final String defaultBookNormalImage = "book/normal/defaultBookHavenImage_normal.png";
	public static final String defaultBook3DImage = "book/3d/defaultBookHavenImage_3d.png";
	public static final String getBestSellingBookListServlet = "/GetBestSellingBookList";
	public static final String getLeastSellingBookListServlet = "/GetLeastSellingBookList";
	public static final String getLowStockBookListServlet = "/GetLowStockBookList";
	
	
	// member servlets
	public static final String getMemberListServlet = "/GetMemberList";
	public static final String defaultMemberImage = "member/defaultuser.png";
	public static final String getMemberByIDServlet = "/GetMemberByID/";
	public static final String updateMemberServlet = "/UpdateMember";
	public static final String createMemberServlet = "/CreateMember";
	public static final String deleteMemberServlet = "/DeleteMember/";
	public static final String createNewAccountServlet = "/CreateNewAccount";
	public static final String getProfileDataServlet = "/GetProfileData";
	public static final String updateProfileServlet = "/UpdateProfile";
	public static final String deleteAccountServlet = "/DeleteAccount";
	
	
	
	// cart servlets
	public static final String addToCartServlet = "/AddtoCart/";
	public static final String removeFromCartServlet = "/RemovefromCart/";
	
	
	
	// order servlets
	public static final String makeOrderServlet = "/MakeOrder";
	public static final String getOrderListServlet = "/GetOrderList";
	public static final String getMemberOrderServlet = "/GetMemberOrder/";
	public static final String cancelMemberOrderItemServlet = "/CancelMemberOrderItem/";
	public static final String completeOrderServlet = "/CompleteOrder/";
	public static final String cancelOrderServlet = "/CancelOrder/";
	public static final String getOrderDetailListServlet = "/GetOrderDetailList/";
	
	
	
	
	// report pages
	public static final String bestSellingServlet = "/GetBestSellingBookList";
	public static final String leastSellingServlet = "/GetLeastSellingBookList";
	public static final String lowStockBookServlet = "/GetLowStockBookList";

	public static final String getTopCustomerseServlet = "/GetTopCustomers";	
	public static final String getCustomersByBookIDServlet = "/GetCustomersByBookID";

	
	
	
	// review servlets
	public static final String reviewBookServlet = "/ReviewBook";
	public static final String getReviewListServlet = "/GetReviewList";
	public static final String updateReviewStatusServlet = "/UpdateReviewStatus/";
	
	
	
	// customer jsp pages
	public static final String homePage = "/index.jsp";
	public static final String bookDetail = "/bookDetail.jsp";
	public static final String signOut = "/signout.jsp";
	public static final String signIn = "/signin.jsp";
	public static final String profile = "/profile.jsp";
	public static final String search = "/search.jsp";
	public static final String history = "/history.jsp";
	public static final String cart = "/cart.jsp";
	public static final String bookGenre = "/bookGenre.jsp";
	public static final String authorDetail = "/authorDetail.jsp";
	public static final String signUp = "/signup.jsp";
	public static final String header = "/header.jsp";
	public static final String footer = "/footer.jsp";
	
	
	//genre servlets
	public static final String getGenreListServlet = "/GetGenreList";
	public static final String getGenreByIDServlet = "/GetGenreByID/";
	public static final String createGenreServlet = "/CreateGenre";
	public static final String updateGenreServlet = "/UpdateGenre";	
	public static final String deleteGenreServlet = "/DeleteGenre/";
	
	//sale servlets
		public static final String getBookSalesServlet = "/GetBookSales";
		public static final String getTopCustomerListServlet = "/GetTopCustomerList";	
		public static final String getCustomerListByBookIDServlet = "/GetCustomerListByBookID";
}
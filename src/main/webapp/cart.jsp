
<%
// Author		: Zay Yar Tun
// Admin No		: 2235035
// Class		: DIT/FT/2A/02
// Group		: 10
// Date			: 17.6.2023
// Description	: shopping cart
// cart layout design is referenced from https://cdn.dribbble.com/users/1569943/screenshots/6745363/cart.png
%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page
	import="model.*, java.util.ArrayList"%>
<!DOCTYPE html>
<html>
<head>
<!-- Mobile Specific Meta -->
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<!-- Favicon-->
<link rel="shortcut icon"
	href="<%=request.getContextPath()%>/img/fav.png">
<!-- Author Meta -->
<meta name="author" content="codepixer">
<!-- Meta Description -->
<meta name="description" content="">
<!-- Meta Keyword -->
<meta name="keywords" content="">
<!-- meta character set -->
<meta charset="UTF-8">
<!-- Site Title -->
<title>BookHaven | Cart</title>

<link
	href="https://fonts.googleapis.com/css?family=Poppins:100,200,400,300,500,600,700"
	rel="stylesheet">
<!--CSS============================================= -->
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/linearicons.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/font-awesome.min.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/bootstrap.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/magnific-popup.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/nice-select.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/animate.min.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/owl.carousel.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/main.css">
<link rel="icon" type="image/png"
	href="<%=request.getContextPath()%>/img/logo.png">
<style>
#btncheckout {
	user-select: none;
	position: relative;
	text-align: center;
	-webkit-transition-duration: 0.4s; /* Safari */
	transition-duration: 0.4s;
	text-decoration: none;
	overflow: hidden;
	cursor: pointer;
	font-size: 13px;
	margin-top: 30px;
	color: white;
	padding: 10px 30px;
	outline: none;
	border: 1px solid #6c5dd4;
	background-color: #6c5dd4;
	letter-spacing: 1.1px;
	font-weight: bold;
	border-radius: 10px;
	box-shadow: 2px 2px 5px 1px #777;
}

#btncheckout:after {
	content: "";
	background: #eee;
	display: block;
	position: absolute;
	padding-top: 300%;
	padding-left: 370%;
	left: 0;
	margin-left: -20px !important;
	margin-top: -120%;
	opacity: 0;
	transition: all 0.5s;
	outline: none;
}

#btncheckout:active:after {
	padding: 0;
	margin: 0;
	opacity: 1;
	transition: 0s;
	outline: none;
}
</style>
</head>
<body>
	<%@ include file="header.jsp"%>

	<%
		if(session != null && !session.isNew()) {
			String token = (String) session.getAttribute("token");
			String role = (String) session.getAttribute("role");
			
			if(token == null || token.isEmpty() || role == null || !role.equals("ROLE_MEMBER")) {
				request.setAttribute("status", Status.unauthorized);
				request.getRequestDispatcher(URL.signOut).forward(request, response);
				return;
			}
		}
		else {
			request.setAttribute("status", Status.unauthorized);
			request.getRequestDispatcher(URL.signOut).forward(request, response);
			return;
		}
		
		String status = (String) request.getAttribute("status");
		if(status != null) {
			if(status.equals(Status.deleteSuccess)) {
				out.println("<script>location = '" + request.getContextPath() + URL.cart + "';</script>");
				return;
			}
			else if(status.equals(Status.invalidData)) {
				out.println("<script>alert('Invalid request!'); location = '" + request.getContextPath() + URL.cart + "';</script>");
				return;
			}
		}
	
		int totalQty = 0;
		double totalPrice = 0;
	
		ArrayList<Book> cart = (ArrayList<Book>) session.getAttribute("cart");
		ArrayList<Integer> cartQty = (ArrayList<Integer>) session.getAttribute("cart-qty");
		
		if (cart == null || cart.isEmpty() || cartQty == null || cartQty.isEmpty()) {
			session.removeAttribute("cart");
			session.removeAttribute("cart-qty");
			out.println("<script>alert('There is no item in cart!'); location='" + request.getContextPath() + URL.homePage + "';</script>");
			return;
		}
		else {
			if(cart.size() == 0 || cartQty.size() == 0) {
				session.removeAttribute("cart");
				session.removeAttribute("cart-qty");
				out.println("<script>alert('There is no item in cart!'); location='" + request.getContextPath() + URL.homePage + "';</script>");
				return;
			}
		}
	%>

	<div style="display: flex; flex-direction: row; justify-content: space-evenly; padding: 130px 100px;"
		id="cart-info">
		<div id="cart-book-info" style="display: flex; width: 70%: flex-direction: row;">

				<div id="cart-detail" style="display: flex; flex-direction: column;">
					<div style="display: flex; flex-direction: row; justify-content: space-between; padding: 0px 8%;">
					<h3>Your Cart <span style="font-size: 16px; font-weight: normal;">(<%= (cart.size() > 1) ? cart.size() + " items" : cart.size() + " item" %>)
						</span>
					</h3>
					<a href="<%=request.getContextPath() + URL.homePage %>">Continue Shopping</a>
				</div>
				
					<%
						for(int i = 0; i < cart.size(); i++) {
							String image = cart.get(i).getImage();
							String title = cart.get(i).getTitle();
							String isbn = cart.get(i).getISBNNo();
							String authors = "";
							
							ArrayList<Author> authorList = cart.get(i).getAuthors();
							for(int j = 0; j < authorList.size(); j++) {
								int authorID = authorList.get(j).getAuthorID(); 
										
								if(j == authorList.size() -1) {
									authors = "<a href='" + request.getContextPath() + URL.authorDetail + "?id=" + authorID + "'>" 
										+ authorList.get(j).getName() + "</a>";
								}
								else {
									authors = "<a href='" + request.getContextPath() + URL.authorDetail + "?id=" + authorID + "'>" 
										+ authorList.get(j).getName() + "</a>, ";
								}
							}
							
							Double rating = cart.get(i).getRating();
							int bookPage = cart.get(i).getPage();
							int qty = cartQty.get(i);
							double price = cart.get(i).getPrice();
							totalQty += qty;
							totalPrice += (price * qty);
							int bookQty = cart.get(i).getQty();
					%>
							
					<div style="display: flex; flex-direction: row; margin-top: 35px; justify-content: space-evenly;">
					
						<div style="margin-right: 25px; position: relative;">
							<img style="width: 150px; height: 220px; border-radius: 7px;" src="<%= image %>" />
							<%
								if(bookQty < 10) {
									if(bookQty == 1 ){
										out.println("<span style='position: absolute; background: rgba(130, 130, 130, 0.9); width: 150px; height: 35px; left: 0; z-index: 100; color: white; bottom: 0; border-radius: 7px; text-indent: 10px; line-height: 35px; user-select: none;'>" + bookQty + " item left</span>");		
									}
									else {
										out.println("<span style='position: absolute; background: rgba(130, 130, 130, 0.9); width: 150px; height: 35px; left: 0; z-index: 100; color: white; bottom: 0; border-radius: 7px; text-indent: 10px; line-height: 35px; user-select: none;'>" + bookQty + " items left</span>");
									}
								}
							%>
						</div>
						
						<div style="display: flex; flex-direction: column; margin-top: 15px; width: 30%;">
							<a href="<%= request.getContextPath() + URL.bookDetail %>?id=<%= isbn %>">
								<h4><%= title %></h4>
							</a>
							<label style="margin-top: 10px;">By <%= authors %></label>
							
							<div>
								<span style="color: gold; font-size: 20px; vertical-align: middle;">
									<%= (rating > 0)? "&#9733;" : "&#9734;" %>
								</span>
								<span style="margin-left: 10px; font-weight: bold; color: #777; vertical-align: middle; letter-spacing: 1.1px;">
									<%= rating %>
								</span>
							</div>
							
							<label style="margin-top: 5px; color: black;"><%= bookPage %> pages</label>
						</div>
						
						<div style="display: flex; align-items: center;" id="cart">
							<label id="btn-minus" onclick="changeQty(-1, '<%= isbn %>')" style="font-size: 28px; font-weight: bold; color: #6c5dd4; margin: 5px 5px; vertical-align: middle; padding: 5px 10px; user-select: none;">-</label>
							<label id="buy-qty" style="font-size: 18px; user-select: none; margin: 5px 15px; vertical-align: middle;">
								<%= qty %>
							</label>
							<label id="btn-plus" onclick="changeQty(1, '<%= isbn %>')" style="font-size: 28px; font-weight: bold; color: #6c5dd4; margin: 5px 5px; vertical-align: middle; padding: 5px 10px; user-select: none;">+</label>
						</div>
						
						<div style="display: flex; flex-direction: column; align-items: flex-end; justify-content: space-evenly;">
							<label style="font-weight: bold; font-size: 18px; color: #222;">$<%= String.format("%.2f", (price * qty)) %></label>
							<a href="<%= request.getContextPath() + URL.removeFromCartServlet + isbn %>" style="color: #555; margin-top: 20px; text-decoration: underline;">Remove</a>
						</div>
					</div>
					
					<%
						}
					%>

				</div>
		</div>
		<div style="display: flex; flex-direction: column; background-color: #eee; height: 30%; padding: 4%;">
			<h3 style="font-size: 22px; text-align: center;">Order Summary</h3>
			<div
				style="display: flex; justify-content: space-between; color: #555; margin-top: 35px;">
				<label style="font-weight: bold; font-size: 15px;">Qty</label> <label
					style="font-weight: bold; font-size: 15px;" id="total-qty"><%= totalQty %></label>
			</div>
			<div
				style="display: flex; justify-content: space-between; color: #555; margin-top: 15px;">
				<label style="font-weight: bold; font-size: 15px;">Total</label> <label
					style="font-weight: bold; font-size: 15px;" id="total-sum">$ <%= String.format("%.2f", totalPrice) %></label>
			</div>
			<button id="btncheckout">CHECKOUT</button>
		</div>
	</div>

	<%@ include file="footer.jsp"%>

	<script
		src="<%=request.getContextPath()%>/js/vendor/jquery-2.2.4.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
		integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
		crossorigin="anonymous"></script>
	<script src="<%=request.getContextPath()%>/js/easing.min.js"></script>
	<script src="<%=request.getContextPath()%>/js/hoverIntent.js"></script>
	<script src="<%=request.getContextPath()%>/js/superfish.min.js"></script>
	<script src="<%=request.getContextPath()%>/js/jquery.ajaxchimp.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/js/jquery.magnific-popup.min.js"></script>
	<script src="<%=request.getContextPath()%>/js/owl.carousel.min.js"></script>
	<script src="<%=request.getContextPath()%>/js/jquery.sticky.js"></script>
	<script
		src="<%=request.getContextPath()%>/js/jquery.nice-select.min.js"></script>
	<script src="<%=request.getContextPath()%>/js/parallax.min.js"></script>
	<script src="<%=request.getContextPath()%>/js/waypoints.min.js"></script>
	<script src="<%=request.getContextPath()%>/js/jquery.counterup.min.js"></script>
	<script src="<%=request.getContextPath()%>/js/main.js"></script>

	<script>				
		function changeQty(num, isbn) {
			addtoCart(num, isbn);
		}
		
		function addtoCart(qty, isbn) {
			fetch('<%=request.getContextPath() + URL.addToCartServlet %>' + isbn + '/' + qty, {
				method: 'GET'
			})
			.then(response => response.json())
			.then(data => {
				let status = data.status;
				if(status == "<%= Status.invalidData %>") {
					alert('Invalid data!');
				}
				else if(status == "<%= Status.maxProduct %>") {
					alert('You have reached maximum quantity for this book!');
				}
				else if(status == "<%= Status.serverError %>") {
					alert('Server error!');
				}
				location.reload();
			})
		}
	</script>
</body>
</html>
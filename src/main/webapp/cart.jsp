
<%
//Author: Zay Yar Tun
//Admin No: 2235035
// Class: DIT/FT/2A/02
// Group: 10
//Date: 17.6.2023
//Description: shopping cart
//cart layout design is referenced from https://cdn.dribbble.com/users/1569943/screenshots/6745363/cart.png
%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page
	import="controller.Authentication, model.Book, java.util.ArrayList"%>
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
	//if (!auth.testMember(session)) {
	//	out.println("<script>alert('Please Log In First!'); location='" + request.getContextPath() + "/signout.jsp';</script>");
	//	return;
	//}

	ArrayList<Book> cart = (ArrayList<Book>) session.getAttribute("cart");
	if (cart == null) {
		out.println("<script>alert('There is no item in cart!'); location='" + request.getContextPath() + "/index.jsp';</script>");
		return;
	}
	else {
		if(cart.size() == 0) {
			out.println("<script>alert('There is no item in cart!'); location='" + request.getContextPath() + "/index.jsp';</script>");
			return;
		}
	}
	%>

	<div
		style="display: flex; flex-direction: row; justify-content: space-evenly; visibility: hidden; padding: 130px 100px;"
		id="cart-info">
		<div id="cart-book-info" style="display: flex; width: 70%: flex-direction: row;">
			<div style="display: flex; flex-direction: column;">
				<div
					style="display: flex; flex-direction: row; justify-content: space-between;">
					<h3>
						Your Cart <span style="font-size: 16px; font-weight: normal;">(<%= (cart.size() > 1) ? cart.size() + " items" : cart.size() + " item" %>)
						</span>
					</h3>
					<a href="<%=request.getContextPath()%>/index.jsp">Continue
						Shopping</a>
				</div>

				<div id="cart-detail" style="display: flex; flex-direction: column;">

				</div>

			</div>
		</div>
		<div style="display: flex; flex-direction: column; background-color: #eee; height: 30%; padding: 4%;">
			<h3 style="font-size: 22px; text-align: center;">Order Summary</h3>
			<div
				style="display: flex; justify-content: space-between; color: #555; margin-top: 35px;">
				<label style="font-weight: bold; font-size: 15px;">Qty</label> <label
					style="font-weight: bold; font-size: 15px;" id="total-qty"></label>
			</div>
			<div
				style="display: flex; justify-content: space-between; color: #555; margin-top: 15px;">
				<label style="font-weight: bold; font-size: 15px;">Total</label> <label
					style="font-weight: bold; font-size: 15px;" id="total-sum"></label>
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
	<script src="<%=request.getContextPath()%>/js/mail-script.js"></script>
	<script src="<%=request.getContextPath()%>/js/main.js"></script>

	<script>
		$(document).ready(function() {
			fetch('<%=request.getContextPath()%>/cart/bookdetail', {
				method: 'GET'
			})
			.then(response => response.json())
			.then(data => {
				let list = data.list;
				let status = data.status;
				if(status == "serverError") {
					alert('Error in retrieving cart details!');
					location = 'index.jsp';
				}
				else {
					let sum = 0;
					let totalQty = 0;
					let htmlStr = "";
					for(let i = 0; i < list.length; i++) {
						let authors = "";
						for(let j = 0; j < list[i].authors.length; j++) {
							if(j == list[i].authors.length - 1) {
								authors += "<a style='color: #777;' href='<%= request.getContextPath() %>/authorDetail.jsp?id=" + list[i].authors[j].authorID + "'>" + list[i].authors[j].name + "</a>";
							}
							else {
								authors += "<a style='color: #777;' href='<%= request.getContextPath() %>/authorDetail.jsp?id=" + list[i].authors[j].authorID + "'>" + list[i].authors[j].name + "</a>, ";
							}
						}
						htmlStr += '<div style="display: flex; flex-direction: row; margin-top: 35px; justify-content: space-evenly;">';
						htmlStr += '<div style="margin-right: 25px;"><img style="width: 150px; height: 220px; border-radius: 7px;" src="<%= request.getContextPath() %>'+list[i].image+' " /></div>';
						
						htmlStr += '<div style="display: flex; flex-direction: column; margin-top: 15px; width: 30%;"><a href="<%= request.getContextPath() %>/bookDetail.jsp?id=' + list[i].ISBNNo + '"><h4>' + list[i].title + '</h4></a><label style="margin-top: 10px;">By ' + authors + '</label><div><span style="color: gold; font-size: 20px; vertical-align: middle;">';
						if(list[i].rating > 0) {
							htmlStr += '&#9733;';						
						}
						else {
							htmlStr += '&#9734;';
						}
							
						htmlStr += '</span><span style="margin-left: 10px; font-weight: bold; color: #777; vertical-align: middle; letter-spacing: 1.1px;">' + list[i].rating.toFixed(1) + '</span></div><label style="margin-top: 5px; color: black;">' + list[i].page + ' pages</label></div>';
						
						totalQty += list[i].qty;
						sum += (list[i].qty * list[i].price);
						
						htmlStr += '<div style="display: flex; align-items: center;" id="cart"><label id="btn-minus" onclick="changeQty(-1, \'' + list[i].ISBNNo + '\')" style="font-size: 28px; font-weight: bold; color: #6c5dd4; margin: 5px 5px; vertical-align: middle; padding: 5px 10px; user-select: none;">-</label><label id="buy-qty" style="font-size: 18px; user-select: none; margin: 5px 15px; vertical-align: middle;">' + list[i].qty + '</label><label id="btn-plus" onclick="changeQty(1, \'' + list[i].ISBNNo + '\')" style="font-size: 28px; font-weight: bold; color: #6c5dd4; margin: 5px 5px; vertical-align: middle; padding: 5px 10px; user-select: none;">+</label></div>';
							
						htmlStr += '<div style="display: flex; flex-direction: column; align-items: flex-end; justify-content: space-evenly;"><label style="font-weight: bold; font-size: 18px; color: #222;">$' + (list[i].qty * list[i].price).toFixed(2) + '</label><a href="" onclick=\'removeItem("' + list[i].ISBNNo + '")\' style="color: #555; margin-top: 20px; text-decoration: underline;">Remove</a></div>';
						
						htmlStr += '</div>';
					}
					$('#total-sum').html("$" + sum.toFixed(2));
					$('#total-qty').html(totalQty);
					$('#total-price').html("Total Price: $" + sum.toFixed(2));
					$('#cart-detail').html(htmlStr);
					$('#cart-info').css({"visibility": "visible"});
				}
			})
		})			
		
		function removeItem(isbn) {
			fetch('<%=request.getContextPath()%>/cart/remove/' + isbn, {
				method: 'GET'	
			})
			.then(response => response.json())
			.then(data => {
				let status = data.status;
				if(status == "invalid") {
					alert('Invalid Request!');
				}
				location = 'cart.jsp';
			})
		}
		
		function changeQty(num, isbn) {
			addtoCart(num, isbn);
		}
		
		function addtoCart(qty, isbn) {
			fetch('<%=request.getContextPath()%>/book/qty/' + isbn + '/' + qty, {
				method: 'GET'
			})
			.then(response => response.json())
			.then(data => {
				let status = data.status;
				if(status == "invalid") {
					alert('Invalid request!');
				}
				else if(status == "full") {
					alert('You have reached the maximum quantity for this book!');
				}
				else if(status == "serverError") {
					alert('Server Error! Please try again later!');
				}
				location = 'cart.jsp';
			})
		}
	</script>
</body>
</html>
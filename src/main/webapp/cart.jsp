<%
//Author: Zay Yar Tun
//Admin No: 2235035
//Date: 5.6.2023
//Description: sign out and destroy session
//cart layout design is referenced from https://cdn.dribbble.com/users/1569943/screenshots/6745363/cart.png
%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="controller.Authentication, model.Book, java.util.ArrayList" %>
<!DOCTYPE html>
<html>
<head>
<!-- Mobile Specific Meta -->
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<!-- Favicon-->
<link rel="shortcut icon" href="<%= request.getContextPath() %>/img/fav.png">
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
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/linearicons.css">
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/font-awesome.min.css">
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/bootstrap.css">
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/magnific-popup.css">
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/nice-select.css">
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/animate.min.css">
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/owl.carousel.css">
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/main.css">
<link rel="icon" type="image/png" href="<%= request.getContextPath() %>/img/logo.png">
</head>
<body>
	<%@ include file="header.jsp" %>
	
	<%
		if(!auth.testMember(session)) {
			out.println("<script>alert('Please Log In First!'); location='signout.jsp';</script>");
			return;
		}
		
		ArrayList<Book> cart = (ArrayList<Book>)session.getAttribute("cart");
		if(cart == null) {
			cart = new ArrayList<Book>();
		}
	%>
	
	<div style="padding: 100px 0px; color: black; visibility: hidden;" id="cart-info">
		<h1 style="text-align: center; margin-bottom: 25px;">Shopping Cart</h1>
		<table border="1" cellspacing="0" cellpadding="0" style="text-align: center; margin: auto; width: 80%;">
			<tr style="height: 35px;">
				<th>No.</th>
				<th>Title</th>
				<th>Publisher</th>
				<th>Quantity</th>
				<th>Unit Price</th>
				<th>Action</th>
			</tr>
			<%
				for(int i = 0; i < cart.size(); i++) {
					out.println("<tr>");
					out.println("<td>" + (i + 1) + ".</td>");
					out.println("<td id='title-"+ i + "'></td>");
					out.println("<td id='publisher-"+ i + "'></td>");
					out.println("<td id='qty-"+ i + "'>" + cart.get(i).getQty() + "</td>");
					out.println("<td id='price-"+ i + "'></td>");
					out.println("<td><button onclick=\"removeItem('" + cart.get(i).getISBNNo() + "')\">Remove</button></td>");
					out.println("</tr>");
				}
			%>
			<tr style="height: 35px; text-align: right;">
				<td colspan="8" id="total-price" style="padding-right: 30px;">Total Price: </td>
			</tr>
		</table>
		<div style="float: right; margin-top: 30px; margin-right: 170px;">
			<a href="index.jsp">Continue Shopping</a>
			<a href="#">Check Out</a>
		</div>
	</div>
	
	<%@ include file="footer.jsp" %>
	
	<script src="<%= request.getContextPath() %>/js/vendor/jquery-2.2.4.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
		integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
		crossorigin="anonymous"></script>
	<script src="<%= request.getContextPath() %>/js/easing.min.js"></script>
	<script src="<%= request.getContextPath() %>/js/hoverIntent.js"></script>
	<script src="<%= request.getContextPath() %>/js/superfish.min.js"></script>
	<script src="<%= request.getContextPath() %>/js/jquery.ajaxchimp.min.js"></script>
	<script src="<%= request.getContextPath() %>/js/jquery.magnific-popup.min.js"></script>
	<script src="<%= request.getContextPath() %>/js/owl.carousel.min.js"></script>
	<script src="<%= request.getContextPath() %>/js/jquery.sticky.js"></script>
	<script src="<%= request.getContextPath() %>/js/jquery.nice-select.min.js"></script>
	<script src="<%= request.getContextPath() %>/js/parallax.min.js"></script>
	<script src="<%= request.getContextPath() %>/js/waypoints.min.js"></script>
	<script src="<%= request.getContextPath() %>/js/jquery.counterup.min.js"></script>
	<script src="<%= request.getContextPath() %>/js/mail-script.js"></script>
	<script src="<%= request.getContextPath() %>/js/main.js"></script>
	
	<script>
		$(document).ready(function() {
			fetch('<%= request.getContextPath() %>/cart/bookdetail', {
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
					for(let i = 0; i < list.length; i++) {
						$('#title-' + i).html(list[i].title);
						$('#publisher-' + i).html(list[i].publisher);
						$('#price-' + i).html("$" + list[i].price);
						let qty = document.getElementById('qty-' + i).innerHTML;
						sum += Number(list[i].price) * Number(qty);
					}
					$('#total-price').html("Total Price: $" + sum.toFixed(2));
					$('#cart-info').css({"visibility": "visible"});
				}
			})
		})
		
		function removeItem(isbn) {
			fetch('<%= request.getContextPath() %>/cart/remove/' + isbn, {
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
	</script>
</body>
</html>
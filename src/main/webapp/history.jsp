<%
// Author		: Zay Yar Tun
// Admin No		: 2235035
// Class		: DIT/FT/2A/02
// Group		: 10
// Date			: 1.8.2023
// Description	: order history page
// layout taken from https://cdn.dribbble.com/userupload/7778324/file/original-810dda72de79666f64f2a4d62f906721.png?resize=1024x768
%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="model.*" %>
<!DOCTYPE html>
<html lang="zxx" class="no-js">

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
<title>BookHaven | History</title>

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
<link rel="stylesheet" href="<%= request.getContextPath() %>/custom-css/style.css">

<style>
	.latest-release h4:hover {
		color: red;
	}
	.latest-release p {
		cursor: default;
	}
</style>
</head>

<body>
	<%@ include file="header.jsp" %>
	
	<%
		String token = "";
		if(session != null && !session.isNew()) {
			token = (String) session.getAttribute("token");
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
	%>
	
	<div class="history">
		<h1>Your Orders</h1>
		<div class="history-header">
			<div class="history-status">
				<button class="active" onclick="makeActive(this)">All</button>
				<button onclick="makeActive(this)">Delivered</button>
				<button onclick="makeActive(this)">Pending</button>
				<button onclick="makeActive(this)">Cancelled</button>
			</div>
			<select id="order-sort-date" class="date-select" onchange="changeOrderDate()">
				<option value="0" selected>This Week</option>
				<option value="1">Last Month</option>
				<option value="2">Past 3 Months</option>
				<option value="3">Past 6 Months</option>
			</select>
		</div>
		<div class="history-body" id="order-items">
			
		</div>
	</div>

	<%@ include file="footer.jsp"%>

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
		function makeActive(element) {
			const elements = document.getElementsByClassName("active");
			for(let i = 0; i < elements.length; i++) {
				elements[i].classList.remove("active");
			}
			element.classList.add("active");
			getOrder(makeSmaller(element.textContent));
		}
		
		function calculateDate() {
			const orderDate = document.getElementById("order-sort-date").value;
			let date = new Date();
			if(orderDate == 1) {
				date.setDate(1);
				date.setMonth(date.getMonth() - 1);
			}
			else if(orderDate == 2) {
				date.setDate(1);
				date.setMonth(date.getMonth() - 3);
			}
			
			else if(orderDate == 3) {
				date.setDate(1);
				date.setMonth(date.getMonth() - 6);
			}
			else {
				date.setDate(date.getDate() - date.getDay());
			}
			let dateStr = date.getFullYear() + "-" + String(date.getMonth() + 1).padStart(2, '0') +"-" + String(date.getDate()).padStart(2, '0');
			return dateStr;
		}
		
		function changeOrderDate() {
			const elements = document.getElementsByClassName("active");
			getOrder(makeSmaller(elements[0].textContent));
		}
		
		function getOrder(status) {
			let htmlStr = '<div class="order">';
			htmlStr += '<div class="order-header"><div class="order-header-item"><label style="font-weight: bold;">Loading...</label>';
			htmlStr += '</div></div>';
			$('#order-items').html(htmlStr);
			
			fetch('<%= request.getContextPath() + URL.getMemberOrderServlet %>' + calculateDate() + '/' + status, {
				method: 'GET'
			})
			.then(response => response.json())
			.then(data => {
				console.log(data);
				if(data != undefined && data != null) {
					htmlStr = "";
					let months = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];
					
					if(data.length == 0) {
						htmlStr += '<div class="order">';
						htmlStr += '<div class="order-header"><div class="order-header-item"><label style="color: red; font-weight: bold;">No order found!</label>';
						htmlStr += '</div></div>';
					}
					
					for(let i = 0; i < data.length; i++) {
						htmlStr += '<div class="order">';
						htmlStr += '<div class="order-header"><div class="order-header-item"><label>Order placed</label>';
						
						let date = new Date(data[i].orderdate);
						let dateStr = months[date.getMonth()];
						dateStr += " " + String(date.getDate()).padStart(2, '0');
						dateStr += ", " + date.getFullYear();
						
						htmlStr += '<label class="black-normal-bold-text">' + dateStr + '</label>';
						htmlStr += '</div><div class="order-header-item"><label>Total <small>(GST included)</small></label>';
						htmlStr += '<label class="black-normal-bold-text">$' + data[i].totalamount.toFixed(2) + '</label>';
						htmlStr += '</div><div class="order-header-item"><label>Delivered to</label>';
						htmlStr += '<label class="black-normal-bold-text">' + data[i].deliveryaddress +'</label>';
						htmlStr += '</div><div class="order-header-item">';
						htmlStr += '<label class="black-normal-bold-text">Order #' + data[i].orderid + '</label>';
						htmlStr += '<label><span class="black-normal-bold-text">' + makeCapital(data[i].orderstatus) + '</span>';
						htmlStr += '</label></div></div>';
						
						let items = data[i].orderitems;
						if(items != undefined && items != null) {
							for(let j = 0; j < items.length; j++) {
								htmlStr += '<div class="order-items">';
								htmlStr += '<img src="' + items[j].book.image + '" alt="Book Image">';
								htmlStr += '<div class="order-item-detail">';
								htmlStr += "<a href='<%= request.getContextPath() + URL.bookDetail %>?id=" + items[j].book.isbnno + "'>" + items[j].book.title + "</a>";
								htmlStr += '<label><span class="full-star" style="font-size: 16px;"></span> ' + items[j].book.rating.toFixed(1) + '&emsp;&emsp;Qty: ' + items[j].qty + '&emsp;&emsp;$' + (items[j].qty * items[j].book.price).toFixed(2) + '</label>';
								htmlStr += '<div class="order-item-buttons">';
								
								if(items[j].status == "pending") {
									htmlStr += '<button onclick="addtoCart(\'' + items[j].book.isbnno + '\')">Buy it again</button>';
									htmlStr += '<button onclick="cancel(' + data[i].orderid + ', \'' + items[j].book.isbnno + '\')">Cancel</button>';
								}
								else if(items[j].status = "cancelled") {
									htmlStr += '<button onclick="addtoCart(\'' + items[j].book.isbnno + '\')">Buy it again</button>';
								}
								else if(items[j].status = "delivered") {
									htmlStr += '<button onclick="addtoCart(\'' + items[j].book.isbnno + '\')">Buy it again</button>';
									htmlStr += '<button>Rate your item</button>';
								}
								htmlStr += '</div></div>';
								htmlStr += '<label class="order-item-status">' + makeCapital(items[j].status) + '</label>';
								htmlStr += '</div>';
								
								if(j != items.length - 1) {
									htmlStr += '<hr style="margin: 7px 0; opacity: 0.5; background: #ddd; width: 100%;">';
								}
							}
						}
						
						htmlStr += '</div>';
					}
					$('#order-items').html(htmlStr);
				}
				else {
					alert("Server error!");
				}
			})
			.catch(error => {
				console.log(error);
				alert("Server error!");
			})
		}
		
		function addtoCart(id) {
			let htmlStr = '<div class="order">';
			htmlStr += '<div class="order-header"><div class="order-header-item"><label style="font-weight: bold;">Loading...</label>';
			htmlStr += '</div></div>';
			$('#order-items').html(htmlStr);
			
			fetch('<%=request.getContextPath() + URL.addToCartServlet %>/' + id + '/1', {
				method: 'GET'
			})
			.then(response => response.json())
			.then(data => {
				console.log(data);
				let status = data.status;
				if(status == "<%= Status.invalidData %>") {
					alert('Invalid data!');
					location.reload();
				}
				else if(status == "<%= Status.maxProduct %>") {
					alert('You have reached maximum quantity for this book!');
					location.reload();
				}
				else if(status == "<%= Status.ok %>") {
					alert('Successfully added to cart!');
					location = '<%= request.getContextPath() + URL.cart %>';
				}
				else {
					alert('Server error! Please try again later!');
					location = '<%= request.getContextPath() + URL.homePage %>';
				}
			})
			.catch(error => {
				alert('Server error!');
				location = '<%= request.getContextPath() + URL.homePage %>';
			})
		}
		
		function cancel(id, isbn) {
			let htmlStr = '<div class="order">';
			htmlStr += '<div class="order-header"><div class="order-header-item"><label style="font-weight: bold;">Loading...</label>';
			htmlStr += '</div></div>';
			$('#order-items').html(htmlStr);
			
			fetch('<%=request.getContextPath() + URL.cancelMemberOrderItemServlet %>' + id + '/' + isbn, {
				method: 'GET'
			})
			.then(response => response.json())
			.then(data => {
				console.log(data);
				let status = data.status;
				if(status == "<%= Status.invalidData %>") {
					alert('Invalid data!');
					location.reload();
				}
				else if(status == "<%= Status.ok %>") {
					alert('Your order item is cancelled!');
					location = '<%= request.getContextPath() + URL.history %>';
				}
				else {
					alert('Server error! Please try again later!');
					location = '<%= request.getContextPath() + URL.homePage %>';
				}
			})
			.catch(error => {
				alert('Server error!');
				location = '<%= request.getContextPath() + URL.homePage %>';
			})
		}
		
		function makeCapital(str) {
			return str.charAt(0).toUpperCase() + str.slice(1);
		}
		
		function makeSmaller(str) {
			return str.charAt(0).toLowerCase() + str.slice(1);
		}
		
		$(document).ready(function() {
			getOrder('all');
		});
	</script>
</body>

</html>
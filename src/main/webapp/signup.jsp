
<%
// Author 	  	: Thu Htet San
// Admin No    	: 2235022
// Class       	: DIT/FT/2A/02
// Group		: 10
// Date        	: 6.6.2023
// Description 	: sign up page
%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zxx" class="no-js">

<head>
<!-- Mobile Specific Meta -->
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<!-- Favicon-->

<!-- Author Meta -->
<meta name="author" content="codepixer">
<!-- Meta Description -->
<meta name="description" content="">
<!-- Meta Keyword -->
<meta name="keywords" content="">
<!-- meta character set -->
<meta charset="UTF-8">
<!-- Site Title -->
<title>BookHaven | Sign Up</title>

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
#btnSignUp {
	user-select: none;
	position: relative;
	background-color: #6c5dd4;
	border: none;
	font-size: 28px;
	color: #FFFFFF;
	text-align: center;
	-webkit-transition-duration: 0.4s; /* Safari */
	transition-duration: 0.4s;
	text-decoration: none;
	overflow: hidden;
	cursor: pointer;
}

#btnSignUp:after {
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

#btnSignUp:active:after {
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
		String status = (String) request.getAttribute("status");
		if(status != null) {
			if(status.equals(Status.invalidData)) {
				out.println("<script>alert('Invalid data!'); location='" + request.getContextPath()
				+ URL.signUp + "';</script>");
				return;
			}
			else if(status.equals(Status.duplicateEmail)) {
				out.println("<script>alert('Email already exists!'); location='" + request.getContextPath()
				+ URL.signUp + "';</script>");
				return;
			}
			else if(status.equals(Status.insertSuccess)) {
				out.println("<script>alert('Your new account is created!'); location='" + request.getContextPath()
				+ URL.signIn + "';</script>");
				return;
			}
			else if(status.equals(Status.serverError)) {
				out.println("<script>alert('Server error!'); location='" + request.getContextPath()
				+ URL.signUp + "';</script>");
				return;
			}
		}
	%>

	<form id="signupForm" action="<%= request.getContextPath() + URL.createNewAccountServlet %>"
		method="post"
		style="padding: 100px 0px; display: flex; flex-direction: column; align-items: center;">
		<h2>SIGN UP FORM</h2>
		<input type="text" name="name" style="padding: 8px 10px; margin-top: 30px; letter-spacing: 1.1px; width: 400px;" placeholder="Name" required/>
		<input placeholder="&#x2709; Email" type="email" id="emailID" name="email" style="padding: 8px 10px; margin-top: 30px; letter-spacing: 1.1px; width: 400px;" required/>
		<input placeholder="&#x1F511; Password" type="password" id="passwordID" name="password" style="padding: 8px 10px; margin-top: 30px; letter-spacing: 1.1px; width: 400px;" required/>
		<input placeholder="Address" type="text" name="address" style="padding: 8px 10px; margin-top: 30px; letter-spacing: 1.1px; width: 400px;" required/>
		<input placeholder="Postal Code" type="number" name="postalCode" style="padding: 8px 10px; margin-top: 30px; letter-spacing: 1.1px; width: 400px;" required/>
		<input placeholder="Phone" type="number" name="phone" style="padding: 8px 10px; margin-top: 30px; letter-spacing: 1.1px; width: 400px;" required/>
		<button id="btnSignUp" type="submit"
			style="font-size: 13px; margin-top: 40px; color: white; padding: 10px 30px; outline: none; border: 1px solid #6c5dd4; background-color: #6c5dd4; font-weight: bold; letter-spacing: 1.1px; border-radius: 10px; box-shadow: 2px 2px 5px 1px #777;">SIGN
			UP</button>
	</form>

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
		$(document).ready(function() {
			$('#signupForm').submit(function(e) {
				$('#btnSignUp').prop('disabled', true);
				$('#btnSignUp').html('Loading...');
				return true;
			})
		});
	</script>
</body>

</html>
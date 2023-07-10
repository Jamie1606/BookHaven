
<%
//Author: Zay Yar Tun
//Admin No: 2235035
// Class: DIT/FT/2A/02
// Group: 10
//Date: 3.6.2023
//Description: home page
%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="controller.Authentication" %>
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
<title>BookHaven | Home</title>

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
	<%@ include file="header.jsp"%>

	<!-- start banner Area -->
	<section class="banner-area" id="home">
		<div class="container">
			<div
				class="row fullscreen d-flex align-items-center justify-content-start">
				<div class="banner-content col-lg-7" id="latest-book"></div>
				<div class="col-lg-5 banner-right" id="latest-book-image">
				</div>
			</div>
		</div>
	</section>
	<!-- End banner Area -->

	<!-- Start course Area -->
	<section class="course-area section-gap" id="course">
		<div class="container">
			<div class="row d-flex justify-content-center">
				<div class="menu-content pb-60 col-lg-9">
					<div class="title text-center">
						<h1 class="mb-10">Latest Releases</h1>
						<p>Who are in extremely love with books.</p>
					</div>
				</div>
			</div>
			<div class="row" style="margin-bottom: 0px;" id="latest-release">
			</div>
		</div>
	</section>
	<!-- End course Area -->

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
		$(document).ready(function() {
			fetch('<%= request.getContextPath() %>/books/latest',
			{
				method: 'GET'
			})
			.then(response => response.json())
			.then(data => {
				var list = data.list;
				var status = data.status;
				if(status == "serverError") {
					alert('Error in retrieving latest book!');
				}
				else {
					for(let i = 0; i < list.length; i++) {
						$('#latest-book').html('<h5 class="text-white text-uppercase"></h5><h1 class="text-uppercase">' + list[i].title + '</h1><p class="text-white pt-20 pb-20">' + list[i].description + '</p><a href="<%= request.getContextPath() %>/bookDetail.jsp?id=' + list[i].ISBNNo + '" class="primary-btn text-uppercase">View More</a>');
						$('#latest-book-image').html('<img class="img-fluid" src="<%= request.getContextPath() %>' + list[i].image3D + '" alt="">');
					}
				}
			});
			fetch('<%= request.getContextPath() %>/books/latestrelease', {
				method: 'GET'
			})
			.then(response => response.json())
			.then(data => {
				var list = data.list;
				var status = data.status;
				if(status == "serverError") {
					alert('Error in retrieving latest releases!');
				}
				else {
					var htmlStr = "";
					for(let i = 0; i < list.length; i++) {
							htmlStr += '<div class="col-lg-4 col-md-4 col-sm-12 latest-release" style="text-align: center; padding-bottom: 45px;"><div style="position: relative;"><img style="width: 250px; height: 300px;" class="img-fluid" src="<%=request.getContextPath() %>' + list[i].image + '" alt=""><p style="position: absolute; bottom: 0; left: 70px; color: white; background: red; padding: 5px 8px; letter-spacing: 1.1px;">' + list[i].status + '</p></div><div style="margin-top: 10px;"><a href="<%= request.getContextPath() %>/bookDetail.jsp?id=' + list[i].ISBNNo + '"><h4>' + list[i].title + '</h4></a><p></p></div></div>';
					}
					$('#latest-release').html(htmlStr);
				}
			})
		})
	</script>
</body>

</html>
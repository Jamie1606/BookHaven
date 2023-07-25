<%
//Author		: Zay Yar Tun
//Admin No		: 2235035
// Class		: DIT/FT/2A/02
// Group		: 10
//Date			: 7.7.2023
//Description	: home page
%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="controller.Authentication, model.URL" %>
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
	<%@ include file="header.jsp" %>

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
			//fetch('/books/latest',
			//{
			//	method: 'GET'
			//})
			//.then(response => response.json())
			//.then(data => {
			//	var list = data.list;
			//	var status = data.status;
			//	if(status == "serverError") {
			//		alert('Error in retrieving latest book!');
			//	}
			//	else {
			//		if(list != undefined) {
			//			for(let i = 0; i < list.length; i++) {
			//				
			//			}
			//		}
			//	}
			//});
			fetch('<%= URL.baseURL + URL.getLatestBook %>6', {
				method: 'GET'
			})
			.then(response => response.json())
			.then(data => {
				console.log(data);
				let htmlStr = "";
				if(data != undefined) {
					for(let i = 0; i < data.length; i++) {
						htmlStr += '<div class="col-lg-4 col-md-4 col-sm-12 latest-release" style="text-align: center; padding-bottom: 45px;">';
						htmlStr += '<div style="position: relative;">';
						htmlStr += '<img style="width: 250px; height: 300px;" class="img-fluid" src="' + data[i].image + '" alt="">';
						let qty = data[i].qty;
						if(qty < 10) {
							htmlStr += '<p style="position: absolute; bottom: 0; left: 70px; color: white; background: red; padding: 5px 8px; letter-spacing: 1.1px;">' + data[i].qty + ' items left </p></div>';
						}
						else {
							htmlStr += '<p style="position: absolute; bottom: 0; left: 70px; color: white; background: red; padding: 5px 8px; letter-spacing: 1.1px;">' + data[i].status + '</p></div>';
						}
						htmlStr += '<div style="margin-top: 10px;"><a href="<%= request.getContextPath() + URL.bookDetail %>?id=' + data[i].isbnno + '"><h4>' + data[i].title + '</h4></a><p></p></div></div>';
				}
					$('#latest-release').html(htmlStr);
					let description = data[0].description;
					if(description == null) {
						$('#latest-book').html('<h5 class="text-white text-uppercase"></h5><h1 class="text-uppercase" style="font-size: 45px;">' + data[0].title + '</h1><p class="text-white pt-20 pb-20">No information available</p><a href="<%= request.getContextPath() + URL.bookDetail %>?id=' + data[0].isbnno + '" class="primary-btn text-uppercase">View More</a>');
					}
					else {
						$('#latest-book').html('<h5 class="text-white text-uppercase"></h5><h1 class="text-uppercase" style="font-size: 45px;">' + data[0].title + '</h1><p class="text-white pt-20 pb-20">' + data[0].description + '</p><a href="<%= request.getContextPath() + URL.bookDetail %>?id=' + data[0].isbnno + '" class="primary-btn text-uppercase">View More</a>');
					}
					
					$('#latest-book-image').html('<img class="img-fluid" src="' + data[0].image3D + '" alt="">');
				}
				else {
					
				}
			})
		})
	</script>
</body>

</html>
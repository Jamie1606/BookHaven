<%
// Author		: Zay Yar Tun
// Admin No		: 2235035
// Class		: DIT/FT/2A/02
// Group		: 10
// Date			: 1.8.2023
// Description	: home page
// layout taken from https://www.lightnovelworld.com/hub_14072106
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
	
	
	<div class="homepage-section">
		<!-- Latest Releases -->
		<div>
			<h1>Latest Releases</h1>
			<div class="book-div" id="latest-release"></div>
		</div>
		<!-- Latest Releases -->
		
		
		<!-- Top Sellers -->
		<div>
			<h1>Best Sellers</h1>
			<div class="book-div" id="best-sellers"></div>
		</div>
		<!-- Top Sellers -->
		
		
		<!-- Top Rated -->
		<div>
			<h1>Top Rated</h1>
			<div class="book-div" id="top-rated"></div>
		</div>
		<!-- Top Rated -->
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
		$(document).ready(function() {
			
			fetch('<%= URL.baseURL + URL.getLatestBook %>7', {
				method: 'GET'
			})
			.then(response => response.json())
			.then(data => {
				console.log(data);
				let htmlStr = "";
				if(data != undefined) {					
					for(let i = 0; i < data.length; i++) {	
						htmlStr += '<div>';
						
						htmlStr += '<div class="book-img-div" onclick="goto(\'' + data[i].isbnno +'\')"><img src="<%= URL.imageLink %>' + data[i].image + '"><span class="rating">';
						htmlStr += '<span class="full-star"></span> ' + data[i].rating.toFixed(1) + '</span></div>';
						
						title = data[i].title;
						if(title.length > 30) {
							title = title.slice(0, 25) + "...";							
						}
						htmlStr += '<label class="book-title" onclick="goto(\'' + data[i].isbnno +'\')">' + title + '</label>';
						htmlStr += '</div>';
					}
					$('#latest-release').html(htmlStr);
					
					let description = data[0].description;
					if(description == null) {
						$('#latest-book').html('<h5 class="text-white text-uppercase"></h5><h1 class="text-uppercase" style="font-size: 45px;">' + data[0].title + '</h1><p class="text-white pt-20 pb-20">No information available</p><a href="<%= request.getContextPath() + URL.bookDetail %>?id=' + data[0].isbnno + '" class="primary-btn text-uppercase">View More</a>');
					}
					else {
						// taken from https://blog.logrocket.com/ways-truncate-text-css/
						description = description.slice(0, 450) + "...";
						$('#latest-book').html('<h5 class="text-white text-uppercase"></h5><h1 class="text-uppercase" style="font-size: 45px;">' + data[0].title + '</h1><p class="text-white pt-20 pb-20">' + description + '</p><a href="<%= request.getContextPath() + URL.bookDetail %>?id=' + data[0].isbnno + '" class="primary-btn text-uppercase">View More</a>');
					}
					
					$('#latest-book-image').html('<img class="img-fluid" src="<%= URL.imageLink %>' + data[0].image3D + '" alt="">');
				}
				else {
					alert("Error in retrieving book data!");
				}
			})
			.catch(error => {
				console.log(error);
				alert("Server error!");
			})
			
			
			fetch('<%= URL.baseURL + URL.getBestSeller %>7', {
				method: 'GET'
			})
			.then(response => response.json())
			.then(data => {

				let htmlStr = "";
				if(data != undefined) {				
					for(let i = 0; i < data.length; i++) {	
						htmlStr += '<div onclick="goto(\'' + data[i].isbnno +'\')">';
						
						htmlStr += '<div class="book-img-div" onclick="goto(\'' + data[i].isbnno +'\')"><img src="<%= URL.imageLink %>' + data[i].image + '"><span class="rating">';
						htmlStr += '<span class="full-star"></span> ' + data[i].rating.toFixed(1) + '</span></div>';
						
						title = data[i].title;
						if(title.length > 30) {
							title = title.slice(0, 25) + "...";							
						}
						htmlStr += '<label class="book-title" onclick="goto(\'' + data[i].isbnno +'\')">' + title + '</label>';
						htmlStr += '</div>';
					}
					$('#best-sellers').html(htmlStr);
				}
				else {
					alert("Error in retrieving book data!");
				}
			})
			.catch(error => {
				console.log(error);
				alert("Server error!");
			})
			
			
			fetch('<%= URL.baseURL + URL.getTopRated %>7', {
				method: 'GET'
			})
			.then(response => response.json())
			.then(data => {

				let htmlStr = "";
				if(data != undefined) {					
					for(let i = 0; i < data.length; i++) {	
						htmlStr += '<div onclick="goto(\'' + data[i].isbnno +'\')">';
						
						htmlStr += '<div class="book-img-div" onclick="goto(\'' + data[i].isbnno +'\')"><img src="<%= URL.imageLink %>' + data[i].image + '"><span class="rating">';
						htmlStr += '<span class="full-star"></span> ' + data[i].rating.toFixed(1) + '</span></div>';
						
						title = data[i].title;
						if(title.length > 30) {
							title = title.slice(0, 25) + "...";							
						}
						htmlStr += '<label class="book-title" onclick="goto(\'' + data[i].isbnno +'\')">' + title + '</label>';
						htmlStr += '</div>';
					}
					$('#top-rated').html(htmlStr);
				}
				else {
					alert("Error in retrieving book data!");
				}
			})
			.catch(error => {
				console.log(error);
				alert("Server error!");
			})
		})
		
		function goto(isbn) {
			location = '<%= request.getContextPath() + URL.bookDetail %>?id=' + isbn;
		}
	</script>
</body>

</html>
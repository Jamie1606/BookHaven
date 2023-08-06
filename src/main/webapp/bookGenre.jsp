<%
// Author 	  	: Thu Htet San
// Admin No    	: 2235022
// Class       	: DIT/FT/2A/02
// Group		: 10
// Date		  	: 8.6.2023
// Description 	: Genre Servlet
%>

<!-- [IMPORT] -->
<<<<<<< Updated upstream
<%@ page import="java.util.ArrayList, model.*"%>
=======
<%@ page import="java.util.ArrayList, model.Genre, model.Book"%>
>>>>>>> Stashed changes

<!DOCTYPE html>
<html lang="zxx" class="no-js">
<head>
<!-- Mobile Specific Meta -->
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<!-- Favicon-->
<link rel="shortcut icon" href="img/elements/fav.png">
<!-- Author Meta -->
<meta name="author" content="colorlib">
<!-- Meta Description -->
<meta name="description" content="">
<!-- Meta Keyword -->
<meta name="keywords" content="">
<!-- meta character set -->
<meta charset="UTF-8">
<!-- Site Title -->
<title>BookHaven | Genre</title>

<link
	href="https://fonts.googleapis.com/css?family=Poppins:100,200,400,300,500,600,700"
	rel="stylesheet">
<!--
			CSS
			============================================= -->
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/linearicons.css">
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/owl.carousel.css">
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/font-awesome.min.css">
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/nice-select.css">
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/magnific-popup.css">
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/bootstrap.css">
<link rel="stylesheet" href="<%= request.getContextPath() %>/custom-css/style.css">
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/main.css">
<link rel="icon" type="image/png" href="<%= request.getContextPath() %>/img/logo.png">
<style>
	#bookResultList h4:hover {
		color: red;
	}
	#bookResultList p {
		cursor: default;
	}
</style>
</head>
<body>

	<%@ include file="header.jsp"%><!-- #header -->
	<!-- Start Category Area -->
	<section class="generic-banner">
		<div class="row height align-items-center justify-content-center">
			<div>
				<div>
					<h2 class="text-white">Genre</h2>
					<div id="genreList" class="button-group-area mt-10"></div>
				</div>
			</div>
		</div>
	</section>
	<!-- End Category Area -->
	<!-- Start Result Area -->
	<section class="course-area section-gap" id="course">
		<div class="container">
			<div class="row d-flex justify-content-center">
				<div class="menu-content pb-60 col-lg-9">
					<div class="title text-center">
						<h2 class="mb-10" id="search-text"></h2>
					</div>
				</div>
			</div>
			<div style="padding: 10px 0;" class="homepage-section">
				<div>
					<div class="book-div" id="bookResultList"></div>
				</div>
			</div>
		</div>
	</section>
	<!-- End Result Area -->

	<!-- start footer Area -->
	<%@ include file="footer.jsp"%>
	<!-- End footer Area -->


	<script src="js/vendor/jquery-2.2.4.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
		integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
		crossorigin="anonymous"></script>
	<script src="<%= request.getContextPath() %>/js/vendor/bootstrap.min.js"></script>
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
	<script src="<%= request.getContextPath() %>/js/main.js"></script>

	<script>
	//[INSERT] to "Category" area
		$(document).ready(function() {
			//sends a GET request to the URL
			fetch('<%= URL.baseURL + URL.getAllGenreList %>', {
				method: 'GET'
			})
			.then(response => response.json())
			.then(data => {
				
				if(data != undefined && data != null && data.length > 0) {
					
					let htmlStr = "";
					for(let i = 0; i < data.length; i++) {
						htmlStr += "<button onclick='submitGenre(" + data[i].genreID + ", \""+ data[i].genre +"\")' class='btn btn-light mt-10 ml-10'>"+ data[i].genre + "</button>";
					}
					$('#genreList').html(htmlStr);
				}
				else {
					alert('Server error!');
				}
			})
			.catch(error => {
				console.log(error);
				alert("Server error!");
			})
		});
		
		function submitGenre(id, name) {
			let htmlStr = "";
			document.getElementById('search-text').innerHTML = name + " Books";
			htmlStr += "<h5 style='font-size: 20px; color: grey;'>Loading ...</h5>";
			$('#bookResultList').html(htmlStr);
			
			
			fetch('<%= URL.baseURL + URL.getBookByGenreID %>' + id, {
				method: 'GET'
			})
			.then(response => response.json())
			.then(data => {
				
				console.log(data);
				
				if(data != undefined && data != null && data.length > 0) {
					htmlStr = "";
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
					$('#bookResultList').html(htmlStr);
				}
				else {
					htmlStr += "<h5 style='font-size: 20px; color: red;'>No Items Found</h5>";
				}
			})
			.catch(error => {
				console.log(error);
				alert("Server error!");
			})
		}
		
		function goto(isbn) {
			location = '<%= request.getContextPath() + URL.bookDetail %>?id=' + isbn;
		}
	</script>
</body>
</html>
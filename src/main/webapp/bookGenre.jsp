<%
//Author 	  : Thu Htet San
//Admin No    : 2235022
//Class       : DIT/FT/2A/02
//Date		  : 8.6.2023
//Description : Genre Servlet
%>

<!-- [IMPORT] -->
<%@ page import="java.util.ArrayList, model.Genre, model.Book"%>

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
<title>BookHaven | Book</title>

<link
	href="https://fonts.googleapis.com/css?family=Poppins:100,200,400,300,500,600,700"
	rel="stylesheet">
<!--
			CSS
			============================================= -->
<link rel="stylesheet" href="css/linearicons.css">
<link rel="stylesheet" href="css/owl.carousel.css">
<link rel="stylesheet" href="css/font-awesome.min.css">
<link rel="stylesheet" href="css/nice-select.css">
<link rel="stylesheet" href="css/magnific-popup.css">
<link rel="stylesheet" href="css/bootstrap.css">
<link rel="stylesheet" href="css/main.css">
</head>
<body>

	<%@ include file="header.jsp"%><!-- #header -->
	<!-- Start Category Area -->
	<section class="generic-banner">
		<div class="container">
			<div class="row height align-items-center justify-content-center">
				<div>
					<div>
						<h2 class="text-white">Genre/Category</h2>
						<div id="genreList" class="button-group-area mt-10"></div>
					</div>
				</div>
			</div>
	</section>
	<!-- End Category Area -->
	<!-- Start Result Area -->
	<section class="course-area section-gap" id="bookResultList">
		
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
	<script src="js/vendor/bootstrap.min.js"></script>
	<script type="text/javascript"
		src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBhOdIF3Y9382fqJYt5I_sswSrEw5eihAA"></script>
	<script src="js/easing.min.js"></script>
	<script src="js/hoverIntent.js"></script>
	<script src="js/superfish.min.js"></script>
	<script src="js/jquery.ajaxchimp.min.js"></script>
	<script src="js/jquery.magnific-popup.min.js"></script>
	<script src="js/owl.carousel.min.js"></script>
	<script src="js/jquery.sticky.js"></script>
	<script src="js/jquery.nice-select.min.js"></script>
	<script src="js/parallax.min.js"></script>
	<script src="js/waypoints.min.js"></script>
	<script src="js/jquery.counterup.min.js"></script>
	<script src="js/mail-script.js"></script>
	<script src="js/main.js"></script>

	<script>
	//[INSERT] to "Category" area
		$(document).ready(function() {
			//sends a GET request to the URL
			fetch('<%=request.getContextPath()%>/genres/all', {
				method: 'GET'
			})
			.then(response => response.json())
			.then(data => {
				var status = data.status;
				var genreList = data.list;
				if(data.status == "true") {
					var htmlString = "";
					for(let i = 0; i < genreList.length; i++) {
						htmlString += "<button onclick='submitGenre(" + genreList[i].genreID + ")' class='btn btn-light mt-10 ml-10'>"+ genreList[i].genre + "</button>";
					}
					$('#genreList').html(htmlString);
				}
			})
		});
		
		function submitGenre(id) {
			fetch('<%=request.getContextPath()%>/genres/books/' + id, {
				method: 'GET'
			})
			.then(response => response.json())
			.then(data => {
				console.log(data);
				var status = data.status;
				var bookList = data.list;
				if(data.status == "true") {
					var htmlString = "";
					htmlString+="<div class='constainer'>"
					htmlString+="<h1 class='mb-10'>RESULT</h1>"
					htmlString += "<div class='row'>";
					for(let i = 0; i < bookList.length; i++) {
						htmlString+="<div class='col-lg-3 col-md-4 col-sm-6'>";   
						htmlString+="<div class='single-course item'>";
						htmlString+="<a href='#'><img href='#' class='img-fluid' src='https://pm1.narvii.com/6464/71a3e3b46d0b0b3b60fc30af9d556fedf43b9495_hq.jpg' alt=''></a>";
						htmlString+="<a href='#'><p class='sale-btn'>Add to cart</p></a>";
						htmlString+="<div class='details'>";
						htmlString+="<a href='#'><h4>"+bookList[i].title+" <span class='price float-right'>"+bookList[i].price+"</span></h4></a>";
						htmlString+="</div></div></div>";
						}
					if(bookList.length==0){
						htmlString+="<p>No Items Found</p>";
					}
					htmlString += "</div></div>";
					$('#bookResultList').html(htmlString);
				}
			})
		}
	</script>
</body>
</html>
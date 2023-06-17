<%
//Author 	  : Thu Htet San
//Admin No    : 2235022
//Class       : DIT/FT/2A/02
//Date		  : 8.6.2023
//Description : Genre Servlet
%>

<!-- [IMPORT] -->
<%@ page import="java.util.ArrayList, model.Genre, model.Book, controller.Authentication"%>

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
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/main.css">
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
			<div class="row justify-content-center" style="margin-bottom: 0px;" id="bookResultList">
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
	<script type="text/javascript"
		src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBhOdIF3Y9382fqJYt5I_sswSrEw5eihAA"></script>
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
				if(data.status == "success") {
					var htmlString = "";
					for(let i = 0; i < genreList.length; i++) {
						htmlString += "<button onclick='submitGenre(" + genreList[i].genreID + ", \""+ genreList[i].genre +"\")' class='btn btn-light mt-10 ml-10'>"+ genreList[i].genre + "</button>";
					}
					$('#genreList').html(htmlString);
				}else if(status == "serverError"){
		    		alert('Server Error!');
		    	}else if(status == "invalid"){
		    		alert('Invalid Request or Data!');
		    	}
			})
		});
		
		function submitGenre(id, name) {
			fetch('<%=request.getContextPath()%>/genres/books/' + id, {
				method: 'GET'
			})
			.then(response => response.json())
			.then(data => {
				var status = data.status;
				var bookList = data.list;
				if(data.status == "success") {
					var htmlString = "";
					for(let i = 0; i < bookList.length; i++) {
						htmlString += '<div class="col-lg-4 col-md-4 col-sm-12 latest-release" style="text-align: center; padding-bottom: 45px;"><div style="position: relative;"><img style="width: 250px; height: 300px;" class="img-fluid" src="<%=request.getContextPath() %>' + bookList[i].image + '" alt=""><p style="position: absolute; bottom: 0; left: 70px; color: white; background: red; padding: 5px 8px; letter-spacing: 1.1px;">' + bookList[i].status + '</p></div><div style="margin-top: 10px;"><a href="<%= request.getContextPath() %>/bookDetail.jsp?id=' + bookList[i].ISBNNo + '"><h4>' + bookList[i].title + '</h4></a><p></p></div></div>';
					}
					if(bookList.length==0){
						htmlString+="<h5 style='font-size: 20px; color: red;'>No Items Found</h5>";
					}
					$('#bookResultList').html(htmlString);
					document.getElementById('search-text').innerHTML = name + " Books";
				}else if(status == "serverError"){
		    		alert('Server Error!');
		    	}
			})
		}
	</script>
</body>
</html>
<%
// Author 	  	: Thu Htet San
// Admin No    	: 2235022
// Class       	: DIT/FT/2A/02
// Group		: 10
// Date		  	: 15.6.2023
// Description 	: Search Books by Title/Authors
%>

<!-- [IMPORT] -->
<%@ page
	import="java.util.ArrayList, model.*"%>

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
<title>BookHaven | AuthorDetail</title>

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
<link rel="icon" type="image/png"
	href="<%=request.getContextPath()%>/img/logo.png">
</head>
<body>
	<%
	String id = request.getParameter("id");
	if (id == null) {
		out.println("<script>alert('Invalid author id!'); location='" + request.getContextPath() + URL.homePage + "';</script>");
		return;
	}
	%>

	<%@ include file="header.jsp"%><!-- #header -->

	<div
		style="display: flex; justify-content: center; padding: 120px 50px; margin: 0px 150px;">
		<div
			style="display: flex; flex-direction: column; align-items: flex-start; text-align: left;">
			<h3 id="name"></h3>
			<div
				style="display: flex; flex-direction: row; margin-top: 15px; justify-content: flex-start;">
				<label id="book-count" style="font-weight: bold;"></label> <label
					style="margin-left: 25px; font-weight: bold;" id="nationality"></label>
				<label style="margin-left: 25px; font-weight: bold;" id="birthdate"></label>
			</div>
			<p style="text-align: justify;" id="description"></p>
			<a id="link" target="_blank"></a>
		</div>
	</div>


	<!-- Start course Area -->
	<section class="course-area section-gap" id="course">
		<div class="container">
			<div class="row d-flex justify-content-center">
				<div class="menu-content pb-60 col-lg-9">
					<div class="title text-center">
						<h1 class="mb-10">Books</h1>
					</div>
				</div>
			</div>
			<div class="row" style="margin-bottom: 0px;" id="bookResultList">
			</div>
		</div>
	</section>
	<!-- End course Area -->

	<!-- start footer Area -->
	<%@ include file="footer.jsp"%>
	<!-- End footer Area -->


	<script src="js/vendor/jquery-2.2.4.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
		integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
		crossorigin="anonymous"></script>
	<script src="js/vendor/bootstrap.min.js"></script>
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
	<script src="js/main.js"></script>


	<script>
	$(document).ready(function() {
		//sends a GET request to the URL
		
		fetch('<%= URL.baseURL + URL.getAuthor + id %>', {
			method: 'GET'
		})
		.then(response => response.json())
		.then(data => {
			if(data != undefined && data != null) {
				document.getElementById('name').innerHTML = data.name;
				document.getElementById('nationality').innerHTML = data.nationality;
				document.getElementById('birthdate').innerHTML = (data.birthDate==null)? "":data.birthDate + " &#128198 ";
				document.getElementById('description').innerHTML = data.biography;
				if(data.link != null){
					document.getElementById('link').innerHTML = "More about me &#x2192;";
				}
				document.getElementById('link').href = data.link;
			}
		});
		
		fetch('<%= URL.baseURL + URL.getBookByAuthorID + id %>', {
			method: 'GET'
		})
		.then(response => response.json())
		.then(data => {
			if(data != undefined && data != null) {
				document.getElementById('book-count').innerHTML = data.length + " Book &#128213;";
				var htmlString = "";
				for(let i = 0; i < data.length; i++) {
	    			htmlString += '<div class="col-lg-4 col-md-4 col-sm-12 latest-release" style="text-align: center; padding-bottom: 45px;"><div style="position: relative;"><img style="width: 250px; height: 300px;" class="img-fluid" src="' + data[i].image + '" alt=""><p style="position: absolute; bottom: 0; left: 70px; color: white; background: red; padding: 5px 8px; letter-spacing: 1.1px;">' + data[i].status + '</p></div><div style="margin-top: 10px;"><a href="<%= request.getContextPath() + URL.bookDetail  %>?id=' + data[i].isbnno + '"><h4>' + data[i].title + '</h4></a><p></p></div></div>';
		    	}
				if(data.length == 0) {
					htmlString += "<p>No Books Yet</p>";
				}
				$('#bookResultList').html(htmlString);
			}
			else {
				alert("Server error!");
			}
		})
	});
	</script>
</body>
</html>

<%
// Author 	  	: Thu Htet San
// Admin No    	: 2235022
// Class       	: DIT/FT/2A/02
// Group		: 10
// Date		  	: 5.8.2023
// Description 	: Search Books/Authors
%>

<<<<<<< Updated upstream
<%@ page import="java.util.ArrayList, model.*"%>

=======

<%@ page import="java.util.ArrayList, model.*"%>


<!-- [IMPORT] -->

<%@ page import="java.util.ArrayList, model.*"%>

<%@ page
	import="java.util.ArrayList, model.Genre, model.Book"%>



>>>>>>> Stashed changes
<!DOCTYPE html>
<html lang="zxx" class="no-js">
<head>
<!-- Mobile Specific Meta -->
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<!-- Favicon-->
<!-- Author Meta -->
<meta name="author" content="colorlib">
<!-- Meta Description -->
<meta name="description" content="">
<!-- Meta Keyword -->
<meta name="keywords" content="">
<!-- meta character set -->
<meta charset="UTF-8">
<!-- Site Title -->
<title>BookHaven | Search</title>

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
</head>
<body>
	<%
		String status = (String) request.getAttribute("status");
		if(status != null) {
			if(status.equals(Status.invalidData)) {
				out.println("<script>alert('Invalid data!');</script>"); 
				out.println("<script>location='" + request.getContextPath() + URL.getBookListServlet + "';</script>");
				return;
			}
			else if(status.equals(Status.invalidRequest)) {
				out.println("<script>alert('Invalid request!');</script>");
				out.println("<script>location='" + request.getContextPath() + URL.getBookListServlet + "';</script>");
				return;
			}
			else if(status.equals(Status.serverError)) {
				out.println("<script>alert('Server error!');</script>");
				return;
			}
		}
	%>

	<%@ include file="header.jsp"%><!-- #header -->
	<!-- Start Search Area -->
	<section>
		<div>
			<div style="padding: 80px">
				<div>
					<div
						style="display: flex; flex-direction: row; justify-content: center;">
						<label style="margin: 5px 10px;"searchInput">Search By:</label> <select
							id="searchOption" name="searchOption"
							style="padding: 5px; margin-right: 10px;">
							<option value="book">Title</option>
							<option value="author">Author</option>
						</select> <input type="text" id="searchInput" name="searchInput"
							style="padding: 5px; margin-right: 10px;">
						<button onclick="search()"
							style="padding: 5px 10px; background-color: #337ab7; color: #fff; border: none; cursor: pointer;">Search</button>
					</div>
				</div>
			</div>
		</div>
	</section>

	<!-- End Search Area -->
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
<<<<<<< Updated upstream
=======
			
>>>>>>> Stashed changes
			<div style="padding: 10px 0;" class="homepage-section">
				<div>
					<div class="book-div" id="bookResultList"></div>
				</div>
			</div>
<<<<<<< Updated upstream
=======


			<div class="row justify-content-center" style="margin-bottom: 0px;"
				id="bookResultList"></div>

>>>>>>> Stashed changes
		</div>
	</section>
	<!-- End Result Area -->

	<!-- start footer Area -->
	<%@ include file="footer.jsp"%>
	<!-- End footer Area -->


	<script
		src="<%= request.getContextPath() %>/js/vendor/jquery-2.2.4.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
		integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
		crossorigin="anonymous"></script>
	<script src="js/vendor/bootstrap.min.js"></script>
	<script src="<%= request.getContextPath() %>/js/easing.min.js"></script>
	<script src="<%= request.getContextPath() %>/js/hoverIntent.js"></script>
	<script src="<%= request.getContextPath() %>/js/superfish.min.js"></script>
	<script
		src="<%= request.getContextPath() %>/js/jquery.ajaxchimp.min.js"></script>
	<script
		src="<%= request.getContextPath() %>/js/jquery.magnific-popup.min.js"></script>
	<script src="<%= request.getContextPath() %>/js/owl.carousel.min.js"></script>
	<script src="<%= request.getContextPath() %>/js/jquery.sticky.js"></script>
	<script
		src="<%= request.getContextPath() %>/js/jquery.nice-select.min.js"></script>
	<script src="<%= request.getContextPath() %>/js/parallax.min.js"></script>
	<script src="<%= request.getContextPath() %>/js/waypoints.min.js"></script>
	<script src="<%= request.getContextPath() %>/js/jquery.counterup.min.js"></script>
	<script src="<%= request.getContextPath() %>/js/mail-script.js"></script>
	<script src="<%= request.getContextPath() %>/js/main.js"></script>

	<script>
	//[INSERT] to "Category" area
		function search() {
		    var searchOption = document.getElementById("searchOption").value;
		    var searchInput = document.getElementById("searchInput").value;

		    if (searchOption === 'author') {
		      searchAuthor(searchInput);
		    } 
		    else if (searchOption === 'book') {
		      searchBook(searchInput);
		    }
		  }

		  function searchAuthor(searchValue) {
<<<<<<< Updated upstream
=======

>>>>>>> Stashed changes
			  document.getElementById('search-text').innerHTML = "Search Result For Author \"" + searchValue + "\"";
			  let htmlStr = "";
			  htmlStr += "<h5 style='font-size: 20px; color: grey;'>Loading ...</h5>";
			  $('#bookResultList').html(htmlStr);
			  
			  fetch('<%= URL.baseURL + URL.searchBookByAuthor %>' + searchValue, {
			    	method: 'GET'
			  })
			  .then(response => response.json())
			  .then(data => {
			    	
			    	console.log(data);
			    	
			    	htmlStr = "";
			    	if(data != undefined && data != null && data.length > 0) {
<<<<<<< Updated upstream
=======

			  fetch('<%=request.getContextPath()%>/search/author/'+searchValue, {
			    	method:'GET'
			    	})
			    .then(response=>response.json())
			    .then(data => {
			    .then(data=>{
			    	console.log(data);
			    	var status=data.status;
			    	var bookList=data.list;
			    	var authorList=data.authorList;
			    	if(status == "success"){
			    		
			    		var htmlString="";
			    		let htmlStr = "";

>>>>>>> Stashed changes
			    		for(let i = 0; i < data.length; i++) {
			    			htmlStr += '<div style="margin-bottom: 30px;">';
							
							htmlStr += '<div class="book-img-div" onclick="goto(\'' + data[i].isbnno +'\')"><img src="<%= URL.imageLink %>' + data[i].image + '"><span class="rating">';
							htmlStr += '<span class="full-star"></span> ' + data[i].rating.toFixed(1) + '</span></div>';
							
							title = data[i].title;
							if(title.length > 30) {
								title = title.slice(0, 25) + "...";
							}
							htmlStr += '<label class="book-title" onclick="goto(\'' + data[i].isbnno +'\')">' + title + '</label>';
							htmlStr += '<label class="book-title" onclick="gotoauthor(\'' + data[i].authors[0].authorID +'\')">' + data[i].authors[0].name + '</label>';
							htmlStr += '</div>';
			    		}
			    	}
			    	else {
			    		htmlStr += "<h5 style='font-size: 20px; color: red;'>No Items Found</h5>";
			    	}
			    	
			    	$('#bookResultList').html(htmlStr);
			    })
			    .catch(error => {
			    	console.log(error);
			    	alert('Error in retrieving results!');
			    })
		  }

		  function searchBook(searchValue) {
			document.getElementById('search-text').innerHTML = "Search Result For Book \"" + searchValue + "\"";
			let htmlStr = "";
			htmlStr += "<h5 style='font-size: 20px; color: grey;'>Loading ...</h5>";
			$('#bookResultList').html(htmlStr);
			
		    fetch('<%= URL.baseURL + URL.searchBookByTitle %>' + searchValue, {
		    	method: 'GET'
		    })
		    .then(response => response.json())
		    .then(data => {
		    	
		    	htmlStr = "";
		    	if(data != undefined && data != null && data.length > 0) {
		    		
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
		    	}
		    	else {
		    		htmlStr += "<h5 style='font-size: 20px; color: red;'>No Items Found</h5>";
		    	}
		    	
		    	$('#bookResultList').html(htmlStr);
		    })
		    .catch(error => {
		    	console.log(error);
		    	alert('Error in retrieving results!');
		    })
		  }
		  
		  function goto(isbn) {
			  location = '<%= request.getContextPath() + URL.bookDetail %>?id=' + isbn;
		  }
		  
		  function gotoauthor(authorID) {
			  location = '<%= request.getContextPath() + URL.authorDetail %>?id=' + authorID;
		  }
		
	</script>
</body>
</html>
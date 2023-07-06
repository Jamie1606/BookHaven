
<%
//Author: Zay Yar Tun
//Admin No: 2235035
//Date: 16.6.2023
// Class: DIT/FT/2A/02
// Group: 10
//Description: book detail page
// book detail layout design is referenced from https://s3.envato.com/files/311567532/Image%20Preview%20Set%20Figma/05_BookDetail-Description.jpg
%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="controller.Authentication, javax.servlet.http.HttpSession, controller.TestReg"%>
<!DOCTYPE html>
<html lang="zxx" class="no-js">

<head>
<!-- Mobile Specific Meta -->
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<!-- Favicon-->
<link rel="shortcut icon"
	href="<%=request.getContextPath()%>/img/fav.png">
<!-- Author Meta -->
<meta name="author" content="codepixer">
<!-- Meta Description -->
<meta name="description" content="">
<!-- Meta Keyword -->
<meta name="keywords" content="">
<!-- meta character set -->
<meta charset="UTF-8">
<!-- Site Title -->
<title>BookHaven | Book Detail</title>

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
.skeleton {
	background-color: #f6f7f8;
	animation: skeleton-loading 1.5s ease-in-out infinite alternate;
}
.skeleton-img {
	min-width: 330px;
	min-height: 500px;
	background-color: #aaa;
}
.skeleton-title {
	min-width: 250px;
	min-height: 40px;
	background-color: #aaa;
}
.skeleton-description {
	min-width: 100%;
	min-height: 200px;
	background-color: #aaa;
}
.skeleton-text {
	min-width: 100%;
	min-height: 40px;
	background-color: #aaa;
}
@keyframes skeleton-loading {
	 0% {
	 	opacity: 1;
	 }
	 100% {
	 	opacity: 0.5;
	 }
}
.latest-release h4:hover {
	color: red;
}

.latest-release p {
	cursor: default;
}

.rating .star {
	color: gold; /* Change the color of the stars */
	font-size: 22px; /* Adjust the size of the stars */
	font-weight: bold;
	vertical-align: middle;
}

#btn-cart {
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

#btn-cart:after {
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

#btn-cart:active:after {
	padding: 0;
	margin: 0;
	opacity: 1;
	transition: 0s;
	outline: none;
}
</style>
</head>

<body>
	<%
	String id = request.getParameter("id");
	if (id == null) {
		out.println("<script>alert('Invalid book id!'); location='" + request.getContextPath() + "/index.jsp';</script>");
		return;
	}
	else {
		if(!TestReg.matchISBN(id)) {
			out.println("<script>alert('Invalid book id!'); location='" + request.getContextPath() + "/index.jsp';</script>");
			return;
		}
	}
	%>
	<%@ include file="header.jsp"%>

	<!-- Start course Area -->
	<section class="course-area section-gap"
		style="padding-bottom: 30px; min-height: 500px;" id="course">
		<div class="container">
			<div class="row d-flex justify-content-center">
				<div class="col-lg-4">
					<img class="skeleton skeleton-img" style="width: 330px; height: 500px; border-radius: 10px;"
						id="book-image" src="" />
				</div>
				<div class="col-lg-8"
					style="color: black; font-size: 16px; letter-spacing: 1.1px;">
					<h1 style="font-size: 26px;" id="book-title" class="skeleton skeleton-title"></h1>
					<div id="rating" class="rating skeleton skeleton-text"
						style="margin-top: 20px; vertical-align: middle;">
						<span class="star"></span> <span class="star"></span>
						<span class="star"></span> <span class="star"></span>
						<span class="star"></span>&ensp;&ensp; <span
							style="font-weight: bold; color: #333; vertical-align: middle; margin-right: 50px;"
							id="book-rating"></span> <span
							style="font-size: 23px; vertical-align: middle;">&#9993;</span><span
							style="color: black; vertical-align: middle; font-size: 15px; margin-left: 15px;"></span>
					</div>
					<p style="text-align: justify; margin-top: 15px; font-size: 13px;"
						id="book-description" class="skeleton skeleton-description"></p>
					<div
						style="display: flex; flex-direction: row; justify-content: space-between;">
						<div style="display: flex; flex: 1; flex-direction: column;">
							<label style="color: grey; font-size: 13px;">Written By</label> <label
								style="color: #555; font-weight: bold;" class="skeleton skeleton-text" id="book-author"></label>
						</div>
						<div style="display: flex; flex: 1; flex-direction: column;">
							<label style="color: grey; font-size: 13px;">Publisher</label> <label
								style="color: #555; font-weight: bold;" class="skeleton skeleton-text" id="book-publisher"></label>
						</div>
						<div style="display: flex; flex: 1; flex-direction: column;">
							<label style="color: grey; font-size: 13px;">Year</label> <label
								style="color: #555; font-weight: bold;"
								class="skeleton skeleton-text" id="book-publicationdate"></label>
						</div>
						<div style="display: flex; align-items: center;">
							<label class="skeleton skeleton-text"
								style="font-weight: bold; color: green; padding: 10px 30px; border-radius: 10px;"
								id="book-status"></label>
						</div>
					</div>
					<hr style="border: 0.5px dashed #666;">
					<div
						style="display: flex; padding-top: 20px; flex-direction: row; justify-content: space-between; align-items: center;">
						<div>
							<label id="book-price"
								style="font-size: 26px; font-weight: bold;"></label>
						</div>
						<%
						HttpSession session2 = request.getSession();
						String memberID = (String) session2.getAttribute("memberID");
						if (memberID != null) {
						%>
						<div style="display: flex; align-items: center;" id="cart">
							<label id="btn-minus" onclick="changeQty(-1)"
								style="font-size: 28px; font-weight: bold; color: #6c5dd4; margin: 5px 10px; vertical-align: middle; padding: 5px 10px; user-select: none;">-</label><label
								id="buy-qty"
								style="font-size: 18px; user-select: none; margin: 5px 25px; vertical-align: middle;">1</label><label
								id="btn-plus" onclick="changeQty(1)"
								style="font-size: 28px; font-weight: bold; color: #6c5dd4; margin: 5px 10px; vertical-align: middle; padding: 5px 10px; user-select: none;">+</label>
							<button id="btn-cart" onclick="addtoCart()"
								style="font-size: 13px; margin-left: 35px; color: white; padding: 10px 30px; outline: none; border: 1px solid #6c5dd4; background-color: #6c5dd4; border-radius: 10px; box-shadow: 2px 2px 5px 1px #777;">
								<i style="padding: 0px 8px; font-size: 16px;"
									class="fa fa-shopping-cart"></i> Add to cart
							</button>
						</div>
						<%
						}
						%>
					</div>
				</div>
			</div>
		</div>
	</section>
	<!-- End course Area -->

	<div id="detail"
		style="width: 75%; text-align: center; visibility: hidden; margin: 50px auto 120px auto;">
		<div
			style="display: flex; flex: 1; flex-direction: row; justify-content: space-between;">
			<div
				style="display: flex; flex-direction: column; align-items: space-between; text-align: left; justify-content: center;">
				<h2 style="user-select: none;">
					<span style="cursor: pointer;" id="btn-detail"
						onclick="showDetail()">Book Detail</span> <span
						style="cursor: pointer; margin-left: 90px; color: grey;"
						id="btn-review" onclick="showReview()">Reviews</span>
				</h2>
				<div
					style="display: flex; margin-top: 50px; margin-left: 30px; flex-direction: row; color: black;">
					<div
						style="display: flex; flex-direction: column; justify-content: center; font-size: 16px; font-weight: bold;">
						<label>Title</label>
						<hr>
						<label>ISBN No</label>
						<hr>
						<label>Author</label>
						<hr>
						<label>Publisher</label>
						<hr>
						<label>Publication Date</label>
						<hr>
						<label>Qty in Stock</label>
						<hr>
						<label>Price</label>
						<hr>
						<label>Genre</label>
					</div>
					<div
						style="display: flex; margin-left: 140px; flex-direction: column; justify-content: center; font-size: 16px;">
						<label id="book-detail-title"></label>
						<hr>
						<label id="book-detail-isbn"></label>
						<hr>
						<label id="book-detail-author"></label>
						<hr>
						<label id="book-detail-publisher"></label>
						<hr>
						<label id="book-detail-publicationdate"></label>
						<hr>
						<label id="book-detail-qty"></label>
						<hr>
						<label id="book-detail-price"></label>
						<hr>
						<label id="book-detail-genre"></label>
					</div>
				</div>
			</div>
			<div
				style="display: flex; width: 30%; flex-direction: column; text-align: left;">
				<h2>Related Books</h2>
				<div id="related-book1"
					style="display: flex; padding-bottom: 30px; visibility: hidden; flex-direction: row; margin-top: 15px;">
					<img
						style="width: 100px; height: 150px; margin-right: 30px; border-radius: 7px;"
						id="book-related-image1" src="" />
					<div
						style="display: flex; flex-direction: column; align-items: flex-start;">
						<a href="" id="book-related-title1"
							style="font-size: 14px; color: black; font-weight: bold; text-decoration: none;"></a>
						<div style="margin-top: 15px;">
							<span id="book-related-rating1"
								style="font-size: 20px; vertical-align: middle; color: gold;"></span>
							<span id="book-related-ratingtext1"
								style="margin-left: 10px; font-weight: bold; color: #777; vertical-align: middle; letter-spacing: 1.1px;"></span>
						</div>
						<p
							style="letter-spacing: 1.1px; margin-top: 10px; font-weight: bold; color: #555;"
							id="book-related-price1"></p>
						<p style="font-weight: bold;" id="book-related-status1"></p>
					</div>
				</div>

				<div id="related-book2"
					style="display: flex; padding-bottom: 30px; visibility: hidden; flex-direction: row; margin-top: 15px;">
					<img
						style="width: 100px; height: 150px; margin-right: 30px; border-radius: 7px;"
						id="book-related-image2" src="" />
					<div
						style="display: flex; flex-direction: column; align-items: flex-start;">
						<a href="" id="book-related-title2"
							style="font-size: 14px; color: black; font-weight: bold; text-decoration: none;"></a>
						<div style="margin-top: 15px;">
							<span id="book-related-rating2"
								style="font-size: 20px; vertical-align: middle; color: gold;"></span>
							<span id="book-related-ratingtext2"
								style="margin-left: 10px; font-weight: bold; color: #777; vertical-align: middle; letter-spacing: 1.1px;"></span>
						</div>
						<p
							style="letter-spacing: 1.1px; margin-top: 10px; font-weight: bold; color: #555;"
							id="book-related-price2"></p>
						<p style="font-weight: bold;" id="book-related-status2"></p>
					</div>
				</div>
			</div>
		</div>
	</div>

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
	<script src="<%=request.getContextPath()%>/js/mail-script.js"></script>
	<script src="<%=request.getContextPath()%>/js/main.js"></script>

	<script>
		$(document).ready(function() {
			fetch('<%=request.getContextPath()%>/book/<%=id%>',
			{
				method: 'GET'
			})
			.then(response => response.json())
			.then(data => {
				var list = data.list;
				var authorList = data.authorList;
				var genreList = data.genreList;
				var status = data.status;
				if(status == "serverError") {
					alert('Error in retrieving book detail!');
					location = "index.jsp";
				}
				else if(status == "invalid") {
					alert('Invalid request for retrieving book detail!');
					location = "index.jsp";
				}
				else {
					$('#course').css({"visibility": "visible"});
					$('#detail').css({"visibility": "visible"});
					let authors = "";
					for(let i = 0; i < authorList.length; i++) {
						if(i == authorList.length - 1) {
							authors += "<a href='<%= request.getContextPath() %>/authorDetail.jsp?id="+ authorList[i].authorID +"' style='color: #555;'>" + authorList[i].name + "</a>";
						}
						else {
							authors += "<a href='<%= request.getContextPath() %>/authorDetail.jsp?id=/"+ authorList[i].authorID +"' style='color: #555;'>" + authorList[i].name + "</a>, "; 
						}
					}
					let genres = "";
					for(let i = 0; i < genreList.length; i++) {
						if(i == genreList.length - 1) {
							genres += genreList[i].genre;
						}
						else {
							genres += genreList[i].genre + ", ";
						}
					}
					$('#book-status').html(makeCapital(list[0].status));
					if(list[0].status == "available") {
						$('#book-status').css({"color": "green", "backgroundColor": "rgba(0, 220, 0, 0.2)"});
					}
					else {
						$('#book-status').css({"color": "red", "backgroundColor": "rgba(220, 0, 0, 0.2)"});
					}
					$('#book-price').html("$" + list[0].price.toFixed(2));
					
					$('#book-rating').html(list[0].rating.toFixed(1));
					
					$('#book-author').html(authors);
					
					$('#book-title').html(list[0].title);
					
					$('#book-detail-genre').html(genres);
					$('#book-detail-publisher').html(list[0].publisher);
					$('#book-detail-publicationdate').html(list[0].publicationDate);
					$('#book-detail-author').html(authors);
					$('#book-detail-title').html(list[0].title);
					$('#book-detail-isbn').html(list[0].ISBNNo);
					$('#book-detail-qty').html(list[0].qty);
					if(list[0].qty <= 0) {
						$('#cart').css({"visibility": "hidden"});
					}
					$('#book-detail-price').html("$" + list[0].price.toFixed(2));
					
					$('#book-description').html(list[0].description);
					
					$('#book-publisher').html(list[0].publisher);
					
					let pubdate = new Date(list[0].publicationDate);
					$('#book-publicationdate').html(pubdate.getFullYear());
					
					$('#book-image').attr("src", "<%=request.getContextPath()%>" + list[0].image);
					
					let star = document.getElementsByClassName("star");
					for(let i = 0; i < star.length; i++) {
						if(i <= list[0].rating - 1) {
							star[i].innerHTML = "&#9733;";
						}
						else {
							star[i].innerHTML = "&#9734;";
						}
					}
					
					// Get all elements with the "skeleton" class
					var elements = document.getElementsByClassName("skeleton");

					// Convert the HTMLCollection to an array to iterate over it
					var elementsArray = Array.from(elements);

					// Loop through the elements and remove the "skeleton" class
					elementsArray.forEach(function(element) {
					  element.classList.remove("skeleton");
					  element.classList.remove("skeleton-text");
					  element.classList.remove("skeleton-title");
					  element.classList.remove("skeleton-img");
					  element.classList.remove("skeleton-description");
					});
					
					fetch('<%=request.getContextPath()%>/book/genres/<%=id%>/' + genres, {
						method: 'GET'
					})
					.then(response => response.json())
					.then(data => {
						var list = data.list;
						var status = data.status;
						if(status == "serverError") {
							alert('Error in retrieving related book!');
						}
						else if(status == "invalid") {
							alert('Invalid request for retrieving related book!');
						}
						else {
							for(let i = 0; i < list.length; i++) {
								$('#related-book' + (i + 1)).css({"visibility": "visible"});
								$('#book-related-image' + (i + 1)).attr("src", "<%=request.getContextPath()%>" + list[i].image);
								$('#book-related-title' + (i + 1)).html(list[i].title);
								$('#book-related-title' + (i + 1)).attr("href", "<%=request.getContextPath()%>/bookDetail.jsp?id=" + list[i].ISBNNo);
								$('#book-related-ratingtext' + (i + 1)).html(list[i].rating.toFixed(1));
								if(list[i].rating > 0) {
									$('#book-related-rating' + (i + 1)).html("&#9733;");
								}
								else {
									$('#book-related-rating' + (i + 1)).html("&#9734;");
								}
								$('#book-related-price' + (i + 1)).html("$" + list[i].price.toFixed(2));
								$('#book-related-status' + (i + 1)).html(makeCapital(list[i].status));
								if(list[i].status == "available") {
									$('#book-related-status' + (i + 1)).css({"color": "green"});
								}
								else {
									$('#book-related-status' + (i + 1)).css({"color": "red"});
								}
							}
						}
					})
				}
			});
		})
		
		function makeCapital(str) {
			return str.charAt(0).toUpperCase() + str.slice(1);
		}
		
		function showReview() {
			$('#btn-detail').css({"color": "grey"});
			$('#btn-review').css({"color": "black"});
		}
		
		function showDetail() {
			$('#btn-review').css({"color": "grey"});
			$('#btn-detail').css({"color": "black"});
		}
		
		function changeQty(num) {
			let buyQty = document.getElementById('buy-qty').innerHTML;
			let stockQty = document.getElementById('book-detail-qty').innerHTML;
			let price = document.getElementById('book-detail-price').innerHTML.slice(1);
			let result = Number(buyQty) + num;
			if(result >= 1 && result <= stockQty) {
				$('#buy-qty').html(result);
				price = Number(price) * result;
				$('#book-price').html("$" + price.toFixed(2));
			}
		}
		
		function addtoCart() {
			$('#btn-cart').prop('disabled', true);
			$('#btn-cart').html('Loading...');
			let qty = Number(document.getElementById('buy-qty').innerHTML);
			fetch('<%=request.getContextPath()%>/book/qty/<%=id%>/' + qty, {
				method: 'GET'
			})
			.then(response => response.json())
			.then(data => {
				$('#btn-cart').prop('disabled', false);
				$('#btn-cart').html('<i style="padding: 0px 8px; font-size: 16px;" class="fa fa-shopping-cart"></i> Add to cart');
				let status = data.status;
				if(status == "invalid") {
					alert('Invalid request!');
				}
				else if(status == "full") {
					alert('You have reached the maximum quantity for this book!');
				}
				else if(status == "ok") {
					alert('Successfully added to cart!');
					location = 'cart.jsp';
				}
				else {
					alert('Server Error! Please try again later!');
				}
			})
		}
	</script>
</body>

</html>
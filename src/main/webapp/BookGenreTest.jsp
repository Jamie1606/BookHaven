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
	<!-- Start banner Area -->
	<section class="generic-banner">
		<div class="container">
			<div class="row height align-items-center justify-content-center">
				<div>
					<div>
						<h2 class="text-white">Genre/Category</h2>
						<div id="genreList" class="button-group-area mt-10">							
						</div>
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
						<h1 class="mb-10">Top Courses That are open for Students</h1>
						<p>Who are in extremely love with eco friendly system.</p>
					</div>
				</div>
			</div>

			<div class="row">
				<!-- DYNAMIC RESULT -->
				<div class="col-lg-3 col-md-4 col-sm-6">
					<div class="single-course item">
						<img class="img-fluid" src=""
							alt="">
						<p class="sale-btn">For Sale</p>
						<div class="details">
							<a href="#"><h4>
									
									<span class="price float-right">$25</span>
								</h4></a>
							<p></p>
						</div>
					</div>
				</div>
				<!-- DYNAMIC RESULT END-->

				<div class="col-lg-3 col-md-4 col-sm-6">
					<div class="single-course item">
						<img class="img-fluid" src="img/c1.jpg" alt="">
						<p class="sale-btn">For Sale</p>
						<div class="details">
							<a href="#"><h4>
									Breakthrough Thinking <span class="price float-right">$25</span>
								</h4></a>
							<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit,
								sed do eiusmod tempor incididunt ut labore et dolore magna</p>
						</div>
					</div>
				</div>
				<div class="col-lg-3 col-md-4 col-sm-6">
					<div class="single-course item">
						<img class="img-fluid" src="img/c2.jpg" alt="">
						<p class="sale-btn">For Sale</p>
						<div class="details">
							<a href="#"><h4>
									Breakthrough Thinking <span class="price float-right">$25</span>
								</h4></a>
							<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit,
								sed do eiusmod tempor incididunt ut labore et dolore magna</p>
						</div>
					</div>
				</div>
				<div class="col-lg-3 col-md-4 col-sm-6">
					<div class="single-course item">
						<img class="img-fluid" src="img/c3.jpg" alt="">
						<p class="sale-btn">For Sale</p>
						<div class="details">
							<a href="#"><h4>
									Breakthrough Thinking <span class="price float-right">$25</span>
								</h4></a>
							<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit,
								sed do eiusmod tempor incididunt ut labore et dolore magna</p>
						</div>
					</div>
				</div>
				<div class="col-lg-3 col-md-4 col-sm-6">
					<div class="single-course item">
						<img class="img-fluid" src="img/c1.jpg" alt="">
						<p class="sale-btn">For Sale</p>
						<div class="details">
							<a href="#"><h4>
									Breakthrough Thinking <span class="price float-right">$25</span>
								</h4></a>
							<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit,
								sed do eiusmod tempor incididunt ut labore et dolore magna</p>
						</div>
					</div>
				</div>
				<div class="col-lg-3 col-md-4 col-sm-6">
					<div class="single-course item">
						<img class="img-fluid" src="img/c2.jpg" alt="">
						<p class="sale-btn">For Sale</p>
						<div class="details">
							<a href="#"><h4>
									Breakthrough Thinking <span class="price float-right">$25</span>
								</h4></a>
							<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit,
								sed do eiusmod tempor incididunt ut labore et dolore magna</p>
						</div>
					</div>
				</div>
				<div class="col-lg-3 col-md-4 col-sm-6">
					<div class="single-course item">
						<img class="img-fluid" src="img/c3.jpg" alt="">
						<p class="sale-btn">For Sale</p>
						<div class="details">
							<a href="#"><h4>
									Breakthrough Thinking <span class="price float-right">$25</span>
								</h4></a>
							<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit,
								sed do eiusmod tempor incididunt ut labore et dolore magna</p>
						</div>
					</div>
				</div>
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
		$(document).ready(function() {
			fetch('<%= request.getContextPath() %>/genres/all', {
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
			fetch('<%= request.getContextPath() %>/genres/books/' + id, {
				method: 'GET'
			})
			.then(response => response.json())
			.then(data => {
				console.log(data);
			})
		}
	</script>
</body>
</html>
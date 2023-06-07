<%
//Author: Zay Yar Tun
//Admin No: 2235035
//Date: 7.6.2023
//Description: book registration page
%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*, java.text.*, model.Author, model.Genre"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="utf-8">
<meta content="width=device-width, initial-scale=1.0" name="viewport">

<title>BookHaven | Book Registration</title>
<meta content="" name="description">
<meta content="" name="keywords">

<!-- Google Fonts -->
<link href="https://fonts.gstatic.com" rel="preconnect">
<link
	href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i|Nunito:300,300i,400,400i,600,600i,700,700i|Poppins:300,300i,400,400i,500,500i,600,600i,700,700i"
	rel="stylesheet">

<!-- Vendor CSS Files -->
<link href="../assets/vendor/bootstrap/css/bootstrap.min.css"
	rel="stylesheet">
<link href="../assets/vendor/bootstrap-icons/bootstrap-icons.css"
	rel="stylesheet">
<link href="../assets/vendor/boxicons/css/boxicons.min.css"
	rel="stylesheet">
<link href="../assets/vendor/quill/quill.snow.css" rel="stylesheet">
<link href="../assets/vendor/quill/quill.bubble.css" rel="stylesheet">
<link href="../assets/vendor/remixicon/remixicon.css" rel="stylesheet">
<link href="../assets/vendor/simple-datatables/style.css"
	rel="stylesheet">
	
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-select@1.14.0-beta3/dist/css/bootstrap-select.min.css">

<!-- Latest compiled and minified JavaScript -->

<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.14.3/dist/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.1.3/dist/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>

<script src="../assets/js/bootstrap-select.js"></script>

<!-- Template Main CSS File -->
<link href="../assets/css/style.css" rel="stylesheet">
<link rel="icon" type="image/png" href="../img/logo.png">

<!-- =======================================================
  * Template Name: NiceAdmin
  * Updated: Mar 09 2023 with Bootstrap v5.2.3
  * Template URL: https://bootstrapmade.com/nice-admin-bootstrap-admin-html-template/
  * Author: BootstrapMade.com
  * License: https://bootstrapmade.com/license/
  ======================================================== -->
</head>

<body>

	<%@ include file="adminheader.jsp"%>

	<%@ include file="adminsidebar.jsp"%>

	<%
	// retrieve author and genre list from request attributes sent from book servlet
	ArrayList<Author> authorList = (ArrayList<Author>)request.getAttribute("authorList");
	ArrayList<Genre> genreList = (ArrayList<Genre>)request.getAttribute("genreList");

	Calendar calendar = Calendar.getInstance();
	calendar.setTime(new Date());
	calendar.add(Calendar.YEAR, 1);
	Date date = calendar.getTime();
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	String dateStr = formatter.format(date);
	%>

	<main id="main" class="main">

		<div class="pagetitle">
			<h1>Book Registration</h1>
			<nav>
				<ol class="breadcrumb">
					<li class="breadcrumb-item"><a href="adminHomePage.jsp">Home</a></li>
					<li class="breadcrumb-item">Registration Forms</li>
					<li class="breadcrumb-item active">Book Registration</li>
				</ol>
			</nav>
		</div>
		<!-- End Page Title -->
		<section class="section">
			<div class="row">
				<div class="col-lg-12">
					<div class="card">
						<div class="card-body">
							<h5 class="card-title">Book Information</h5>

							<!-- Multi Columns Form -->
							<form class="row g-3" action="books" method="post" enctype="multipart/form-data">
								<div class="col-md-12">
									<label for="isbn" class="form-label">ISBN No.</label> <input
										type="text" name="isbn" class="form-control" id="isbn" required>
								</div>
								<div class="col-md-12">
									<label for="title" class="form-label">Title</label> <input
										type="text" name="title" class="form-control" id="title" required>
								</div>

								<div class="col-md-4">
									<label for="page" class="form-label">Page</label> <input
										type="number" name="page" class="form-control" id="page" min="1" required>
								</div>
								<div class="col-md-4">
									<label for="price" class="form-label">Price</label> <input
										type="number" name="price" step=".01" min="5" class="form-control" id="price" required>
								</div>
								<div class="col-md-4">
									<label for="qty" class="form-label">Qty</label> <input
										type="number" name="qty" class="form-control" id="qty" min="0" required>
								</div>

								<div class="col-md-6">
<<<<<<< HEAD
									<label for="author" class="form-label">Author</label> <select
										id="author" name="author" class="form-control selectpicker" multiple="multiple" required>
										<%
											for(Author author: authorList) {
												out.println("<option value='" + author.getAuthorID() + "'>" + author.getName() + "</option>");
											}
										%>
									</select>
								</div>
								<div class="col-md-6">
									<label for="genre" class="form-label">Genre</label> <select
										id="genre" name="genre" class="form-control selectpicker" multiple="multiple" required>
										<%
											for(Genre genre: genreList) {
												out.println("<option value='" + genre.getGenreID() + "'>" + genre.getGenre() + "</option>");
											}
										%>
									</select>
								</div>

								<div class="col-md-6">
=======
>>>>>>> dd75e72b3b3b5185a207a026cd725b1210fe2aa5
									<label for="publisher" class="form-label">Publisher</label> <input
										type="text" name="publisher" class="form-control" id="publisher" required>
								</div>
								<div class="col-md-6">
									<label for="publicationdate" class="form-label">Publication
										Date</label> <input type="date" class="form-control"
										id="publicationdate" name="publicationdate" max="<%=dateStr%>" required>
								</div>

								<div class="col-12">
									<label for="description" class="form-label">Description</label>
									<textarea class="form-control" name="description" id="description"></textarea>
								</div>
								<div class="col-12">
									<label for="image" class="form-label">Image</label> <input
										type="file" class="form-control" name="image" id="image" accept="image/*">
								</div>
								<div class="col-md-12">
<<<<<<< HEAD
									<label for="image3d" class="form-label">3D Image</label> <input
										type="file" class="form-control" name="image3d" id="image3d" accept="image/*">
								</div>
								<div class="col-md-12">
									<label for="status" class="form-label">Status</label> <select
										id="status" class="form-control" name="status">
=======
									<label for="image3d" class="form-label">3D Image</label>
									<input type="file" class="form-control" id="image3d"
										placeholder="Apartment, studio, or floor">
								</div>
								<div class="col-md-12">
									<label for="status" class="form-label">Status</label>
									<select id="status" class="form-control">
>>>>>>> dd75e72b3b3b5185a207a026cd725b1210fe2aa5
										<option selected value="available">Available</option>
										<option value="unavailable">Unavailable</option>
									</select>
								</div>
								<div class="text-center">
									<button type="submit" class="btn btn-primary">Save</button>
									<button type="reset" class="btn btn-secondary">Clear</button>
								</div>
							</form>
							<!-- End Multi Columns Form -->

						</div>
					</div>
				</div>
			</div>
		</section>

	</main>
	<!-- End #main -->

	<!-- ======= Footer ======= -->
	<footer id="footer" class="footer">
		<div class="copyright">
			&copy; Copyright <strong><span>BookHaven</span></strong>. All Rights
			Reserved
		</div>
		<div class="credits">
			<!-- All the links in the footer should remain intact. -->
			<!-- You can delete the links only if you purchased the pro version. -->
			<!-- Licensing information: https://bootstrapmade.com/license/ -->
			<!-- Purchase the pro version with working PHP/AJAX contact form: https://bootstrapmade.com/nice-admin-bootstrap-admin-html-template/ -->
			Designed by <a href="https://bootstrapmade.com/">BootstrapMade</a>
		</div>
	</footer>
	<!-- End Footer -->

	<a href="#"
		class="back-to-top d-flex align-items-center justify-content-center"><i
		class="bi bi-arrow-up-short"></i></a>

	<!-- Vendor JS Files -->
	<script src="../assets/vendor/apexcharts/apexcharts.min.js"></script>
	<script src="../assets/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
<<<<<<< HEAD
	<script src="../assets/vendor/tinymce/tinymce.min.js"></script>
=======
	<script src="../assets/vendor/chart.js/chart.umd.js"></script>
	<script src="../assets/vendor/echarts/echarts.min.js"></script>
	<script src="../assets/vendor/quill/quill.min.js"></script>
>>>>>>> dd75e72b3b3b5185a207a026cd725b1210fe2aa5
	<script src="../assets/vendor/simple-datatables/simple-datatables.js"></script>
	<script src="../assets/vendor/tinymce/tinymce.min.js"></script>
	<script src="../assets/vendor/php-email-form/validate.js"></script>

	<!-- Template Main JS File -->
	<script src="../assets/js/main.js"></script>
	
	<script>
		$(document).ready(function() {
			$('#author').selectpicker();
			$('#genre').selectpicker();
		})
	</script>

</body>

</html>
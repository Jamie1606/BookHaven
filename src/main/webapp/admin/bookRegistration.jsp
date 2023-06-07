<%
//Author: Zay Yar Tun
//Admin No: 2235035
//Date: 4.6.2023
//Description: book registration page
%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*, java.text.*"%>
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
	Calendar calendar = Calendar.getInstance();
	calendar.setTime(new Date());
	calendar.add(Calendar.YEAR, 1);
	Date date = calendar.getTime();
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	String date_str = formatter.format(date);
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
							<form class="row g-3">
								<div class="col-md-12">
									<label for="isbn" class="form-label">ISBN No.</label> <input
										type="text" class="form-control" id="isbn">
								</div>
								<div class="col-md-12">
									<label for="title" class="form-label">Title</label> <input
										type="text" class="form-control" id="title">
								</div>

								<div class="col-md-4">
									<label for="page" class="form-label">Page</label> <input
										type="number" class="form-control" id="page" min="1">
								</div>
								<div class="col-md-4">
									<label for="price" class="form-label">Price</label> <input
										type="number" step=".01" class="form-control" id="price">
								</div>
								<div class="col-md-4">
									<label for="qty" class="form-label">Qty</label> <input
										type="number" class="form-control" id="qty" min="0">
								</div>

								<div class="col-md-6">
									<label for="publisher" class="form-label">Publisher</label> <input
										type="text" class="form-control" id="publisher">
								</div>
								<div class="col-md-6">
									<label for="publicationdate" class="form-label">Publication
										Date</label> <input type="date" class="form-control"
										id="publicationdate" max="<%=date_str%>">
								</div>

								<div class="col-12">
									<label for="description" class="form-label">Description</label>
									<textarea class="form-control" id="description"></textarea>
								</div>
								<div class="col-12">
									<label for="image" class="form-label">Image</label> <input
										type="file" class="form-control" id="image">
								</div>
								<div class="col-md-12">
									<label for="image3d" class="form-label">3D Image</label>
									<input type="file" class="form-control" id="image3d"
										placeholder="Apartment, studio, or floor">
								</div>
								<div class="col-md-12">
									<label for="status" class="form-label">Status</label>
									<select id="status" class="form-control">
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
	<script src="../assets/vendor/chart.js/chart.umd.js"></script>
	<script src="../assets/vendor/echarts/echarts.min.js"></script>
	<script src="../assets/vendor/quill/quill.min.js"></script>
	<script src="../assets/vendor/simple-datatables/simple-datatables.js"></script>
	<script src="../assets/vendor/tinymce/tinymce.min.js"></script>
	<script src="../assets/vendor/php-email-form/validate.js"></script>

	<!-- Template Main JS File -->
	<script src="../assets/js/main.js"></script>

</body>

</html>
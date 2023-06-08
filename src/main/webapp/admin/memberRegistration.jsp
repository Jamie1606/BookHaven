<%
//Author 	  : Thu Htet San
//Admin No    : 2235022
//Class       : DIT/FT/2A/02
//Date		  : 7.6.2023
//Description : member registration page(admin side)
%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*, java.text.*"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="utf-8">
<meta content="width=device-width, initial-scale=1.0" name="viewport">

<title>BookHaven | Member Registration</title>
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
	<%
		String errCode = request.getParameter("errCode");
	boolean success = Boolean.valueOf(request.getParameter("success"));
	%>
	<%@ include file="adminheader.jsp"%>

	<%@ include file="adminsidebar.jsp"%>

	<%
	Calendar calendar = Calendar.getInstance();
	calendar.setTime(new Date());
	calendar.add(Calendar.YEAR, -5);
	Date date = calendar.getTime();
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	String date_str = formatter.format(date);
	%>

	<main id="main" class="main">

		<div class="pagetitle">
			<h1>Member Registration</h1>
			<nav>
				<ol class="breadcrumb">
					<li class="breadcrumb-item"><a href="adminHomePage.jsp">Home</a></li>
					<li class="breadcrumb-item">Registration Forms</li>
					<li class="breadcrumb-item active">Member Registration</li>
				</ol>
			</nav>
		</div>
		<!-- End Page Title -->
				<%
						if(errCode != null) {
							if(errCode.equals("invalidEmail")) {
								out.print("<div class='alert alert-danger' role='alert'>Email Already Exists!</div>");
							}
							if(errCode.equals("invalid")) {
								out.print("<div class='alert alert-danger' role='alert'>Invalid Data!</div>");
							}							
							if(errCode.equals("serverError")) {
								out.print("<div class='alert alert-danger' role='alert'>Server Error</div>");
							}
						}
		if(success) {
			out.print("<div class='alert alert-success' role='success'>Successfully Added!</div>");
		}
		%>
		
		<section class="section">
			<div class="row">
				<div class="col-lg-12">
					<div class="card">
						<div class="card-body">
							<h5 class="card-title">Member Information</h5>

							<!-- Member Registration Form -->
							<form id="memberRegistrationForm" class="row g-3" action="<%= request.getContextPath() %>/MemberServlet" method="post">
								
								<!-- formName -->
								<input type="hidden" name="formName" value="memberRegistrationForm" />
								
								<!-- Name input -->
								<div class="col-12">
									<label for="nameID" class="form-label">Name</label> 
									<input type="text" class="form-control" name="name" id="nameID" required>
								</div>
													
								<!-- Email input -->
								<div class="col-12">
									<label for="emailID" class="form-label">Email</label> 
									<input type="email" class="form-control" name="email" id="emailID" required>
								</div>
														
								<!-- Password input -->
								<div class="col-12">
									<label for="passwordID" class="form-label">Password</label> 
									<input type="password" class="form-control" name="password" id="passwordID" required>
								</div>
												
								<!-- Address input -->
								<div class="col-8">
									<label for="addressID" class="form-label">Address</label> 
									<input type="text" class="form-control" name="address" id="addressID" required>
								</div>
								
								<!-- Postal input -->
								<div class="col-4">
									<label for="postalCodeID" class="form-label">Postal Code</label> 
									<input type="number" class="form-control" name="postalCode" id="postalCodeID" required>
								</div>

								<!-- Phone input -->
								<div class="col-md-4">
									<label for="phoneID" class="form-label">Phone</label> <input
										type="number" class="form-control" name="phone" id="phoneID" required>
								</div>
								
								<!-- Birth Date input -->
								<div class="col-md-4">
									<label for="birthDateID" class="form-label">Birth Date</label>
									<input type="date" class="form-control" name="birthDate" id=""birthDate"">
								</div>
									
								<!-- Gender input -->
								<div class="col-md-4">
									<label for="genderID" class="form-label">Gender</label>
									  <select name="gender" id="genderID">
									    <option value></option>
									    <option value="F">F</option>
									    <option value="M">M</option>
									  </select>
								</div>

								<!-- Image input -->
								<div class="col-12">
									<label for="imageID" class="form-label">Image</label>
									<input type="file" class="form-control" id="imageID" name="image">
								</div>
								
								<!-- Submit button -->
								<div class="text-center">
									<button type="submit" class="btn btn-primary">Save</button>
									<button type="reset" class="btn btn-secondary">Clear</button>
								</div>
							</form>
							<!-- End Member Registration Form -->

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
<%
//Author: Zay Yar Tun
//Admin No: 2235035
// Class: DIT/FT/2A/02
// Group: 10
//Date: 4.6.2023
//Description: admin home page
%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="utf-8">
<meta content="width=device-width, initial-scale=1.0" name="viewport">

<title>BookHaven | Dashboard</title>
<meta content="" name="description">
<meta content="" name="keywords">

<!-- Google Fonts -->
<link href="https://fonts.gstatic.com" rel="preconnect">
<link
	href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i|Nunito:300,300i,400,400i,600,600i,700,700i|Poppins:300,300i,400,400i,500,500i,600,600i,700,700i"
	rel="stylesheet">

<!-- Vendor CSS Files -->
<link href="<%= request.getContextPath() %>/assets/vendor/bootstrap/css/bootstrap.min.css"
	rel="stylesheet">
<link href="<%= request.getContextPath() %>/assets/vendor/bootstrap-icons/bootstrap-icons.css"
	rel="stylesheet">
<link href="<%= request.getContextPath() %>/assets/vendor/boxicons/css/boxicons.min.css"
	rel="stylesheet">
<link href="<%= request.getContextPath() %>/assets/vendor/quill/quill.snow.css" rel="stylesheet">
<link href="<%= request.getContextPath() %>/assets/vendor/quill/quill.bubble.css" rel="stylesheet">
<link href="<%= request.getContextPath() %>/assets/vendor/remixicon/remixicon.css" rel="stylesheet">
<link href="<%= request.getContextPath() %>/assets/vendor/simple-datatables/style.css"
	rel="stylesheet">

<!-- Template Main CSS File -->
<link href="<%= request.getContextPath() %>/assets/css/style.css" rel="stylesheet">
<link rel="icon" type="image/png" href="<%= request.getContextPath() %>/img/logo.png">

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

	<main id="main" class="main">

		<div class="pagetitle">
			<h1>Dashboard</h1>
			<nav>
				<ol class="breadcrumb">
					<li class="breadcrumb-item"><a href="<%= request.getContextPath() + URL.adminHomePage %>">Home</a></li>
					<li class="breadcrumb-item active">Dashboard</li>
				</ol>
			</nav>
		</div>
		<!-- End Page Title -->

	</main>
	<!-- End #main -->
	
	<%@ include file="adminfooter.jsp" %>

	<a href="#"
		class="back-to-top d-flex align-items-center justify-content-center"><i
		class="bi bi-arrow-up-short"></i></a>

	<!-- Vendor JS Files -->
	<script src="<%= request.getContextPath() %>/assets/vendor/apexcharts/apexcharts.min.js"></script>
	<script src="<%= request.getContextPath() %>/assets/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
	<script src="<%= request.getContextPath() %>/assets/vendor/chart.js/chart.umd.js"></script>
	<script src="<%= request.getContextPath() %>/assets/vendor/echarts/echarts.min.js"></script>
	<script src="<%= request.getContextPath() %>/assets/vendor/quill/quill.min.js"></script>
	<script src="<%= request.getContextPath() %>/assets/vendor/tinymce/tinymce.min.js"></script>
	<script src="<%= request.getContextPath() %>/assets/vendor/php-email-form/validate.js"></script>

	<!-- Template Main JS File -->
	<script src="<%= request.getContextPath() %>/assets/js/main.js"></script>

</body>

</html>
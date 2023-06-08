
<%
//Author: Zay Yar Tun
//Admin No: 2235035
//Date: 8.6.2023
//Description: author registration page
%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*, java.text.*, model.Author"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="utf-8">
<meta content="width=device-width, initial-scale=1.0" name="viewport">

<title>BookHaven | Author Registration</title>
<meta content="" name="description">
<meta content="" name="keywords">

<!-- Google Fonts -->
<link href="https://fonts.gstatic.com" rel="preconnect">
<link
	href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i|Nunito:300,300i,400,400i,600,600i,700,700i|Poppins:300,300i,400,400i,500,500i,600,600i,700,700i"
	rel="stylesheet">

<!-- Vendor CSS Files -->
<link
	href="<%=request.getContextPath()%>/assets/vendor/bootstrap/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="<%=request.getContextPath()%>/assets/vendor/bootstrap-icons/bootstrap-icons.css"
	rel="stylesheet">
<link
	href="<%=request.getContextPath()%>/assets/vendor/boxicons/css/boxicons.min.css"
	rel="stylesheet">
<link
	href="<%=request.getContextPath()%>/assets/vendor/quill/quill.snow.css"
	rel="stylesheet">
<link
	href="<%=request.getContextPath()%>/assets/vendor/quill/quill.bubble.css"
	rel="stylesheet">
<link
	href="<%=request.getContextPath()%>/assets/vendor/remixicon/remixicon.css"
	rel="stylesheet">
<link
	href="<%=request.getContextPath()%>/assets/vendor/simple-datatables/style.css"
	rel="stylesheet">

<!-- Template Main CSS File -->
<link href="<%=request.getContextPath()%>/assets/css/style.css"
	rel="stylesheet">
<link rel="icon" type="image/png"
	href="<%=request.getContextPath()%>/img/logo.png">

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
	// set default value for status
	String status = "register";
	Author author = null;

	// show error and success for registration
	String errCode = request.getParameter("errCode");
	if (errCode != null) {
		if (errCode.equals("serverError")) {
			out.println("<script>alert('Server Error!'); location='" + request.getContextPath()
			+ "/admin/authorRegistration.jsp';</script>");
		} else if (errCode.equals("invalid")) {
			out.println("<script>alert('Invalid Data or Request!'); location='" + request.getContextPath()
			+ "/admin/authorRegistration.jsp';</script>");
		} else {
			out.println("<script>alert('Unexpected Error! Please contact IT team!'); location='" + request.getContextPath()
			+ "/admin/authorRegistration.jsp';</script>");
		}
	} else {
		String success = request.getParameter("success");
		if (success != null) {
			if (success.equals("register")) {
		out.println("<script>alert('Author data is successfully added!'); location='" + request.getContextPath()
				+ "/admin/authorRegistration.jsp';</script>");
			}
			if (success.equals("update")) {
		out.println("<script>alert('Author data is successfully updated!'); location='" + request.getContextPath()
				+ "/admin/authorRegistration.jsp';</script>");
			}
		}
	}

	// check whether it is to update author data
	status = (String) request.getAttribute("status");
	request.removeAttribute("status");
	if (status == null) {
		status = "register";
	} else {
		author = (Author) request.getAttribute("author");
		request.removeAttribute("author");
	}

	// calendar for author birthdate
	// author must be at least 5 years old
	Calendar calendar = Calendar.getInstance();
	calendar.setTime(new Date());
	calendar.add(Calendar.YEAR, -5);
	Date date = calendar.getTime();
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	String date_str = formatter.format(date);
	%>

	<main id="main" class="main">

		<div class="pagetitle">
			<h1>Author Registration</h1>
			<nav>
				<ol class="breadcrumb">
					<li class="breadcrumb-item"><a
						href="<%=request.getContextPath()%>admin/adminHomePage.jsp">Home</a></li>
					<li class="breadcrumb-item">Registration Forms</li>
					<li class="breadcrumb-item active">Author Registration</li>
				</ol>
			</nav>
		</div>
		<!-- End Page Title -->
		<section class="section">
			<div class="row">
				<div class="col-lg-12">
					<div class="card">
						<div class="card-body">
							<h5 class="card-title">Author Information</h5>

							<!-- Multi Columns Form -->
							<form class="row g-3"
								action="<%=request.getContextPath()%>/admin/authors"
								method="post">
								<input type="hidden" name="status" value="<%=status%>">
								<input type="hidden" name="id"
									value="<%=(status == "update") ? author.getAuthorID() : ""%>">
								<div class="col-md-4">
									<label for="name" class="form-label">Name</label> <input
										type="text" class="form-control" name="name" id="name"
										value="<%=(status == "update") ? author.getName() : ""%>"
										required>
								</div>
								<div class="col-md-4">
									<label for="nationality" class="form-label">Nationality</label>
									<input type="text" class="form-control" name="nationality"
										value="<%=(status == "update") ? author.getNationality() : ""%>"
										id="nationality">
								</div>

								<div class="col-md-4">
									<label for="birthdate" class="form-label">BirthDate</label> <input
										type="date" class="form-control" name="birthdate"
										value="<%=(status == "update") ? author.getBirthDate() : ""%>"
										id="birthdate" max="<%=date_str%>">
								</div>

								<div class="col-12">
									<label for="biography" class="form-label">Biography</label>
									<textarea rows="10" cols="10" class="form-control"
										name="biography" id="biography"><%=(status == "update") ? author.getBiography() : ""%></textarea>
								</div>
								<div class="col-12">
									<label for="link" class="form-label">Link</label> <input
										type="text" class="form-control" name="link" id="link"
										value="<%=(status == "update") ? author.getLink() : ""%>">
								</div>
								<div class="text-center">
									<button type="submit" class="btn btn-primary"><%=(status == "update") ? "Update" : "Save"%></button>
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

	<%@ include file="adminfooter.jsp"%>

	<a href="#"
		class="back-to-top d-flex align-items-center justify-content-center"><i
		class="bi bi-arrow-up-short"></i></a>

	<!-- Vendor JS Files -->
	<script
		src="<%=request.getContextPath()%>/assets/vendor/apexcharts/apexcharts.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/assets/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/assets/vendor/chart.js/chart.umd.js"></script>
	<script
		src="<%=request.getContextPath()%>/assets/vendor/echarts/echarts.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/assets/vendor/quill/quill.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/assets/vendor/simple-datatables/simple-datatables.js"></script>
	<script
		src="<%=request.getContextPath()%>/assets/vendor/tinymce/tinymce.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/assets/vendor/php-email-form/validate.js"></script>

	<!-- Template Main JS File -->
	<script src="<%=request.getContextPath()%>/assets/js/main.js"></script>

</body>

</html>
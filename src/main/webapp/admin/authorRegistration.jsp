<%
// Author		: Zay Yar Tun
// Admin No		: 2235035
// Class		: DIT/FT/2A/02
// Group		: 10
// Date			: 11.7.2023
// Description	: author registration page
%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*, java.text.*, model.Author, model.URL"%>
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
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

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
	// set default value for author data
	Author author = null;
	int authorid = 0;
	String name = "";
	String nationality = "";
	String birthdate = "";
	String biography = "";
	String link = "";
			
	String status = (String) request.getAttribute("status");
	if(status != null) {
		if(status.equals("invalid")) {
			out.println("<script>alert('Invalid data!'); location='" + request.getContextPath()
			+ URL.authorRegistration + "';</script>");
			return;
		}
		else if(status.equals("success")) {
			out.println("<script>alert('Author data is successfully added!'); location='" + request.getContextPath()
			+ URL.authorRegistration + "';</script>");
			return;
		}
		else if(status.equals("servererror")) {
			out.println("<script>alert('Server error!'); location='" + request.getContextPath()
			+ URL.authorRegistration + "';</script>");
			return;
		}
		else {
			out.println("<script>alert('Unexpected error! Please contact IT team!'); location='" + request.getContextPath()
			+ URL.authorRegistration + "';</script>");
			return;
		}
	}
	
	String update = (String) request.getAttribute("update");
	if(update != null) {
		if(update.equals("true")) {
			author = (Author) request.getAttribute("author");
			request.removeAttribute("author");
			authorid = author.getAuthorID();
			name = author.formatNull(author.getName());
			nationality = author.formatNull(author.getNationality());
			biography = author.formatNull(author.getBiography());
			link = author.formatNull(author.getLink());
			if(author.getBirthDate() != null) {
				birthdate = author.getBirthDate().toString();				
			}
		}
		else {
			out.println("<script>location='" + request.getContextPath()	+ URL.authorRegistration + "';</script>");
			return;
		}
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
						href="<%=request.getContextPath()%>/admin/adminHomePage.jsp">Home</a></li>
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
							<form id="authorForm" class="row g-3"
								action='<%=request.getContextPath() + ((update != null) ? URL.updateAuthorServlet : URL.createAuthorServlet) %>'
								method="post">
								<input type="hidden" name="id"
									value="<%=(update != null) ? authorid : ""%>">
								<div class="col-md-4">
									<label for="name" class="form-label">Name</label> <input
										type="text" class="form-control" name="name" id="name"
										value="<%=(update != null) ? name : ""%>"
										required>
								</div>
								<div class="col-md-4">
									<label for="nationality" class="form-label">Nationality</label>
									<input type="text" class="form-control" name="nationality"
										value="<%=(update != null) ? nationality : ""%>"
										id="nationality">
								</div>

								<div class="col-md-4">
									<label for="birthdate" class="form-label">BirthDate</label> <input
										type="date" class="form-control" name="birthdate"
										value="<%=(update != null) ? birthdate : ""%>"
										id="birthdate" max="<%=date_str%>">
								</div>

								<div class="col-12">
									<label for="biography" class="form-label">Biography</label>
									<textarea rows="10" cols="10" class="form-control"
										name="biography" id="biography"><%=(update != null) ? biography : ""%></textarea>
								</div>
								<div class="col-12">
									<label for="link" class="form-label">Link</label> <input
										type="text" class="form-control" autocomplete="off"
										name="link" id="link"
										value="<%=(update != null) ? link : ""%>">
								</div>
								<div class="text-center">
									<button id="btnSave" type="submit" class="btn btn-primary"><%=(update != null) ? "Update" : "Save"%></button>
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
	<script src="<%= request.getContextPath() %>/js/vendor/jquery-2.2.4.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
		integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
		crossorigin="anonymous"></script>
	<script src="<%= request.getContextPath() %>/js/vendor/bootstrap.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/assets/vendor/apexcharts/apexcharts.min.js"></script>
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

	<script>
		$(document).ready(function() {							
			$('#authorForm').submit(function(e) {
				$('#btnSave').prop('disabled',true);
				$('#btnSave').html('Loading...');
				return true;
			})
		});
	</script>
</body>

</html>
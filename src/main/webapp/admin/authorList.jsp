<%
// Author		: Zay Yar Tun
// Admin No		: 2235035
// Class		: DIT/FT/2A/02
// Group		: 10
// Date			: 11.7.2023
// Description	: author list page
%>

<%@ page import="java.util.ArrayList, java.util.Date, model.Author, model.URL"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="utf-8">
<meta content="width=device-width, initial-scale=1.0" name="viewport">

<title>BookHaven | Author List</title>
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

<link rel="stylesheet" href="https://cdn.datatables.net/1.13.5/css/jquery.dataTables.css" />

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
	String status = (String) request.getAttribute("status");
	request.removeAttribute("status");
	if(status != null) {
		if(status.equals("servererror")) {
			out.println("<script>alert('Server error!'); location='" + request.getContextPath() + URL.adminHomePage + "';</script>");
			return;
		}
		if(status.equals("invalid")) {
			out.println("<script>alert('Invalid data or request!'); location='" + request.getContextPath() + URL.getAuthorListServlet + "';</script>");
			return;
		}
		if(status.equals("success")) {
			out.println("<script>alert('Author is successfully deleted!'); location='" + request.getContextPath() + URL.getAuthorListServlet + "';</script>");
			return;
		}
		if(status.equals("deleteservererror")) {
			out.println("<script>alert('Server error!'); location='" + request.getContextPath() + URL.getAuthorListServlet + "';</script>");
			return;
		}
	}
	
	String servlet = (String)request.getAttribute("servlet");
	request.removeAttribute("servlet");
	if(servlet == null || !servlet.equals("true")) {
		out.println("<script>location='" + request.getContextPath() + URL.getAuthorListServlet + "';</script>");
		return;
	}

	ArrayList<Author> authorList = (ArrayList<Author>) request.getAttribute("authorList");
	if(authorList == null) {
		out.println("<script>alert('Server error!'); location='" + request.getContextPath() + URL.adminHomePage + "';</script>");
		return;
	}
	%>

	<main id="main" class="main">

		<div class="pagetitle">
			<h1>Author Table</h1>
			<nav>
				<ol class="breadcrumb">
					<li class="breadcrumb-item"><a
						href="<%= request.getContextPath() + URL.adminHomePage %>">Home</a></li>
					<li class="breadcrumb-item">Tables</li>
					<li class="breadcrumb-item active">Author Data</li>
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

							<!-- Table with stripped rows -->
							<table class="display datatable nowrap hover" style="width: 100%">
								<thead>
									<tr>
										<th scope="col">No.</th>
										<th scope="col">Name</th>
										<th scope="col">Nationality</th>
										<th scope="col">BirthDate</th>
										<th scope="col">Action</th>
									</tr>
								</thead>
								<tbody>
									<%
									for (int i = 0; i < authorList.size(); i++) {
										int authorID = authorList.get(i).getAuthorID();
										String name = authorList.get(i).getName();
										Date birthDate = authorList.get(i).getBirthDate();
										String nationality = authorList.get(i).getNationality();
										
										out.println("<tr>");
										out.println("<td>" + (i + 1) + ".</td>");
										out.println("<td>" + name + "</td>");
										
										if(nationality == null) {
											out.println("<td></td>");
										}
										else {
											out.println("<td>" + nationality + "</td>");
										}
						
										
										if (birthDate == null) {
											out.println("<td></td>");
										} else {
											out.println("<td>" + birthDate.toString() + "</td>");
										}
										
										
										out.println("<td><a href='" + request.getContextPath() + "/GetAuthorByID/" + authorID + "'>Edit</a> | "
										+ "<a href='" + request.getContextPath() + URL.deleteAuthorServlet + authorID + "'>Delete</a></td>");
										out.println("</tr>");
									}
									%>
								</tbody>
								<tfoot>
									<tr>
										<th scope="col">No.</th>
										<th scope="col">Name</th>
										<th scope="col">Nationality</th>
										<th scope="col">BirthDate</th>
										<th scope="col">Action</th>
									</tr>
								</tfoot>
							</table>
							<!-- End Table with stripped rows -->

						</div>
					</div>

				</div>
			</div>
		</section>

		<p id="test-info"></p>

	</main>
	<!-- End #main -->

	<%@ include file="adminfooter.jsp"%>

	<a href="#"
		class="back-to-top d-flex align-items-center justify-content-center"><i
		class="bi bi-arrow-up-short"></i></a>

	<script src="https://code.jquery.com/jquery-3.7.0.js"
		integrity="sha256-JlqSTELeR4TLqP0OG9dxM7yDPqX1ox/HfgiSLBj8+kM="
		crossorigin="anonymous"></script>
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
		src="<%=request.getContextPath()%>/assets/vendor/tinymce/tinymce.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/assets/vendor/php-email-form/validate.js"></script>
		
	<script src="https://cdn.datatables.net/1.13.5/js/jquery.dataTables.js"></script>

	<!-- Template Main JS File -->
	<script src="<%=request.getContextPath()%>/assets/js/main.js"></script>
	
	<script>
		let table = new DataTable('.datatable', {
			"scrollX": true,
			"pageLength": 25,
			"stateSave": true
		});
	</script>
</body>
</html>

<%
// Author		: Thu Htet San
// Admin No		: 2235022
// Class		: DIT/FT/2A/02
// Group		: 10
// Date			: 6.8.2023
// Description	: top customer list
%>

<%@ page import="java.util.ArrayList, java.util.Date, model.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="utf-8">
<meta content="width=device-width, initial-scale=1.0" name="viewport">

<title>BookHaven | Member List</title>
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

<link rel="stylesheet" href="https://cdn.datatables.net/1.13.5/css/jquery.dataTables.css" />
<link rel="stylesheet" href="https://cdn.datatables.net/colreorder/1.7.0/css/colReorder.dataTables.min.css" />

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
			if(status.equals(Status.serverError)) {
				out.println("<script>alert('Server error!'); location='" + request.getContextPath() + URL.adminHomePage + "';</script>");
				return;
			}
			else if(status.equals(Status.invalidData)) {
				out.println("<script>alert('Invalid data!'); location='" + request.getContextPath() + URL.getMemberListServlet + "';</script>");
				return;
			}
			else if(status.equals(Status.invalidRequest)) {
				out.println("<script>alert('Invalid request!'); location='" + request.getContextPath() + URL.getMemberListServlet + "';</script>");
				return;
			}
		}
	
		ArrayList<Member> memberList = (ArrayList<Member>) request.getAttribute("memberList");
		if(memberList == null) {
			out.println("<script>alert('Server error!');</script>");
			out.println("<script>location='" + request.getContextPath() + URL.adminHomePage + "';</script>");
			return;
		}
	%>

	<main id="main" class="main">

		<div class="pagetitle">
			<h1>Member Table</h1>
			<nav>
				<ol class="breadcrumb">
					<li class="breadcrumb-item"><a
						href="<%=request.getContextPath() + URL.adminHomePage %>">Home</a></li>
					<li class="breadcrumb-item">Data List</li>
					<li class="breadcrumb-item active">Top Customers</li>
				</ol>
			</nav>
		</div>
		<!-- End Page Title -->

		<section class="section">
			<div class="row">
				<div class="col-lg-12">

					<div class="card">
						<div class="card-body">
							<h5 class="card-title">Top Customers</h5>

							<!-- Table with stripped rows -->
							<table class="display data-table nowrap hover" style="width: 100%">
								<thead>
									<tr>
										<th scope="col">No.</th>
										<th scope="col">Name</th>
										<th scope="col">Gender</th>
										<th scope="col">BirthDate</th>
										<th scope="col">Phone</th>
										<th scope="col">Address</th>
										<th scope="col">Email</th>
										<th scope="col">Total Purchase</th>
									</tr>
								</thead>
								<tbody>
								<%
									for (int i = 0; i < memberList.size(); i++) {
										
										int memberID = memberList.get(i).getMemberID();
										String name = memberList.get(i).getName();
										char gender = memberList.get(i).getGender();
										Date birthDate = memberList.get(i).getBirthDate();
										String phone = memberList.get(i).getPhone();
										String email = memberList.get(i).getEmail();
										Double totalPurchase = memberList.get(i).getTotalPurchase();
										
										String lastActive = "";
										if(memberList.get(i).getLastActive() != null) {
											lastActive = memberList.get(i).getLastActive().toString();											
										}
										
										String address = memberList.get(i).getAddress();
										String[] addressArr = address.split("\\|");
										address = addressArr[0];
										
										if(addressArr.length == 2) {
											address += " S" + addressArr[1];
										}
										
										out.println("<tr>");
										out.println("<td>" + (i + 1) + ".</td>");
										out.println("<td>" + name + "</td>");
										
										if (gender == 'M') {
											out.println("<td>Male</td>");
										} 
										else if (gender == 'F') {
											out.println("<td>Female</td>");
										} 
										else {
											out.println("<td>N/A</td>");
										}
										
										if (birthDate == null) {
											out.println("<td></td>");
										} 
										else {
											out.println("<td>" + birthDate.toString() + "</td>");
										}
										
										out.println("<td>" + phone + "</td>");
										out.println("<td>" + address + "</td>");
										out.println("<td>" + email + "</td>");out.println("<td>$" + String.format("%.2f", totalPurchase) + "</td>");
										
									
										out.println("</tr>");
									}
								%>
								</tbody>
								<tfoot>
									<tr>
										<th scope="col">No.</th>
										<th scope="col">Name</th>
										<th scope="col">Gender</th>
										<th scope="col">BirthDate</th>
										<th scope="col">Phone</th>
										<th scope="col">Address</th>
										<th scope="col">Email</th>
										<th scope="col">Total Purchase</th>
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
	<script src="https://cdn.datatables.net/colreorder/1.7.0/js/dataTables.colReorder.min.js"></script>

	<!-- Template Main JS File -->
	<script src="<%=request.getContextPath()%>/assets/js/main.js"></script>

	<script>
		let table = new DataTable('.data-table', {
			"scrollX": true,
			"pageLength": 25,
			"stateSave": true,
			"colReorder": true,
			"searching": false,
			"paging": false,
		});
	</script>
</body>

</html>
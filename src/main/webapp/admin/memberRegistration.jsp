
<%
//Author 	  : Thu Htet San
//Admin No    : 2235022
//Class       : DIT/FT/2A/02
//Date		  : 7.6.2023
//Description : member registration page(admin side)
%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page
	import="java.util.*, java.text.*, model.Member, java.time.LocalDate"%>
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
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
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

<!-- bootstrap-select -->
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-select@1.14.0-beta3/dist/css/bootstrap-select.min.css">
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
	integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
	crossorigin="anonymous"></script>
<script
	src="https://cdn.jsdelivr.net/npm/popper.js@1.14.3/dist/umd/popper.min.js"
	integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49"
	crossorigin="anonymous"></script>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@4.1.3/dist/js/bootstrap.min.js"
	integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy"
	crossorigin="anonymous"></script>
<script
	src="<%=request.getContextPath()%>/assets/js/bootstrap-select.js"></script>
<!-- bootstrap-select -->

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
	String status = "register";
	Member member = null;
	String postalCode = "";
	String address = "";
	// show error and success for registration
	String errCode = (String) request.getAttribute("errCode");
	if (errCode != null) {
		if (errCode.equals("serverError")) {
			out.println("<script>alert('Server Error!'); location='" + request.getContextPath()
			+ "/admin/memberRegistration.jsp';</script>");
			return;
		} else if (errCode.equals("invalid")) {
			out.println("<script>alert('Invalid Data or Request!'); location='" + request.getContextPath()
			+ "/admin/memberRegistration.jsp';</script>");
			return;
		} else if (errCode.equals("invalidEmail")) {
			out.println("<script>alert('Invalid Email!'); location='" + request.getContextPath()
			+ "/admin/memberRegistration.jsp';</script>");
			return;
		} else if (errCode.equals("upload")) {
			out.println("<script>alert('Error in uploading data!'); location='" + request.getContextPath()
			+ "/admin/memberRegistration.jsp';</script>");
			return;
		} else if (errCode.equals("unauthorized")) {
			out.println("<script>alert('Unauthorized!'); location='" + request.getContextPath()
			+ "/signout.jsp';</script>");
			return;
		} else {
			out.println("<script>alert('Unexpected Error! Please contact IT team!'); location='" + request.getContextPath()
			+ "/admin/memberRegistration.jsp';</script>");
			return;
		}
	} else {
		String success = (String) request.getAttribute("success");
		if (success != null) {
			if (success.equals("register")) {
		out.println("<script>alert('Member data is successfully added!'); location='" + request.getContextPath()
				+ "/admin/memberRegistration.jsp';</script>");
		return;
			}
			if (success.equals("update")) {
		out.println("<script>alert('Member data is successfully updated!'); location='" + request.getContextPath()
				+ "/admin/members';</script>");
		return;
			}
		}
	}

	// check whether it is to update author data
	status = (String) request.getAttribute("status");
	request.removeAttribute("status");
	if (status == null) {
		status = "register";
	} else {
		if (status.equals("update")) {
			member = (Member) request.getAttribute("member");
			request.removeAttribute("member");
			String[] addressArr = member.getAddress().split("\\|");
			if (addressArr.length == 2) {
		address = addressArr[0];
		postalCode = addressArr[1];
			}
			if (addressArr.length == 1) {
		address = addressArr[0];
			}
		} else {
			out.println("<script>alert('Unauthorized! Please Log In First!'); location='" + request.getContextPath()
			+ "/signout.jsp';</script>");
			return;
		}

	}
	%>

	<main id="main" class="main">

		<div class="pagetitle">
			<h1>Member Registration</h1>
			<nav>
				<ol class="breadcrumb">
					<li class="breadcrumb-item"><a
						href="<%=request.getContextPath()%>/admin/adminHomePage.jsp">Home</a></li>
					<li class="breadcrumb-item">Registration Forms</li>
					<li class="breadcrumb-item active">Member Registration</li>
				</ol>
			</nav>
		</div>
		<!-- End Page Title -->
		<section class="section">
			<div class="row">
				<div class="col-lg-12">
					<div class="card">
						<div class="card-body">
							<h5 class="card-title">Member Information</h5>

							<!-- Member Registration Form -->
							<form id="memberRegistrationForm" class="row g-3"
								action="<%=request.getContextPath()%>/admin/members"
								method="post" enctype="multipart/form-data">

								<!-- formName -->
								<input type="hidden" name="formName"
									value="memberRegistrationForm" /> <input type="hidden"
									name="status" value="<%=status%>" /> <input type="hidden"
									name="formName" value="memberRegistrationForm" /> <input
									type="hidden" name="MemberID"
									value="<%=(status.equals("update")) ? member.getMemberID() : ""%>" />

								<!-- Name input -->
								<div class="col-12">
									<label for="nameID" class="form-label">Name</label> <input
										type="text" class="form-control" name="name" id="nameID"
										value="<%=(status.equals("update")) ? member.getName() : ""%>"
										required>
								</div>

								<!-- Email input -->
								<div class="col-12">
									<label for="emailID" class="form-label">Email</label> <input
										type="email" class="form-control" name="email" id="emailID"
										value="<%=(status.equals("update")) ? member.getEmail() : ""%>"
										required <%=(status.equals("update")) ? "readonly" : ""%>>
								</div>

								<!-- Password input -->
								<%
								if (!status.equals("update")) {
									out.println(
									"<div class='col-12'><label for='passwordID' class='form-label'>Password</label> <input type='password' class='form-control' name='password' id='passwordID' required></div>");
								}
								%>

								<!-- Address input -->
								<div class="col-8">
									<label for="addressID" class="form-label">Address</label> <input
										type="text" class="form-control" name="address" id="addressID"
										value="<%=(status.equals("update")) ? address : ""%>" required>
								</div>

								<!-- Postal input -->
								<div class="col-4">
									<label for="postalCodeID" class="form-label">Postal
										Code</label> <input type="number" class="form-control"
										name="postalCode" id="postalCodeID"
										value="<%=(status.equals("update")) ? postalCode : ""%>"
										required>
								</div>

								<!-- Phone input -->
								<div class="col-md-4">
									<label for="phoneID" class="form-label">Phone</label> <input
										type="number" class="form-control" name="phone" id="phoneID"
										value="<%=(status.equals("update")) ? member.getPhone() : ""%>"
										required>
								</div>

								<!-- Birth Date input -->
								<div class="col-md-4">
									<label for="birthDateID" class="form-label">Birth Date</label>
									<input type="date" class="form-control" name="birthDate"
										id="birthDateID" max="<%=LocalDate.now()%>"
										value="<%=(status.equals("update")) ? member.getBirthDate() : ""%>"
										required>
								</div>

								<!-- Gender input -->
								<div class="col-md-4">
									<label for="genderID" class="form-label">Gender</label> <select
										name="gender" id="genderID" class="form-control" required>
										<%
										if (status.equals("update")) {
											if (member.getGender() == 'N') {
												out.println("<option value='N' selected>Not Selected</option>");
											}
										}
										%>
										<option
											<%=(status.equals("update") && member.getGender() == 'F') ? "selected" : ""%>
											value="F">Female</option>
										<option
											<%=(status.equals("update") && member.getGender() == 'M') ? "selected" : ""%>
											value="M">Male</option>
									</select>
								</div>

								<!-- Image input -->
								<div class="col-12">
									<label for="imageID" class="form-label">Image</label> <input
										type="hidden" name="oldimage"
										value="<%=(status.equals("update")) ? member.getImage() : ""%>">
									<input type="file" class="form-control" id="imageID"
										name="image">
								</div>

								<!-- Submit button -->
								<div class="text-center">
									<button id="btnSave" type="submit" class="btn btn-primary"><%=(status.equals("update")) ? "Update" : "Save"%></button>
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

	<%@ include file="adminfooter.jsp"%>

	<a href="#"
		class="back-to-top d-flex align-items-center justify-content-center"><i
		class="bi bi-arrow-up-short"></i></a>

	<!-- Vendor JS Files -->
	<script
		src="<%=request.getContextPath()%>/assets/vendor/apexcharts/apexcharts.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/assets/vendor/tinymce/tinymce.min.js"></script>
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
			$('#memberRegistrationForm').submit(function(e) {
				$('#btnSave').prop('disabled', true);
				$('#btnSave').html('Loading...');
				return true;
			});
		})
	</script>
</body>

</html>
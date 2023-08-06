
<%
// Author 	  	: Thu Htet San
// Admin No    	: 2235022
// Class       	: DIT/FT/2A/02
// Group		: 10
// Date		  	: 6.8.2023
// Description 	: Show user profile
%>

<%@page import="java.time.LocalDate"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="model.*"%>
<!DOCTYPE html>
<html lang="zxx" class="no-js">

<head>
<!-- Mobile Specific Meta -->
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<!-- Favicon-->
<link rel="shortcut icon"
	href="<%=request.getContextPath()%>/img/fav.png">
<!-- Author Meta -->
<meta name="author" content="codepixer">
<!-- Meta Description -->
<meta name="description" content="">
<!-- Meta Keyword -->
<meta name="keywords" content="">
<!-- meta character set -->
<meta charset="UTF-8">
<!-- Site Title -->
<title>BookHaven | Profile</title>

<link
	href="https://fonts.googleapis.com/css?family=Poppins:100,200,400,300,500,600,700"
	rel="stylesheet">
<!--CSS============================================= -->
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/linearicons.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/font-awesome.min.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/bootstrap.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/magnific-popup.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/nice-select.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/animate.min.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/owl.carousel.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/main.css">
<link rel="icon" type="image/png"
	href="<%=request.getContextPath()%>/img/logo.png">

<link
	href="<%=request.getContextPath()%>/assets/vendor/bootstrap-icons/bootstrap-icons.css"
	rel="stylesheet">

<!-- Template Main CSS File -->
<link href="<%=request.getContextPath()%>/assets/css/style.css"
	rel="stylesheet">

</head>

<body>
	<%@ include file="header.jsp"%>

	<%
	
	System.out.println("......in profile.jsp ..");
	
	Member member = null;
	
	int memberid = 0;
	String name = "";
	char gender = 'N';
	String birthdate = "";
	String phone = "";
	String email = "";
	String image = "";
	String postalCode = "";
	String address = "";
	if (session != null && !session.isNew()) {
		String token = (String) session.getAttribute("token");
		String role = (String) session.getAttribute("role");

		if (token == null || token.isEmpty() || role == null || !role.equals("ROLE_MEMBER")) {
			request.setAttribute("status", Status.unauthorized);
			request.getRequestDispatcher(URL.signOut).forward(request, response);
			return;
		}
	} else {
		request.setAttribute("status", Status.unauthorized);
		request.getRequestDispatcher(URL.signOut).forward(request, response);
		return;
	}
	
	
	String status = (String) request.getAttribute("status");
	request.removeAttribute("status");
	if (status != null) {
		if (status.equals(Status.serverError)) {
			out.println("<script>alert('Server error!'); location='" + request.getContextPath() + URL.adminHomePage
			+ "';</script>");
			return;
		} else if (status.equals(Status.invalidData)) {
			out.println("<script>alert('Invalid data!'); location='" + request.getContextPath() + URL.getGenreListServlet
			+ "';</script>");
			return;
		} else if (status.equals(Status.invalidRequest)) {
			out.println("<script>alert('Invalid request!'); location='" + request.getContextPath() + URL.getGenreListServlet
			+ "';</script>");
			return;
		} else if (status.equals(Status.updateSuccess)) {
			out.println("<script>alert('Genre is successfully updated!');</script>");
			out.println("<script>location='" + request.getContextPath() + URL.getGenreListServlet + "';</script>");
			return;
		} else if (status.equals(Status.deleteSuccess)) {
			out.println("<script>alert('Genre is successfully deleted!');</script>");
			out.println("<script>location='" + request.getContextPath() + URL.getGenreListServlet + "';</script>");
			return;
		}
	}

	member = (Member) request.getAttribute("member");
	request.removeAttribute("member");
	if(member!=null){
	memberid = member.getMemberID();
	name = member.getName();
	gender = member.getGender();
	email = member.getEmail();
	phone = member.getPhone();
	image = member.getImage();
	
	String[] addressArr = member.getAddress().split("\\|");
	if (addressArr.length == 2) {
		address = addressArr[0].trim();
		postalCode = addressArr[1].trim();
	}
	if (addressArr.length == 1) {
		address = addressArr[0].trim();
	}
	
	if(member.getBirthDate() != null) {
		birthdate = member.getBirthDate().toString();
	}
	}
	%>

	<section class="section profile" style="padding: 100px 20px 20px 20px;">
		<div class="row">
			<div class="col-xl-4">

				<div class="card">
					<div
						class="card-body profile-card pt-4 d-flex flex-column align-items-center">

						<img src="<%=request.getContextPath() + member.getImage()%>"
							class="rounded-circle">
						<h2><%=member.getName()%></h2>
						<h3>Member</h3>
						<div class="social-links mt-2">
							<a href="#" class="twitter"><i class="bi bi-twitter"></i></a> <a
								href="#" class="facebook"><i class="bi bi-facebook"></i></a> <a
								href="#" class="instagram"><i class="bi bi-instagram"></i></a> <a
								href="#" class="linkedin"><i class="bi bi-linkedin"></i></a>
						</div>
					</div>
				</div>

			</div>

			<div class="col-xl-8">

				<div class="card">
					<div class="card-body pt-3">
						<!-- Bordered Tabs -->
						<ul class="nav nav-tabs nav-tabs-bordered">

							<li class="nav-item">
								<button class="nav-link active" data-bs-toggle="tab"
									data-bs-target="#profile-overview" style="outline: none;">Overview</button>
							</li>

							<li class="nav-item">
								<button class="nav-link" data-bs-toggle="tab"
									data-bs-target="#profile-edit" style="outline: none;">Edit
									Profile</button>
							</li>


							<li class="nav-item">
								<button class="nav-link" data-bs-toggle="tab"
									data-bs-target="#profile-delete" style="outline: none;">Delete Account</button>
							</li>
						</ul>
						<div class="tab-content pt-2">

							<div class="tab-pane fade show active profile-overview"
								id="profile-overview">

								<h5 class="card-title">Profile Details</h5>
								<div class="row">
									<div class="col-lg-3 col-md-4 label ">Profile Image</div>
									<div class="col-lg-9 col-md-8">
										<img style="width: 100px; height: 100px;"
											src="<%=request.getContextPath() + member.getImage()%>"
											alt="Profile">

									</div>
								</div>

								<div class="row">
									<div class="col-lg-3 col-md-4 label ">Name</div>
									<div class="col-lg-9 col-md-8"><%=member.getName()%></div>
								</div>

								<div class="row">
									<div class="col-lg-3 col-md-4 label">Email</div>
									<div class="col-lg-9 col-md-8"><%=member.getEmail()%></div>
								</div>

								<div class="row">
									<div class="col-lg-3 col-md-4 label">Address</div>
									<div class="col-lg-9 col-md-8"><%=address%></div>
								</div>

								<div class="row">
									<div class="col-lg-3 col-md-4 label">Postal Code</div>
									<div class="col-lg-9 col-md-8"><%=postalCode%></div>
								</div>

								<div class="row">
									<div class="col-lg-3 col-md-4 label">Phone</div>
									<div class="col-lg-9 col-md-8"><%=member.getPhone()%></div>
								</div>

								<div class="row">
									<div class="col-lg-3 col-md-4 label">Birth Date</div>
									<div class="col-lg-9 col-md-8"><%=member.getBirthDate() == null ? "N/A" : member.getBirthDate()%></div>
								</div>

								<div class="row">
									<div class="col-lg-3 col-md-4 label">Gender</div>
									<%
									if (member.getGender() == 'M') {
										out.println("<div class='col-lg-9 col-md-8'>Male</div>");
									} else if (member.getGender() == 'F') {
										out.println("<div class='col-lg-9 col-md-8'>Female</div>");
									} else {
										out.println("<div class='col-lg-9 col-md-8'>N/A</div>");
									}
									%>

								</div>

							</div>

							<div class="tab-pane fade profile-edit pt-3" id="profile-edit">

								<!-- Profile Edit Form -->
								<form action="<%=request.getContextPath()+URL.updateProfileServlet%>"
									method="post" enctype="multipart/form-data">
									<input type="hidden" name="memberID" value="<%=member.getMemberID()%>" />
									<!-- Name input -->
									<div class="row mb-3">
										<label for="fullName" class="col-md-4 col-lg-3 col-form-label">Name</label>
										<div class="col-md-8 col-lg-9">
											<input name="name" type="text" class="form-control"
												id="nameID" value="<%=member.getName()%>" required>
										</div>
									</div>

									<!-- Current Password input -->
									<div class="row mb-3">
										<label for="currentPasswordID"
											class="col-md-4 col-lg-3 col-form-label">Current
											Password</label>
										<div class="col-md-8 col-lg-9">
											<input type="password" class="form-control"
												name="currentPassword" id="currentPasswordID">
										</div>
									</div>

									<!-- New Password input -->
									<div class="row mb-3">
										<label for="currentPasswordID"
											class="col-md-4 col-lg-3 col-form-label">New Password</label>
										<div class="col-md-8 col-lg-9">
											<input type="password" class="form-control"
												name="newPassword" id="newPasswordID">
										</div>
									</div>

									<!-- Address input -->
									<div class="row mb-3">
										<label for="addressID"
											class="col-md-4 col-lg-3 col-form-label">Address</label>
										<div class="col-md-8 col-lg-9">
											<input type="text" class="form-control" name="address"
												id="addressID" value="<%=address%>" required>
										</div>
									</div>

									<!-- Postal input -->
									<div class="row mb-3">
										<label for="postalCodeID"
											class="col-md-4 col-lg-3 col-form-label">Postal Code</label>
										<div class="col-md-8 col-lg-9">
											<input type="number" class="form-control" name="postalCode"
												id="postalCodeID" value="<%=postalCode%>" required>
										</div>
									</div>

									<!-- Phone input -->
									<div class="row mb-3">
										<label for="phoneID" class="col-md-4 col-lg-3 col-form-label">Phone</label>
										<div class="col-md-8 col-lg-9">
											<input type="number" class="form-control" name="phone"
												id="phoneID" value="<%=member.getPhone()%>" required>
										</div>
									</div>

									<!-- Birth Date input -->
									<div class="row mb-3">
										<label for="birthDateID"
											class="col-md-4 col-lg-3 col-form-label">Birth Date</label>
										<div class="col-md-8 col-lg-9">
											<input type="date" class="form-control" name="birthDate"
												id="birthDateID" max="<%=LocalDate.now()%>"
												value="<%=member.getBirthDate()%>">
										</div>
									</div>

									<!-- Gender input -->
									<div class="row mb-3">
										<label for="genderID" class="col-md-4 col-lg-3 col-form-label">Gender</label>
										<div class="col-md-8 col-lg-9">
											<select name="gender" id="genderID" class="form-control">
												<option value="N"
													<%=member.getGender() == 'N' ? "selected" : ""%>>N/A</option>
												<option <%=member.getGender() == 'F' ? "selected" : ""%>
													value="F">Female</option>
												<option <%=member.getGender() == 'M' ? "selected" : ""%>
													value="M">Male</option>
											</select>
										</div>
									</div>


									<!-- Image input -->
									<div class="row mb-3">
										<label for="imageID" class="col-md-4 col-lg-3 col-form-label">Image</label>
										<div class="col-md-8 col-lg-9">
											<input type="hidden" name="oldimage"
												value="<%=member.getImage()%>"> <input type="file"
												class="form-control" id="imageID" name="image">
										</div>
									</div>

									<!-- Submit button -->
									<div class="text-center">
										<button type="submit" class="btn btn-primary">Save
											Changes</button>
									</div>
								</form>
								<!-- End Profile Edit Form -->

							</div>
<!-- delete -->
<div class="tab-pane fade profile-delete pt-3" id="profile-delete">

								<!-- Profile Edit Form -->
								<form action="<%=request.getContextPath()+URL.deleteAccountServlet%>/<%= member.getMemberID() %>"
									method="post" enctype="multipart/form-data">
									<input type="hidden" name="memberID" value="<%=member.getMemberID()%>" />
									<h5 class="card-title">Are you sure you want to delete?</h5>
									<!-- Submit button -->
									<div class="text-center">
										<button type="submit" class="btn btn-primary">Delete</button>
									</div>
								</form>
								<!-- End Profile Edit Form -->

							</div>
							<!--  delete  end -->

						</div>

					</div>
					<!-- End Bordered Tabs -->

				</div>
			</div>

		</div>
	</section>

	<%@ include file="footer.jsp"%>

	<script
		src="<%=request.getContextPath()%>/assets/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/js/vendor/jquery-2.2.4.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
		integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
		crossorigin="anonymous"></script>
	<script src="<%=request.getContextPath()%>/js/easing.min.js"></script>
	<script src="<%=request.getContextPath()%>/js/hoverIntent.js"></script>
	<script src="<%=request.getContextPath()%>/js/superfish.min.js"></script>
	<script src="<%=request.getContextPath()%>/js/jquery.ajaxchimp.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/js/jquery.magnific-popup.min.js"></script>
	<script src="<%=request.getContextPath()%>/js/owl.carousel.min.js"></script>
	<script src="<%=request.getContextPath()%>/js/jquery.sticky.js"></script>
	<script
		src="<%=request.getContextPath()%>/js/jquery.nice-select.min.js"></script>
	<script src="<%=request.getContextPath()%>/js/parallax.min.js"></script>
	<script src="<%=request.getContextPath()%>/js/waypoints.min.js"></script>
	<script src="<%=request.getContextPath()%>/js/jquery.counterup.min.js"></script>
	<script src="<%=request.getContextPath()%>/js/mail-script.js"></script>
	<script src="<%=request.getContextPath()%>/js/main.js"></script>


</body>

</html>
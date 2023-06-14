<%
//Author 	  : Thu Htet San
//Admin No    : 2235022
//Class       : DIT/FT/2A/02
//Date        : 6.6.2023
//Description : sign up page
%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>BookHaven | Sign Up</title>
<style>
.divider:after, .divider:before {
	content: "";
	flex: 1;
	height: 1px;
	background: #eee;
}
/* Autofill styling for the input fields */
input:-webkit-autofill {
    -webkit-box-shadow: 0 0 0 30px white inset !important;
}

/* Add a margin to the input field when it has a value */
.form-outline input:not(:placeholder-shown) {
    margin-top: 20px;
}

/* Move the label up when the input is autofilled */
.form-outline input:-webkit-autofill ~ label {
    transform: translateY(-20px);
    font-size: 12px;
    color: gray;
}
</style>

<!-- Font Awesome -->
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css"
	rel="stylesheet" />
<!-- Google Fonts -->
<link
	href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap"
	rel="stylesheet" />
<!-- MDB -->
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/6.3.1/mdb.min.css"
	rel="stylesheet" />
<link rel="stylesheet" href="css/linearicons.css">
<link rel="stylesheet" href="css/font-awesome.min.css">
<link rel="stylesheet" href="css/bootstrap.css">
<link rel="stylesheet" href="css/magnific-popup.css">
<link rel="stylesheet" href="css/nice-select.css">
<link rel="stylesheet" href="css/animate.min.css">
<link rel="stylesheet" href="css/owl.carousel.css">
<link rel="stylesheet" href="css/main.css">
<link rel="icon" type="image/png" href="img/logo.png">

<script type="text/javascript"
	src="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/6.3.1/mdb.min.js"></script>

</head>

<body>
	<%
		String errCode = request.getParameter("errCode");
	%>
	<a style="position: fixed; top: 15%; left: 15%;" href="index.jsp"><span
		style="font-size: 20px; font-weight: bold;">&#8592;</span>&ensp;<span>Go
			Back</span></a>
	<section class="vh-100">
		<div class="container py-5 h-100">
			<div
				class="row d-flex align-items-center justify-content-center h-100">
				<div class="col-md-8 col-lg-7 col-xl-6">
					<img
						src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-login-form/draw2.svg"
						class="img-fluid" alt="Phone image">
				</div>
				<div class="col-md-7 col-lg-5 col-xl-5 offset-xl-1">
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
					%>
					
					<!-- Sign Up Form -->
					<form id="signupForm" action="<%=request.getContextPath()%>/signup" method="post">
						<!-- formName -->
						<input type="hidden" name="formName" value="signupForm" />
					
						<!-- Name input -->
						<div class="form-outline mb-4">
							<input type="text" id="nameID" name="name" class="form-control form-control-lg" required/> 
							<label class="form-label" for="nameID">Name</label>
						</div>
					
						<!-- Email input -->
						<div class="form-outline mb-4">
							<input type="email" id="emailID" name="email" class="form-control form-control-lg" required/> 
							<label class="form-label" for="emailID">Email</label>
						</div>

						<!-- Password input -->
						<div class="form-outline mb-4">
							<input type="password" id="passwordID" name="password" class="form-control form-control-lg" required/>
							<label class="form-label" for="passwordID">Password</label>
						</div>
						
						<!-- Address input -->
						<div class="form-outline mb-4">
							<input type="text" id="addressID" name="address" class="form-control form-control-lg" required/> 
							<label class="form-label" for="addressID">Address</label>
						</div>
						
						<!-- Postal input -->
						<div class="form-outline mb-4">
							<input type="number" id="postalCodeID" name="postalCode" class="form-control form-control-lg" required/>
							 <label class="form-label" for="postalCodeID">Postal Code</label>
						</div>
						
						<!-- Phone input -->
						<div class="form-outline mb-4">
							<input type="number" id="phoneID" name="phone" class="form-control form-control-lg" required/>
							<label class="form-label" for="phoneID">Phone</label>
						</div>
						
						<!-- Submit button -->
						<button id="btnSignup" type="submit" class="btn btn-primary btn-lg btn-block">Sign
							up</button>

						<div class="divider d-flex align-items-center my-4">
							<p class="text-center fw-bold mx-3 mb-0 text-muted">OR</p>
						</div>

						<a class="btn btn-primary btn-lg btn-block"
							style="background-color: #3b5998" href="signin.jsp" role="button">Already
							A Member? </a>
					</form>
					<!-- End Sign Up Form -->
				</div>
			</div>
		</div>
	</section>


	<script src="js/vendor/jquery-2.2.4.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
		integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
		crossorigin="anonymous"></script>
	<script src="js/vendor/bootstrap.min.js"></script>
	<script type="text/javascript"
		src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBhOdIF3Y9382fqJYt5I_sswSrEw5eihAA"></script>
	<script src="js/easing.min.js"></script>
	<script src="js/hoverIntent.js"></script>
	<script src="js/superfish.min.js"></script>
	<script src="js/jquery.ajaxchimp.min.js"></script>
	<script src="js/jquery.magnific-popup.min.js"></script>
	<script src="js/owl.carousel.min.js"></script>
	<script src="js/jquery.sticky.js"></script>
	<script src="js/jquery.nice-select.min.js"></script>
	<script src="js/parallax.min.js"></script>
	<script src="js/waypoints.min.js"></script>
	<script src="js/jquery.counterup.min.js"></script>
	<script src="js/mail-script.js"></script>
	<script src="js/main.js"></script>
	
	<script>
		$(document).ready(function() {
			$('#signinForm').submit(function(e) {
				$('#btnSignIn').prop('disabled', true);
				$('#btnSignIn').html('<div class="spinner-border text-dark" role="status"><span class="visually-hidden">Loading...</span></div>');
				return true;
			})	
		});
	</script>
</body>
</html>
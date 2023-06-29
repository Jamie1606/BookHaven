
<%
//Author: Zay Yar Tun
//Admin No: 2235035
// Class: DIT/FT/2A/02
// Group: 10
//Date: 7.6.2023
//Description: book registration page
%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page
	import="java.util.*, java.text.*, model.Author, model.Genre, model.Book"%>
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

<style>
input::placeholder {
	color: #999 !important;
	font-size: 15px;
}
</style>

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
	String error = (String) request.getAttribute("error");
	request.removeAttribute("error");
	String success = (String) request.getAttribute("success");
	request.removeAttribute("success");
	if (error != null) {
		if (error.equals("invalid")) {
			out.println("<script>alert('Invalid Request or Data!'); location='" + request.getContextPath()
			+ "/admin/bookRegistration';</script>");
		} else if (error.equals("serverError")) {
			out.println("<script>alert('Server Error!'); location='" + request.getContextPath()
			+ "/admin/bookRegistration';</script>");
		} else if (error.equals("upload")) {
			out.println("<script>alert('Error in uploading data!'); location='" + request.getContextPath()
			+ "/admin/bookRegistration';</script>");
			return;
		} else if (error.equals("unauthorized")) {
			out.println("<script>alert('Please Log In First!'); location='" + request.getContextPath()
			+ "/signout.jsp';</script>");
			return;
		} else if (error.equals("authorError")) {
			out.println(
			"<script>alert('Error in linking author and book! Please try with edit function in bookList!'); location='"
					+ request.getContextPath() + "/admin/bookRegistration';</script>");
			return;
		} else if (error.equals("genreError")) {
			out.println(
			"<script>alert('Error in linking genre and book! Please try with edit function in bookList!'); location='"
					+ request.getContextPath() + "/admin/bookRegistration';</script>");
			return;
		} else if (error.equals("duplicate")) {
			out.println("<script>alert('This book is already registered in database!'); location='"
			+ request.getContextPath() + "/admin/bookRegistration';</script>");
			return;
		} else {
			out.println("<script>alert('Unexpected Error! Please contact IT team!'); location='" + request.getContextPath()
			+ "/admin/bookRegistration.jsp';</script>");
			return;
		}
	}
	if (success != null) {
		if (success.equals("register")) {
			out.println("<script>alert('Book data is successfully added!'); location='" + request.getContextPath()
			+ "/admin/bookRegistration';</script>");
			return;
		}
		if (success.equals("update")) {
			out.println("<script>alert('Book data is successfully updated!'); location='" + request.getContextPath()
			+ "/admin/books';</script>");
			return;
		}
	}

	String servlet = (String) request.getAttribute("servlet");
	if (servlet == null || !servlet.equals("true")) {
		out.println("<script>location='" + request.getContextPath() + "/admin/bookRegistration';</script>");
		return;
	}

	// retrieve author and genre list from request attributes sent from book servlet

	String status = (String) request.getAttribute("status");
	request.removeAttribute("status");
	Book book = null;
	ArrayList<Author> bookAuthorList = new ArrayList<Author>();;
	ArrayList<Genre> bookGenreList = new ArrayList<Genre>();

	if (status == null) {
		status = "register";
	} else {
		if (status.equals("update")) {
			book = (Book) request.getAttribute("book");
			request.removeAttribute("book");
			bookAuthorList = (ArrayList<Author>) request.getAttribute("bookAuthorList");
			request.removeAttribute("bookAuthorList");
			bookGenreList = (ArrayList<Genre>) request.getAttribute("bookGenreList");
			request.removeAttribute("bookGenreList");
		} else {
			out.println("<script>alert('Unauthorized! Please Log In First!'); location='" + request.getContextPath()
			+ "/signout.jsp';</script>");
			return;
		}
	}

	ArrayList<Author> authorList = (ArrayList<Author>) request.getAttribute("authorList");
	ArrayList<Genre> genreList = (ArrayList<Genre>) request.getAttribute("genreList");
	request.removeAttribute("authorList");
	request.removeAttribute("genreList");

	Calendar calendar = Calendar.getInstance();
	calendar.setTime(new Date());
	calendar.add(Calendar.YEAR, 1);
	Date date = calendar.getTime();
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	String dateStr = formatter.format(date);
	%>

	<main id="main" class="main">

		<div class="pagetitle">
			<h1>Book Registration</h1>
			<nav>
				<ol class="breadcrumb">
					<li class="breadcrumb-item"><a
						href="<%=request.getContextPath()%>/admin/adminHomePage.jsp">Home</a></li>
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
							<form id="bookForm" class="row g-3"
								action="<%=request.getContextPath()%>/admin/books"
								method="post" enctype="multipart/form-data">
								<input type="hidden" value="<%=status%>" name="status">
								<div class="col-md-12">
									<label for="isbn" id="label-isbn" class="form-label">ISBN
										No <span id="check-isbn"></span>
									</label> <input type="text" name="isbn" class="form-control" id="isbn"
										value="<%=status.equals("update") ? book.getISBNNo() : ""%>"
										placeholder="111-1234567890" required
										<%=status.equals("update") ? "readonly" : ""%>>
								</div>
								<div class="col-md-12">
									<label for="title" class="form-label" id="label-title">Title
										<span style="color: red; font-weight: bold;"></span>
									</label> <input type="text" name="title" class="form-control"
										id="title"
										value="<%=status.equals("update") ? book.getTitle() : ""%>" placeholder="Book Title ..."
										required> <span
										style="float: right; color: red; font-weight: bold;"
										id="charCount-title">0 / 100</span>
								</div>

								<div class="col-md-4">
									<label for="page" class="form-label">Page</label> <input
										type="number" name="page" class="form-control" id="page"
										min="1" placeholder="Total Page ..."
										value="<%=status.equals("update") ? book.getPage() : ""%>"
										required>
								</div>
								<div class="col-md-4">
									<label for="price" class="form-label">Price</label> <input
										type="number" name="price" step=".01" min="5" placeholder="Unit Price ..."
										value="<%=status.equals("update") ? book.getPrice() : ""%>"
										class="form-control" id="price" required>
								</div>
								<div class="col-md-4">
									<label for="qty" class="form-label">Qty</label> <input
										type="number" name="qty" class="form-control" id="qty" placeholder="Qty ..."
										value="<%=status.equals("update") ? book.getQty() : ""%>"
										min="0" required>
								</div>

								<div class="col-md-6">
									<label for="author" class="form-label">Author</label> <select
										id="author" name="author" class="form-control selectpicker"
										multiple="multiple" required>
										<%
										for (Author author : authorList) {
											boolean isSelected = false;
											for (Author bookAuthor : bookAuthorList) {
												if (author.getAuthorID() == bookAuthor.getAuthorID()) {
											isSelected = true;
											break;
												}
											}
											if (isSelected) {
												out.println("<option value='" + author.getAuthorID() + "' selected>" + author.getName() + "</option>");
											} else {
												out.println("<option value='" + author.getAuthorID() + "'>" + author.getName() + "</option>");
											}
										}
										%>
									</select>
								</div>
								<div class="col-md-6">
									<label for="genre" class="form-label">Genre</label> <select
										id="genre" name="genre" class="form-control selectpicker"
										multiple="multiple" required>
										<%
										for (Genre genre : genreList) {
											boolean isSelected = false;
											for (Genre bookGenre : bookGenreList) {
												if (genre.getGenreID() == bookGenre.getGenreID()) {
											isSelected = true;
											break;
												}
											}
											if (isSelected) {
												out.println("<option value='" + genre.getGenreID() + "' selected>" + genre.getGenre() + "</option>");
											} else {
												out.println("<option value='" + genre.getGenreID() + "'>" + genre.getGenre() + "</option>");
											}
										}
										%>
									</select>
								</div>

								<div class="col-md-6">
									<label for="publisher" class="form-label">Publisher</label> <input
										type="text" name="publisher" class="form-control"
										value="<%=status.equals("update") ? book.getPublisher() : ""%>"
										id="publisher" required>
								</div>
								<div class="col-md-6">
									<label for="publicationdate" class="form-label">Publication
										Date</label> <input type="date" class="form-control"
										id="publicationdate" name="publicationdate" max="<%=dateStr%>"
										value="<%=status.equals("update") ? book.getPublicationDate() : ""%>"
										required>
								</div>

								<div class="col-12">
									<label for="description" class="form-label"
										id="label-description" style="">Description <small
										style="color: grey; font-weight: normal;">(Optional)</small> <span
										id="check-description"></span></label>
									<textarea rows="10" cols="10" class="form-control"
										name="description" id="description"><%=status.equals("update") ? book.getDescription() : ""%></textarea>
									<span style="float: right; color: green; font-weight: bold;"
										id="charCount-description">0 / 1000</span>
								</div>
								<div class="col-12">
									<label for="image" class="form-label">Image <small
										style="color: grey;">(Optional)</small></label> <input type="file"
										class="form-control" name="image" id="image" accept="image/*">
									<input type="hidden"
										value="<%=status.equals("update") ? book.getImage() : ""%>"
										name="oldimage">
								</div>
								<div class="col-md-12">
									<label for="image3d" class="form-label">3D Image <small
										style="color: grey;">(Optional)</small></label> <input type="file"
										class="form-control" name="image3d" id="image3d"
										accept="image/*"> <input type="hidden"
										value="<%=status.equals("update") ? book.getImage3D() : ""%>"
										name="oldimage3d">
								</div>
								<div class="text-center">
									<button id="btnSave" type="submit" class="btn btn-primary"><%=(status.equals("update")) ? "Update" : "Save"%></button>
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
		$(document)
				.ready(
						function() {
							$('#author').selectpicker();
							$('#genre').selectpicker();
							$('#bookForm')
									.submit(
											function(e) {
												if ($('#description').val().length > 1000) {
													alert("The description cannot be more than 1000 characters!");
													$('html, body')
															.animate(
																	{
																		scrollTop : $(
																				'#description')
																				.offset().top
																	}, 1500);
													return false;
												} else {
													$('#btnSave').prop(
															'disabled', true);
													$('#btnSave').html(
															'Loading...');
													return true;
												}
											});
							$('#description').on("input", checkDescription);
							$('#isbn').on("input", checkISBN);
						})

		function checkISBN() {
			let value = $('#isbn').val();
			if (value.length == 14) {
				let condition = true;
				for (let i = 0; i < value.length; i++) {
					if (i == 3) {
						if (value[i] !== "-") {
							condition = false;
							break;
						}
					} else {
						if (isNaN(value[i])) {
							condition = false;
							break;
						}
					}
				}
				if (condition) {
					$('#check-isbn').html("&#x2713;");
					$('#label-isbn').css({
						"color" : "green",
						"fontWeight" : "bold"
					});
				} else {
					$('#check-isbn').html("&#x2717;");
					$('#label-isbn').css({
						"color" : "red",
						"fontWeight" : "bold"
					});
				}
			} else if (value.length == 0) {
				$('#check-isbn').html("");
				$('#label-isbn').css({
					"color" : "black",
					"fontWeight" : "normal"
				});
			} else {
				$('#check-isbn').html("&#x2717;");
				$('#label-isbn').css({
					"color" : "red",
					"fontWeight" : "bold"
				});
			}
		}

		function checkDescription() {
			let count = $('#description').val().length;
			$('#charCount-description').html(count + " / 1000");
			if (count > 1000) {
				$('#charCount').css({
					"color" : "red"
				});
				$('#check-description').html("&#x2717;");
				$('#label-description').css({
					"color" : "red",
					"fontWeight" : "bold"
				});
			} else if (count == 0) {
				$('#charCount').css({
					"color" : "green"
				});
				$('#check-description').html("");
				$('#label-description').css({
					"color" : "black",
					"fontWeight" : "normal"
				});
			} else {
				$('#charCount').css({
					"color" : "green"
				});
				$('#check-description').html("&#x2713;");
				$('#label-description').css({
					"color" : "green",
					"fontWeight" : "bold"
				});
			}
		}
	</script>

</body>

</html>
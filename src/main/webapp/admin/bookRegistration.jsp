
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
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
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
										<span id="check-title"></span>
									</label> <input type="text" name="title" class="form-control"
										id="title"
										value="<%=status.equals("update") ? book.getTitle() : ""%>" placeholder="Book Title ..."
										required> <span
										style="float: right; color: red; font-weight: bold;"
										id="charCount-title">0 / 100</span>
								</div>

								<div class="col-md-4">
									<label for="page" id="label-page" class="form-label">Page <span id="check-page"></span></label> 
									<input type="number" name="page" class="form-control" id="page"
										min="1" placeholder="Total Page ..."
										value="<%=status.equals("update") ? book.getPage() : ""%>"
										required>
								</div>
								<div class="col-md-4">
									<label for="price" id="label-price" class="form-label">Price <span id="check-price"></span></label> <input
										type="number" name="price" step=".01" min="5" placeholder="Unit Price ..."
										value="<%=status.equals("update") ? book.getPrice() : ""%>"
										class="form-control" id="price" required>
								</div>
								<div class="col-md-4">
									<label for="qty" id="label-qty" class="form-label">Qty <span id="check-qty"></span></label> <input
										type="number" name="qty" class="form-control" id="qty" placeholder="Qty ..."
										value="<%=status.equals("update") ? book.getQty() : ""%>"
										min="0" required>
								</div>

								<div class="col-md-6">
									<label for="author" id="label-author" class="form-label">Author</label> <select
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
									<label for="genre" id="label-genre" class="form-label">Genre</label> <select
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
									<label for="publisher" id="label-publisher" class="form-label">Publisher <span id="check-publisher"></span>
									</label> 
									<input type="text" name="publisher" class="form-control"
										value="<%=status.equals("update") ? book.getPublisher() : ""%>"
										id="publisher" required>
								</div>
								<div class="col-md-6">
									<label for="publicationdate" id="label-publicationdate" class="form-label">Publication
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

	<!-- Template Main JS File -->
	<script src="<%=request.getContextPath()%>/assets/js/main.js"></script>

	<script>
		document.addEventListener('DOMContentLoaded', function() {
			let descriptionInput = document.getElementById('description');
			let titleInput = document.getElementById('title');
			let isbnInput = document.getElementById('isbn');
			let btnSave = document.getElementById('btnSave');
			
			document.getElementById('author').classList.add('selectpicker');
			document.getElementById('genre').classList.add('selectpicker');
			document.getElementById('bookForm').addEventListener('submit', function(e) {
				let invalid = "none";
				e.preventDefault();
				
				if(descriptionInput.value.length > 1000) {
					invalid = "label-description";
				}
				if(invalid === "none") {
					btnSave.disabled = true;
					btnSave.innerHTML = "Loading...";
					this.submit();
				}
				else if (invalid === "label-description"){
					alert("The description cannot be more than 1000 characters!");
					scrollToElement(invalid);
				}
			})
			descriptionInput.addEventListener('input', checkDescription);
			titleInput.addEventListener('input', checkTitle);
			isbnInput.addEventListener('input', () => {checkISBN(isbnInput)});
		})
		
		function scrollToElement(element) {
			var elementRect = document.getElementById(element).getBoundingClientRect();
			var absoluteElementTop = elementRect.top + window.pageYOffset;
			var middle = absoluteElementTop - (window.innerHeight / 2) + 150;
			window.scrollTo(0, middle);
		}

		function checkISBN(isbnInput) {
			let checkisbn = document.getElementById('check-isbn');
			let labelisbn = document.getElementById('label-isbn');
			let value = isbnInput.value;
			
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
					checkisbn.innerHTML = "&#x2713;";
					labelisbn.style.color = "green";
					labelisbn.style.fontWeight = "bold";
				} else {
					checkisbn.innerHTML = "&#x2717;";
					labelisbn.style.color = "red";
					labelisbn.style.fontWeight = "bold";
				}
			} else if (value.length == 0) {
				checkisbn.innerHTML = "";
				labelisbn.style.color = "black";
				labelisbn.style.fontWeight = "normal";
			} else {
				checkisbn.innerHTML = "&#x2717;";
				labelisbn.style.color = "red";
				labelisbn.style.fontWeight = "bold";
			}
		}
		
		function checkTitle() {
			let count = $('#title').val().length;
			$('#charCount-title').html(count + " / 100");
			if (count > 100) {
				$('#charCount-title').css({
					"color" : "red"
				});
				$('#check-title').html("&#x2717;");
				$('#label-title').css({
					"color" : "red",
					"fontWeight" : "bold"
				});
			} else if (count == 0) {
				$('#charCount-title').css({
					"color" : "red"
				});
				$('#check-title').html("");
				$('#label-title').css({
					"color" : "black",
					"fontWeight" : "normal"
				});
			} else {
				$('#charCount-title').css({
					"color" : "green"
				});
				$('#check-title').html("&#x2713;");
				$('#label-title').css({
					"color" : "green",
					"fontWeight" : "bold"
				});
			}
		}

		function checkDescription() {
			let count = $('#description').val().length;
			$('#charCount-description').html(count + " / 1000");
			if (count > 1000) {
				$('#charCount-description').css({
					"color" : "red"
				});
				$('#check-description').html("&#x2717;");
				$('#label-description').css({
					"color" : "red",
					"fontWeight" : "bold"
				});
			} else if (count == 0) {
				$('#charCoun-descriptiont').css({
					"color" : "green"
				});
				$('#check-description').html("");
				$('#label-description').css({
					"color" : "black",
					"fontWeight" : "normal"
				});
			} else {
				$('#charCount-description').css({
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
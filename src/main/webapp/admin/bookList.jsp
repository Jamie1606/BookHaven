
<%
// Author		: Zay Yar Tun
// Admin No		: 2235035
// Class		: DIT/FT/2A/02
// Group		: 10
// Date			: 29.7.2023
// Description	: book list page
%>

<%@ page import="java.util.ArrayList, java.util.Date, model.Book, model.Status, model.URL"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="utf-8">
<meta content="width=device-width, initial-scale=1.0" name="viewport">

<title>BookHaven | Book List</title>
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
<link rel="stylesheet" href="https://cdn.datatables.net/1.13.5/css/jquery.dataTables.css" />
<link rel="stylesheet" href="https://cdn.datatables.net/colreorder/1.7.0/css/colReorder.dataTables.min.css" />
<link
	href="<%=request.getContextPath()%>/assets/vendor/remixicon/remixicon.css"
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
	
		String status = (String) request.getAttribute("status");
		if(status != null) {
			if(status.equals(Status.invalidData)) {
				out.println("<script>alert('Invalid data!');</script>"); 
				out.println("<script>location='" + request.getContextPath() + URL.getBookListServlet + "';</script>");
				return;
			}
			else if(status.equals(Status.invalidRequest)) {
				out.println("<script>alert('Invalid request!');</script>");
				out.println("<script>location='" + request.getContextPath() + URL.getBookListServlet + "';</script>");
				return;
			}
			else if(status.equals(Status.deleteSuccess)) {
				out.println("<script>alert('Book is successfully deleted!');</script>"); 
				out.println("<script>location='" + request.getContextPath() + URL.getBookListServlet + "';</script>");
				return;
			}
			else if(status.equals(Status.updateSuccess)) {
				out.println("<script>alert('Book is successfully updated!');</script>"); 
				out.println("<script>location='" + request.getContextPath() + URL.getBookListServlet + "';</script>");
				return;
			}
			else if(status.equals(Status.serverError)) {
				out.println("<script>alert('Server error!'); location='" + request.getContextPath() + URL.adminHomePage + "';</script>");
				return;
			}
			else if(!status.equals(Status.servletStatus)) {
				out.println("<script>location='" + request.getContextPath() + URL.getBookListServlet + "';</script>");
				return;
			}
		}
	
		ArrayList<Book> bookList = (ArrayList<Book>) request.getAttribute("bookList");
		if(bookList == null) {
			out.println("<script>alert('Server error!');</script>");
			out.println("<script>location='" + request.getContextPath() + URL.adminHomePage + "';</script>");
			return;
		}
	%>

	<main id="main" class="main">

		<div class="pagetitle">
			<h1>Book Table</h1>
			<nav>
				<ol class="breadcrumb">
					<li class="breadcrumb-item"><a
						href="<%=request.getContextPath() + URL.adminHomePage %>">Home</a></li>
					<li class="breadcrumb-item">Data List</li>
					<li class="breadcrumb-item active">Book Data</li>
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

							<!-- Table with stripped rows -->
							<table class="display data-table nowrap hover" style="width: 100%">
								<thead>
									<tr>
										<th scope="col">ISBN</th>
										<th scope="col">Title</th>
										<th scope="col">Page</th>
										<th scope="col">Price</th>
										<th scope="col">Publisher</th>
										<th scope="col">Publish Date</th>
										<th scope="col">Qty</th>
										<th scope="col">Rating</th>
										<th scope="col">Action</th>
									</tr>
								</thead>
								<tbody>
									<%
									for (int i = 0; i < bookList.size(); i++) {
										out.println("<tr>");
										out.println("<td>" + bookList.get(i).getISBNNo() + "</td>");
										out.println("<td>" + bookList.get(i).getTitle() + "</td>");
										out.println("<td>" + bookList.get(i).getPage() + "</td>");
										out.println("<td>$" + String.format("%.2f", bookList.get(i).getPrice()) + "</td>");
										out.println("<td>" + bookList.get(i).getPublisher() + "</td>");
										Date publicationDate = bookList.get(i).getPublicationDate();
										if (publicationDate == null) {
											out.println("<td></td>");
										} else {
											out.println("<td>" + publicationDate.toString() + "</td>");
										}
										out.println("<td>" + bookList.get(i).getQty() + "</td>");
										out.println("<td>" + bookList.get(i).getRating() + "</td>");
										out.println("<td><a href='" + request.getContextPath() + URL.getBookByISBNServlet
										+ bookList.get(i).getISBNNo() + "'>Edit</a> | <a data-book-title='" + bookList.get(i).getTitle() 
										+ "' class='deleteLink' href='" + request.getContextPath() + URL.deleteBookServlet 
										+ bookList.get(i).getISBNNo() + "'>Delete</a></td>");
										out.println("</tr>");
									}
									%>
								</tbody>
								<tfoot>
									<tr>
										<th scope="col">ISBN</th>
										<th scope="col">Title</th>
										<th scope="col">Page</th>
										<th scope="col">Price</th>
										<th scope="col">Publisher</th>
										<th scope="col">Publish Date</th>
										<th scope="col">Qty</th>
										<th scope="col">Rating</th>
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
	</main>
	<!-- End #main -->
	
	<!-- Modal -->
	<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
	  <div class="modal-dialog modal-dialog-centered">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h1 class="modal-title fs-5" id="exampleModalLabel">Confirmation</h1>
	        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
	      </div>
	      <div class="modal-body">
	        	Are you sure you want to delete <span id="bookTitleDelete"></span>?
	      </div>
	      <div class="modal-footer">
	      	<button type="button" class="btn btn-danger btnDelete">Delete</button>
	        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>	        
	      </div>
	    </div>
	  </div>
	</div>

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
			"colReorder": true
		});
		
		document.addEventListener('click', function(event) {
			let target = event.target;
			
			if(target.classList.contains("deleteLink")) {
				event.preventDefault();
				
				let deleteModal = new bootstrap.Modal(document.getElementById("exampleModal"));
				let bookTitle = target.getAttribute('data-book-title');
				document.getElementById("bookTitleDelete").textContent = "\"" + bookTitle + "\"";
				deleteModal.show();
				
				document.querySelector('#exampleModal .btnDelete').addEventListener('click', function() {
					window.location.href = target.getAttribute('href');
					deleteModal.hide();
				})
			}
		});
	</script>

</body>

</html>
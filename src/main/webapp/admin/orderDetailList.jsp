<%
// Author		: Zay Yar Tun
// Admin No		: 2235035
// Class		: DIT/FT/2A/02
// Group		: 10
// Date			: 29.7.2023
// Description	: order detail list page
%>

<%@ page import="java.util.ArrayList, java.util.Date, model.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="utf-8">
<meta content="width=device-width, initial-scale=1.0" name="viewport">

<title>BookHaven | Order Detail List</title>
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
				out.println("<script>alert('Invalid data!'); location='" + request.getContextPath() + URL.getOrderListServlet + "';</script>");
				return;
			}
			else if(status.equals(Status.invalidRequest)) {
				out.println("<script>alert('Invalid request!'); location='" + request.getContextPath() + URL.getOrderListServlet + "';</script>");
				return;
			}
			else if(status.equals(Status.updateSuccess)) {
				out.println("<script>alert('Order delivered!!');</script>"); 
			}
			else if(status.equals(Status.deleteSuccess)) {
				out.println("<script>alert('Order cancelled!');</script>");
			}
		}
		
		String orderid = (String) request.getParameter("id");
		if(orderid != null) {
			out.println("<script>location='" + request.getContextPath() + URL.getOrderDetailListServlet + orderid + "';</script>");
			return;
		}

		ArrayList<OrderItem> itemList = (ArrayList<OrderItem>) request.getAttribute("itemList");
		if(itemList == null) {
			out.println("<script>alert('Server error!');</script>");
			out.println("<script>location='" + request.getContextPath() + URL.adminHomePage + "';</script>");
			return;
		}
	%>

	<main id="main" class="main">

		<div class="pagetitle">
			<h1>Order Table</h1>
			<nav>
				<ol class="breadcrumb">
					<li class="breadcrumb-item"><a
						href="<%= request.getContextPath() + URL.adminHomePage %>">Home</a></li>
					<li class="breadcrumb-item">Tables</li>
					<li class="breadcrumb-item"><a href="<%= request.getContextPath() + URL.getOrderListServlet %>">Order</a></li>
					<li class="breadcrumb-item active">Order Detail</li>
				</ol>
			</nav>
		</div>
		<!-- End Page Title -->

		<section class="section">
			<div class="row">
				<div class="col-lg-12">

					<div class="card">
						<div class="card-body">
							<h5 class="card-title">Order Detail Information</h5>

							<!-- Table with stripped rows -->
							<table class="display data-table nowrap hover" style="width: 100%">
								<thead>
									<tr>
										<th scope="col">Order No.</th>
										<th scope="col">ISBN No.</th>
										<th scope="col">Title</th>
										<th scope="col">Qty</th>
										<th scope="col">Unit Price</th>
										<th scope="col">Amount</th>
										<th scope="col">Status</th>
										<th scope="col">Action</th>
									</tr>
								</thead>
								<tbody>
									<%
									for (int i = 0; i < itemList.size(); i++) {
										
										String contextPath = request.getContextPath();
										int orderID = itemList.get(i).getOrderid();
										String isbnno = itemList.get(i).getIsbnno();
										if(isbnno == null) {
											isbnno = "";
										}
										double price = itemList.get(i).getBook().getPrice();
										String priceStr = "$" + String.format("%.2f", price);
										String title = itemList.get(i).getBook().getTitle();
										if(title == null) {
											title = "";
											priceStr = "N/A";
										}
										int qty = itemList.get(i).getQty();
										double amount = itemList.get(i).getAmount();
										String orderStatus = itemList.get(i).getStatus();
										
										out.println("<tr>");
										out.println("<td>" + orderID + "</td>");
										out.println("<td>" + isbnno + "</td>");
										out.println("<td>" + title + "</td>");
										out.println("<td>" + qty + "</td>");
										out.println("<td>" + priceStr + "</td>");
										out.println("<td>$" + String.format("%.2f", amount) + "</td>");
										out.println("<td>" + orderStatus.substring(0,1).toUpperCase() + orderStatus.substring(1) + "</script></td>");
										out.println("<td>");
										if(orderStatus.equals("pending")) {
											out.println("<a data-order-id='" + orderID + "' class='orderCompleteLink' href='" + request.getContextPath() + URL.completeOrderServlet + orderID + "/" + isbnno + "'>Complete</a>");
											out.println(" | ");
											out.println("<a data-order-id='" + orderID + "' class='orderCancelLink' href='" + request.getContextPath() + URL.cancelOrderServlet + orderID + "/" + isbnno + "'>Cancel</a>");
										}
										out.println("</td>");
										out.println("</tr>");
									}
									%>
								</tbody>
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
	
	<!-- Modal -->
	<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
	  <div class="modal-dialog modal-dialog-centered">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h1 class="modal-title fs-5" id="exampleModalLabel">Confirmation</h1>
	        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
	      </div>
	      <div class="modal-body">
	        <span id="modal-text"></span>
	      </div>
	      <div class="modal-footer">
	      	<button type="button" id="btnDelete" class="btn btn-danger btnDelete">Delete</button>
	        <button type="button" class="btn btn-secondary" id="btnCancel" data-bs-dismiss="modal">Cancel</button>	        
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
		function makeCapital(str) {
			return str.charAt(0).toUpperCase() + str.slice(1);
		}
	
		let table = new DataTable('.data-table', {
			"scrollX": true,
			"pageLength": 25,
			"stateSave": true,
			"colReorder": true
		});
		
		document.addEventListener('click', function(event) {
			let target = event.target;
			
			if(target.classList.contains("orderCompleteLink")) {
				event.preventDefault();
				
				var completeModal = new bootstrap.Modal(document.getElementById("exampleModal"));
				let orderid = target.getAttribute('data-order-id');
				document.getElementById("modal-text").textContent = "Are you sure you want to complete this order #'" + orderid + "'?";
				document.getElementById("btnDelete").textContent = "Complete Order";
				document.getElementById("btnCancel").textContent = "No";
				completeModal.show();
				
				document.querySelector('#exampleModal .btnDelete').addEventListener('click', function() {
					window.location.href = target.getAttribute('href');
					completeModal.hide();
				})
			}
		});
		
		document.addEventListener('click', function(event) {
			let target = event.target;
			
			if(target.classList.contains("orderCancelLink")) {
				event.preventDefault();
				
				var completeModal = new bootstrap.Modal(document.getElementById("exampleModal"));
				let orderid = target.getAttribute('data-order-id');
				document.getElementById("modal-text").textContent = "Are you sure you want to cancel this order #'" + orderid + "'?";
				document.getElementById("btnDelete").textContent = "Cancel Order";
				document.getElementById("btnCancel").textContent = "No";
				completeModal.show();
				
				document.querySelector('#exampleModal .btnDelete').addEventListener('click', function() {
					window.location.href = target.getAttribute('href');
					completeModal.hide();
				})
			}
		});
	</script>
</body>
</html>
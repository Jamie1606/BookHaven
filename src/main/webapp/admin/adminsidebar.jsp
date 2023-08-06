<%@page import="model.URL" %>

<!-- ======= Sidebar ======= -->
<aside id="sidebar" class="sidebar">

	<ul class="sidebar-nav" id="sidebar-nav">

		<li class="nav-item">
			<a class="nav-link collapsed" href="<%= request.getContextPath() + URL.adminHomePage %>"> 
				<i class="bi bi-grid"></i> <span>Dashboard</span>
			</a>
		</li>

		<li class="nav-item">
			<a class="nav-link collapsed" data-bs-target="#forms-nav" data-bs-toggle="collapse" href="#"> 
				<i class="bi bi-journal-text"></i><span>Registration Forms</span>
				<i class="bi bi-chevron-down ms-auto"></i>
			</a>
			
			<ul id="forms-nav" class="nav-content collapse"	data-bs-parent="#sidebar-nav">
			
				<li>
					<a href="<%= request.getContextPath() + URL.authorRegistration %>"> 
						<i class="bi bi-circle"></i><span>Author Registration</span>
					</a>
				</li>
				<li>
					<a href="<%= request.getContextPath() + URL.getBookRegistrationServlet %>"> 
						<i class="bi bi-circle"></i><span>Book Registration</span>
					</a>
				</li>
				<li>
					<a href="<%= request.getContextPath() + URL.genreRegistration %>"> 
						<i class="bi bi-circle"></i><span>Genre	Registration</span>
					</a>
				</li>
				<li>
					<a href="<%= request.getContextPath() + URL.memberRegistration %>"> 
						<i class="bi bi-circle"></i><span>Member Registration</span>
					</a>
				</li>
			</ul>
		</li>

		<li class="nav-item">
			<a class="nav-link collapsed" data-bs-target="#tables-nav" data-bs-toggle="collapse" href="#">
				<i class="bi bi-layout-text-window-reverse"></i><span>Data List</span><i class="bi bi-chevron-down ms-auto"></i>
			</a>
			
			<ul id="tables-nav" class="nav-content collapse "
				data-bs-parent="#sidebar-nav">
				
				<li>
					<a href="<%= request.getContextPath() + URL.getAuthorListServlet %>"> 
						<i class="bi bi-circle"></i><span>Author List</span>
					</a>
				</li>
				
				<li>
					<a href="<%= request.getContextPath() + URL.getBookListServlet %>"> 
						<i class="bi bi-circle"></i><span>Book List</span>
					</a>
				</li>
				
				<li>
					<a href="<%= request.getContextPath() + URL.getGenreListServlet %>"> 
						<i class="bi bi-circle"></i><span>Genre List</span>
					</a>
				</li>
				
				<li>
					<a href="<%= request.getContextPath() + URL.getMemberListServlet %>"> 
						<i class="bi bi-circle"></i><span>Member List</span>
					</a>
				</li>
				
				<li>
					<a href="<%= request.getContextPath() + URL.getOrderListServlet %>"> 
						<i class="bi bi-circle"></i><span>Order List</span>
					</a>
				</li>
				
				<li>
					<a href="<%= request.getContextPath() + URL.getReviewListServlet %>"> 
						<i class="bi bi-circle"></i><span>Review List</span>
					</a>
				</li>
				
			</ul>
		</li>
		<li class="nav-item">
			<a class="nav-link collapsed" data-bs-target="#charts-nav" data-bs-toggle="collapse" href="#">
				<i class="bi bi-layout-text-window-reverse"></i><span>Book Report</span><i class="bi bi-chevron-down ms-auto"></i>
			</a>
			
			<ul id="charts-nav" class="nav-content collapse "
				data-bs-parent="#sidebar-nav">
				
				<li>
					<a href="<%= request.getContextPath() + URL.bestSellingBookList %>"> 
						<i class="bi bi-circle"></i><span>Best Selling</span>
					</a>
				</li>
				
				<li>
					<a href="<%= request.getContextPath() + URL.leastSellingBookList %>"> 
						<i class="bi bi-circle"></i><span>Least Selling</span>
					</a>
				</li>
				
				<li>
					<a href="<%= request.getContextPath() + URL.lowStockBookList %>"> 
						<i class="bi bi-circle"></i><span>Low Stock</span>
					</a>
				</li>

				
			</ul>
		</li>
		
		<li class="nav-item">
			<a class="nav-link collapsed" data-bs-target="#icons-nav" data-bs-toggle="collapse" href="#">
				<i class="bi bi-layout-text-window-reverse"></i><span>Sales Report</span><i class="bi bi-chevron-down ms-auto"></i>
			</a>
			
			<ul id="icons-nav" class="nav-content collapse "
				data-bs-parent="#sidebar-nav">
				
				<li>
					<a href="<%= request.getContextPath() + URL.getBookSalesServlet %>"> 
						<i class="bi bi-circle"></i><span>Book Sales</span>
					</a>
				</li>
				
				<li>
					<a href="<%= request.getContextPath() + URL.getTopCustomerseServlet %>"> 
						<i class="bi bi-circle"></i><span>Top Customers</span>
					</a>
				</li>
				
				<li>
					<a href="<%= request.getContextPath() + URL.getCustomersByBookIDServlet %>"> 
						<i class="bi bi-circle"></i><span>Customers of the Book</span>
					</a>
				</li>

				
			</ul>
		</li>
	</ul>
</aside>
<!-- End Sidebar-->
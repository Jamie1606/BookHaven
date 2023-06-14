<!-- ======= Sidebar ======= -->
<aside id="sidebar" class="sidebar">

	<ul class="sidebar-nav" id="sidebar-nav">

		<li class="nav-item"><a class="nav-link collapsed"
			href="<%= request.getContextPath() %>/admin/adminHomePage.jsp"> <i class="bi bi-grid"></i> <span>Dashboard</span>
		</a></li>
		<!-- End Dashboard Nav -->

		<li class="nav-item"><a class="nav-link collapsed"
			data-bs-target="#forms-nav" data-bs-toggle="collapse" href="#"> <i
				class="bi bi-journal-text"></i><span>Registration Forms</span><i
				class="bi bi-chevron-down ms-auto"></i>
		</a>
			<ul id="forms-nav" class="nav-content collapse"
				data-bs-parent="#sidebar-nav">
				<li><a href="<%= request.getContextPath() %>/admin/authorRegistration.jsp"> <i
						class="bi bi-circle"></i><span>Author Registration</span>
				</a></li>
				<li><a href="<%= request.getContextPath() %>/admin/bookRegistration"> <i class="bi bi-circle"></i><span>Book
							Registration</span>
				</a></li>
				<li><a href="<%= request.getContextPath() %>/admin/genreRegistration.jsp"> <i class="bi bi-circle"></i><span>Genre
							Registration</span>
				</a></li>
				<li><a href="<%= request.getContextPath() %>/admin/memberRegistration.jsp"> <i class="bi bi-circle"></i><span>Member
							Registration</span>
				</a></li>
			</ul></li>
		<!-- End Forms Nav -->

		<li class="nav-item"><a class="nav-link collapsed"
			data-bs-target="#tables-nav" data-bs-toggle="collapse" href="#">
				<i class="bi bi-layout-text-window-reverse"></i><span>Data
					List</span><i class="bi bi-chevron-down ms-auto"></i>
		</a>
			<ul id="tables-nav" class="nav-content collapse "
				data-bs-parent="#sidebar-nav">
				<li><a href="<%= request.getContextPath() %>/admin/authors"> <i class="bi bi-circle"></i><span>Author
							List</span>
				</a></li>
				<li><a href="<%= request.getContextPath() %>/admin/books"> <i class="bi bi-circle"></i><span>Book
							List</span>
				</a></li>
				<li><a href=""> <i class="bi bi-circle"></i><span>Genre
							List</span>
				</a></li>
				<li><a href=""> <i class="bi bi-circle"></i><span>Member
							List</span>
				</a></li>
				<li><a href="<%= request.getContextPath() %>/admin/reviews"> <i class="bi bi-circle"></i><span>Review
							List</span>
				</a></li>
			</ul></li>
		<!-- End Tables Nav -->
	</ul>

</aside>
<!-- End Sidebar-->
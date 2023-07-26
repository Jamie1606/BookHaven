<%@page import="model.URL, model.Status"%>
<%
	if(session != null) {
		String role = (String) session.getAttribute("role");
		String token = (String) session.getAttribute("token");
		
		if(role != null && token != null) {
			if(!role.equals("ROLE_ADMIN")) {
				request.setAttribute("status", Status.unauthorized);
				out.println("<script>location='" + request.getContextPath() + URL.signOut + "';</script>");
				return;
			}
		}
	}
	else {
		request.setAttribute("status", Status.unauthorized);
		out.println("<script>location='" + request.getContextPath() + URL.signOut + "';</script>");
		return;
	}
%>

<!-- ======= Header ======= -->
<header id="header" class="header fixed-top d-flex align-items-center">

	<div class="d-flex align-items-center justify-content-between">
		<a href="<%=request.getContextPath() + URL.adminHomePage %>"
			class="logo d-flex align-items-center"> <img
			src="<%= request.getContextPath() %>/img/logo.png" alt=""> <span class="d-none d-lg-block">BookHaven</span>
		</a> <i class="bi bi-list toggle-sidebar-btn"></i>
	</div>
	<!-- End Logo -->

	<nav class="header-nav ms-auto">
		<ul class="d-flex align-items-center">

			<li class="nav-item dropdown pe-3"><a
				class="nav-link nav-profile d-flex align-items-center pe-0" href="#"
				data-bs-toggle="dropdown"> <img
					src="<%= request.getContextPath() %>/assets/img/profile-img.jpg" alt="Profile"
					class="rounded-circle"> <span
					class="d-none d-md-block dropdown-toggle ps-2">Admin</span>
			</a> <!-- End Profile Iamge Icon -->

				<ul
					class="dropdown-menu dropdown-menu-end dropdown-menu-arrow profile">
					<li class="dropdown-header">
						<h6>System Admin</h6>
					</li>
					<li>
						<hr class="dropdown-divider">
					</li>

					<li><a class="dropdown-item d-flex align-items-center"
						href="<%=request.getContextPath() + URL.signOut %>"> <i
							class="bi bi-box-arrow-right"></i> <span>Sign Out</span>
					</a></li>

				</ul> <!-- End Profile Dropdown Items --></li>
			<!-- End Profile Nav -->

		</ul>
	</nav>
	<!-- End Icons Navigation -->

</header>
<!-- End Header -->
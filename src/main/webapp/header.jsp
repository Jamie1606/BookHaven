<%
// Author		: Zay Yar Tun, Thu Htet San
// Admin No		: 2235035, 2235022
// Class		: DIT/FT/2A/02
// Group		: 10
// Date			: 1.8.2023
// Description	: header
%>

<%@ page import="model.URL, model.Status" %>

<header id="header">
	<div class="container">
		<div class="row align-items-center justify-content-between d-flex">
			<div id="logo">
				<a href="<%= request.getContextPath() + URL.homePage %>"><span
					style="font-size: 30px; font-weight: bold; text-transform: uppercase; letter-spacing: 1.2px; color: white;">BookHaven</span></a>
			</div>
			<nav id="nav-menu-container">
				<ul class="nav-menu">
					<li><a href="<%= request.getContextPath() + URL.homePage %>">Home</a></li>
					<li><a href="<%= request.getContextPath() + URL.search %>">Search</a></li>
					<li><a href="<%= request.getContextPath() + URL.bookGenre %>">Genre</a></li>
					<%
						if(session != null) {
							String role = (String) session.getAttribute("role");
							String token = (String) session.getAttribute("token");
							if(role != null && token != null) {
								if(role.equals("ROLE_MEMBER")) {
									out.println("<li><a href='" + request.getContextPath() + URL.getProfileDataServlet + "'>Profile</a></li>");
									out.println("<li><a href='" + request.getContextPath() + URL.history + "'>History</a></li>");
									out.println("<li><a href='" + request.getContextPath() + URL.cart + "'>Cart</a></li>");
									out.println("<li><a href='" + request.getContextPath() + URL.signOut + "'>Sign Out</a></li>");
								}
								else if(role.equals("ROLE_ADMIN")) {
									out.println("<script>location = '" + request.getContextPath() + URL.adminHomePage + "';</script>");
									return;
								}
								else {
									request.setAttribute("status", Status.unauthorized);
									out.println("<script>location = '" + request.getContextPath() + URL.signOut + "';</script>");
									return;
								}
							}
							else {
								out.println("<li><a href='" + request.getContextPath() + URL.signIn + "'>Sign In</a></li>");
								out.println("<li><a href='" + request.getContextPath() + URL.signUp + "'>Sign Up</a></li>");
							}
						}
						else {
							out.println("<li><a href='" + request.getContextPath() + URL.signIn + "'>Sign In</a></li>");
							out.println("<li><a href='" + request.getContextPath() + URL.signUp + "'>Sign Up</a></li>");
						}
					%>
				</ul>
			</nav>
			<!-- #nav-menu-container -->
		</div>
	</div>
</header>
<!-- #header -->
<div style="position: absolute; top:0; left: 0; width: 100%; height: 60px; background-image: linear-gradient(90deg, #f45622 0%, #f53e54 100%);">
</div>
<header id="header">
	<div class="container">
		<div class="row align-items-center justify-content-between d-flex">
			<div id="logo">
				<a href="<%= request.getContextPath() %>/index.jsp"><span
					style="font-size: 30px; font-weight: bold; text-transform: uppercase; letter-spacing: 1.2px; color: white;">BookHaven</span></a>
			</div>
			<nav id="nav-menu-container">
				<ul class="nav-menu">
					<li><a href="<%= request.getContextPath() %>/index.jsp">Home</a></li>
					<li><a href="<%= request.getContextPath() %>/search.jsp">Search</a></li>
					<li><a href="<%= request.getContextPath() %>/bookGenre.jsp">Genre</a></li>
					<%
						Authentication auth = new Authentication();
						if(auth.testMember(session)) {
							%>
							<li><a href="<%= request.getContextPath() %>/profile">Profile</a></li>
							<li><a href="<%= request.getContextPath() %>/cart.jsp">Cart</a></li>
							<li><a href="<%= request.getContextPath() %>/signout.jsp">Sign Out</a></li>
							<%
						}
						else {
							%>
							<li><a href="<%= request.getContextPath() %>/signin.jsp">Sign In</a></li>
							<li><a href="<%= request.getContextPath() %>/signup.jsp">Sign Up</a></li>
							<%
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
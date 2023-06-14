<header id="header" id="home">
	<div class="container">
		<div class="row align-items-center justify-content-between d-flex">
			<div id="logo">
				<a href="<%= request.getContextPath() %>/index.jsp"><span
					style="font-size: 30px; font-weight: bold; text-transform: uppercase; letter-spacing: 1.2px; color: white;">BookHaven</span></a>
			</div>
			<nav id="nav-menu-container">
				<ul class="nav-menu">
					<li><a href="<%= request.getContextPath() %>/index.jsp">Home</a></li>
					<li><a href="<%= request.getContextPath() %>/book.jsp">Book</a></li>
					<li><a href="<%= request.getContextPath() %>/BookGenreTest.jsp">Genre</a></li>
					<li><a href="<%= request.getContextPath() %>/author.jsp">Author</a></li>
					<li><a href="<%= request.getContextPath() %>/signin.jsp">Sign In</a></li>
					<li><a href="<%= request.getContextPath() %>/signup.jsp">Sign Up</a></li>
				</ul>
			</nav>
			<!-- #nav-menu-container -->
		</div>
	</div>
</header>
<!-- #header -->
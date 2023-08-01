<%
// Author		: Zay Yar Tun, Thu Htet San
// Admin No		: 2235035
// Class		: DIT/FT/2A/02
// Group		: 10
// Date			: 1.8.2023
// Description	: sign out and destroy session
%>
<%@ page import="model.*" %>
<%
	session.invalidate();
	String status = (String) request.getAttribute("status");
	request.removeAttribute("status");
	
	if(status != null && status.equals(Status.unauthorized)) {
		out.println("<script>alert('Unauthorized!');</script>");
	}
	out.println("<script>location='" + request.getContextPath() + URL.homePage + "';</script>");
	return;
%>
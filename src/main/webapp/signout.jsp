<%
//Author: Zay Yar Tun
//Admin No: 2235035
//Date: 5.6.2023
//Description: sign out and destroy session
%>
<%@ page import="javax.servlet.http.HttpSession" %>
<%
session.invalidate();
String error = (String) request.getAttribute("error");
if(error != null && error.equals("unauthorized")) {
	out.println("<script>alert('Unauthorized'); location = '" + request.getContextPath() +  "/index.jsp';</script>");
	return;
}else if(error != null && error.equals("invalid")) {
	out.println("<script>alert('Invalid Data'); location = '" + request.getContextPath() +  "/index.jsp';</script>");
	return;
}else if(error != null && error.equals("serverError")) {
	out.println("<script>alert('Server Error'); location = '" + request.getContextPath() +  "/index.jsp';</script>");
	return;
}
else {
	out.println("<script>location='" + request.getContextPath() +  "/index.jsp';</script>");
	return;
}
%>
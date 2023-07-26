<%
//Author: Zay Yar Tun
//Admin No: 2235035
// Class: DIT/FT/2A/02
// Group: 10
//Date: 5.6.2023
//Description: sign out and destroy session
%>
<%@ page import="javax.servlet.http.HttpSession, model.Status" %>
<%
session.invalidate();
String status = (String) request.getAttribute("status");
if(status != null && status.equals(Status.unauthorized)) {
	out.println("<script>alert('Unauthorized!');</script>");
}
out.println("<script>location='" + request.getContextPath() +  "/index.jsp';</script>");
return;
%>
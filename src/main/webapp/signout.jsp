<%
//Author: Zay Yar Tun
//Admin No: 2235035
//Date: 5.6.2023
//Description: sign out and destroy session
%>
<%
session.invalidate();
String error = (String) request.getAttribute("error");
if(error != null && error.equals("unauthorized")) {
	out.println("<script>alert('Unauthorized'); location = 'index.jsp';</script>");
}
else {
	out.println("<script>location='index.jsp';</script>");
}
%>
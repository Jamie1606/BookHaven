<%
//Author: Zay Yar Tun
//Admin No: 2235035
//Date: 5.6.2023
//Description: sign out and destroy session
%>
<%
session.invalidate();
response.sendRedirect("index.jsp");
%>
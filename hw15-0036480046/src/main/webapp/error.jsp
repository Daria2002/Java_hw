<%@page import="java.io.OutputStream"%>
<%@page import="java.util.Base64"%>
<%@ page session="true" %>
<%@ page session="true" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   
<!DOCTYPE html>
<html>	
<head>
<%
	
		if(request.getSession().getAttribute("current.user.fn") != null) {
			%>
			
			<p><%=request.getSession().getAttribute("current.user.fn")%> <%=request.getSession().getAttribute("current.user.ln") %></p>
			
			<form action="<%=request.getContextPath()%>/servleti/logout" method="get">
			    <input type="submit" value="logout">
			</form>
			<%
		} else {
		%>	
			<p>not loged in</p>
			
		<%}
		
	%>

</head>
	<p>Error occurred.</p>
</html>
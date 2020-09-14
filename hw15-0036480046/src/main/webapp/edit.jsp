<%@page import="hr.fer.zemris.java.tecaj_13.model.BlogComment"%>
<%@page import="java.util.List"%>
<%@page import="hr.fer.zemris.java.tecaj_13.model.BlogEntry"%>
<%@page import="hr.fer.zemris.java.tecaj_13.model.BlogUser"%>
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
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
		<form action="" method="post" id="edit">
			Text:<textarea name="newText" form="edit"><%=request.getAttribute("exText")%></textarea>
			<br>
			Title:<textarea name="newTitle" form="edit"><%=request.getAttribute("exTitle")%></textarea>
			<input type="submit" value="edit entry" /> <br>
		</form>
</body>
</html>
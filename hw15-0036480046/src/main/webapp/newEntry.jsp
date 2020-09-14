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
		<form action="" method="post">
			Title:<br><input type="text" name="title"><br>
			Text:<br><input type="text" name="text"><br>
			<input type="submit" value="add entry" /> <br>
		</form>
</body>
</html>
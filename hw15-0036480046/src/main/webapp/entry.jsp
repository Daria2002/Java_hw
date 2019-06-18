<%@page import="hr.fer.zemris.java.tecaj_13.model.BlogEntry"%>
<%@page import="hr.fer.zemris.java.tecaj_13.model.BlogUser"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

		<%
			if(request.getAttribute("current.user.nick").equals((
					(BlogUser)request.getAttribute("nick")))) {
				%>
				<form action="/servleti/<%=request.getAttribute("current.user.nick")%>/edit" method="post">
					<input type="submit" value="edit entry" /> <br>
				</form>
				
				<form action="/servleti/<%=request.getAttribute("current.user.nick")%>/new" method="post">
					<input type="submit" value="new entry" /> <br>
				</form>
				<%
			}
		
			String title = (String)request.getAttribute("title");
			String text = (String)request.getAttribute("text");
		%>
		
		<h1><%=title%></h1>
		<p><%=text%></p>
		
</body>
</html>
<%@page import="hr.fer.zemris.java.tecaj_13.model.BlogComment"%>
<%@page import="java.util.List"%>
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
		<form action="<%=request.getAttribute("current.user.nick")%>" method="post">
			Title:<br><input type="text" name="title"><br>
			Text:<br><input type="text" name="text"><br>
			<input type="submit" value="add entry" /> <br>
		</form>
</body>
</html>
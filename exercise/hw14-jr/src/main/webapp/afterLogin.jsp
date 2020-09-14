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
		String mess = (String)session.getAttribute("mess");
	%>
	<p><%=mess%></p>
	
	<p></p>
	<a href="../vjezba.jsp">Pocetna jr</a>
	<p></p>
	
</body>
</html>
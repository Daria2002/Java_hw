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
	
		String name = (String)request.getAttribute("imeS");
		Integer l = (Integer)request.getAttribute("numL");
		Integer krug = (Integer)request.getAttribute("numKRUG");
		Integer kru = (Integer)request.getAttribute("numK");
		Integer tro = (Integer)request.getAttribute("numT");

	%>
	
	<p>ime slike je <%=name %></p>

	<p>kruznice <%=kru %></p>
	<p>krugovi <%=krug %></p>
	<p>linije<%=l %></p>
	<p>trokuti<%=tro %></p>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<h2>Grafički prikaz rezultata</h2>
	<img alt="Pie-chart" src="<%=request.getContextPath()%>/servleti/statistikaDB" width="400" height="400" />
	<h2>Grafički prikaz rezultata stup.</h2>
	<!-- ovdje se koristi /servleti/ServletGrafDB jer se tamo stvara graf -->
	<img alt="Pie-chart" src="<%=request.getContextPath()%>/servleti/ServletGrafDB" width="400" height="400" />
</body>
</html>
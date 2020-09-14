<%@page import="hr.fer.zemris.java.p12.model.Poll"%>
<%@page import="java.util.List"%>
<%@page import="hr.fer.zemris.java.p12.dao.sql.SQLDAO"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>


<%
	String myColor = (String)session.getAttribute("pickedBgCol");
 
	if (myColor == null){
		myColor = "white";
	}
%>

<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body bgcolor="<%=myColor%>">
<form action="servleti/glasanje" method="post">
    first name<input type="text" name="firstName">
    <br>
    last name<input type="text" name="lastName">
    <br>
    email<input type="text" name="email">
    <br>
    nick<input type="text" name="nick">
    <br>
    password<input type="password" name="password">
    <br>
	<input type="submit" value="register">
    <!-- <span class="error">${error}</span>   -->
</form>

</body>
</html>
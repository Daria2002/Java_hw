<%@ page session="true" %>
<%@ page import="java.util.Date,java.util.Calendar" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- Primjer bloka u kojem možemo deklarirati funkcije -->

<%
	String myColor = (String)session.getAttribute("pickedBgCol");
 
	if (myColor == null){
		myColor = "white";
	}
%>

<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Application running time</title>
	</head>
	<body>
	Application running time: <%= (Long)request.getServletContext().getAttribute("startTime") - System.currentTimeMillis()/1000%>
	</body>
</html>
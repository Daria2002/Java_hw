<%@page import="java.util.concurrent.TimeUnit"%>
<%@ page session="true" %>
<%@ page import="java.util.Date,java.util.Calendar" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- Primjer bloka u kojem možemo deklarirati funkcije -->

<%!
public String getTime(Long milliseconds) {
	final long dy = TimeUnit.MILLISECONDS.toDays(milliseconds);
	final long hr = TimeUnit.MILLISECONDS.toHours(milliseconds)
			- TimeUnit.DAYS.toHours(dy);
	final long min = TimeUnit.MILLISECONDS.toMinutes(milliseconds)
			- TimeUnit.HOURS.toMinutes(hr);
	final long sec = TimeUnit.MILLISECONDS.toSeconds(milliseconds)
			- TimeUnit.MINUTES.toSeconds(min);
	final long ms = TimeUnit.MILLISECONDS.toMillis(milliseconds)
			- TimeUnit.SECONDS.toMillis(sec);
	return String.format("%d Days %d Hours %d Minutes %d Seconds %d Milliseconds", dy, hr, min, sec, ms);
}
%>

<%
	String myColor = (String)session.getAttribute("pickedBgCol");
 
	if (myColor == null){
		myColor = "white";
	}
%>

<html>
	<body bgcolor="<%= myColor %>">
		<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Application running time</title>
		</head>
		
		<td width="100%"><b>&nbsp;Application running time:&nbsp;
		<font>
		<%= getTime(new java.util.Date().getTime() - (Long)request.getServletContext().getAttribute("startTime")) %>
		</font></b></td>
	</body>
</html>


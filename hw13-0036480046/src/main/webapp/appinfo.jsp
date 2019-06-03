<%@page import="java.util.concurrent.TimeUnit"%>
<%@ page session="true" %>
<%@ page import="java.util.Date,java.util.Calendar" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- Primjer bloka u kojem možemo deklarirati funkcije -->

<%!
public String getTime(Long milliseconds) {
	final long dy = TimeUnit.MILLISECONDS.toDays(milliseconds);
	final long hr = TimeUnit.MILLISECONDS.toHours(milliseconds)
			- TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(milliseconds));
	final long min = TimeUnit.MILLISECONDS.toMinutes(milliseconds)
			- TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(milliseconds));
	final long sec = TimeUnit.MILLISECONDS.toSeconds(milliseconds)
			- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliseconds));
	final long ms = TimeUnit.MILLISECONDS.toMillis(milliseconds)
			- TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(milliseconds));
	return String.format("%d Days %d Hours %d Minutes %d Seconds %d Milliseconds", dy, hr, min, sec, ms);
}
%>

<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Application running time</title>
	</head>
	<body>
	<td width="100%"><b>&nbsp;Application running time:&nbsp;
	<font>
	<%= getTime(new java.util.Date().getTime()/1000 - (Long)request.getServletContext().getAttribute("startTime")) %>
	</font></b></td>
	</body>
</html>


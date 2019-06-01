<%@ page session="true" %>
<%@ page import="java.util.Date,java.util.Calendar" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- Primjer bloka u kojem možemo deklarirati funkcije -->

<%
	String myColor = (String)request.getSession().getAttribute("pickedBgCol");
 
	if (myColor == null){
		myColor = "white";
	}
%>

<html>
   <body bgcolor="<%=myColor%>">
   	 <a href="colors.jsp">Background color chooser</a>
   </body>
</html>
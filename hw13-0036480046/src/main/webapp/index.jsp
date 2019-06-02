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
   <body bgcolor="<%=myColor%>">
   	 <a href="colors.jsp">Background color chooser</a>
   	 <form action="trigonometric" method="GET">
		Početni kut:<br><input type="number" name="a" min="0" max="360" step="1" value="0"><br>
		Završni kut:<br><input type="number" name="b" min="0" max="360" step="1" value="360"><br>
		<input type="submit" value="Tabeliraj" name="table"><input type="reset" value="Reset">
	</form>
   </body>
</html>
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
   	 <a href="/webapp2/colors.jsp">Background color chooser</a>
   	 
   	 <form action="trigonometric" method="GET">
		Početni kut:<br><input type="number" name="a" min="0" max="360" step="1" value="0"><br>
		Završni kut:<br><input type="number" name="b" min="0" max="360" step="1" value="360"><br>
		<input type="submit" value="Tabeliraj"><input type="reset" value="Reset">
	</form>
	
	<a href="/webapp2/trigonometric?a=0&b=90">Sin and cos for angle = [0, 90]</a>
	<p></p>
	<a href="/webapp2/stories/funny.jsp">Story</a>
	<p></p>
	<a href="/webapp2/powers.jsp">Power function</a>
	<p></p>
	<a href="/webapp2/powers?a=1&b=100&n=3">Power(a=1 , b=100 , n=3)</a>
	<p></p>
	<a href="/webapp2/glasanje">Glasanje</a>
   </body>
</html>

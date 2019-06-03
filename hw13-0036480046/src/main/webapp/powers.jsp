<%@ page session="true" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
	String myColor = (String)session.getAttribute("pickedBgCol");
 
	if (myColor == null){
		myColor = "white";
	}
%>
  
<html>
	<body bgcolor="<%=myColor%>">
	   	<form action="powers" method="GET">
		a:<br><input type="number" name="a" step="1" value="1"><br>
		b:<br><input type="number" name="b" step="1" value="2"><br>
		n:<br><input type="number" name="n" step="1" value="1"><br>
		<input type="submit" value="Create xls"><input type="reset" value="Reset">
	</form>
	</body>
</html>
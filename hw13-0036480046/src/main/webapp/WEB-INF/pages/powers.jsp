<%@ page session="true" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
	
%>
  
<html>
	<body>
	   	<form action="powers" method="GET">
		a:<br><input type="number" name="a" min="-100" max="100" step="1" value="2"><br>
		b:<br><input type="number" name="b" min="-100" max="100" step="1" value="3"><br>
		n:<br><input type="number" name="n" min="1" max="5" step="1" value="1"><br>
		<input type="submit" value="Create xls"><input type="reset" value="Reset">
		
		<%
			if(request.getAttribute("mess") != null) {
				%>
				<p><% request.getAttribute("mess"); %></a></p>
			   <%
			}
		%>
		
		
	</form>
	</body>
</html>
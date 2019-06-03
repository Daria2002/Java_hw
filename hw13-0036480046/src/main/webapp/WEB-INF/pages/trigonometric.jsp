<%@ page session="true" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
	Double[] sin = (Double[]) session.getAttribute("sin");	
	Double[] cos = (Double[]) session.getAttribute("cos");
	Integer a = (Integer) session.getAttribute("a");
	
	String myColor = (String)session.getAttribute("pickedBgCol");
	 
	if (myColor == null){
		myColor = "white";
	}
%>
  
<html>
	<body bgcolor="<%=myColor%>">
	   	<table border="1">
			<thead>
			    <tr>
			        <th>x</th>
			        <th>sin(x)</th>
			        <th>cos(x)</th>
			    </tr>
			</thead>
			
			<tbody>
				<%
					for(int i = 0; i < sin.length; i++) {   
					    %>
					    <tr>
					    <td><%= a+i %></td>
					    <td><%= sin[i] %></td>
					    <td><%= cos[i] %></td>
					   </tr>
					   <%
					}
				%>
			</tbody>
		</table>
	</body>
</html>
<%@ page session="true" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
	Double[] sinA = (Double[]) session.getAttribute("sinA");	
	Double[] cosA = (Double[]) session.getAttribute("cosA");
	Integer a = (Integer) session.getAttribute("a");
%>
  
<html>
   	<table border="1">
		<thead>
		    <tr>
		        <th>value</th>
		        <th>sin</th>
		        <th>cos</th>
		    </tr>
		</thead>
		
		<tbody>
			<%
				String query = request.getParameter("item");
				for(int i = 0; i < sinA.length; i++) {   
				    %>
				    <tr>
				    <td><%= a+i %></td>
				    <td><%= sinA[i] %></td>
				    <td><%= cosA[i] %></td>
				   </tr>
				   <%
				}
			%>
		</tbody>
	</table>
</html>
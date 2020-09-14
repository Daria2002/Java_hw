<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	
	<%
		List<Integer> ages = (List<Integer>)request.getAttribute("ages");
		String name = (String)request.getAttribute("name");
	%>
	
	<table style="width:100%">
	  <tr>
	    <th>Name</th>
	    <th>Age</th>
	  </tr>
	  
	  <%for(int i = 0; i < ages.size(); i++) {%>
	  
		  <tr>
		    <td><%=name%></td>
		    <td><%=ages.get(i)%></td>
		  </tr>
	  
	  <%} %>
	</table>
	
	<p></p>
	<a href="../vjezba.jsp">Pocetna jr</a>
	<p></p>
	
</body>
</html>
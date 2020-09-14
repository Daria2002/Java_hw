<%@page import="java.util.Map"%>
<%@page import="hr.fer.zemris.java.p12.dao.sql.SQLDAO"%>
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
		SQLDAO sqldao = new SQLDAO();
	
		Map<String, Integer> map = sqldao.getAllUsers();
		
		%>
		
		<table style="width:100%">
			  <tr>
			    <th>Name</th>
			    <th>Age</th>
			  </tr>
		
		<%
		
		for(String key:map.keySet()) {
			%>
			
		  <tr>
		    <td><%=key%></td>
		    <td><%=map.get(key)%></td>
		  </tr>
	  
	  <%} %>
	</table>
</body>
</html>
<%@page import="hr.fer.zemris.java.p12.model.User"%>
<%@page import="java.util.Set"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
		<table border="1" cellspacing="0" class="rez">
			<thead><tr><th>Ime</th><th>Prezime</th></tr></thead>
			<tbody>
			<%
				Set<User> users = (Set<User>)request.getSession().getAttribute("users");
					
				for(User u:users) {
					%><tr><td><%=u.getFn()%></td><td><%=u.getLn() %></td></tr><%
				}
			
				%>
			</tbody>
		</table>
		<%

		User userID = (User)request.getSession().getAttribute("userID");
		%>
			<p>ime: <%=userID.getFn() %></p>
			<p>prezime: <%=userID.getLn() %></p>
</body>
</html>
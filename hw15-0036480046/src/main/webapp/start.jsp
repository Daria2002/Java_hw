<%@page import="hr.fer.zemris.java.p12.jpdao.JPDAOProvider"%>
<%@page import="hr.fer.zemris.java.tecaj_13.model.BlogUser"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>

<%
	
		if(request.getSession().getAttribute("current.user.fn") != null) {
			%>
			
			<p><%=request.getSession().getAttribute("current.user.fn")%> <%=request.getSession().getAttribute("current.user.ln") %></p>
			<%
		} else {
		%>	
			<p>not loged in</p>
		<%}
		
	%>

<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	
	<%
	
		if(request.getSession().getAttribute("current.user.id") == null) {
			%>
			<form action="main" method="post">
			<%
				String username = String.valueOf(request.getAttribute("username"));
				if(request.getAttribute("username") != null) {
					%>
					<p><%=request.getAttribute("error") %></p>
					<br>
					username<input type="text" name="username" value="<%=username%>">
					<%
				} else {
					%>
					username<input type="text" name="username">
					<%
				}
			%>
			<br>
		    password<input type="password" name="password">
		    <input type="submit" value="login">
		    <!-- <span class="error">${error}</span>   -->
			</form>
			
			<form action="servleti/register" method="post">
			    <input type="submit" value="registration">
			</form>
			
			
			<%
		} else { %>
			
			<form action="logout" method="get">
			    <input type="submit" value="logout">
			</form>
		<% }
		List<BlogUser> registredUsers = JPDAOProvider.getDao().getRegistredUsers();
	
		%>
			
			<ul>
			<%
			if(registredUsers != null) {
				for(int i = 0; i < registredUsers.size(); i++) {
			  		%>
			  		<li><a href="author/<%=registredUsers.get(i).getNick()%>"><%=registredUsers.get(i).getNick() %></a></li>
			    	<%		
			  	}
			} else {%>
				<p>no registred users</p>
				<%
			}
				
			%>
			</ul>
	
	
	
	

</body>
</html>
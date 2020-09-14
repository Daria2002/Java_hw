<%@page import="hr.fer.zemris.java.tecaj_13.model.BlogComment"%>
<%@page import="java.util.List"%>
<%@page import="hr.fer.zemris.java.tecaj_13.model.BlogEntry"%>
<%@page import="hr.fer.zemris.java.tecaj_13.model.BlogUser"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%
	
		if(request.getSession().getAttribute("current.user.fn") != null) {
			%>
			
			<p><%=request.getSession().getAttribute("current.user.fn")%> <%=request.getSession().getAttribute("current.user.ln") %></p>
			
			<form action="<%=request.getContextPath()%>/servleti/logout" method="get">
			    <input type="submit" value="logout">
			</form>
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
		
			String nick = (String)request.getSession().getAttribute("current.user.nick");
			if(nick != null && nick.equals(((String)request.getAttribute("nick")))) {
				%>
				<form action="edit/" method="post">
					<input type="submit" name = "edit" value="edit" /> <br>
				</form>
				<%
			}
		
			String title = (String)request.getAttribute("title");
			String text = (String)request.getAttribute("text");
			List<BlogComment> comments = (List<BlogComment>)request.getAttribute("comments");
			
			if(comments == null || comments.size() == 0) {
				%>
					<p>There are no comments</p>
					<br>
				<% 
			} else {%>
					<p>Comments:</p>
					<br>
			<%} %>
		
		<ul>
			<%
				for(int i = 0; i < comments.size(); i++) {
			  		%>
			  		<li><%=comments.get(i).getMessage()%> by: <%=comments.get(i).getUsersEMail()%></li>
			    	<%		
			  	}
			%>
		</ul>
		
		<form action="" method="post">
			Comment:<br><input type="text" name="comment"><br>
			
			<%if(request.getSession().getAttribute("current.user.nick") == null) { %>
				Email:<br><input type="text" name="email"><br>
			<%} %>
			
			<input type="submit" value="add comment" /> <br>
		</form>
		
		<h1><%=title%></h1>
		<p><%=text%></p>
		
</body>
</html>
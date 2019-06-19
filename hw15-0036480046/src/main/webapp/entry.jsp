<%@page import="hr.fer.zemris.java.tecaj_13.model.BlogComment"%>
<%@page import="java.util.List"%>
<%@page import="hr.fer.zemris.java.tecaj_13.model.BlogEntry"%>
<%@page import="hr.fer.zemris.java.tecaj_13.model.BlogUser"%>
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
		
			String nick = (String)request.getSession().getAttribute("current.user.nick");
			if(nick != null && nick.equals(((String)request.getAttribute("nick")))) {
				%>
				<form action="/servleti/<%=request.getAttribute("nick")%>/edit" method="post">
					<input type="submit" value="edit entry" /> <br>
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
			
			<%if(request.getSession().getAttribute("current.user.nick") != null) { %>
				Email:<br><input type="text" name="email"><br>
			<%} %>
			
			<input type="submit" value="add comment" /> <br>
		</form>
		
		<h1><%=title%></h1>
		<p><%=text%></p>
		
</body>
</html>
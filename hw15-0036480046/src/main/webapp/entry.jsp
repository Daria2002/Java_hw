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
			if(request.getAttribute("current.user.nick").equals((
					(BlogUser)request.getAttribute("nick")))) {
				%>
				<form action="/servleti/<%=request.getAttribute("current.user.nick")%>/edit" method="post">
					<input type="submit" value="edit entry" /> <br>
				</form>
				<%
			}
		
			String title = (String)request.getAttribute("title");
			String text = (String)request.getAttribute("text");
			List<BlogComment> comments = (List<BlogComment>)request.getAttribute("comments");
		%>
		
		<ul>
			<%
				for(int i = 0; i < comments.size(); i++) {
			  		%>
			  		<li><%=comments.get(i).getMessage()%></li>
			    	<%		
			  	}
			%>
		</ul>
		
		<form action="/servleti/<%=request.getAttribute("current.user.nick")%>/addComment" method="post">
			Comment:<br><input type="text" name="comment"><br>
			<input type="submit" value="add comment" /> <br>
		</form>
		
		<h1><%=title%></h1>
		<p><%=text%></p>
		
</body>
</html>
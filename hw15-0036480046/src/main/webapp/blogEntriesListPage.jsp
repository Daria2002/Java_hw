<%@page import="java.util.List"%>
<%@page import="hr.fer.zemris.java.tecaj_13.model.BlogEntry"%>
<%@page import="java.util.ArrayList"%>
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
		List<BlogEntry> entries = (List<BlogEntry>)request.getAttribute("nickEntries");
		String nickName = String.valueOf(request.getAttribute("nickName"));
	%>

	<%
	
		if(nickName.equals(request.getAttribute("current.user.nick"))) {
			%>
			
				<form action="/servleti/author/<%=nickName%>/new" method="post">
					<input type="submit" value="new entry" />
				</form>
			
			<%
		}
	
	%>

	<ul>
		<%
			for(int i = 0; i < entries.size(); i++) {
		  		%>
		  		<li>
		  			<a href="/servleti/author/<%=nickName%>/<%=entries.get(i).getId()%>/">
		  			<%=entries.get(i).getTitle()%></a>			
		  		</li>
		    	<%		
		  	}
		%>
	</ul>

</body>
</html>
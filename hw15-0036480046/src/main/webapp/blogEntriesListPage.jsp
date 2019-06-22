<%@page import="java.util.List"%>
<%@page import="hr.fer.zemris.java.tecaj_13.model.BlogEntry"%>
<%@page import="java.util.ArrayList"%>
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
		List<BlogEntry> entries = (List<BlogEntry>)request.getAttribute("nickEntries");
		String nickName = String.valueOf(request.getAttribute("nickName"));
		
	%>

	<%
	
		if(nickName.equals(request.getSession().getAttribute("current.user.nick"))) {
			%>
				<form action="<%=nickName%>/new" method="post">
					<input type="submit" value="add new entry">
				</form>
			<%
		}
	
	%>

	<ul>
		<%
			for(int i = 0; i < entries.size(); i++) {
		  		%>
		  		<li>
		  			<a href="<%=nickName%>/<%=entries.get(i).getId()%>/">
		  			<%=entries.get(i).getTitle()%></a>			
		  		</li>
		    	<%		
		  	}
		%>
	</ul>

</body>
</html>
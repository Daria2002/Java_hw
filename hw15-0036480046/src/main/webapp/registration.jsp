<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>

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

<body>

	<%if(request.getAttribute("error") != null) { %>
	
		<p><%=request.getAttribute("error")%><p>
		<br>
		
	<%}%>
		<form action="register" method="post">
	    first name<input type="text" name="firstName">
	    <br>
	    last name<input type="text" name="lastName">
	    <br>
	    email<input type="text" name="email">
	    <br>
	    nick<input type="text" name="nick">
	    <br>
	    password<input type="password" name="password">
	    <br>
	    <input type="submit" value="register">
	    <!-- <span class="error">${error}</span>   -->
		</form>
	

</body>
</html>
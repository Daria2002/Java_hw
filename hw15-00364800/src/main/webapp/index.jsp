<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<form action="/servleti/main" method="post">
		<%
			String username = String.valueOf(request.getAttribute("username"));
			if(username == null) {
				%>
				<input type="text" name="username">
				<%
			} else {
				%>
				<input type="text" name="username" value="<%=username%>">
				<%
			}
		%>
	    <input type="password" name="password">
	    <input type="submit" value="login">
	    <!-- <span class="error">${error}</span>   -->
	</form>
	
	<form action="/servleti/register" method="post">
	    <input type="submit" value="registration">
	</form>

</body>
</html>
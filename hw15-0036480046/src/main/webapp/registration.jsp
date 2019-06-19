<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<form action="/servleti/register" method="post">
	    <input type="text" name="firstName">
	    <input type="text" name="lastName">
	    <input type="text" name="email">
	    <input type="text" name="nick">
	    <input type="password" name="password">
	    <input type="submit" value="register">
	    <!-- <span class="error">${error}</span>   -->
	</form>

</body>
</html>
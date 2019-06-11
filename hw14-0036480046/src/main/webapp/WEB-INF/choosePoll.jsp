<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<ol>
	<%
		Map<Long, String> map = (HashMap<Long, String>)request.getAttribute("polls");
		
		for(Long id:map.keySet()) {
			%><li value="<%=map.get(id)%>"><a href="/servleti/glasanje?pollID=<%=id%>"><%=map.get(id)%></a></li><%
		}
	%>
</ol>

</body>
</html>
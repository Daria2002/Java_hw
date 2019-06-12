<%@page import="hr.fer.zemris.java.p12.model.Poll"%>
<%@page import="java.util.List"%>
<%@page import="hr.fer.zemris.java.p12.dao.sql.SQLDAO"%>
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
		SQLDAO sqlDao = new SQLDAO();
		List<Poll> list = sqlDao.getDefinedPolls();
	
		for(int i = 0; i < list.size(); i++) {
			%><li value="<%=list.get(i).getPollId()%>"><a href="glasanje?pollID=<%=list.get(i).getPollId()%>"><%=list.get(i).getTitle()%></a></li><%
		}
	%>
</ol>

</body>
</html>
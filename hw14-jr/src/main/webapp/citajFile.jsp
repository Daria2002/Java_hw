<%@page import="java.io.InputStreamReader"%>
<%@page import="java.io.BufferedReader"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.nio.file.Path"%>
<%@page import="java.io.File"%>
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
		String f;
		if((f = (String)request.getSession().getAttribute("fileContent")) != null) {
			%> 
			
			<p><%=f%></p>
			<p>file se cita prek servleta</p>
			<%
			request.getSession().setAttribute("fileContent", null);
		} else {
	
			ClassLoader classLoader = getClass().getClassLoader();
			String fileName = "test.txt";
			File test = new File(classLoader.getResource(fileName).getFile());
			FileInputStream inputStream = new FileInputStream(test);
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
			String line;
			while((line = reader.readLine()) != null) {
				%>
				<p><%=line%></p>
				<%
			}
			%>
			<p>file se cita prek jsp</p>
			
			<%
			reader.close();
		}
	%>


</body>
</html>
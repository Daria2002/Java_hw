<%@page import="java.nio.file.Files"%>
<%@page import="java.io.File"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<p>show</p>
	<p></p>
	<a href="add">Slike</a>
	<p></p>
	<%
	
	File imagesFolder = null;
	try {
		String imagesPath = getServletContext().getRealPath("WEB-INF/images");
		imagesFolder = new File(imagesPath);

	} catch (Exception e) {
	}
	
	
	File[] filesInImages = imagesFolder.listFiles();
	
	for(int i = 0; i < filesInImages.length; i++) {
		%>
			<li value="<%=i%>"><a href="slikaa?name=<%=filesInImages[i].getName()%>"><%=filesInImages[i].getName()%></a></li>
		<%
	}
	
	File f = new File(".");
	String helper = f.getAbsolutePath();
	//System.out.println("bok"+helper);
	%>
	
</body>
</html>
<%@page import="org.apache.poi.hssf.util.HSSFColor.LIGHT_BLUE"%>
<%@page import="java.nio.file.Files"%>
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
	
		ClassLoader cl = this.getClass().getClassLoader();
		String folderName = "slike";
		
		File folder = new File(cl.getResource(folderName).getFile());
		File[] listOfFiles = folder.listFiles();
		
		byte[] imageByteArray;
		
		for(int i = 0; i < listOfFiles.length; i++) {
			String filePath = listOfFiles[i].getPath();
			%>
			<img src="servleti/slike?path=<%=filePath%>" height="100" width="100"/>	
			<%
		}
	%>

</body>
</html>
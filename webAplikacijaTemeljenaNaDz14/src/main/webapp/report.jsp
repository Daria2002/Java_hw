<%@page import="java.io.OutputStream"%>
<%@page import="java.util.Base64"%>
<%@ page session="true" %>
<%@page import="org.jfree.chart.JFreeChart"%>
<%@ page session="true" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	String myColor = (String)session.getAttribute("pickedBgCol");
 
	if (myColor == null){
		myColor = "white";
	}
%>   
 
<!DOCTYPE html>
<html>	
	<body bgcolor="<%=myColor%>">
	<h1>OS usage</h1>
	<p>Here are the results of OS usage in survey that we completed.</p>
	<img src="reportImage">
	</body>
</html>
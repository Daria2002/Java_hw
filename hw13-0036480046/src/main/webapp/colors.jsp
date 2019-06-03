<%@ page session="true" %>
<%@ page import="java.util.Date,java.util.Calendar" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
	String myColor = (String)session.getAttribute("pickedBgCol");	

	if (myColor == null){
		myColor = "white";
	}
%>
  
<html>
   <body bgcolor="<%=myColor%>">
   	 <a href="/webapp2/setcolor?color=white">WHITE</a>
   	 <a href="/webapp2/setcolor?color=red">RED</a>
   	 <a href="/webapp2/setcolor?color=green">GREEN</a>
   	 <a href="/webapp2/setcolor?color=cyan">CYAN</a>
   </body>
</html>
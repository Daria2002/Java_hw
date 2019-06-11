<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    

<%
	String myColor = (String)session.getAttribute("pickedBgCol");	

	if (myColor == null) {
		myColor = "white";
	}
%>
    
<!DOCTYPE html>
<html>
<body bgcolor="<%=myColor%>">
<h1>Glasanje za omiljeni bend:</h1>
<p>Od sljedećih bendova, koji Vam je bend najdraži? Kliknite na link kako biste
glasali!</p>

<ol>
	<%
		Map<String, String> map = new HashMap<String, String>();
	
		map = (Map<String, String>)request.getAttribute("map");
		
		
		for(String idEl:map.keySet()) {
			%><li value="<%=idEl%>"><a href="glasanje-glasaj?id=<%=idEl%>"><%=map.get(idEl)%></a></li><%
		}
	%>
</ol>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<body>
<h1>Glasanje za omiljeni bend:</h1>
<p>Od sljedećih bendova, koji Vam je bend najdraži? Kliknite na link kako biste
glasali!</p>

<ol>
	<%
		String[] ids = (String[])request.getAttribute("ids");
		String[] names = (String[])request.getAttribute("names");
		
		for(int i = 0; i < ids.length; i++) {
			%><li value="<%=ids[i]%>"><a href="glasanje-glasaj?id=<%=ids[i]%>"><%=names[i]%></a></li><%
		}
	%>
</ol>
</body>
</html>
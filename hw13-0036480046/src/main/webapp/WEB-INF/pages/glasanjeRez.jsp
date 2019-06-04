<%@page import="java.util.Map"%>
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
<head>
<style type="text/css">
table.rez td {text-align: center;}
</style>
</head>
	<body bgcolor="<%=myColor%>">
		<h1>Rezultati glasanja</h1>
		<p>Ovo su rezultati glasanja.</p>
		<table border="1" cellspacing="0" class="rez">
			<thead><tr><th>Bend</th><th>Broj glasova</th></tr></thead>
			<tbody>
			<%
				Map<String, String> mapNamesAndId = (Map<String, String>)request.getSession().getAttribute("mapIdAndNames");
				Map<String, String> mapIdAndPoints = (Map<String, String>)request.getSession().getAttribute("map");
				
				if(mapIdAndPoints != null) {
					for(String id:mapNamesAndId.keySet()) {
						if(mapIdAndPoints.get(id) != null) {
							%><tr><td><%=mapNamesAndId.get(id)%></td><td><%=mapIdAndPoints.get(id)%></td></tr><%
						}
					}
				} else if(mapNamesAndId != null) {
					for(String id:mapNamesAndId.keySet()) {
						%><tr><td><%=mapNamesAndId.get(id)%></td><td>"0"</td></tr><%
					}
				}
				
				
			%>
			</tbody>
		</table>
		<h2>Grafički prikaz rezultata</h2>
		<img alt="Pie-chart" src="/glasanje-grafika" width="400" height="400" />
		<h2>Rezultati u XLS formatu</h2>
		<p>Rezultati u XLS formatu dostupni su <a href="/glasanje-xls">ovdje</a></p>
		<h2>Razno</h2>
		<p>Primjeri pjesama pobjedničkih bendova:</p>
		<ul>
			<li><a href="https://www.youtube.com/watch?v=z9ypq6_5bsg" target="_blank">The
			Beatles</a></li>
			<li><a href="https://www.youtube.com/watch?v=H2di83WAOhU" target="_blank">The
			Platters</a></li>
		</ul>
	</body>
</html>
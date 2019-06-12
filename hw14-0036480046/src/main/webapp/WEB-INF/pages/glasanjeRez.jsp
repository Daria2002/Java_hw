<%@page import="hr.fer.zemris.java.p12.dao.sql.SQLDAO"%>
<%@page import="hr.fer.zemris.java.p12.model.Unos"%>
<%@page import="java.util.Stack"%>
<%@page import="java.util.Collections"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="java.util.Comparator"%>
<%@page import="java.util.LinkedList"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    

<%
	String myColor = (String)session.getAttribute("pickedBgCol");	

	if (myColor == null) {
		myColor = "white";
	}
%>
    
<%!

private HashMap<Long, Long> sortByValue(HashMap<Long, Long> hm) { 
    // Create a list from elements of HashMap 
    List<Map.Entry<Long, Long> > list = 
           new LinkedList<Map.Entry<Long, Long> >(hm.entrySet()); 

    // Sort the list 
    Collections.sort(list, new Comparator<Map.Entry<Long, Long> >() { 
        public int compare(Map.Entry<Long, Long> o1,  
                           Map.Entry<Long, Long> o2) { 
            return (Long.valueOf(o2.getValue())).compareTo(Long.valueOf(o1.getValue())); 
        } 
    }); 
    
    // put data from sorted list to hashmap  
    HashMap<Long, Long> temp = new LinkedHashMap<Long, Long>(); 
    for (Map.Entry<Long, Long> aa : list) { 
        temp.put(aa.getKey(), aa.getValue()); 
    } 
    return temp; 
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
				Map<Long, String> mapNamesAndId = (Map<Long, String>)request.getSession().getAttribute("mapIdAndNames");
				Map<Long, Long> mapIdAndPoints = (Map<Long, Long>)request.getSession().getAttribute("map");
				
				Map<Long, Long> sortdMap = sortByValue((HashMap)mapIdAndPoints);
				
				List<Long> bestIds = new ArrayList<Long>();
				Long bestId = Long.valueOf(1);
				
				if(mapIdAndPoints != null) {
					for(long id:sortdMap.keySet()) {
						
						if(sortdMap.get(id) >= bestId) {
							bestId = sortdMap.get(id);
							bestIds.add(id);
						}
						
						if(mapIdAndPoints.get(id) != null) {
							%><tr><td><%=mapNamesAndId.get(id)%></td><td><%=mapIdAndPoints.get(id)%></td></tr><%
						} else {
							%><tr><td><%=mapNamesAndId.get(id)%></td><td>0</td></tr><%
						}
					}
				} else if(mapNamesAndId != null) {
					for(Long id:mapNamesAndId.keySet()) {
						%><tr><td><%=mapNamesAndId.get(id)%></td><td>0</td></tr><%
					}
				}
				%>
			</tbody>
		</table>
		<h2>Grafički prikaz rezultata</h2>
		<img alt="Pie-chart" src="<%=request.getContextPath()%>/servleti/glasanje-grafika?pollID=<%=request.getSession().getAttribute("pollId")%>" width="400" height="400" />
		<h2>Rezultati u XLS formatu</h2>
		<p>Rezultati u XLS formatu dostupni su <a href="<%=request.getContextPath()%>/glasanje-xls">ovdje</a></p>
		<h2>Razno</h2>
		<p>Primjeri pjesama pobjedničkih bendova:</p>
		<ul>
			<%
				Map<Long, String> mapIdAndLinks = 
				(Map<Long, String>)request.getSession().getAttribute("mapIdAndLinks");

				for(Long id : bestIds) {
					%><li><a href="<%=mapIdAndLinks.get(id)%>" target="_blank"><%=mapNamesAndId.get(id)%></a></li><%
				}
			%>
		</ul>
	</body>
</html>
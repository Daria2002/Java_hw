<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<form action="servleti/upisDB" method="POST">
	ime:<br><input type="text" name="imeDB"><br>
	godine:<br><input type="number" name="godDB"><br>
	<input type="submit" value="Dodaj"><input type="reset" value="Reset">
	</form>

	<form action="servleti/prikazDB" method="GET">
	ime:<br><input type="text" name="imeDBShow"><br>
	<input type="submit" value="Prikazi"><input type="reset" value="Reset">
	</form>

	<p></p>
	<a href="svi.jsp">Prikaz svih korisnika</a>
	<p></p>
	
	<p></p>
	<a href="statDB.jsp">Statistika grafovi</a>
	<p></p>
	
	<p>ista stvar koja se gore radi za db, napravi za file, "remote" file</p>

	<p></p>
	<a href="citajFile.jsp">Sadrzaj filea prek jsp</a>
	<p></p>
	
	<p></p>
	<a href="servleti/citajFileServlet">Sadrzaj filea prek servleta</a>
	<p></p>
	
	<p>ovdje se nalazi link na kojem se prikazuju slike iz nekog foldera</p>
	<p></p>
	<a href="servleti/slike">Sike</a>
	<p></p>

</body>
</html>
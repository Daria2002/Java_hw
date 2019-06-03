package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GlasanjeGlasajServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Zabiljezi glas...
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
		// Napravi datoteku ako je potrebno; ažuriraj podatke koji su u njoj...
		// ...
		// ...
		// Kad je gotovo, pošalji redirect pregledniku I dalje NE generiraj odgovor
		resp.sendRedirect(req.getContextPath() + "/glasanje-rezultati");
	}
}

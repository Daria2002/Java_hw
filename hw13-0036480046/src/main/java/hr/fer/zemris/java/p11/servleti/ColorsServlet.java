package hr.fer.zemris.java.p11.servleti;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ColorsServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    String color = (String)req.getAttribute("color");
	    req.getSession().setAttribute("pickedBgCol", color);
	    req.getRequestDispatcher("/index.jsp").forward(req, resp);
	}
}

package hr.fer.zemris.java.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.p12.dao.sql.SQLDAO;

public class UpisDBServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String name = "";
		Integer age = 0;
		try {
			name = req.getParameter("imeDB");
			age = Integer.valueOf(req.getParameter("godDB"));
		} catch (Exception e) {
			System.out.println("can't add user in db table");
		}
	
		SQLDAO sqldao = new SQLDAO();
		sqldao.addUserDB(name, age);
		
		//req.setAttribute("message", o);
	    //req.getRequestDispatcher("/WEB-INF/pages/trigonometric.jsp").forward(req, resp);

		//req.getRequestDispatcher("vjezba.jsp").forward(req, resp);
	}
	
}

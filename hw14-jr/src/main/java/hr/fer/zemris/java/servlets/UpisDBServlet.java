package hr.fer.zemris.java.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.p12.dao.sql.SQLDAO;

public class UpisDBServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String name = "";
		Integer age = 0;
		try {
			name = req.getParameter("imeDB");
			age = Integer.valueOf(req.getParameter("godDB"));
		} catch (Exception e) {
			System.out.println("can't add user in db table");
		}
	
		SQLDAO sqldao = new SQLDAO();
		boolean postojao = sqldao.addUserDB(name, age);
		
		System.out.println("postojao = " + postojao);
		
		req.getSession().setAttribute("mess", 
				"user " + (postojao ? "je":"nije") +" vec postojao");
		
	    req.getRequestDispatcher("/afterLogin.jsp").forward(req, resp);

	}
	
}

package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.p12.dao.sql.SQLDAO;

public class PrikazDB extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		SQLDAO sqldao = new SQLDAO();
		String name = req.getParameter("imeDBShow");
		List<Integer> ages = sqldao.getAgesOfUsers(name);
		
		for(int i = 0; i < ages.size(); i++) {
			System.out.println(ages.get(i));
		}
		
		req.setAttribute("ages", ages);
		req.setAttribute("name", name);
		
		req.getRequestDispatcher("/prikazJednog.jsp").forward(req, resp);
	}
}

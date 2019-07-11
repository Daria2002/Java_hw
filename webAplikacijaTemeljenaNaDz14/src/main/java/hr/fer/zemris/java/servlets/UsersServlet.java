package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.p12.dao.sql.SQLDAO;
import hr.fer.zemris.java.p12.model.User;

public class UsersServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		Integer id = Integer.valueOf(req.getParameter("id"));
		
		System.out.println("trazeni id je " + id);
		
		SQLDAO sd = new SQLDAO();
		Set<User> setUser = sd.getUsers();
		
		req.getSession().setAttribute("users", setUser);
		
		User userID = sd.getUserWithId(id);
		
		System.out.println("dohvacen je korisnik sa imenom: " + userID.getFn());
		System.out.println("dohvacen je korisnik sa prezimenom: " + userID.getLn());
		
		req.getSession().setAttribute("userID", userID);
	    req.getRequestDispatcher("/usersTable.jsp").forward(req, resp);
	}
	
}

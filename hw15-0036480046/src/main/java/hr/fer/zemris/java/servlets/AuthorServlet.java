package hr.fer.zemris.java.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.p12.dao.sql.SQLDAO;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

public class AuthorServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		SQLDAO sqlDao = new SQLDAO();
		
		BlogUser blogUser = sqlDao.getBlogUser(
				req.getPathInfo().substring(1).split("/")[0]);
		
		req.setAttribute("nickEntries", 
				sqlDao.getEntries(blogUser.getNick()));
		req.setAttribute("nickName", blogUser.getNick());
		
		req.getRequestDispatcher("blogEntriesListPage.jsp");
	}
}

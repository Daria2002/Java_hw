package hr.fer.zemris.java.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.p12.dao.sql.SQLDAO;
import hr.fer.zemris.java.p12.model.Unos;

public class PollOptionsServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Long pollId = Long.valueOf(req.getParameter("pollID"));
		SQLDAO sqlDao = new SQLDAO();
		Unos unos = sqlDao.getEntry(pollId);
		
		int votes = unos.getVotes();
		unos.setVotes(votes++);
	}
}

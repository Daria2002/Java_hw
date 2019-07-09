package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.p12.dao.sql.SQLDAO;
import hr.fer.zemris.java.p12.model.Poll;
import hr.fer.zemris.java.p12.model.Unos;

/**
 * This class represents servlet that sets attribute with map where key is id, and 
 * value is bend name
 * @author Daria Matković
 *
 */
public class GlasanjeServlet extends HttpServlet {

	/**
	 * default serial version id
	 */
	private static final long serialVersionUID = 1L;
	private SQLDAO sqlDao = new SQLDAO();
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//super.doPost(req, resp);

		String firstName = req.getParameter("firstName");
		String lastName = req.getParameter("lastName");
		
		sqlDao.addUserInTable(firstName, lastName);
		
		req.getRequestDispatcher("/choosePoll.jsp").forward(req, resp);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Long pollId = null;
		try {
			pollId = Long.valueOf(req.getParameter("pollID"));
		} catch (Exception e) {
			doPost(req, resp);
		}
		List<Unos> entries = sqlDao.getOptions(pollId);
		
		Map<String, String> entriesIdAndName = new HashMap<String, String>();
		
		for(int i = 0; i < entries.size(); i++) {
			entriesIdAndName.put(String.valueOf(entries.get(i).getId()), entries.get(i).getTitle());
		}
		
		Poll poll = sqlDao.getPoll(pollId);
		
		req.setAttribute("map", entriesIdAndName);
		req.setAttribute("title", poll.getTitle());
		req.setAttribute("desc", poll.getMessage());
		
		// Pošalji ih JSP-u...
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp")
		.forward(req, resp);
	}
}

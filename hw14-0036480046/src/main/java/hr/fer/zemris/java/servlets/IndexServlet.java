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

public class IndexServlet extends HttpServlet {

	/**
	 * default serial version id
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		SQLDAO sqlDao = new SQLDAO();
		List<Poll> polls = sqlDao.getDefinedPolls();
		
		Map<Long, String> pollsMap = new HashMap<Long, String>();
		
		for(Poll poll:polls) {
			pollsMap.put(poll.getPollId(), poll.getTitle());
		}
		
		req.getSession().setAttribute("polls", pollsMap);
		
	    req.getRequestDispatcher("/choosePoll.jsp").forward(req, resp);
	}
}

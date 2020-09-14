package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import hr.fer.zemris.java.p12.dao.sql.SQLDAO;
import hr.fer.zemris.java.p12.model.Unos;
import hr.fer.zemris.java.p12.model.User;

/**
 * This class represents servlet that is called after voting. It refreshes data in
 * glasanje-rezultati file. This class sets attributes with data about name, data and ids.
 * @author Daria Matković
 *
 */
public class GlasanjeGlasajServlet extends HttpServlet {

	/**
	 * default serial version id
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		SQLDAO sqlDao = new SQLDAO();
		
		long id = Long.valueOf(req.getParameter("id"));
		long pollId = (long) sqlDao.getEntry(id).getPollId();
		
		sqlDao.increaseVotes(id);
		
		List<Unos> entries = sqlDao.getOptions(pollId);
		// map with key:id, value:votes
		Map<Long, Long> map = new HashMap<Long, Long>();
		
		for(int i = 0; i < entries.size(); i++) {
			map.put(entries.get(i).getId(), entries.get(i).getVotes());
		}
		
		req.getSession().setAttribute("map", map);

		req.getSession().setAttribute("pollId", pollId);
		
		// map with key:id, value:names
		Map<Long, String> mapIdAndNames = new HashMap<Long, String>();
		// map with key:id, value:links
		Map<Long, String> mapIdAndLinks = new HashMap<Long, String>();
		
		for(int i = 0; i < entries.size(); i++) {
			mapIdAndNames.put(entries.get(i).getId(), entries.get(i).getTitle());
			mapIdAndLinks.put(entries.get(i).getId(), entries.get(i).getDesc());
		}
		
		req.getSession().setAttribute("mapIdAndNames", mapIdAndNames);
		req.getSession().setAttribute("mapIdAndLinks", mapIdAndLinks);
		
		Set<User> users = sqlDao.getUsers();
		req.getSession().setAttribute("users", users);
		
		// Kad je gotovo, pošalji redirect pregledniku I dalje NE generiraj odgovor
		resp.sendRedirect(req.getContextPath() + "/servleti/glasanje-rezultati");
	}
}

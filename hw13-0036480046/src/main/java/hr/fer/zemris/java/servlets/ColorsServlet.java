package hr.fer.zemris.java.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class represents colors servlet that is used for setting selected color.
 * @author Daria Matković
 *
 */
public class ColorsServlet extends HttpServlet {

	/**
	 * default serial version id
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	    String color = (String)req.getParameter("color");
	    req.getSession().setAttribute("pickedBgCol", color);
	    req.getRequestDispatcher("/index.jsp").forward(req, resp);
	}
}

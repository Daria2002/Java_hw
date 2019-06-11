package hr.fer.zemris.java.servleti;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class represents trigonometric servlet for calculation sin and cos for
 * given range
 * @author Daria Matković
 *
 */
public class TrigonometricServlet extends HttpServlet {

	/**
	 * default serial version id
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
    	Integer a;
    	Integer b;
    	
    	try {
    		a = Integer.valueOf(req.getParameter("a"));
		} catch (Exception e) {
			a = 0;
		}
    	
    	try {
    		b = Integer.valueOf(req.getParameter("b"));
		} catch (Exception e) {
			b = 360;
		}
    	
    	if(a > b) {
    		int help = a;
    		a = b;
    		b = help;
    	}
    	
    	if(b > a + 720) {
    		b = a + 720;
    	}
    	
    	Double[] sin = new Double[b-a+1];
    	Double[] cos = new Double[b-a+1];
    	
    	for(int i = a; i <= b; i++) {
    		sin[i-a] = Math.sin(i*180/(Math.PI));
    		cos[i-a] = Math.cos(i*180/(Math.PI));
    	}
    	
	    req.getSession().setAttribute("sin", sin);
	    req.getSession().setAttribute("cos", cos);
	    req.getSession().setAttribute("a", a);
	    
	    req.getRequestDispatcher("/WEB-INF/pages/trigonometric.jsp").forward(req, resp);
	}
}

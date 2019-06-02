package hr.fer.zemris.java.p11.servleti;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TrigonometricServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	    String table = (String)req.getParameter("table");
	    if(table != null) {
	    	Integer a = Integer.valueOf(req.getParameter("a"));
	    	Integer b = Integer.valueOf(req.getParameter("b"));
	    	
	    	Double[] sin = new Double[b-a+1];
	    	Double[] cos = new Double[b-a+1];
	    	
	    	for(int i = a; i <= b; i++) {
	    		sin[i-a] = Math.sin(i);
	    		cos[i-a] = Math.cos(i);
	    	}
	    	
		    req.getSession().setAttribute("sin", sin);
		    req.getSession().setAttribute("cos", cos);
		    req.getSession().setAttribute("a", a);
		    
		    req.getRequestDispatcher("/pages/trigonometric.jsp").forward(req, resp);
	    }
	}
}

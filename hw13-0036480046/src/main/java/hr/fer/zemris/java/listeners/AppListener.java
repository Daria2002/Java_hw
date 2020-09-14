package hr.fer.zemris.java.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * This class represents servlet context listener that adds information when it was called
 * into servlet context's attributes.
 * @author Daria Matković
 *
 */
public class AppListener implements ServletContextListener {
	/** start time in long **/
	public long startTime = 0;
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		startTime = System.currentTimeMillis();
		sce.getServletContext().setAttribute("startTime", startTime);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}
}

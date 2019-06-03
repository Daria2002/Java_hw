package hr.fer.zemris.java.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class AppListener implements ServletContextListener {

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

package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * This class represents hemo worker
 * @author Daria Matković
 *
 */
public class HomeWorker implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) {
		
		System.out.println(context.getPersistentParameter("bgcolor"));
		if(context.getPersistentParameter("bgcolor") != null) {
			context.setTemporaryParameter("background",context.getPersistentParameter("bgcolor"));
		} else {
			context.setTemporaryParameter("background", "7F7F7F");
		}
		
		try {
			context.getDispatcher().dispatchRequest("private/pages/home.smscr");
		} catch (Exception e) {
		}
	}
}

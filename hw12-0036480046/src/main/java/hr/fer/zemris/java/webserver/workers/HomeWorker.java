package hr.fer.zemris.java.webserver.workers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

public class HomeWorker implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) {
		
		context.setMimeType("text/html");
		
		if(context.getPersistentParameter("bgcolor") != null) {
			context.setTemporaryParameter("background", context.getPersistentParameter("bgcolor"));
		} else {
			context.setTemporaryParameter("background", "7F7F7F");
		}
		
		try {
			context.getDispatcher().dispatchRequest("private/pages/home.smscr");
		} catch (Exception e) {
		}
	}
}

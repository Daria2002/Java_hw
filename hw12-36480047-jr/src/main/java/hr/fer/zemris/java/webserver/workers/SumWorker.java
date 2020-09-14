package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * This class represents sum worker
 * @author Daria Matković
 *
 */
public class SumWorker implements IWebWorker {
	/** result **/
	private int result = 0;
	
	@Override
	public void processRequest(RequestContext context) {
		
		int a;
		int b;
		
		try {
			a = Integer.valueOf(context.getParameter("a"));
			b = Integer.valueOf(context.getParameter("b"));
		} catch (Exception e) {
			a = 1;
			b = 2;
		}
		
		result = a + b;
		
		context.setTemporaryParameter("zbroj", String.valueOf(result));
		context.setTemporaryParameter("varA", String.valueOf(a));
		context.setTemporaryParameter("varB", String.valueOf(b));
		context.setTemporaryParameter("imgName", result % 2 == 0 ? 
				"beach.jpg" : "mountain.png");
		
		try {
			context.getDispatcher().dispatchRequest("private/pages/calc.smscr");
			
		} catch(Exception ex) {
		}
	}
}

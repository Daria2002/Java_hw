package hr.fer.zemris.java.webserver.workers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * This class represents echo params, and implements IWebWorker interface
 * @author Daria Matković
 *
 */
public class EchoParams implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) {
		context.setMimeType("text/html");
		
		try {
			context.write("<html><body>");
			context.write("<h1>Hello!!!</h1>");
			
			StringBuilder sb = new StringBuilder();
			
			sb.append("<html>\n" + 
					"\n" + 
					"<body>\n" + 
					"<table>\n");
			
			for(String paramName : context.getParameterNames()) {
				sb.append("<tr>\n");
				
				sb.append("<td>" + paramName + "</td>\n");
				sb.append("<td>" + context.getParameter(paramName) + "</td>\n");
				
				sb.append("</tr>\n");
			}
			
			sb.append("</table>\n" + 
					"</body>\n" + 
					"\n" + 
					"</html>\n");
			
			context.write(sb.toString());
			
		} catch(IOException ex) {
		}
	}
}

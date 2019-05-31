package hr.fer.zemris.java.webserver.workers;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * This class represents hello worker
 * @author Daria Matković
 *
 */
public class HelloWorker implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		
		try {
			
			StringBuilder sb = new StringBuilder();
			String name = context.getParameter("name");
			sb.append("<html><body>");
			sb.append("<h1>Hello!!!</h1>");
			sb.append("<p>Now is: "+sdf.format(now)+"</p>");
			
			if(name==null || name.trim().isEmpty()) {
				sb.append("<p>You did not send me your name!</p>");
			} else {
				sb.append("<p>Your name has "+name.trim().length()
				+" letters.</p>");
			}
			
			sb.append("</body></html>");
			
			context.write(
					("HTTP/1.1 200 OK\r\n"+
					"Server: simple java server\r\n"+
					"Content-Type: "+ "text/html"+ "\r\n"+
					"Content-Length: "+ sb.length()+"\r\n"+
					"Connection: close\r\n"+
					"\r\n").getBytes(StandardCharsets.US_ASCII)
				);
			
			context.write(sb.toString());
			
		} catch(IOException ex) {
		}
	}
}

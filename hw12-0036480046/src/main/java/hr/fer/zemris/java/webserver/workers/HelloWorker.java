package hr.fer.zemris.java.webserver.workers;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

public class HelloWorker implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		/*
		context.setMimeType("text/html");
		context.setStatusCode(200);
		context.setStatusText("OK");
		String name = context.getParameter("name");
		
		try {
			context.write("<html><body>");
			context.write("<h1>Hello!!!</h1>");
			context.write("<p>Now is: "+sdf.format(now)+"</p>");
			
			if(name==null || name.trim().isEmpty()) {
				context.write("<p>You did not send me your name!</p>");
			} else {
				context.write("<p>Your name has "+name.trim().length()
				+" letters.</p>");
			}
			
			context.write("</body></html>");
			*/
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
		// Log exception to servers log...
		ex.printStackTrace();
		}
	}
}

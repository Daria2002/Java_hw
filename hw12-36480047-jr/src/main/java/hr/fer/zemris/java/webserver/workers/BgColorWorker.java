package hr.fer.zemris.java.webserver.workers;

import java.nio.charset.StandardCharsets;
import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * This class represents background color worker
 * @author Daria Matković
 *
 */
public class BgColorWorker implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) throws Exception {
		String value = context.getParameter("bgcolor");
		
		StringBuilder sb = new StringBuilder();
		
		if(value != null) {
			sb.append("<html><head><title>set</title></head><body>");
			
			if(value.length() != 6 || !hexDigits(value)) {
				try {
					//context.write("Color is not updated");
					sb.append("Color is not updated");
					sb.append("not changed");
				} catch (Exception e) {
				}
			}
			else {
				context.setPersistentParameter("bgcolor", value);
				sb.append("color is updated");
			}
		}
		sb.append("<br><a href=\"/index2.html\">return</a></body></html>");
		
		context.write(
				("HTTP/1.1 200 OK\r\n"+
				"Server: simple java server\r\n"+
				"Content-Type: "+ "text/html"+ "\r\n"+
				"Content-Length: "+ sb.length()+"\r\n"+
				"Connection: close\r\n"+
				"\r\n").getBytes(StandardCharsets.US_ASCII)
			);
		
		context.write(sb.toString());
	}

	/**
	 * Checks if value contains hex digits
	 * @param value value to check
	 * @return true if given value contains hex values, otherwise false
	 */
	private boolean hexDigits(String value) {
		return value.matches("^[0-9a-fA-F]+$");
	}
}

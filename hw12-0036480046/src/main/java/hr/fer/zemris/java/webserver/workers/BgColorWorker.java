package hr.fer.zemris.java.webserver.workers;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

public class BgColorWorker implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) throws Exception {
		String value = context.getParameter("bgcolor");
		
		if(value != null) {
			
			if(value.length() != 6 && !hexDigits(value)) {
				try {
					context.getDispatcher().dispatchRequest("private/pages/home.smscr");
					context.write("Color is not updated");
				} catch (Exception e) {
				}
			}
			
			context.setPersistentParameter("bgcolor", value);
			context.getDispatcher().dispatchRequest("index2.html");
		}
	}

	private boolean hexDigits(String value) {
		return value.matches("^[0-9a-fA-F]+$");
	}
}

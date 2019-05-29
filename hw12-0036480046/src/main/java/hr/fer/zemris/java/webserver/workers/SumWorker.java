package hr.fer.zemris.java.webserver.workers;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import javax.imageio.ImageIO;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

public class SumWorker implements IWebWorker {

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
			context.write("Zbrajanje\n");

			context.getDispatcher().dispatchRequest("private/pages/calc.smscr");

			BufferedImage img = ImageIO.read(
					new File(System.getProperty("user.dir") + "/webroot/images/" +
							context.getTemporaryParameter("imgName")));
			
			String imgsrc = "images/" + context.getTemporaryParameter("imgName");
			context.write(String.format("<img src=\"../../" + imgsrc + "\"/>"));
			
		} catch(Exception ex) {
		// Log exception to servers log...
			ex.printStackTrace();
		}
	}
}

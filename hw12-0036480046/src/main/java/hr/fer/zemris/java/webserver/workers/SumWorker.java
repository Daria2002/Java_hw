package hr.fer.zemris.java.webserver.workers;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import javax.imageio.ImageIO;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

public class SumWorker implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) {
		
		context.setMimeType("text/html");
		
		int a;
		int b;
		
		try {
			a = Integer.valueOf(context.getParameter("a"));
			b = Integer.valueOf(context.getParameter("b"));
		} catch (Exception e) {
			a = 1;
			b = 2;
		}
		
		int result = a + b;
		
		context.setTemporaryParameter("zbroj", String.valueOf(result));
		context.setTemporaryParameter("varA", String.valueOf(a));
		context.setTemporaryParameter("varB", String.valueOf(b));
		context.setTemporaryParameter("imgName", result % 2 == 0 ? 
				"beach.jpg" : "mountain.png");
		try {
			context.write("Zbrajanje\n");
			
			context.getDispatcher().dispatchRequest("/private/pages/calc.smscr");
		
			BufferedImage in = ImageIO.read(
					new File("/home/daria/eclipse-workspace/my-hw/hw12-0036480046/webroot/images/" + 
			context.getTemporaryParameter("imgName")));
			
			BufferedImage bim = new BufferedImage(
			    in.getWidth(), in.getHeight(), BufferedImage.TYPE_INT_ARGB);

			Graphics2D g = bim.createGraphics();
			g.drawImage(in, 0, 0, null);
			g.dispose();
			
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			try {
				ImageIO.write(bim, "png", bos);
				context.setMimeType("image/png");
				context.write(bos.toByteArray());
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		} catch(Exception ex) {
		// Log exception to servers log...
		ex.printStackTrace();
		}
	}
}

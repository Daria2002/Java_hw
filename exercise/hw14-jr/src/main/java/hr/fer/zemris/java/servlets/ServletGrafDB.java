package hr.fer.zemris.java.servlets;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import hr.fer.zemris.java.p12.dao.sql.SQLDAO;

public class ServletGrafDB extends HttpServlet {

	/** arrow size **/
	private static final int ARR_SIZE = 5;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
        int width = 600;
		int height = 500;
		
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = image.createGraphics();
		
		g2d.setColor(Color.GRAY);
		g2d.fillRect(0, 0, width, height);
		
		// kod ovog treba paziti jer je (0, 0) u gornjem lijevom kutu
		
		// nacrtaj x os :)
		g2d.setColor(Color.RED);
		g2d.drawLine(10, 490, 590, 490);
		
		// nacrtaj y os :)
		g2d.setColor(Color.RED);
		g2d.drawLine(10, 10, 10, 490);
		
		
		SQLDAO sqldao = new SQLDAO();
		Map<String, Integer> map = sqldao.getAllUsers();
		
		Integer barW = (int)Math.floor((width-11-map.size()*2)/map.size());
		
		g2d.setColor(Color.GREEN);
		
		// za svakog korisnika nacrtaj jedan rect
		int first = 11;
		for(String name : map.keySet()) {
			g2d.fillRect(first, 490-map.get(name), barW, map.get(name));
			first += barW + 2;
		}
		
		g2d.dispose();
		
		resp.setContentType("image/jpg");
		OutputStream os = resp.getOutputStream();
		ImageIO.write(image, "jpg", os);
//        
//		ByteArrayOutputStream bos = new ByteArrayOutputStream();
//		os.write(bos.toByteArray());
//		os.close();
		
//		req.setAttribute("servleti/mojGraf", bos.toByteArray());
	}
}

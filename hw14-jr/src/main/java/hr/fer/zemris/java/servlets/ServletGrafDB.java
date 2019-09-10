package hr.fer.zemris.java.servlets;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;

import hr.fer.zemris.java.p12.dao.sql.SQLDAO;

public class ServletGrafDB extends HttpServlet {

	/** arrow size **/
	private static final int ARR_SIZE = 5;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("image/png");

		// graphics2d to image
		// image to bytearrayoutputstream
		
//		JFrame frame = makeFrameWithDiagram();
		
		JPanel content = new JPanel();
        content.setLayout(new BorderLayout());
        content.add(new JLabel("Ovo je graf korisnika", SwingConstants.CENTER), BorderLayout.NORTH);
        
        String xDescription = "naziv korisnika";
        String yDescription = "godine korisnika";
        
        Integer yMin = 0;
        
        SQLDAO sd = new SQLDAO();
        Map<String, Integer> map = sd.getAllUsers(); 
        
        Integer yMax = 0;
        for(String name:map.keySet()) {
        	if(map.get(name) > yMax) {
        		yMax = map.get(name);
        	}
        }
        
        Integer space = map.size();
        
        List<XYValue> result = new ArrayList<ServletGrafDB.XYValue>();
        
        List<String> names = new ArrayList<String>();
        
        int k = 0;
        for(String name:map.keySet()) {
        	names.add(name);
        	k++;
        	result.add(new XYValue(k, map.get(name)));
        }
        
        content.add(new BarChartComponent(new BarChart(result, names,
        		xDescription, yDescription, yMin, yMax, space)), BorderLayout.CENTER);
        
        BarChartDemo bcd = new BarChartDemo();
		bcd.setLayout(new BoxLayout(bcd.getContentPane(), BoxLayout.Y_AXIS));
        bcd.setContentPane(content);
        
//		SwingUtilities.invokeLater(() -> {bcd.setVisible(true);});
        
        int width = 600;
		int height = 500;
		List<Integer> data = new ArrayList<>();
		data.add(1);
		data.add(4);
		data.add(6);
		int sum = 3;
		
        System.out.println(data);
		int barWidth = width / data.size();
		
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = image.createGraphics();
		
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, width, height);
		
		for (int i = 0; i < data.size(); i++) {
			int R = (int)(Math.random()*256);
			int G = (int)(Math.random()*256);
			int B= (int)(Math.random()*256);
			Color color = new Color(R, G, B);
			
			double barHeight = (double)data.get(i) / sum * height;
			int x = i * barWidth;
			int y = (int) (height - barHeight);
			
			g2d.setColor(color);
			g2d.fillRect(x, y, barWidth, height);
			System.out.println(String.format("%s %s %s %s", x, y, barWidth, barHeight));
		}
		
		g2d.dispose();
		
		resp.setContentType("image/jpg");
		OutputStream os = resp.getOutputStream();
		ImageIO.write(image, "jpg", os);
        
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		os.write(bos.toByteArray());
		os.close();
		
		req.setAttribute("servleti/mojGraf", bos.toByteArray());
        
//		
//		ByteArrayOutputStream bos = new ByteArrayOutputStream();
//		ChartUtilities.writeChartAsPNG(bos, bcd, 500, 270);
//		OutputStream os = new BufferedOutputStream(resp.getOutputStream());
//		os.write(bos.toByteArray());
//		os.close();
//		
//		req.setAttribute("servleti/grafDB", bos.toByteArray());
	}
	
	// ovo je prozor grafa
	private class BarChartDemo extends JFrame {
		
		public BarChartDemo() {
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			setSize(500, 500);
		}
	}
	
	// ovdje se detaljno crta graf
	private class BarChartComponent extends JComponent {
		
		BarChart barChart;
		
		public BarChartComponent(BarChart bc) {
			this.barChart = bc;
		}
		
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			int width = getWidth();
			int height = getHeight();
			
			int yPadding = 40 + g.getFontMetrics().stringWidth(String.valueOf(
					barChart.getyMax()));
			
			Graphics2D g2D = (Graphics2D)g;
			
			g2D.setPaint(Color.GRAY);
			// x axis
			g2D.drawLine(width - yPadding, height - 50-5-getY(), yPadding, height - 50-5-getY());
			// y axis
			g2D.drawLine(yPadding+5, height-50-getY(), yPadding+5, getY());
			
			g2D.setPaint(Color.BLACK);
			// x description
			g2D.drawString(barChart.getxDescription(), 
					(width - g.getFontMetrics().stringWidth(String.valueOf(barChart.getxDescription())))/2, height-10);
			
			// y description
			AffineTransform at = new AffineTransform();
			at.rotate(-Math.PI / 2);
			g2D.setTransform(at);
			g2D.drawString(barChart.getyDescription(), 
					-(height + g.getFontMetrics().stringWidth(String.valueOf(barChart.getyDescription())))/2, 20);
			at.rotate(Math.PI / 2);
			g2D.setTransform(at);
			
			g2D.setPaint(Color.ORANGE);
			int columnWidth = (width - yPadding - yPadding-5) / barChart.getValues().size();
			// draw lines for columns
			for(int i = 0; i < barChart.getValues().size(); i++) {
				g2D.drawLine(yPadding + 5 + (i+1)*columnWidth, height - 50,
						yPadding + 5 + (i+1)*columnWidth, 50);
			}
			
			int rowsHeight = (height - 50 - 50) / (barChart.getyMax() - barChart.getyMin());
			// draw lines for rows
			for(int i = barChart.getyMin(); i <= barChart.getyMax(); i = i + barChart.getSpace()) {
				g2D.drawLine(width - yPadding, height - 50-5 - i * rowsHeight,
						yPadding, height - 50-5 - i * rowsHeight);
			}
			
			g2D.setPaint(Color.BLACK);
			
			// write numbers for x axis
			for(int i = 0; i < barChart.getValues().size(); i++) {	
				g2D.drawString(barChart.namesForY.get(i), 
						yPadding + 5 + i*columnWidth,
						height-30);
			}
			
			Font font = g2D.getFont();
			FontRenderContext frc = g2D.getFontRenderContext();
			LineMetrics lineMetrics = font.getLineMetrics("0", frc);
			
			float letterHeight = lineMetrics.getHeight();
			

			int spaceLength = String.valueOf(barChart.getyMax()).length();
			// write numbers for y axis
			for(int i = barChart.getyMin(); i <= barChart.getyMax(); i = i + barChart.getSpace()) {
				String help = String.format("%" + spaceLength + "d", i);
				g2D.drawString(help, yPadding-g2D.getFontMetrics().stringWidth(help),
						height - 50 + 5 - (i-barChart.getyMin()) * rowsHeight-letterHeight/2);
			}
			
			g2D.setColor(Color.ORANGE);
			
			for(XYValue xy : barChart.getValues()) {
				int x = yPadding + 5 + (xy.getX()-1)*columnWidth;
				int y = height - 50-5 - xy.getY() * rowsHeight;
				
				g2D.fillRect(x, y, columnWidth-1, rowsHeight * xy.getY());
			}
			
			g2D.setColor(Color.GRAY);
			
			drawArrow(g2D, yPadding+5, 50-getY(), yPadding+5, 45-getY());
			drawArrow(g2D, width - yPadding, height - 50-getY()+9, width - yPadding+5, height - 50-getY()+9);
		}
		
		/**
		 * This method is used for drawing arrow
		 * @param g1 graphics
		 * @param x1 start x value
		 * @param y1 start y value
		 * @param x2 stop x value
		 * @param y2 stop y value
		 */
		private void drawArrow(Graphics g1, int x1, int y1, int x2, int y2) {
	        Graphics2D g = (Graphics2D) g1.create();

	        double dx = x2 - x1, dy = y2 - y1;
	        double angle = Math.atan2(dy, dx);
	        int len = (int) Math.sqrt(dx*dx + dy*dy);
	        AffineTransform at = AffineTransform.getTranslateInstance(x1, y1);
	        at.concatenate(AffineTransform.getRotateInstance(angle));
	        g.transform(at);

	        // Draw horizontal arrow starting in (0, 0)
	        g.drawLine(0, 0, len, 0);
	        g.fillPolygon(new int[] {len, len-ARR_SIZE, len-ARR_SIZE, len},
	                      new int[] {0, -ARR_SIZE, ARR_SIZE, 0}, 4);
	    }
		
	}
	
	// ovo je bar chart razred koji samo sadrži vrijednosti bitne za graf
	private class BarChart {

		private final List<String> namesForY;
		
		/** list of values to show on bar chart **/
		private final List<XYValue> values;
		/** x description **/
		private final String xDescription;
		/** y description **/
		private final String yDescription;
		/** y min **/
		private final int yMin;
		/** y max **/
		private final int yMax;
		/** space between two y values **/
		private final int space;
		
		public List<String> getNamesForY() {
			return namesForY;
		}
		
		/**
		 * Gets values
		 * @return returns list of XYValue objects
		 */
		public List<XYValue> getValues() {
			return values;
		}

		/**
		 * Gets description for x axis
		 * @return returns description for x axis
		 */
		public String getxDescription() {
			return xDescription;
		}

		/**
		 * Gets description for y axis
		 * @return returns description for y axis
		 */
		public String getyDescription() {
			return yDescription;
		}

		/**
		 * Gets minimum value on y axis
		 * @return returns minimum value on y axis
		 */
		public int getyMin() {
			return yMin;
		}

		/**
		 * Gets maximum value on y axis
		 * @return returns maximum value on y axis
		 */
		public int getyMax() {
			return yMax;
		}

		/**
		 * Gets space
		 * @return returns space
		 */
		public int getSpace() {
			return space;
		}

		/** 
		 * This method represents constructor for bar chart that initialize variables.
		 * @param values list of values to show on bar chart
		 * @param xDescription x description
		 * @param yDescription y description
		 * @param yMin y min
		 * @param yMax y max 
		 * @param space space between two y values
		 */
		public BarChart(List<XYValue> values, List<String> names, String xDescription, 
				String yDescription, int yMin, int yMax, int space) {
			
			if(yMin < 0 || yMax < yMin) {
				throw new IllegalArgumentException("y range is not ok.");
			}
			
			if((yMax - yMin) % space != 0) {
				int help = (yMax - yMin + space) / space;
				yMax = space * help;
			}
			
			this.namesForY = names;
			this.values = values;
			this.xDescription = xDescription;
			this.yDescription = yDescription;
			this.yMin = yMin;
			this.yMax = yMax;
			this.space = space;
		}
	}
	
	// ovo sam skuzila ovo je razred koji predstavlja vrijednost na osi
	private class XYValue {
		/** value on x axis **/
		private final int x;
		/** value on y axis **/
		private final int y;
		
		/**
		 * This method represents constructor for x and y
		 * @param x value to show on x axis
		 * @param y value to show on y axis
		 */
		public XYValue(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		/**
		 * Gets x
		 * @return x value
		 */
		public int getX() {
			return x;
		}
		
		/**
		 * Gets y 
		 * @return y value
		 */
		public int getY() {
			return y;
		}
		
	}

}

package hr.fer.zemris.java.gui.charts;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.geom.AffineTransform;

import javax.swing.JComponent;

/**
 * This class represents bar chart component that implements interface JCompoenent.
 * @author Daria Matković
 *
 */
public class BarChartComponent extends JComponent {
	/**
	 * default serial version
	 */
	private static final long serialVersionUID = 1L;
	/** bar chart **/
	BarChart barChart;
	/** arrow size **/
	private static final int ARR_SIZE = 5;
	
	/**
	 * This method represents bar char component that initializes bar chart
	 * @param barChart bar chart
	 */
	public BarChartComponent(BarChart barChart) {
		this.barChart = barChart;
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
			g2D.drawString(String.valueOf(barChart.getValues().get(i).getX()),
					yPadding + 5 + i*columnWidth + columnWidth/2,
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

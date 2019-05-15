package hr.fer.zemris.java.gui.charts;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.geom.AffineTransform;

import javax.swing.JComponent;

public class BarChartComponent extends JComponent {

	BarChart barChart;
	private static final int ARR_SIZE = 5;
	
	public BarChartComponent(BarChart barChart) {
		this.barChart = barChart;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		int width = getWidth();
		int height = getHeight();
		
		int yPadding = 40 + g.getFontMetrics().stringWidth(String.valueOf(
				barChart.yMax));
		int xPadding = 40;
		
		Graphics2D g2D = (Graphics2D)g;
		
		g2D.setPaint(Color.GRAY);
		// x axis
		g2D.drawLine(width - yPadding, height - 50-5-getY(), yPadding, height - 50-5-getY());
		// y axis
		g2D.drawLine(yPadding+5, height-50-getY(), yPadding+5, getY());
		
		g2D.setPaint(Color.BLACK);
		// x description
		g2D.drawString(barChart.xDescription, 
				(width - g.getFontMetrics().stringWidth(String.valueOf(barChart.xDescription)))/2, height-10);
		
		// y description
		AffineTransform at = new AffineTransform();
		at.rotate(-Math.PI / 2);
		g2D.setTransform(at);
		g2D.drawString(barChart.yDescription, 
				-(height + g.getFontMetrics().stringWidth(String.valueOf(barChart.yDescription)))/2, 20);
		at.rotate(Math.PI / 2);
		g2D.setTransform(at);
		
		g2D.setPaint(Color.ORANGE);
		int columnWidth = (width - yPadding - yPadding-5) / barChart.values.size();
		// draw lines for columns
		for(int i = 0; i < barChart.values.size(); i++) {
			g2D.drawLine(yPadding + 5 + (i+1)*columnWidth, height - 50,
					yPadding + 5 + (i+1)*columnWidth, 50);
		}
		
		int rowsHeight = (height - 50 - 50) / (barChart.yMax - barChart.yMin);
		// draw lines for rows
		for(int i = barChart.yMin; i <= barChart.yMax; i = i + barChart.space) {
			g2D.drawLine(width - yPadding, height - 50-5 - i * rowsHeight, yPadding, height - 50-5 - i * rowsHeight);
		}
		
		g2D.setPaint(Color.BLACK);
		
		// write numbers for x axis
		for(int i = 0; i < barChart.values.size(); i++) {
			g2D.drawString(String.valueOf(barChart.values.get(i).getX()),
					yPadding + 5 + i*columnWidth + columnWidth/2,
					height-30);
		}
		
		Font font = g2D.getFont();
		FontRenderContext frc = g2D.getFontRenderContext();
		LineMetrics lineMetrics = font.getLineMetrics("0", frc);
		
		float letterHeight = lineMetrics.getHeight();
		
		// write numbers for y axis
		for(int i = barChart.yMin; i <= barChart.yMax; i = i + barChart.space) {
			String help = String.format("%" + String.valueOf(barChart.yMax).length() + "d", i);
			g2D.drawString(help, yPadding - 20, height - 50 + 5 - i * rowsHeight-letterHeight/2);
		}
		
		g2D.setColor(Color.ORANGE);
		
		for(XYValue xy : barChart.values) {
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
	void drawArrow(Graphics g1, int x1, int y1, int x2, int y2) {
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

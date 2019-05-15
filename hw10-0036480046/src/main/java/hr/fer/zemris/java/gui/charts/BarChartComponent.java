package hr.fer.zemris.java.gui.charts;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import javax.swing.JComponent;

public class BarChartComponent extends JComponent {

	BarChart barChart;
	
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
		g2D.drawLine(width - yPadding, height - 50-5, yPadding, height - 50-5);
		// y axis
		g2D.drawLine(yPadding+5, height-50, yPadding+5, 50);
		
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
		
		// write numbers for y axis
		for(int i = barChart.yMin; i <= barChart.yMax; i = i + barChart.space) {
			String help = String.format("%" + String.valueOf(barChart.yMax).length() + "d", i);
			g2D.drawString(help, yPadding - 20, height - 50-5 - i * rowsHeight);
		}
		
		g2D.setColor(Color.ORANGE);
		
		for(XYValue xy : barChart.values) {
			int x = yPadding + 5 + (xy.getX()-1)*columnWidth;
			int y = height - 50-5 - xy.getY() * rowsHeight;
			
			g2D.fillRect(x, y, columnWidth-1, rowsHeight * xy.getY());
		}
	}
}

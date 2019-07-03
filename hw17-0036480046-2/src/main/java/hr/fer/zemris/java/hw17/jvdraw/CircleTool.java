package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

public class CircleTool implements Tool {

	int xCenter;
	int yCenter;
	int radius;
	Color color;
	boolean centerAdded = false;
	boolean radiusAdded = false;
	DrawingModel dm;
	JDrawingCanvas canvas;
	
	public CircleTool(DrawingModel dm, JDrawingCanvas canvas, IColorProvider colorProvider) {
		this.canvas = canvas;
		this.color = colorProvider.getCurrentColor();
		this.dm = dm;
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(!centerAdded) {
			xCenter = e.getX();
			yCenter = e.getY();
			
			centerAdded = true;
			radiusAdded = false;
			return;
		}
		radius = (int) Math.sqrt(Math.pow(Math.abs(xCenter-e.getX()), 2) +
				Math.pow(Math.abs(yCenter-e.getY()), 2));
		
		dm.add(new Circle(xCenter, yCenter, radius, color));
		
		radiusAdded = true;
		centerAdded = false;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if(centerAdded && !radiusAdded) {
			radius = (int) Math.sqrt(Math.pow(Math.abs(xCenter-e.getX()), 2) +
					Math.pow(Math.abs(yCenter-e.getY()), 2));
			
			canvas.repaint();
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void paint(Graphics2D g2d) {
		if(radiusAdded && !centerAdded) {
			g2d.setColor(color);
			int d = radius*2;
			g2d.drawOval(xCenter-radius, yCenter-radius, d, d);
		}
	}

}

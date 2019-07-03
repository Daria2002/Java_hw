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
	JDrawingCanvas canvas;
	
	public CircleTool(JDrawingCanvas canvas, Color color) {
		this.canvas = canvas;
		this.color = color;
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
		} else {
			radius = (int) Math.sqrt(Math.pow(Math.abs(xCenter-e.getX()), 2) +
					Math.pow(Math.abs(yCenter-e.getY()), 2));
			
			radiusAdded = true;
			centerAdded = false;
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if(centerAdded && !radiusAdded) {
			radius = (int) Math.sqrt(Math.pow(Math.abs(xCenter-e.getX()), 2) +
					Math.pow(Math.abs(yCenter-e.getY()), 2));
			
			radiusAdded = true;
			centerAdded = false;
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
			
		}
	}

}

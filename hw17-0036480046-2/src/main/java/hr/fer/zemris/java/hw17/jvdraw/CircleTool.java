package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

public class CircleTool implements Tool {

	int xCenter;
	int yCenter;
	int radius;
	IColorProvider colorProvider;
	boolean centerAdded = false;
	boolean radiusAdded = false;
	DrawingModel dm;
	JDrawingCanvas canvas;
	
	public CircleTool(DrawingModel dm, JDrawingCanvas canvas, IColorProvider colorProvider) {
		this.dm = dm;
		this.canvas = canvas;
		this.colorProvider = colorProvider;
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
		Color c = colorProvider.getCurrentColor();
		dm.add(new Circle(xCenter, yCenter, radius, c));
		
		radiusAdded = true;
		centerAdded = false;
		canvas.repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		canvas.repaint();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void paint(Graphics2D g2d) {
	}

}

package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

/**
 * This class represents circle tool
 * @author Daria Matković
 *
 */
public class CircleTool implements Tool {
	/** x center */
	int xCenter;
	/** y center */
	int yCenter;
	/** radius */
	int radius;
	/** color provider */
	IColorProvider colorProvider;
	/** center added flag */
	boolean centerAdded = false;
	/** radius added flag */
	boolean radiusAdded = false;
	/** drawing model */
	DrawingModel dm;
	/** drawing canvas */
	JDrawingCanvas canvas;
	
	/**
	 * Constructor for circle tool
	 * @param dm drawing model
	 * @param canvas canvas
	 * @param colorProvider color provider 
	 */
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
		Circle circle = new Circle(xCenter, yCenter, radius, c);
		dm.add(circle);
		
		radiusAdded = true;
		centerAdded = false;
		canvas.repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if(centerAdded) {
			canvas.repaint();
			radius = (int) Math.sqrt(Math.pow(Math.abs(xCenter-e.getX()), 2) +
					Math.pow(Math.abs(yCenter-e.getY()), 2));
			paint((Graphics2D)canvas.getGraphics());
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void paint(Graphics2D g2d) {
		canvas.repaint();
		g2d.setColor(colorProvider.getCurrentColor());
		g2d.drawOval(xCenter-radius, yCenter-radius, radius*2, radius*2);
	}

}

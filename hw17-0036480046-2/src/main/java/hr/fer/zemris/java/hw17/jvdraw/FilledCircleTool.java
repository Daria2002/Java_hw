package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

/**
 * This class represents filled circle tool that implements tool
 * @author Daria Matković
 *
 */
public class FilledCircleTool implements Tool {
	/** outline color provider */
	IColorProvider outlineColorProvider;
	/** fill color provider */
	IColorProvider fillColorProvider;
	/** drawing model */
	DrawingModel dm;
	/** canvas */
	JDrawingCanvas canvas;
	/** center x */
	int centerX;
	/** center y */
	int centerY;
	/** radius */
	int radius;
	/** radius added flag */
	boolean radiusAdded = false;
	/** center added flag */
	boolean centerAdded = false;
	
	/**
	 * Filled circle tool constructor
	 * @param outlineColorProvider outline color provider
	 * @param fillColorProvider fill color provider
	 * @param c canvas
	 * @param dm drawing model
	 */
	public FilledCircleTool(IColorProvider outlineColorProvider,
			IColorProvider fillColorProvider,
			JDrawingCanvas c, DrawingModel dm) {
		this.canvas = c;
		this.outlineColorProvider = outlineColorProvider;
		this.fillColorProvider = fillColorProvider;
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
			centerX = e.getX();
			centerY = e.getY();
			centerAdded = true;
			radiusAdded = false;
			return;
		}
		radius = (int) Math.sqrt(Math.pow(centerX-e.getX(), 2) + 
				Math.pow(centerY-e.getY(), 2));
		
		centerAdded = false;
		radiusAdded = true;
		dm.add(new FilledCircle(centerX, centerY, radius,
				outlineColorProvider.getCurrentColor(), fillColorProvider.getCurrentColor()));
		canvas.repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if(!radiusAdded && centerAdded) {
			radius = (int) Math.sqrt(Math.pow(Math.abs(centerX-e.getX()), 2) +
					Math.pow(Math.abs(centerY-e.getY()), 2));
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
		g2d.setColor(fillColorProvider.getCurrentColor());
		g2d.fillOval(centerX-radius, centerY-radius, radius*2, radius*2);
		g2d.setColor(outlineColorProvider.getCurrentColor());
		g2d.drawOval(centerX-radius, centerY-radius, radius*2, radius*2);
	}
}

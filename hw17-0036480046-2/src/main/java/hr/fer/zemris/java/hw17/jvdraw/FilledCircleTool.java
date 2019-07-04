package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

public class FilledCircleTool implements Tool {

	IColorProvider outlineColorProvider;
	IColorProvider fillColorProvider;
	DrawingModel dm;
	JDrawingCanvas canvas;
	
	int centerX;
	int centerY;
	int radius;
	
	boolean radiusAdded = false;
	boolean centerAdded = false;
	
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
		}
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

package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

public class FilledCircleTool implements Tool {

	Color outlineColor;
	Color fillCollor;
	DrawingModel dm;
	JDrawingCanvas canvas;
	
	int centerX;
	int centerY;
	int radius;
	
	boolean radiusAdded = false;
	boolean centerAdded = false;
	
	public FilledCircleTool(IColorProvider fillColorProvider,
			IColorProvider outlineColorProvider,
			JDrawingCanvas c, DrawingModel dm) {
		this.canvas = c;
		this.outlineColor = outlineColorProvider.getCurrentColor();
		this.fillCollor = outlineColorProvider.getCurrentColor();
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
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if(!radiusAdded && centerAdded) {
			radius = (int) Math.sqrt(Math.pow(Math.abs(centerX-e.getX()), 2) +
					Math.pow(Math.abs(centerY-e.getY()), 2));
			
			canvas.repaint();
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void paint(Graphics2D g2d) {
		if(radiusAdded) {
			g2d.setColor(fillCollor);
			int d = radius*2;
			g2d.fillOval(centerX, centerY, d, d);
			g2d.setColor(outlineColor);
			g2d.drawOval(centerX, centerY, d, d);
			
		}
	}
}

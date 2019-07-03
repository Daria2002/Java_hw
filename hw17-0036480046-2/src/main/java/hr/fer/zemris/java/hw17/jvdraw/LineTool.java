package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

public class LineTool implements Tool {
	/** start x */
	int x0;
	/** start y */
	int y0;
	/** end x */
	int x1;
	/** end y */
	int y1;
	Color color;
	boolean startCoordinatesAdded = false;
	boolean endCoordinatesAdded = false;
	DrawingModel dm;
	JDrawingCanvas canvas;
	
	public LineTool(DrawingModel dm, IColorProvider colorProvider, JDrawingCanvas c) {
		this.color = colorProvider.getCurrentColor();
		this.canvas = c;
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
		if(!startCoordinatesAdded) {
			x0 = e.getX();
			y0 = e.getY();
			startCoordinatesAdded = true;
			endCoordinatesAdded = false;
			return;
		}
		
		x1 = e.getX();
		y1 = e.getY();
		dm.add(new Line(x0, y0, x1, y1, color));
		
		endCoordinatesAdded = true;
		startCoordinatesAdded = false;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if(!endCoordinatesAdded && startCoordinatesAdded) {
			x1 = e.getX();
			y1 = e.getY();
			canvas.repaint();
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void paint(Graphics2D g2d) {
		if(endCoordinatesAdded) {
			g2d.setColor(color);
			g2d.drawLine(x0, y0, x1, y1);
		}
	}
}

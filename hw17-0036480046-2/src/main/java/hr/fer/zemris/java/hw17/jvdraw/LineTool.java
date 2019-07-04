package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;

public class LineTool implements Tool {
	/** start x */
	int x0;
	/** start y */
	int y0;
	/** end x */
	int x1;
	/** end y */
	int y1;
	IColorProvider colorProvider;
	boolean startCoordinatesAdded = false;
	boolean endCoordinatesAdded = false;
	DrawingModel dm;
	JDrawingCanvas canvas;
	
	public LineTool(DrawingModel dm, IColorProvider colorProvider, JDrawingCanvas c) {
		this.colorProvider = colorProvider;
		this.canvas = c;
		this.dm = dm;
	}
	
	
	@Override
	public void mousePressed(MouseEvent e) {
		x1 = e.getX();
		y1 = e.getY();
		canvas.repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		canvas.repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(!startCoordinatesAdded) {
			x0 = e.getX();
			y0 = e.getY();
			startCoordinatesAdded = true;
			endCoordinatesAdded = false;
			canvas.repaint();
			return;
		}
		
		x1 = e.getX();
		y1 = e.getY();
		Color c = colorProvider.getCurrentColor();
		dm.add(new Line(x0, y0, x1, y1, c));
		
		endCoordinatesAdded = true;
		startCoordinatesAdded = false;
		canvas.repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		x1 = e.getX();
		y1 = e.getY();
		Color c = colorProvider.getCurrentColor();
		dm.add(new Line(x0, y0, x1, y1, c));
		canvas.repaint();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		
	}
	
	@Override
	public void paint(Graphics2D g2d) {
		Color c = colorProvider.getCurrentColor();
		g2d.setColor(c);
		g2d.drawLine(x0, y0, x1, y1);
	}
}

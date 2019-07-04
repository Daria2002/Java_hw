package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;

import org.apache.derby.tools.sysinfo;

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
		if(startCoordinatesAdded) {
			x1 = e.getX();
			y1 = e.getY();
			Color c = colorProvider.getCurrentColor();
			this.paint((Graphics2D)canvas.getGraphics());
			//dm.add(new Line(x0, y0, x1, y1, c));
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(startCoordinatesAdded) {
			x1 = e.getX();
			y1 = e.getY();
			Color c = colorProvider.getCurrentColor();
			this.paint((Graphics2D)canvas.getGraphics());
			//dm.add(new Line(x0, y0, x1, y1, c));
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if(!startCoordinatesAdded) {
			x0 = e.getX();
			y0 = e.getY();
			startCoordinatesAdded = true;
			endCoordinatesAdded = false;
			this.paint((Graphics2D)canvas.getGraphics());
			return;
		}
		
		x1 = e.getX();
		y1 = e.getY();
		Color c = colorProvider.getCurrentColor();
		dm.add(new Line(x0, y0, x1, y1, c));

		this.paint((Graphics2D)canvas.getGraphics());
		endCoordinatesAdded = true;
		startCoordinatesAdded = false;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if(startCoordinatesAdded) {
			x1 = e.getX();
			y1 = e.getY();
			Color c = colorProvider.getCurrentColor();
			//dm.add(new Line(x0, y0, x1, y1, c));
			this.paint((Graphics2D)canvas.getGraphics());
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}
	
	@Override
	public void paint(Graphics2D g2d) {
		canvas.repaint();
		g2d.setColor(colorProvider.getCurrentColor());
		g2d.drawLine(x0, y0, x1, y1);
	}
}

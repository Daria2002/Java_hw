package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

/**
 * This class represents filled circle tool that implements tool
 * @author Daria Matković
 *
 */
public class FilledTTool implements Tool {
	/** outline color provider */
	IColorProvider outlineColorProvider;
	/** fill color provider */
	IColorProvider fillColorProvider;
	/** drawing model */
	DrawingModel dm;
	/** canvas */
	JDrawingCanvas canvas;
	/** center x */
	int[] centerX = new int[3];
	/** center y */
	int[] centerY = new int[3];
	/** radius */
	int radius;
	/** center added flag */
	boolean centerAdded = false;
	static int ic = 0;
	static int jc = 0;
	/**
	 * Filled circle tool constructor
	 * @param outlineColorProvider outline color provider
	 * @param fillColorProvider fill color provider
	 * @param c canvas
	 * @param dm drawing model
	 */
	public FilledTTool(IColorProvider outlineColorProvider,
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
		//if(!centerAdded) {
			centerX[ic++] = e.getX();
			centerY[jc++] = e.getY();
			if(ic == 3) {
				ic = 0;
				jc = 0;
				dm.add(new FilledT(centerX, centerY,
						outlineColorProvider.getCurrentColor(), fillColorProvider.getCurrentColor()));
				canvas.repaint();
				centerX= new int[3];
				centerY= new int[3];
				centerAdded = true;
			}
			return;
//		}
//		centerAdded = false;
//		dm.add(new FilledT(centerX, centerY,
//				outlineColorProvider.getCurrentColor(), fillColorProvider.getCurrentColor()));
//		canvas.repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void paint(Graphics2D g2d) {
		canvas.repaint();
		g2d.setColor(fillColorProvider.getCurrentColor());
		g2d.fillPolygon(centerX, centerY, 3);
		g2d.setColor(outlineColorProvider.getCurrentColor());
		g2d.drawPolygon(centerX, centerY, 3);
	}
}

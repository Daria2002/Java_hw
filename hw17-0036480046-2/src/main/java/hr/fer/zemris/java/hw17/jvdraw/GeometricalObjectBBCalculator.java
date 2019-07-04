package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.Dimension;
import java.awt.Rectangle;

/**
 * Visitor that is used for getting bounding box
 * @author Daria Matković
 *
 */
public class GeometricalObjectBBCalculator implements GeometricalObjectVisitor {
	/** x0 */
	private int x0 = -1;
	/** y0 */
	private int y0 = -1;
	/** x1 */
	private int x1 = 0;
	/** y1 */
	private int y1 = 0;
	/** width */
	private int width = 0;
	/** height */
	private int height = 0;
	
	/**
	 * checks if given values are min or max
	 * @param x0 x min
	 * @param y0 y min
	 * @param x1 x max
	 * @param y1 y max
	 */
	private void isMinMax(int x0, int y0, int x1, int y1) {
		if(this.x0 < 0) {
			setCoordinates(x0, y0, x1, y1);
		} else {
			if(this.x0 > x0) {
				this.x0 = x0;
			}
			
			if(this.y0 > y0) {
				this.y0 = y0;
			}
			
			if(this.x1 < x1) {
				this.x1 = x1;
			}
			
			if(this.y1 < y1) {
				this.y1 = y1;
			}
		}
		width = this.x1 - this.x0;
		height = this.y1 - this.y0;
	}

	@Override
	public void visit(Line line) {
		isMinMax(line.getX0(), line.getY0(), line.getX1(), line.getY1());
	}

	@Override
	public void visit(Circle circle) {
		isMinMax(circle.getCenterX()-circle.getRadius(),
				circle.getCenterY()-circle.getRadius(),
				circle.getCenterX()+circle.getRadius(),
				circle.getCenterY()+circle.getRadius());
	}
	
	@Override
	public void visit(FilledCircle filledCircle) {
		isMinMax(filledCircle.centerX-filledCircle.radius,
				filledCircle.centerY-filledCircle.radius,
				filledCircle.centerX+filledCircle.radius,
				filledCircle.centerY+filledCircle.radius);
	}

	/**
	 * Sets coordinates
	 * @param x0 x0
	 * @param y0 y0
	 * @param x1 x1 
	 * @param y1 y1
	 */
	private void setCoordinates(int x0, int y0, int x1, int y1) {
		this.x0 = x0;
		this.y0 = y0;
		this.x1 = x1;
		this.y1 = y1;
	}
	
	/**
	 * Get bounding box
	 * @return rectangle
	 */
	public Rectangle getBoundingBox() {
		if(x0 < 0) {
			return new Rectangle(0, 0, 0, 0);
		}
		return new Rectangle(x0, y0, width, height);
	}
}

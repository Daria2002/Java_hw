package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.Color;

/**
 * This class represents Circle model that extends GeometricalObject.
 * @author Daria Matković
 *
 */
public class Circle extends GeometricalObject {
	/** name */
	private static final String NAME = "CIRCLE";
	/** x of center */
	private int centerX;
	/** y of center */
	private int centerY;
	/** radius */
	private int radius;
	/** color */
	private Color color;
	
	/**
	 * Constructor that initialize circle data
	 * @param centerX center x
	 * @param centerY center y
	 * @param radius radius
	 * @param color color
	 */
	public Circle(int centerX, int centerY, int radius, Color color) {
		super();
		this.centerX = centerX;
		this.centerY = centerY;
		this.radius = radius;
		this.color = color;
	}
	
	/**
	 * center x setter
	 * @param centerX x center
	 */
	public void setCenterX(int centerX) {
		this.centerX = centerX;
	}

	/**
	 * center y setter
	 * @param centerY center y
	 */
	public void setCenterY(int centerY) {
		this.centerY = centerY;
	}

	/**
	 * radius setter
	 * @param radius radius
	 */
	public void setRadius(int radius) {
		this.radius = radius;
	}

	/**
	 * color setter
	 * @param color color
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * center x getter
	 * @return center x
	 */
	public int getCenterX() {
		return centerX;
	}

	/**
	 * center y getter
	 * @return center y
	 */
	public int getCenterY() {
		return centerY;
	}

	/**
	 * radius getter
	 * @return radius
	 */
	public int getRadius() {
		return radius;
	}

	/**
	 * color getter
	 * @return color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * name getter
	 * @return name
	 */
	public static String getName() {
		return NAME;
	}

	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);
	}

	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new CircleEditor(this);
	}
	
	@Override
	public String toString() {
		return "Circle (" + centerX + "," + centerY + "), " + radius;
	}
}

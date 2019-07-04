package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.Color;

/**
 * This class represents geometrical onject line
 * @author Daria Matković
 *
 */
public class Line extends GeometricalObject {
	/** name */
	private static final String NAME = "LINE";
	/** x0 */
	private int x0;
	/** y0 */
	private int y0;
	/** x1 */
	private int x1;
	/** y1 */
	private int y1;
	/** color */
	private Color color;

	/**
	 * constructor for line that initialize dimensions and color
	 * @param x0 x0
	 * @param y0 y0
	 * @param x1 x1 
	 * @param y1 y1 
	 * @param color color
	 */
	public Line(int x0, int y0, int x1, int y1, Color color) {
		this.x0 = x0;
		this.y0 = y0;
		this.x1 = x1;
		this.y1 = y1;
		this.color = color;
	}

	/**
	 * gets name
	 * @return name
	 */
	public static String getName() {
		return NAME;
	}
	
	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);
	}
	
	/**
	 * sets x0
	 * @param x0 x0
	 */
	public void setX0(int x0) {
		this.x0 = x0;
	}

	/**
	 * sets y0
	 * @param y0 y0
	 */
	public void setY0(int y0) {
		this.y0 = y0;
	}

	/**
	 * sets x1
	 * @param x1 x1
	 */
	public void setX1(int x1) {
		this.x1 = x1;
	}

	/**
	 * sets y1
	 * @param y1 y1
	 */
	public void setY1(int y1) {
		this.y1 = y1;
	}

	/**
	 * sets color
	 * @param color color
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * gets x0
	 * @return x0
	 */
	public int getX0() {
		return x0;
	}
	
	/**
	 * gets y0
	 * @return y0
	 */
	public int getY0() {
		return y0;
	}

	/**
	 * gets x1 
	 * @return x1
	 */
	public int getX1() {
		return x1;
	}

	/**
	 * gets y1
	 * @return y1
	 */
	public int getY1() {
		return y1;
	}

	/**
	 * gets color 
	 * @return color
	 */
	public Color getColor() {
		return color;
	}

	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new LineEditor(this);
	}
	
	@Override
	public String toString() {
		return "Line (" + x0 + "," + y0 + ")-(" + x1 + "," + y1 + ")";
	}
}

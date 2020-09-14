package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.Color;

public class FilledT  extends GeometricalObject {
	/** name */
	private static final String NAME = "FTRIANGLE";
	/** x center */
	int[] x;
	/** y center */
	int[] y;
	/** outline color */
	Color outlineColor;
	/** fill color */
	Color fillColor;
	
	/**
	 * This method represents constructor for filled circle
	 * @param centerX center x
	 * @param centerY center y
	 * @param radius radius
	 * @param outlineColor outline color
	 * @param fillColor fill color
	 */
	public FilledT(int[] centerX, int[] centerY, Color outlineColor, Color fillColor) {
		super();
		this.x = centerX;
		this.y = centerY;
		this.outlineColor = outlineColor;
		this.fillColor = fillColor;
	}

	/**
	 * Gets name
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
	 * Sets outline color
	 * @param outlineColor outline color
	 */
	public void setOutlineColor(Color outlineColor) {
		this.outlineColor = outlineColor;
	}

	/**
	 * Sets fill color
	 * @param fillColor fill color
	 */
	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;
	}
	
	/**
	 * gets outline color
	 * @return outline color
	 */
	public Color getOutlineColor() {
		return outlineColor;
	}

	/**
	 * Gets fill color
	 * @return fill color
	 */
	public Color getFillColor() {
		return fillColor;
	}

	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new FilledTEditor(this);
	}
	
	@Override
	public String toString() {
		return "Filled t";
	}
}

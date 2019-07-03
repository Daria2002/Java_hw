package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.Color;

public class FilledCircle extends GeometricalObject {

	private static final String NAME = "FCIRCLE";
	int centerX;
	int centerY;
	int radius;
	Color outlineColor;
	Color fillColor;
	
	public FilledCircle(int centerX, int centerY, int radius, Color outlineColor, Color fillColor) {
		super();
		this.centerX = centerX;
		this.centerY = centerY;
		this.radius = radius;
		this.outlineColor = outlineColor;
		this.fillColor = fillColor;
	}

	public static String getName() {
		return NAME;
	}
	
	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);
	}

	public int getCenterX() {
		return centerX;
	}

	public int getCenterY() {
		return centerY;
	}

	public int getRadius() {
		return radius;
	}

	public Color getOutlineColor() {
		return outlineColor;
	}

	public Color getFillColor() {
		return fillColor;
	}

	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new FilledCircleEditor(this);
	}
}

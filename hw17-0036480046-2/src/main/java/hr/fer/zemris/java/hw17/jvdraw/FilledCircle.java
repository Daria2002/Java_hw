package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.Color;

public class FilledCircle extends GeometricalObject {

	private static final String NAME = "FCIRCLE";
	int centerX;
	int centerY;
	int radius;
	Color outlineColor;
	Color fillColor;
	private static int objectsCounter = 0;
	
	public FilledCircle(int centerX, int centerY, int radius, Color outlineColor, Color fillColor) {
		super();
		this.centerX = centerX;
		this.centerY = centerY;
		this.radius = radius;
		this.outlineColor = outlineColor;
		this.fillColor = fillColor;
		objectsCounter++;
	}

	public static String getName() {
		return NAME;
	}
	
	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);
	}
	
	public void setCenterX(int centerX) {
		this.centerX = centerX;
	}

	public void setCenterY(int centerY) {
		this.centerY = centerY;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	public void setOutlineColor(Color outlineColor) {
		this.outlineColor = outlineColor;
	}

	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;
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
	
	@Override
	public String toString() {
		return "Filled circle " + objectsCounter;
	}
}

package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.Color;

public class Circle extends GeometricalObject {

	private static final String NAME = "CIRCLE";
	private int centerX;
	private int centerY;
	private int radius;
	private Color color;
	private static int objectCounter = 0;
	
	public Circle(int centerX, int centerY, int radius, Color color) {
		super();
		this.centerX = centerX;
		this.centerY = centerY;
		this.radius = radius;
		this.color = color;
		objectCounter++;
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



	public void setColor(Color color) {
		this.color = color;
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

	public Color getColor() {
		return color;
	}

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
		return "Circle " + objectCounter;
	}
}

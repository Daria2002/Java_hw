package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.Color;

public class Circle extends GeometricalObject {

	private static final String NAME = "CIRCLE";
	int centerX;
	int centerY;
	int radius;
	Color color;
	
	public Circle(int centerX, int centerY, int radius, Color color) {
		super();
		this.centerX = centerX;
		this.centerY = centerY;
		this.radius = radius;
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
		// TODO Auto-generated method stub
		
	}

}

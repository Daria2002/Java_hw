package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.Color;

public class Line extends GeometricalObject {

	private static final String NAME = "LINE";
	int x0;
	int y0;
	int x1;
	int y1;
	private Color color;

	public Line(int x0, int y0, int x1, int y1, Color color) {
		this.x0 = x0;
		this.y0 = y0;
		this.x1 = x1;
		this.y1 = y1;
		this.color = color;
	}

	public static String getName() {
		return NAME;
	}

	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);
	}

	public int getX0() {
		return x0;
	}
	
	public int getY0() {
		return y0;
	}

	public int getX1() {
		return x1;
	}

	public int getY1() {
		return y1;
	}

	public Color getColor() {
		return color;
	}

	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new LineEditor(this);
	}
}

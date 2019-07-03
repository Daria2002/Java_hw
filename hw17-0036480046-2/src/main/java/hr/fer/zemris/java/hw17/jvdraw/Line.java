package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.Color;

public class Line extends GeometricalObject {

	private static final String NAME = "LINE";
	private int x0;
	private int y0;
	private int x1;
	private int y1;
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
	
	public void setX0(int x0) {
		this.x0 = x0;
	}

	public void setY0(int y0) {
		this.y0 = y0;
	}

	public void setX1(int x1) {
		this.x1 = x1;
	}

	public void setY1(int y1) {
		this.y1 = y1;
	}

	public void setColor(Color color) {
		this.color = color;
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

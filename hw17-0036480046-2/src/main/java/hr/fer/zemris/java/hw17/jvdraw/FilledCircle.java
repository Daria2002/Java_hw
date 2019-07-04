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
		return "Filled circle (" + centerX + "," + centerY + "), " + radius +
				" #" + decToHex(fillColor.getRed()) + 
				decToHex(fillColor.getGreen()) + 
				decToHex(fillColor.getBlue());
	}
	
	public static String decToHex(int dec) {
		int sizeOfIntInHalfBytes = 2;
		int numberOfBitsInAHalfByte = 4;
		int halfByte = 0x0F;
		char[] hexDigits = { 
		    '0', '1', '2', '3', '4', '5', '6', '7', 
		    '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
		  };
		
	    StringBuilder hexBuilder = new StringBuilder(sizeOfIntInHalfBytes);
	    hexBuilder.setLength(sizeOfIntInHalfBytes);
	    for (int i = sizeOfIntInHalfBytes - 1; i >= 0; --i)
	    {
	      int j = dec & halfByte;
	      hexBuilder.setCharAt(i, hexDigits[j]);
	      dec >>= numberOfBitsInAHalfByte;
	    }
	    return hexBuilder.toString(); 
	  }
}

package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.Color;

/**
 * This class represents filled circle
 * @author Daria Matković
 *
 */
public class FilledCircle extends GeometricalObject {
	/** name */
	private static final String NAME = "FCIRCLE";
	/** x center */
	int centerX;
	/** y center */
	int centerY;
	/** radius */
	int radius;
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
	public FilledCircle(int centerX, int centerY, int radius, Color outlineColor, Color fillColor) {
		super();
		this.centerX = centerX;
		this.centerY = centerY;
		this.radius = radius;
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
	 * center x setter
	 * @param centerX center x
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
	 * gets center x
	 * @return center x
	 */
	public int getCenterX() {
		return centerX;
	}

	/**
	 * gets center y
	 * @return center y
	 */
	public int getCenterY() {
		return centerY;
	}

	/**
	 * gets radius
	 * @return radius
	 */
	public int getRadius() {
		return radius;
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
		return new FilledCircleEditor(this);
	}
	
	@Override
	public String toString() {
		return "Filled circle (" + centerX + "," + centerY + "), " + radius +
				" #" + decToHex(fillColor.getRed()) + 
				decToHex(fillColor.getGreen()) + 
				decToHex(fillColor.getBlue());
	}
	
	/**
	 * Converts decimal number to hex
	 * @param dec decimal number
	 * @return hex number
	 */
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

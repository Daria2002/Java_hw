package hr.fer.zemris.lsystems.impl;

import java.awt.Color;

/**
 * Class that implements turtle position management.
 * @author Daria Matković
 *
 */
public class TurtleState {
	/** current turtle position **/
	private Vector2D positionVector;
	/** unit vector for current turtle direction **/
	private Vector2D orientationUnitVector;
	/** drawing color **/
	private Color color;
	/** effective step length **/
	private double effectiveStepLength;
	private static final double TOLERANCE = 0.0001;
	
	/**
	 * Constructor that initialize currentCVector, currentUnitVector,
	 * color and stepLength
	 * @param positionVector current position
	 * @param orientationUnitVector unit vector for direction
	 * @param color current drawing color
	 * @param stepLength step length
	 */
	public TurtleState(Vector2D positionVector, Vector2D orientationUnitVector,
			Color color, double stepLength) {
		this.positionVector = positionVector;
		
		if(Math.abs(Math.sqrt(Math.pow(orientationUnitVector.getX(), 2) +
				Math.pow(orientationUnitVector.getY(), 2)) - 1) > TOLERANCE) {
			throw new IllegalArgumentException(
					"Orientation vector must be unit vector");
		}
		
		this.orientationUnitVector = orientationUnitVector;
		this.color = color;
		this.effectiveStepLength = stepLength;
	}
	
	/**
	 * Method that copies turtle state and returns copy
	 * @return copy of turtle state
	 */
	public TurtleState copy() {
		Vector2D currentVectorCopy = this.positionVector.copy();
		Vector2D currentUnitVectorCopy = this.orientationUnitVector.copy();
		Color colorCopy = this.color;
		double stepLengthCopy = this.effectiveStepLength;
		
		return new TurtleState(currentVectorCopy, currentUnitVectorCopy,
				colorCopy, stepLengthCopy);
	}

	/**
	 * Getter for currentUnitVector
	 * @return currentUnitVector
	 */
	public Vector2D getCurrentUnitVector() {
		return orientationUnitVector;
	}

	/**
	 * Getter for effective step length
	 * @return effectiveStepLength
	 */
	public double getEffectiveStepLength() {
		return effectiveStepLength;
	}

	/**
	 * Getter for currentVector
	 * @return currentVector
	 */
	public Vector2D getCurrentVector() {
		return positionVector;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
}

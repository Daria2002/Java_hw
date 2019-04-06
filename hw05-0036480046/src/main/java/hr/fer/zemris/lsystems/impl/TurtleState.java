package hr.fer.zemris.lsystems.impl;

import java.awt.Color;

/**
 * Class that implements turtle position management.
 * @author Daria Matković
 *
 */
public class TurtleState {
	/** current turtle position **/
	private Vector2D currentVector;
	/** unit vector for current turtle direction **/
	private Vector2D currentUnitVector;
	/** drawing color **/
	private Color color;
	/** effective step length **/
	private double effectiveStepLength;
	
	/**
	 * Constructor that initialize currentCVector, currentUnitVector,
	 * color and stepLength
	 * @param currentVector current position
	 * @param currentUnitVector unit vector for direction
	 * @param color current drawing color
	 * @param stepLength step length
	 */
	public TurtleState(Vector2D currentVector, Vector2D currentUnitVector,
			Color color, double stepLength) {
		this.currentVector = currentVector;
		this.currentUnitVector = currentUnitVector;
		this.color = color;
		this.effectiveStepLength = stepLength;
	}
	
	/**
	 * Method that copies turtle state and returns copy
	 * @return copy of turtle state
	 */
	public TurtleState copy() {
		Vector2D currentVectorCopy = this.currentVector.copy();
		Vector2D currentUnitVectorCopy = this.currentUnitVector.copy();
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
		return currentUnitVector;
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
		return currentVector;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
}

package hr.fer.zemris.java.hw17.jvdraw;

/**
 * Interface for geometrical object listener
 * @author Daria Matković
 *
 */
interface GeometricalObjectListener {
	/**
	 * Method called when geometrical object changes
	 * @param o changed object
	 */
	public void geometricalObjectChanged(GeometricalObject o);
}
package hr.fer.zemris.java.hw17.jvdraw;

import javax.swing.JPanel;

/**
 * Interface for geometrical object editor
 * @author Daria Matković
 *
 */
abstract class GeometricalObjectEditor extends JPanel {
	/**
	 * Checks editing
	 */
	public abstract void checkEditing();
	
	/**
	 * accept editing
	 */
	public abstract void acceptEditing();
}
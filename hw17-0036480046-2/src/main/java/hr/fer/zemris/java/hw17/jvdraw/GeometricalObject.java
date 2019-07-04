package hr.fer.zemris.java.hw17.jvdraw;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is abstract class that represents geometrical object
 * @author Daria Matković
 *
 */
abstract class GeometricalObject {
	/** listeners */
	private List<GeometricalObjectListener> listeners = new ArrayList<>();
	
	/**
	 * Adds geometrical object listener
	 * @param l listener
	 */
	public void addGeometricalObjectListener(GeometricalObjectListener l) {
		listeners.add(l);
	}
	
	/**
	 * Accept method for given visitor
	 * @param v visitor
	 */
	public abstract void accept(GeometricalObjectVisitor v);
	
	/**
	 * Removes listener
	 * @param l listener to remove
	 */
	public void removeGeometricalObjectListener(GeometricalObjectListener l) {
		listeners.remove(l);
	}
	
	/**
	 * This method creates object editor
	 * @return
	 */
	public abstract GeometricalObjectEditor createGeometricalObjectEditor();
}
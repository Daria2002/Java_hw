package hr.fer.zemris.java.hw17.jvdraw;

/**
 * This interface represents drawing model
 * @author Daria Matković
 *
 */
interface DrawingModel {
	/**
	 * size getter
	 * @return size
	 */
	public int getSize();
	
	/**
	 * object getter
	 * @param index index
	 * @return GeometricalObject
	 */
	public GeometricalObject getObject(int index);
	
	/**
	 * adds given object
	 * @param object object to add
	 */
	public void add(GeometricalObject object);
	
	/**
	 * Removes given object
	 * @param object object to remove
	 */
	public void remove(GeometricalObject object);
	
	/**
	 * Changes order
	 * @param object object 
	 * @param offset offset
	 */
	public void changeOrder(GeometricalObject object, int offset);
	
	/**
	 * Gets index of given object
	 * @param object object
	 * @return index of object
	 */
	public int indexOf(GeometricalObject object);
	
	/**
	 *	Removes all objects
	 */
	public void clear();
	
	/**
	 * clears modification flag
	 */
	public void clearModifiedFlag();
	
	/**
	 * gets is modified
	 * @return modified
	 */
	public boolean isModified();
	
	/**
	 * adds drawing model listener
	 * @param l listener to add 
	 */
	public void addDrawingModelListener(DrawingModelListener l);
	
	/**
	 * removes drawing model listener
	 * @param l listener to remove
	 */
	public void removeDrawingModelListener(DrawingModelListener l);
}

package hr.fer.zemris.java.hw17.jvdraw;

/**
 * This is interface for drawing model listener
 * @author Daria Matković
 *
 */
public interface DrawingModelListener {
	/**
	 * This method is called when object is added
	 * @param source source
	 * @param index0 index before
	 * @param index1 index after
	 */
	public void objectsAdded(DrawingModel source, int index0, int index1);
	
	/**
	 * This method is called when object is removed
	 * @param source source
	 * @param index0 index before
	 * @param index1 index after
	 */
	public void objectsRemoved(DrawingModel source, int index0, int index1);
	
	/**
	 * This method is called when object is changed
	 * @param source source
	 * @param index0 index before
	 * @param index1 index after
	 */
	public void objectsChanged(DrawingModel source, int index0, int index1);
}
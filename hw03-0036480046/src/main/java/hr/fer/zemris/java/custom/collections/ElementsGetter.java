package hr.fer.zemris.java.custom.collections;

/**
 * This program implements getter for elements
 * @author Daria Matković
 *
 */
public interface ElementsGetter {
	/**
	 * checks if there are more elements to get
	 * @return true if there are elements, otherwise false
	 */
	boolean hasNextElement();
	
	/**
	 * Gets next element
	 * @return next element if next element exists, otherwise throws NoSuchElementException
	 */
	Object getNextElement();
	
	/**
	 * Calls set processor on all remain elements
	 * @param p processor
	 */
	default void processRemaining(Processor p) {
		while(hasNextElement()) {
			p.process(getNextElement());
		}
	}
}

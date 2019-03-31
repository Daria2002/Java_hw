package hr.fer.zemris.java.custom.collections;

/**
 * This program implements getter for elements
 * @author Daria Matković
 *
 */
public interface ElementsGetter<T> {
	/**
	 * checks if there are more elements to get
	 * @return true if there are elements, otherwise false
	 */
	abstract boolean hasNextElement();
	
	/**
	 * Gets next element
	 * @return next element if next element exists, otherwise throws NoSuchElementException
	 */
	abstract T getNextElement();
	
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

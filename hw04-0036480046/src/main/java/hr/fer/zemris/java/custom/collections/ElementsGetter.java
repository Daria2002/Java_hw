package hr.fer.zemris.java.custom.collections;

/**
 * Interface for getting elements in collection.
 * @author Daria Matković
 *
 * @param <T> generic element type
 */
public interface ElementsGetter<T> {
	/**
	 * checks if there are more elements to get
	 * @return true if there are elements, otherwise false
	 */
	boolean hasNextElement();
	
	/**
	 * Gets next element
	 * @return next element if next element exists, otherwise throws NoSuchElementException
	 */
	T getNextElement();
	
	/**
	 * Calls set processor on all remain elements
	 * @param p processor
	 */
	default void processRemaining(Processor<? super T> p) {
		while(hasNextElement()) { 
			p.process(getNextElement());
		}
	}
}

package hr.fer.zemris.java.custom.collections;

/**
 * Checks if object is acceptable or not.
 * @author Daria Matković
 *
 * @param <T> generic element type
 */
public interface Tester<T> {
	
	/**
	 * Tests given object
	 * @param object object to check 
	 * @return true if object is acceptable, otherwise false
	 */
	abstract boolean test(T object);
	
}

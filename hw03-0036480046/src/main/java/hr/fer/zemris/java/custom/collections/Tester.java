package hr.fer.zemris.java.custom.collections;

/**
 * Checks if object is acceptable or not
 * @author Daria Matković
 *
 */
public interface Tester {
	
	/**
	 * Tests given object
	 * @param object object to check 
	 * @return true if object is acceptable, otherwise false
	 */
	abstract boolean test(Object object);
	
}

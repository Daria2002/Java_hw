package hr.fer.zemris.java.custom.collections;

/**
 * List extends collection
 * @author Daria Matković
 *
 */
public interface List extends Collection {

	/**
	 * Gets object at position index
	 * @param index position
	 * @return Object at position index
	 */
	Object get(int index);
	
	/**
	 * Inserts value at given position
	 * @param value value to insert
	 * @param position position to insert value
	 */
	void insert(Object value, int position);
	
	/**
	 * Index of given object
	 * @param value value to check index
	 * @return index of given value
	 */
	int indexOf(Object value);
	
	/**
	 * Remove value at position index
	 * @param index position
	 */
	void remove(int index);
}

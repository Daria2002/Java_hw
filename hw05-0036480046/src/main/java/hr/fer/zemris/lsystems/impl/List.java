package hr.fer.zemris.lsystems.impl;

/**
 * Interface for extending collection and defining list specific methods.
 * @author Daria Matković
 *
 * @param <T> generic element type
 */
public interface List<T> extends Collection<T> {

	/**
	 * Gets object at position index
	 * @param index position
	 * @return Object at position index
	 */
	T get(int index);
	
	/**
	 * Inserts value at given position
	 * @param value value to insert
	 * @param position position to insert value
	 */
	void insert(T value, int position);
	
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

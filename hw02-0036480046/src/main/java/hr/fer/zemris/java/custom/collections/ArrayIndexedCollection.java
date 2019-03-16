package hr.fer.zemris.java.custom.collections;


/**
 * 
 * @author Daria Matkovic
 *
 */
public class ArrayIndexedCollection extends Collection {
	// size is number of elements stored in array elements
	private int size;
	// array of object references, null references are not allowed, duplicate
	// elements are allowed
	private Object[] elements;
	private int capacity;
	
	/**
	 * Initialize capacity variable to 16 and preallocates elements to that size
	 */
	public ArrayIndexedCollection() {
		this.capacity = 16;
		this.elements = new Object[16];
	}

	/**
	 * Checks if collection is null and delegates previous constructor
	 * @param collection is given collection to check if it is null
	 */
	public ArrayIndexedCollection(Collection collection) {
		this();
		if(collection == null) {
			throw new NullPointerException();
		}
	}
	
	/**
	 * Changes array capacity and sets capacity variable
	 * @param initialCapacity new array capacity
	 */
	public ArrayIndexedCollection(int initialCapacity) {
		if(initialCapacity < 1) {
			throw new IllegalArgumentException();
		}
		this.capacity = initialCapacity;
		this.elements = new Object[this.capacity];
	}
	
	/**
	 * Initializes capacity and throws exception if collection is null
	 * @param collection given collection which has to be checked if it is null
	 * @param initialCapacity initial value for capacity
	 */
	public ArrayIndexedCollection(Collection collection, int initialCapacity) {
		this(initialCapacity);
		if(collection == null) {
			throw new NullPointerException();
		}
		if(initialCapacity < collection.size()) {
			initialCapacity = collection.size();
		}
	}
	
	public void add(Object value) {
		if(value == null) {
			throw new NullPointerException();
		}
		// checking if elements array is full
		if(this.capacity == this.size) {
			this.capacity *= 2;
			this.elements = new Object[this.capacity];
		}
	}
	
	
}

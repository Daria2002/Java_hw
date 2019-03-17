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
	
	/**
	 * doubling array's size
	 */
	public void doubleSize() {
		this.capacity *= 2;
		this.elements = new Object[this.capacity];
	}
	
	/**
	 * Adds the given object into first empty place in the elements array
	 */
	public void add(Object value) {
		if(value == null) {
			throw new NullPointerException();
		}
		// doubling array's size if array is full
		if(this.capacity == this.size) {
			doubleSize();
		}
		// adding elements into first empty place
		for(int i = 0; i < this.size; i++) {
			if(this.elements[i] == null) {
				this.elements[i] = value;
			}
		}
	}
	
	/**
	 * Throws exception if index value is wrong, otherwise returns the object
	 * stored in elements at position index.
	 * @param index Position of acquired object
	 * @return the object that is stored in elements at given index
	 */
	public Object get(int index) {
		if(index < 0 || index > size-1) {
			throw new IndexOutOfBoundsException();
		}
		return this.elements[index];
	}
	
	/**
	 * Removes all elements from the collection, doesn't change capacity
	 * variable, initialize array elements to null and sets size variable to 0
	 */
	public void clear() {
		for(int i = 0; i < this.size; i++) {
			this.elements[i] = null;
		}
		this.size = 0;
	}
	
	/**
	 * Insert value at given position, and shifting elements at position and at
	 * greater positions toward the end
	 * @param value to insert at index position in elements array
	 * @param position in array where value need to be inserted
	 */
	public void insert(Object value, int position) {
		if(position < 0 || position > size) {
			throw new IndexOutOfBoundsException();
		}
		// doubling array's size if array is full
		if(this.capacity == this.size) {
			doubleSize();
		}
		// element at size index should be empty, because array is filled at 
		// indexes in range from 0 to size-1
		if(position < this.size) {
			// shifting elements from position to end
			for(int i = this.size; i >= position; i--) {
				this.elements[i + 1] = this.elements[i];
			}
		}
		this.elements[position] = value;
		this.size += 1;
	}
	
	/**
	 * Searches the given value in the collection
	 * @param value to search in collection
	 * @return the index of the first occurrence of the given value or 
	 * -1 if the value is not found
	 */
	public int indexOf(Object value) {
		for(int i = 0; i < this.size; i++) {
			if(this.elements[i] == value) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Removes element at index position, and shifts other element toward start
	 * position
	 * @param index is position at which element has to be removed
	 */
	public void remove(int index) {
		if(index < 0 || index > size-1) {
			throw new IndexOutOfBoundsException();
		}
		for(int i = index; i < this.size; i++) {
			this.elements[i] = this.elements[i+1];
		}
		this.size -= 1;
	}
}

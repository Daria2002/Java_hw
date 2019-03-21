package hr.fer.zemris.java.custom.collections;

/**
 * This program implements resizable array-backed collection of objects. It is
 * allowed to store duplicate elements, but null references are not allowed.
 * @author Daria Matkovic
 *
 */
public class ArrayIndexedCollection extends Collection {
	private int size;
	private Object[] elements;
	public int capacity;
	
	/**
	 * Initialize capacity variable to 16 and preallocates elements to that size.
	 */
	public ArrayIndexedCollection() {
		this(16);
	}

	/**
	 * Checks if collection is null and delegates to previous constructor
	 * @param collection collection is given collection to check if it is null
	 */
	public ArrayIndexedCollection(Collection collection) {
		this(collection, 16);
	}
	
	/**
	 * Changes array capacity and sets capacity variable.
	 * @param initialCapacity initalCapacity is capacity of new array.
	 */
	public ArrayIndexedCollection(int initialCapacity) {
		if(initialCapacity < 1) {
			throw new IllegalArgumentException("Initial capacity is less than 1");
		}
		this.capacity = initialCapacity;
		this.elements = new Object[this.capacity];
	}
	
	/**
	 * Initializes capacity, adds all elements from given collection to this 
	 * collection or throws exception if collection is null
	 * @param collection collection is given collection which has to be checked
	 * if it is equal to null
	 * @param initialCapacity initial value for capacity
	 */
	public ArrayIndexedCollection(Collection collection, int initialCapacity) {
		this(initialCapacity);
		if(collection == null) {
			throw new NullPointerException("Collection object is null.");
		}
		if(initialCapacity < collection.size()) {
			this.capacity = collection.size();
			this.elements = new Object[this.capacity];
		}
		this.addAll(collection);
	}
	
	/**
	 * Doubling element's capacity
	 */
	private void doubleCapacity() {
		Object[] helpArray = new Object[this.size];
		for(int i = 0; i < this.size; i++) {
			helpArray[i] = this.elements[i];
		}
		this.capacity *= 2;
		this.elements = new Object[this.capacity];
		for(int i = 0; i < this.size; i++) {
			this.elements[i] = helpArray[i];
		}
	}
	
	/**
	 * Adds the given object into first empty place in the elements array
	 * complexity: O(1) if array is not full, otherwise O(n)
	 * @param value value added to collection.
	 */
	public void add(Object value) {
		if(value == null) {
			throw new NullPointerException();
		}
		// doubling array's size if array is full
		if(this.capacity == this.size) {
			doubleCapacity();
		}
		// adding elements into first empty place
		this.elements[this.size] = value;
		this.size += 1;
	}
	
	/**
	 * Throws exception if index value is wrong, otherwise returns the object
	 * stored in elements at position index.
	 * @param index index is position of acquired object
	 * @return the object that is stored in elements at given index
	 * complexity: O(1)
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
	 * Inserts value at given position, and shifts all elements from given position
	 * by one space.
	 * @param value value to insert at index position in elements array
	 * @param position position in array where value need to be inserted
	 * complexity: O(n)
	 */
	public void insert(Object value, int position) {
		if(position < 0 || position > size) {
			throw new IndexOutOfBoundsException();
		}
		
		if(value == null) {
			throw new NullPointerException("Can't store null value.");
		}
		
		// doubling array's size if array is full
		if(this.capacity == this.size) {
			doubleCapacity();
		}
		
		// element at size index should be empty, because array is filled at 
		// indexes in range from 0 to size-1
		if(position < this.size) {
			// shifting elements from position to end
			for(int i = this.size; i > position; i--) {
				this.elements[i] = this.elements[i-1];
			}
		}
		
		this.elements[position] = value;
		this.size += 1;
	}
	
	/**
	 * Searches for the given value in the collection
	 * @param value value to search in collection
	 * @return the index of the first occurrence of the given value or 
	 * -1 if the value is not found
	 * complexity: O(n)
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
	 * @param index index is position at which element has to be removed
	 */
	public void remove(int index) {
		if(index < 0 || index > size-1) {
			throw new IndexOutOfBoundsException();
		}
		if(index == size-1) {
			this.elements[index] = null;
		} else {
			for(int i = index; i < this.size; i++) {
				this.elements[i] = this.elements[i+1];
			}
		}
		this.size -= 1;
	}
	
	@Override
	public int size() {
		return this.size;
	}
	
	@Override
	public boolean contains(Object value) {
		if(indexOf(value) == -1) {
			return false;
		}
		return true;
	}
	
	@Override
	public Object[] toArray() {
		Object[] newArray = new Object[this.size];
		for(int i = 0; i < this.size; i++) {
			newArray[i] = this.elements[i];
		}
		return newArray;
	}
	
	@Override
	public void forEach(Processor processor) {
		for(int i = 0; i < this.size; i++) {
			processor.process(this.elements[i]);
		}
	}
}

package hr.fer.zemris.java.custom.collections;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * This program implements resizable array-backed collection of objects. It is
 * allowed to store duplicate elements, but null references are not allowed.
 * @author Daria Matkovic
 * 
 */
public class ArrayIndexedCollection<T> implements List<T> {
	/** size of array elements **/
	private int size;
	/** array of Objects **/
	private T[] elements;
	/** initial capacity **/
	private static final int INITIAL_CAPACITY = 16;
	private long modificationCount = 0;
	
	private static class ElementsGetterArray<T> implements ElementsGetter<T> {
		private int index = 0;
		private long savedModificationCount;
		ArrayIndexedCollection<T> array;
		
		public ElementsGetterArray(ArrayIndexedCollection<T> collection) {
			array = collection;
			savedModificationCount = array.modificationCount;
		}
		
		@Override
		public boolean hasNextElement() {
			if(array.modificationCount != savedModificationCount) {
				throw new ConcurrentModificationException("Array is modified.");
			}
			
			return array.size-1 >= index ?  true : false;
		}

		@Override
		public T getNextElement() {
			if(array.modificationCount != savedModificationCount) {
				throw new ConcurrentModificationException("Array is modified.");
			}
			if(!hasNextElement()) {
				throw new NoSuchElementException("No more elements");
			}
			return array.elements[index++];
		}
	}
	
	@Override
	public ElementsGetter<T> createElementsGetter() {
		return new ElementsGetterArray<T>(this);
	}
	
	/**
	 * Initialize capacity variable to 16 and preallocates elements to that size.
	 */
	public ArrayIndexedCollection() {
		this(INITIAL_CAPACITY);
	}
	
	/**
	 * Checks if collection is null and delegates to previous constructor
	 * @param collection collection is given collection to check if it is null
	 */
	public ArrayIndexedCollection(Collection<T> collection) {
		this(collection, INITIAL_CAPACITY);
	}
	
	/**
	 * Changes array capacity and sets capacity variable.
	 * @param initialCapacity initalCapacity is capacity of new array.
	 */
	public ArrayIndexedCollection(int initialCapacity) {
		if(initialCapacity < 1) {
			throw new IllegalArgumentException("Initial capacity is less than 1");
		}
		this.elements = (T[])new Object[initialCapacity];
	}
	
	/**
	 * Initializes capacity, adds all elements from given collection to this 
	 * collection or throws exception if collection is null
	 * @param collection collection is given collection which has to be checked
	 * if it is equal to null
	 * @param initialCapacity initial value for capacity
	 */
	public ArrayIndexedCollection(Collection<T> collection, int initialCapacity) {
		if(collection == null) {
			throw new NullPointerException("Collection object is null.");
		}
		int capacity;
		if(initialCapacity < collection.size()) {
			capacity = collection.size();
		} else {
			capacity = initialCapacity;
		}

		this.elements = (T[])new Object[capacity];
		this.addAll(collection);
	}
	
	/**
	 * Gets capacity of elements list
	 * @return capacity
	 */
	public int getCapacity() {
		return elements.length;
	}
	
	/**
	 * Doubling element's capacity
	 */
	private void doubleCapacity() { 
		elements = Arrays.copyOf(elements, elements.length*2);
	}
	
	/**
	 * Adds the given object into first empty place in the elements array
	 * complexity: O(1) if array is not full, otherwise O(n)
	 * @param value value added to collection.
	 */
	public void add(T value) {
		if(value == null) {
			throw new NullPointerException("Null can't be added.");
		}
		// doubling array's size if array is full
		if(elements.length == this.size) {
			modificationCount++;
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
	public T get(int index) {
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
		modificationCount++;
		this.size = 0;
	}
	
	/**
	 * Inserts value at given position, and shifts all elements from given position
	 * by one space.
	 * @param value value to insert at index position in elements array
	 * @param position position in array where value need to be inserted
	 * complexity: O(n)
	 */
	public void insert(T value, int position) {
		if(position < 0 || position > size) {
			throw new IndexOutOfBoundsException("Index out of bounds.");
		}
		
		if(value == null) {
			throw new NullPointerException("Can't store null value.");
		}
		
		// doubling array's size if array is full
		if(elements.length == this.size) {
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
		
		if(position != this.size) {
			modificationCount++;
		}
		
		this.elements[position] = value;
		this.size += 1;
	}
	
	/**
	 * Searches for the given value in the collection
	 * Complexity: O(n)
	 * @param value value to search in collection
	 * @return the index of the first occurrence of the given value or 
	 * -1 if the value is not found
	 */
	public int indexOf(Object value) {
		if(value == null) {
			return -1;
		}
		for(int i = 0; i < this.size; i++) {
			if(this.elements[i].equals(value)) {
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
			modificationCount++;
		}
		this.size -= 1;
	}
	
	@Override
	public boolean remove(Object value) {
		int index = this.indexOf(value);
		if(index < 0) {
			return false;
		}
		if(index != size) {
			modificationCount++;
		}
		this.remove(index);
		return true;
	}
	
	@Override
	public int size() {
		return this.size;
	}
	
	@Override
	public boolean contains(Object value) {
		return indexOf(value) != -1;
	}
	
	@Override
	public T[] toArray() {
		T[] newArray = (T[])new Object[this.size];
		for(int i = 0; i < this.size; i++) {
			newArray[i] = this.elements[i];
		}
		return newArray;
	}
}

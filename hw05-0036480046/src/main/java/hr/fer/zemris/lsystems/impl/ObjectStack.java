package hr.fer.zemris.lsystems.impl;

/**
 * Implementation of the stack-like collection.
 * @author Daria Matković
 *
 * @param <T> Generic element type
 */
public class ObjectStack<T> {
	/** Collection for stack implementation **/
	private ArrayIndexedCollection<T> collection;
	
	/** 
	 * Default constructor initializes stack.
	 */
	public ObjectStack() {
		collection = new ArrayIndexedCollection<>();
	} 
	
	/**
	 * Checks if stack is empty.
	 * @return true if stack is empty, otherwise false.
	 */
	public boolean isEmpty() {
		return size() < 0;
	}
	
	/**
	 * Counts elements on stack.
	 * @return number of elements on stack
	 */
	public int size() {
		return collection.size();
	}
	
	/**
	 * Put on stack given non-null value.
	 * Complexity: O(1)
	 * @param value value to push on stack
	 */
	public void push(T value) {
		if(value == null) {
			throw new NullPointerException("Null can't pushed on stack.");
		}
		collection.add(value);
	} 
	
	/**
	 * Removes last value pushed on stack.
	 * Throws EmptyStackException if stack is empty.
	 * Complexity: O(1)
	 * @return removed value
	 */
	public T pop() {
		T object = peek();
		collection.remove(this.size() - 1);
		return object;
	}
	
	/**
	 * Returns last element pushed on stack.
	 * Throws EmptyStackException if stack is empty.
	 * @return last element pushed on stack
	 */
	public T peek() {
		if(this.isEmpty()) {
			throw new EmptyStackException("Stack is empty.");
		}
		return collection.get(this.size()-1);
	}
	
	/**
	 * Removes all elements from stack.
	 */
	public void clear() {
		for(int i = 0; i < this.size(); i++) {
			this.pop();
		}
	}
}

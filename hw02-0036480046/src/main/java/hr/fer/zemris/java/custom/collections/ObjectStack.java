package hr.fer.zemris.java.custom.collections;

import sun.tools.tree.ThisExpression;

/**
 * Implementation of the stack-like collection
 * @author Daria Matkovic
 *
 */
public class ObjectStack {
	
	private ArrayIndexedCollection collection;
	
	public ObjectStack() {
		collection = new ArrayIndexedCollection();
	}
	
	/**
	 * Checks if stack is empty
	 * @return true if stack is empty, otherwise false
	 */
	private boolean isEmpty() {
		if(this.size() == 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * Counts elements on stack.
	 * @return number of elements on stack
	 */
	private int size() {
		return collection.size();
	}
	
	/**
	 * Put on stack given non-null value
	 * @param value to push on stack
	 */
	private void push(Object value) {
		if(value == null) {
			throw new NullPointerException("Null can't pushed on stack.");
		}
		collection.add(value);
	}
	
	/**
	 * Removes last value pushed on stack.
	 * Throws EmptyStackException if stack is empty
	 * @return removed value
	 */
	private Object pop() {
		if(this.isEmpty()) {
			throw new EmptyStackException("Stack is empty.");
		}
		Object object = collection.get(this.size()-1);
		collection.remove(this.size() - 1);
		return object;
	}
	
	/**
	 * Returns last element pushed on stack.
	 * Throws EmptyStackException if stack is empty.
	 * @return last element pushed on stack
	 */
	private Object peek() {
		if(this.isEmpty()) {
			throw new EmptyStackException("Stack is empty.");
		}
		Object object = collection.get(this.size()-1);
		return object;
	}
	
	/**
	 * Removes all elements from stack.
	 */
	private void clear() {
		this.clear();
	}
}

package hr.fer.zemris.java.custom.collections;

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
	public boolean isEmpty() {
		if(this.size() == 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * Counts elements on stack.
	 * @return number of elements on stack
	 */
	public int size() {
		return collection.size();
	}
	
	/**
	 * Put on stack given non-null value
	 * @param value to push on stack
	 */
	public void push(Object value) {
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
	public Object pop() {
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
	public Object peek() {
		if(this.isEmpty()) {
			throw new EmptyStackException("Stack is empty.");
		}
		Object object = collection.get(this.size()-1);
		return object;
	}
	
	/**
	 * Removes all elements from stack.
	 */
	public void clear() {
		this.clear();
	}
}

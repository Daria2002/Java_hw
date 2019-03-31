package hr.fer.zemris.java.custom.collections;

/**
 * Implementation of the stack-like collection.
 * @author Daria Matkovic
 *
 */
<<<<<<< ours
public class ObjectStack {
	/** Collection for stack implementation **/
	private ArrayIndexedCollection collection;
=======
public class ObjectStack<T> {
	/** Collection for stack implementation **/
	private ArrayIndexedCollection<T> collection;
>>>>>>> theirs
	
	/**
	 * Default constructor initializes stack.
	 */
	public ObjectStack() {
<<<<<<< ours
		collection = new ArrayIndexedCollection();
=======
		collection = new ArrayIndexedCollection<T>();
>>>>>>> theirs
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
<<<<<<< ours
	public void push(Object value) {
=======
	public void push(T value) {
>>>>>>> theirs
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
<<<<<<< ours
	public Object pop() {
		Object object = peek();
=======
	public T pop() {
		T object = peek();
>>>>>>> theirs
		collection.remove(this.size() - 1);
		return object;
	}
	
	/**
	 * Returns last element pushed on stack.
	 * Throws EmptyStackException if stack is empty.
	 * @return last element pushed on stack
	 */
<<<<<<< ours
	public Object peek() {
=======
	public T peek() {
>>>>>>> theirs
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

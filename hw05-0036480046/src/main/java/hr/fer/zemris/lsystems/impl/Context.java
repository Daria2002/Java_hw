package hr.fer.zemris.lsystems.impl;

/**
 * This class implements ObjectStack for turtle position.
 * @author Daria Matković
 *
 */
public class Context {

	/** stack for remembering TurtleState **/
	private ObjectStack<TurtleState> stack;
	
	/**
	 * Returns last element from stack and doesn't remove it. 
	 * @return last element from stack
	 */
	public TurtleState getCurrentState() {
		return stack.peek();
	}
	
	/**
	 * Pushes state on stack
	 * @param state state to push on stack
	 */
	public void pushState(TurtleState state) {
		stack.push(state);
	}
	
	/**
	 * Removes last element from stack
	 */
	public void popState() {
		stack.pop();
	}
}

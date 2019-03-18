package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * Gets expression from command line and splits it.
 * @author Daria Matković
 *
 */
public class StackDemo {
	/**
	 * This method executes when program starts.
	 * @param args expression to split.
	 */
	public static void main(String[] args) {
		if(args.length != 1) {
			return;
		}
		String expression = args[0];
		String[] splitExpression = expression.split(" ");
		ObjectStack objectStack = new ObjectStack();
		
	}
}

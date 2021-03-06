package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * Gets expression from command line and splits it. Given expression consists of
 * integer numbers and operators. Operators can be only +, -, %, *, /. Everything 
 * is separated by one or more spaces. Expression must be in postfix representation.
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
			System.out.println("Write one argument expression.");
			return;
		}
		
		String expression = args[0].trim().replaceAll("\\s+"," ");
		String[] splitExpression = expression.split(" ");
		ObjectStack objectStack = new ObjectStack();
		
		for(int i = 0; i < splitExpression.length; i++) {
			
			try {
				int number = Integer.parseInt(splitExpression[i]);
				objectStack.push(number);
				
			} catch (NumberFormatException e) {
				int result = 0;
				
				if(objectStack.size() < 2) {
					System.out.println("Invalid expression.");
					System.exit(1); 
				}
				
				// number2 is number that is 2nd put on stack
				int number2 = (int)objectStack.pop();
				// number1 is number that is 1st put on stack
				int number1 = (int)objectStack.pop();
				
				switch (splitExpression[i]) {
					case "+":
						result = number1 + number2;
						break;
					case "-":
						result = number1 - number2;
						break;
					case "*":
						result = number1 * number2;
						break;
					case "/":
						if(number2 == 0) {
							System.out.println("Division by 0.");
							System.exit(1);
						}
						result = number1 / number2;
						break;
					case "%":
						if(number2 == 0) {
							System.out.println("Division by 0.");
							System.exit(1);
						}
						result = number1 % number2;
						break;
					default:
						System.out.println("Given value is not operator or number.");
						System.exit(1);
				}
				objectStack.push(result);
			}
		}
		
		if(objectStack.size() != 1) {
			System.out.println("Invalid expression.");
		} else {
			System.out.println("Expression evaluates to " + objectStack.pop() + ".");
		}
	}
}

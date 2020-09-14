package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * This class represents calculator for factorial operation. Calculator can 
 * be used only for numbers in range [3, 20].
 * @author Daria Matković
 *
 */
public class Factorial {

	private static final int CONST3 = 3;
	private static final int CONST20 = 20;
	
	/**
	 * This method runs calculator
	 * @param args takes no arguments
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Long inputNumber;
		String inputValue;
		
		while(true) {
			System.out.print("Unesite broj > ");
			inputValue = sc.next();
			
			if("kraj".equals(inputValue)) {
				System.out.println("Doviđenja.");
				break;
			}
			
			try {
				inputNumber = Long.valueOf(inputValue);
				
				if(inputNumber >= CONST3 && inputNumber <= CONST20) {
					System.out.println(inputNumber + "! = " +
							factorial(inputNumber));
					continue;
				}
				System.out.println("'" + inputNumber +
						"' nije broj u dozvoljenom rasponu");
				
			} catch (Exception e) {
				// not possible to parse to number
				System.out.println("'" + inputValue + "' nije cijeli broj.");
			}
		}
		
		sc.close();
	}

	/**
	 * This method calculates factorial of given number
	 * @param inputNumber number for which factorial must be calculated
	 * @return factorial of input number
	 */
	public static Long factorial(Long inputNumber) {
		if(inputNumber < 0) {
			throw new IllegalArgumentException("It's not possible to "
					+ "calculate factorial of negative number.");
		}
		
		Long factorial = (long) 1;
		
		for(int i = 2; i <= inputNumber; i++) {
			factorial *= i;
		}
		
		return factorial;
	}
}

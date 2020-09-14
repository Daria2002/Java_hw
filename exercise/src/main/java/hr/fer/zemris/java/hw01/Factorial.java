package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * Program calculates factorial of a number that user enters. Entered numbers
 * are expected to be integer numbers in interval from 3 to 20, otherwise error
 * message appears. Program stops when "kraj" is entered.
 * @author Daria Matković
 *
 */
public class Factorial {

	/**
	 * This method executes when program starts.
	 * @param args unused
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.print("Unesite broj > ");
		String input = sc.next();
		
		int intInput, factorial;
		while(true) {
			
			try {
				intInput = Integer.parseInt(input);
				factorial = calculateFactorial(intInput);
				System.out.println(input + "! = " + factorial);
			} catch(NumberFormatException e) {
				if ("kraj".equals(input)) {
					System.out.println("Doviđenja.");
					break;
				}
				System.out.println("'" + input + "'" + " nije cijeli broj.");
			} catch (IllegalArgumentException e) {
				System.out.println(input + " nije broj u dozvoljenom rasponu.");
			}
			
			System.out.print("Unesite broj > ");
			input = sc.next();
		}
		
		sc.close();
	}
	
	/**
	 * Method calculates factorial of entered number. It works only for
	 * numbers in range from 3 to 20, otherwise throws exception.
	 * @param number entered number 
	 * @return factorial of entered number
	 */
	public static int calculateFactorial(int number) {
		if (number < 3 || number > 20) {
			throw new IllegalArgumentException("Function works only for numbers"
					+ " in range from 3 to 20");
		}
		int factorial = 1;
		for(int i = 1; i <= number; i++) {
			factorial *= i;
		}
		return factorial;
	}

}

package hr.fer.zemris.java.hw01;

import java.util.Scanner;
 
/**
 * Program calculates surface area and circumference of rectangle.
 * @author Daria Matković
 *
 */
public class Rectangle {

	/**
	 * This method executes when program starts.
	 * @param args Either 2 arguments entered as numbers specifying height
	 * and width or no arguments
	 */
	public static void main(String[] args) {
		int numberOfArguments = args.length;
		if (numberOfArguments != 0 && numberOfArguments != 2) {
			System.out.print("Program je potrebno pokrenuti bez argumenata ili"
					+ " sa 2 argumenta.");
			System.out.print(numberOfArguments);
			System.exit(0);
		}
		else if (numberOfArguments == 2) {
			try {
				double width = Double.parseDouble(args[0]);
				double height = Double.parseDouble(args[1]);
				printSurfaceAreaAndCircumference(height, width);
			} catch (Exception e) {
				System.out.print("Zadani argumenti trebaju biti pozitivni brojevi.");
			}
			System.exit(0);
		}
		
		double height, width;
		Scanner sc = new Scanner(System.in);
		
		String input = getValue("širinu", sc);
		while(!checkInput(input)) {
			input = getValue("širinu", sc);
		}
		width = Double.parseDouble(input);
		
		input = getValue("visinu", sc);
		while(!checkInput(input)) {
			input = getValue("visinu", sc);
		}
		height = Double.parseDouble(input);
		
		printSurfaceAreaAndCircumference(height, width);
		
		sc.close();
	}
	
	/**
	 * Gets value from user.
	 * @param value to enter
	 * @param sc is Scanner object
	 * @return entered value
	 */
	private static String getValue(String value, Scanner sc) {
		System.out.print("Unesite " + value + " > ");
		return sc.next();
	}
	
	/**
	 * Prints surface area and circumference for entered height and width.
	 * @param height of rectangle
	 * @param width of rectangle
	 */
	private static void printSurfaceAreaAndCircumference(double height, double width) {
		double surfaceArea = calculateSurfaceArea(height, width);
		double circumference = calculateCircumference(height, width);
		System.out.print("Pravokutnik širine " + width + " i visine " 
		+ height + " ima površinu " + surfaceArea + " te opseg " 
				+ circumference + ".");
	}
	
	/**
	 * Checks if input value is positive number.
	 * @param input is value to check
	 * @return true if input is positive number, otherwise false
	 */
	public static Boolean checkInput(String input) {
		try {
			double doubleInput = Double.parseDouble(input);
			if(doubleInput < 0) {
				System.out.println("Unijeli ste negativnu vrijednost.");
				return false;
			}
		} catch (NumberFormatException e) {
			System.out.println("'" + input + "'" + " se ne može protumačiti kao broj.");
			return false;
		}
		return true;
	}
	
	/**
	 * Calculates surface area.
	 * @param height of rectangle
	 * @param width of rectangle
	 * @return surface area
	 */
	public static double calculateSurfaceArea(double height, double width) {
		double surfaceArea = height * width;
		return surfaceArea;
	}

	/**
	 * Calculates circumference.
	 * @param height of rectangle
	 * @param width of rectangle
	 * @return rectangle's circumference
	 */
	public static double calculateCircumference(double height, double width) {
		double circumference = 2 * (height + width);
		return circumference;
	}
	
}

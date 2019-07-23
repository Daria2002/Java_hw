package hr.fer.zemris.java.hw01;

import java.util.Scanner;


/** 
 * This class represents program for calculating surface area and perimeter
 * of rectangle.
 * @author Daria Matković
 *
 */
public class Rectangle {

	/**
	 * This method is executed when program is run. 
	 * @param args program is executed with and without arguments. If there are
	 * arguments it's expected that width and height are given, otherwise 
	 * user gives those aruments in console.
	 */
	public static void main(String[] args) {
		
		double width = 0;
		double height = 0;
		
		if(args.length == 2) {
			try {
				width = Double.valueOf(args[0]);
				height = Double.valueOf(args[1]);
				
				if(width < 0 || height < 0) {
					System.out.println("Ne može se izračunati površina jer "
							+ "postoji negativna vrijednost kod mjera.");
				} else {
					printResult(width, height);
				}
				
			} catch (Exception e) {
				System.out.println("Postoji mjera koja se ne može protumačiti"
						+ " kao broj");
			}
			
			return;
		}
		
		Scanner sc = new Scanner(System.in);
		
		width = getValue("Unesite širinu > ", sc);
		height = getValue("Unesite visinu > ", sc);;
		
		System.out.println("Pravokutnik širine " + width + " i visine " +
				height + " ima površinu " + width * height + " te opseg " + 
				2*(width + height) + ".");
		
		sc.close();
	}
	
	/**
	 * This method prints result
	 * @param width width obtained from user
	 * @param height height obtained from user
	 */
	private static void printResult(double width, double height) {
		System.out.println("Pravokutnik širine " + width + " i visine " +
				height + " ima površinu " + width * height + " te opseg " + 
				2*(width + height) + ".");
	}
	
	/**
	 * This method represents console where user provides width or height
	 * @param message message about desired value
	 * @param sc scanner
	 * @return observed value
	 */
	private static double getValue(String message, Scanner sc) {
		double value;
		String input;
		
		while(true) {
			System.out.print(message);
			input = sc.next();
			
			try {
				value = Double.valueOf(input);
				
				if(value < 0) {
					System.out.println("Unijeli ste negativnu vrijednost");
					continue;
				}
				
				break;
			} catch (Exception e) {
				System.out.println("'" + input + "' se ne može protumačiti "
						+ "kao broj.");
			}
		}
		
		return value;
	}
}

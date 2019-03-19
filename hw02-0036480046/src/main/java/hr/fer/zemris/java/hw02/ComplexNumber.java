package hr.fer.zemris.java.hw02;

/**
 * Represents an unmodifiable complex number.
 * When comparing two double numbers tolerance for difference between two numbers
 * is 0.00001
 * @author Daria Matkovic
 *
 */
public class ComplexNumber {
	
	// after initialization real and imaginary are unmodifiable
	private final double real;
	private final double imaginary;
	
	/**
	 * Constructor initialize unmodifiable real and imaginary part of complex
	 * number
	 * @param real component of complex number
	 * @param imaginary component of complex number
	 */
	public ComplexNumber(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
	}
	
	/**
	 * Makes complex number from real number
	 * @param real given real number
	 * @return complex number created from real number
	 */
	public static ComplexNumber fromReal(double real) {
		ComplexNumber complexNumber = new ComplexNumber(real, 0);
		return complexNumber;
	}
	
	/**
	 * Makes complex number from imaginary number
	 * @param imaginary given imaginary number
	 * @return complex number created from imaginary number
	 */
	public static ComplexNumber fromImaginary(double imaginary) {
		ComplexNumber complexNumber = new ComplexNumber(0, imaginary);
		return complexNumber;
	}
	
	/**
	 * Calculates complex number from magnitude and angle
	 * @param magnitude of complex number
	 * @param angle of complex number
	 * @return calculated complex number from angle and magnitude
	 */
	public static ComplexNumber fromMagnitudeAndAngle(double magnitude,
			double angle) {
		double real = magnitude * Math.cos(angle);
		double imaginary = magnitude * Math.sin(angle);
		ComplexNumber complexNumber = new ComplexNumber(real, imaginary);
		
		return complexNumber;
	}
	
	/**
	 * Parse given string
	 * @param s given string to parse
	 * @return complex number
	 */
	public static ComplexNumber parse(String s) {
		char[] charArray = s.toCharArray();
		StringBuilder helpString = new StringBuilder();
		boolean realAdded = false;
		boolean	imaginaryAdded = false;
		boolean realNegative = false;
		boolean imaginaryNegative = false;
		boolean helpNegative = false;
		double real = 0;
		double imaginary = 0;
		for(int i = 0; i < charArray.length; i++) {
			if(realAdded && imaginaryAdded) {
				throw new IllegalArgumentException("Enter complex number in"
						+ " format a+bi.");
			}
			// if + is between imaginary and real number
			if('+' == (charArray[i]) && helpString.length() != 0) {
				real = Double.parseDouble(helpString.toString());
				realAdded = true;
				helpString = new StringBuilder();
			}
			// if minus is between imaginary and real number
			else if('-' == (charArray[i]) && helpString.length() != 0) {
				real = Double.parseDouble(helpString.toString());
				realAdded = true;
				helpString = new StringBuilder();
				imaginaryNegative = true;
				if(helpNegative) {
					realNegative = true;
				}
				helpNegative = false;
			}
			// if + is before first number
			else if("+".equals(charArray[i]) && helpString.length() == 0) {
				continue;
			}
			// if minus is before first number
			else if("-".equals(charArray[i]) && helpString.length() == 0) {
				helpNegative = true;
			}
			// if "i" is after number
			else if('i' == (charArray[i])) {
				if(helpString.length() < 1) {
					imaginary = Double.parseDouble("1");
					continue;
				}
				if(imaginaryAdded) {
					throw new IllegalArgumentException("Enter complex number in "
							+ "format a+bi.");
				}
				imaginary = Double.parseDouble(helpString.toString());
				imaginaryAdded = true;
				helpString = new StringBuilder();
				if(helpNegative) {
					imaginaryNegative = true;
				}
				helpNegative = false;
			}
			else {
				helpString.append(charArray[i]);
			}
		}
		
		if((charArray[charArray.length-1]) != 'i') {
			if(realAdded) {
				throw new IllegalArgumentException("Enter complex number in "
						+ "format a+bi.");
			}
			real = Double.parseDouble(helpString.toString());
			realAdded = true;
			helpString = new StringBuilder();
		}
		
		if(imaginaryNegative) {
			imaginary *= (-1);
		}
		if(realNegative) {
			real *= (-1);
		}
		
		ComplexNumber complexNumber = new ComplexNumber(real, imaginary);
		
		return complexNumber;
	}
	
	/**
	 * Gets real component of complex number
	 * @return real number
	 */
	public double getReal() {
		return this.real;
	}
	
	/**
	 * Gets imaginary component of complex number
	 * @return imaginary part of complex number
	 */
	public double getImaginary() {
		return this.imaginary;
	}
	
	/**
	 * Calculates magnitude of complex number
	 * @return magnitude of complex number
	 */
	public double getMagnitude() {
		return Math.sqrt(Math.pow(this.real, 2) + Math.pow(this.imaginary, 2));
	}
	
	/**
	 * Calculates angle of complex number
	 * @return angle of complex number
	 */
	public double getAngle() {
		return Math.atan2(this.imaginary,this.real);
	}
	
	/**
	 * Adds complex numbers.
	 * @param c complex number to add
	 * @return result of adding two complex number
	 */
	public ComplexNumber add(ComplexNumber c) {
		if(c == null) {
			throw new IllegalArgumentException("Can't operate with null.");
		}
		double real = this.real + c.real;
		double imaginary = this.imaginary + c.imaginary;
		ComplexNumber result = new ComplexNumber(real, imaginary);
		
		return result;
	}
	
	/**
	 * Sub complex numbers
	 * @param c complex number to sub
	 * @return result of sub operation
	 */
	public ComplexNumber sub(ComplexNumber c) {
		if(c == null) {
			throw new IllegalArgumentException("Can't operate with null.");
		}
		double real = this.real - c.real;
		double imaginary = this.imaginary - c.imaginary;
		ComplexNumber result = new ComplexNumber(real, imaginary);
		
		return result;
	}
	
	/**
	 * Multiply two complex numbers
	 * @param c complex number to multiply with
	 * @return result of multiplying two complex numbers
	 */
	public ComplexNumber mul(ComplexNumber c) {
		if(c == null) {
			throw new IllegalArgumentException("Can't operate with null.");
		}
		double real = this.real * c.real +
				this.imaginary * c.imaginary;
		double imaginary = this.imaginary * c.real +
				this.real * c.imaginary;
		ComplexNumber result = new ComplexNumber(real, imaginary);
		
		return result;
	}
	
	/**
	 * Divide complex numbers
	 * @param c complex number to divide with
	 * @return result of dividing complex numbers
	 */
	public ComplexNumber div(ComplexNumber c) {
		if(c == null) {
			throw new IllegalArgumentException("Can't operate with null.");
		}
		if(c.imaginary == 0 && c.real == 0) {
			throw new IllegalArgumentException("Division by 0.");
		}
		double real = (this.real * c.real + 
				this.imaginary * c.imaginary) /
				(Math.pow(this.getMagnitude(), 2));
		
		double imaginary = (-this.real * c.imaginary + 
				this.imaginary * c.real) /
				(Math.pow(this.getMagnitude(), 2));
		
		ComplexNumber complexNumber = new ComplexNumber(real, imaginary);
		
		return complexNumber;
	}
	
	/**
	 * Power of complex number
	 * @param n degree of power
	 * @return power of complex number
	 */
	public ComplexNumber power(int n) {
		if(n < 0) {
			throw new IllegalArgumentException("n must be larger or equal"
					+ " to 0 for power operation");
		}
		double real = Math.pow(this.getMagnitude(), n) * Math.cos(n * this.getAngle());
		double imaginary = Math.pow(this.getMagnitude(), n) * Math.sin(n * this.getAngle());
		ComplexNumber complexNumber = new ComplexNumber(real, imaginary);
		
		return complexNumber;
	}
	
	/**
	 * Root of n
	 * @param n degree
	 * @return complex number
	 */
	public ComplexNumber[] root(int n) {
		if(n <= 0) {
			throw new IllegalArgumentException("n must be larger than 0 for"
					+ " root operation.");
		}
		
		ComplexNumber[] complexNumbers = new ComplexNumber[n];
		
		double real;
		double imaginary;
		double magnitudeRoot = Math.pow(this.getMagnitude(), 1./n);
		
		for(int i = 0; i < n; i++) { 
			real =  magnitudeRoot * Math.cos((this.getAngle() + 2*i*Math.PI)/n);
			imaginary = magnitudeRoot * Math.sin((this.getAngle() + 2*i*Math.PI)/n);
			ComplexNumber complexNumber = new ComplexNumber(real, imaginary);
			complexNumbers[i] = complexNumber;
		}
		
		return complexNumbers;
	}
	
	/**
	 * Create string out of complex number
	 */
	public String toString() {
		String complexNumberString = Double.toString(this.real) + "+" +
				Double.toString(this.imaginary) + "i";
		return complexNumberString;
		
	}
	
}

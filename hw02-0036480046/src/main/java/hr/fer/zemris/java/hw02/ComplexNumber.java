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
		return new ComplexNumber(real, 0);
	}
	
	/**
	 * Makes complex number from imaginary number
	 * @param imaginary given imaginary number
	 * @return complex number created from imaginary number
	 */
	public static ComplexNumber fromImaginary(double imaginary) {
		return new ComplexNumber(0, imaginary);
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

		return new ComplexNumber(real, imaginary);
	}
	
	/**
	 * Parse given string. Given string is complex number. Real and imaginary
	 * components of given complex number are real numbers. Letter 'i' must be
	 * after imaginary component.
	 * @param s given string to parse
	 * @return complex number
	 */
	public static ComplexNumber parse(String s) {
		if("".equals(s) || s == null) {
			throw new IllegalArgumentException("Invalid expression.");
		}
		
		StringBuilder helpString = new StringBuilder();
		boolean realAdded = false;
		boolean	imaginaryAdded = false;
		boolean helpNegative = false;
		double real = 0;
		double imaginary = 0;
		char[] charArray = s.toCharArray();
		
		for(int i = 0; i < charArray.length; i++) {
			
			if(realAdded && imaginaryAdded) {
				throw new IllegalArgumentException("Enter complex number in"
						+ " format a+bi.");
			}
			
			// if + is between imaginary and real number
			if('+' == (charArray[i]) && helpString.length() > 0) {
				real = Double.parseDouble(helpString.toString());
				
				if(i+1 <= charArray.length-1) {
					if('+' == charArray[i+1] || '-' == charArray[i+1]) {
						throw new IllegalArgumentException("Multiple signs are not"
								+ " acceptable.");
					}
				}
				
				if(helpNegative) {
					real *= (-1);
				}
				
				helpNegative = false;
				realAdded = true;
				helpString = new StringBuilder();
				
			} else if('-' == (charArray[i]) && helpString.length() > 0) {
				// if minus is between imaginary and real number
				if(i+1 <= charArray.length-1) {
					if('+' == charArray[i+1] || '-' == charArray[i+1]) {
						throw new IllegalArgumentException("Multiple signs are not"
								+ " acceptable.");
					}
				}
				
				real = Double.parseDouble(helpString.toString());
				realAdded = true;
				helpString = new StringBuilder();
				if(helpNegative) {
					real *= (-1);
				}
				helpNegative = true;
				
			} else if('+' == (charArray[i]) && helpString.length() == 0) {
				// if + is before first number
				if('+' == charArray[i+1] || '-' == charArray[i+1]) {
					throw new IllegalArgumentException("Multiple signs are not"
							+ " acceptable.");
				}
				continue;
				
			} else if('-' == (charArray[i]) && helpString.length() == 0) {
				// if minus is before first number
				if('+' == charArray[i+1] || '-' == charArray[i+1]) {
					throw new IllegalArgumentException("Multiple signs are not"
							+ " acceptable.");
				}
				helpNegative = true;
				continue;
				
			} else if('i' == (charArray[i])) {

				if(i+1 <= charArray.length-1) {
					if(Character.isDigit(charArray[i+1])) {
						throw new IllegalArgumentException("Letter 'i' comes "
								+ "after imaginary number.");
					}
				}
				
				// if "i" is after number
				if(imaginaryAdded) {
					throw new IllegalArgumentException("Enter complex number in "
							+ "format a+bi.");
					
				} else if(helpString.length() < 1) {
					// if nothing stands before i
					imaginary = Double.parseDouble("1");
					
				} else if(helpString.length() >= 1){
					imaginary = Double.parseDouble(helpString.toString());
				}
				
				imaginaryAdded = true;
				helpString = new StringBuilder();
				
				if(helpNegative) {
					imaginary *= (-1);
				}
				helpNegative = false;
				
			} else {
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
			if(helpNegative) {
				real *= (-1);
			}
		}
		
		return new ComplexNumber(real, imaginary);
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
		double angle = Math.atan2(this.imaginary,this.real);
		
		return (angle < 0 ? angle + 2*Math.PI : angle);
	}
	
	/**
	 * Adds complex numbers.
	 * @param c c is complex number to add
	 * @return result of adding two complex number
	 */
	public ComplexNumber add(ComplexNumber c) {
		if(c == null) {
			throw new NullPointerException("Can't operate with null.");
		}
		
		double real = this.real + c.real;
		double imaginary = this.imaginary + c.imaginary;
		
		return new ComplexNumber(real, imaginary);
	}
	
	/**
	 * Sub complex numbers
	 * @param c c is complex number to sub
	 * @return result of sub operation
	 */
	public ComplexNumber sub(ComplexNumber c) {
		if(c == null) {
			throw new NullPointerException("Can't operate with null.");
		}
		
		double real = this.real - c.real;
		double imaginary = this.imaginary - c.imaginary;
		
		return new ComplexNumber(real, imaginary);
	}
	
	/**
	 * Multiply two complex numbers
	 * @param c c is complex number to multiply with
	 * @return result of multiplying two complex numbers
	 */
	public ComplexNumber mul(ComplexNumber c) {
		if(c == null) {
			throw new NullPointerException("Can't operate with null.");
		}
		
		double real = this.real * c.real - this.imaginary * c.imaginary;
		double imaginary = this.imaginary * c.real + this.real * c.imaginary;
		
		return new ComplexNumber(real, imaginary);
	}
	
	/**
	 * Divide complex numbers
	 * @param c c is complex number to divide with
	 * @return result of dividing complex numbers
	 */
	public ComplexNumber div(ComplexNumber c) {
		if(c == null) {
			throw new NullPointerException("Can't operate with null.");
		}
		
		double real = (this.real * c.real + 
				this.imaginary * c.imaginary) /
				(Math.pow(c.getReal(), 2) + Math.pow(c.getImaginary(), 2));
		
		double imaginary = (-this.real * c.imaginary + 
				this.imaginary * c.real) /
				(Math.pow(c.getReal(), 2) + Math.pow(c.getImaginary(), 2));
		
		return new ComplexNumber(real, imaginary);
	}
	
	/**
	 * Power of complex number
	 * @param n n is degree of power
	 * @return power of complex number
	 */
	public ComplexNumber power(int n) {
		if(n < 0) {
			throw new IllegalArgumentException("n must be larger or equal"
					+ " to 0 for power operation");
		}
		
		double real = Math.pow(this.getMagnitude(), n) * Math.cos(n * this.getAngle());
		double imaginary = Math.pow(this.getMagnitude(), n) * Math.sin(n * this.getAngle());
		
		return new ComplexNumber(real, imaginary);
	}
	
	/**
	 * nth root of complex number
	 * @param n n is degree
	 * @return complex number that is result of root operation.
	 */
	public ComplexNumber[] root(int n) {
		if(n <= 0) {
			throw new IllegalArgumentException("n must be larger than 0 for"
					+ " root operation.");
		}
		
		ComplexNumber[] complexNumbers = new ComplexNumber[n];
		
		double magnitudeRoot = Math.pow(this.getMagnitude(), 1./n);
		
		for(int i = 0; i < n; i++) { 
			double real =  magnitudeRoot * Math.cos((this.getAngle() + 2*i*Math.PI)/n);
			double imaginary = magnitudeRoot * Math.sin((this.getAngle() + 2*i*Math.PI)/n);
			ComplexNumber complexNumber = new ComplexNumber(real, imaginary);
			complexNumbers[i] = complexNumber;
		}
		
		return complexNumbers;
	}
	
	/**
	 * Create string of complex number
	 * @return string of complex number
	 */
	public String toString() {
		return Double.toString(this.real) + (this.imaginary >= 0 ? "+" : "") +
				Double.toString(this.imaginary) + "i";
	}
	
}

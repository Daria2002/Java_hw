package hr.fer.zemris.java.hw02;

/**
 * Represents an unmodifiable complex number.
 * When comparing two double numbers tolerance for difference between two numbers
 * is 0.00001
 * @author Daria Matkovic
 *
 */  
public class ComplexNumber {
	/** unmodifiable real part of complex number **/
	private final double real; 
	/** unmodifiable imaginary part of complex number **/
	private final double imaginary;
	/** tolerance used for comparing floating point numbers **/
	private static final double TOLERANCE = 0.00001;
	
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
	 * Parse given complex number. Real and imaginary components are real numbers.
	 * Letter 'i' must be after imaginary component.
	 * @param s given string to parse
	 * @return complex number
	 */
	public static ComplexNumber parse(String s) {
		checkCondition("".equals(s) || s == null);
		
		StringBuilder helpString = new StringBuilder();
		boolean realAdded = false;
		boolean	imaginaryAdded = false;
		boolean helpNegative = false;
		double real = 0;
		double imaginary = 0;
		char[] charArray = s.toCharArray();
		
		for(int i = 0; i < charArray.length; i++) {
			checkCondition(realAdded && imaginaryAdded);
			
			// if + is between imaginary and real number
			if('+' == (charArray[i]) && helpString.length() > 0) {
				checkCondition(i+1 <= charArray.length-1 &&
						('+' == charArray[i+1] || '-' == charArray[i+1]));
				
				real = makeNegative(Double.parseDouble(helpString.toString()),
						helpNegative);
				
				helpNegative = false;
				realAdded = true;
				helpString = new StringBuilder();
				continue;
				
			} else if('-' == (charArray[i]) && helpString.length() > 0) {
				// if minus is between imaginary and real number
				checkCondition(i+1 <= charArray.length-1 && 
						('+' == charArray[i+1] || '-' == charArray[i+1]));

				real = makeNegative(Double.parseDouble(helpString.toString()),
						helpNegative);
				realAdded = true;
				helpString = new StringBuilder();
				helpNegative = true;
				continue;
				
			} else if(('+' == (charArray[i]) || '-' == (charArray[i])) &&
					helpString.length() == 0) {
				// if + is before first number
				checkCondition('+' == charArray[i+1] || '-' == charArray[i+1]);
				helpNegative = '-' == charArray[i] ? true : false;
				continue;
				
			}  else if('i' == (charArray[i])) {
				checkCondition(i+1 <= charArray.length-1 &&
						Character.isDigit(charArray[i+1]) || imaginaryAdded);
				
				// length < 1 than complex number is "i"
				imaginary = makeNegative(helpString.length() < 1 ?
					Double.parseDouble("1") : Double.parseDouble(helpString.toString()),
					helpNegative);
				
				imaginaryAdded = true;
				helpString = new StringBuilder();
				helpNegative = false;
				continue;
			}
			
			helpString.append(charArray[i]);
		}
		
		if((charArray[charArray.length-1]) != 'i') {
			checkCondition(realAdded);

			real = makeNegative(Double.parseDouble(helpString.toString()),
					helpNegative);
			realAdded = true;
			helpString = new StringBuilder();
		}
		
		return new ComplexNumber(real, imaginary);
	}
	
	/**
	 * Throws exception is condition is true, otherwise does nothing
	 * @param condition condition to check
	 */
	private static void checkCondition(boolean condition) {
		if(condition) {
			throw new IllegalArgumentException("Invalid format.");
		}
	}
	
	/**
	 * Makes negative value of given value if condition is true, otherwise does
	 * nothing
	 * @param value value to make negative
	 * @return negative value of value if condition is true, otherwise return value
	 */
	private static double makeNegative(double value, boolean condition) {
		return condition ? value * (-1) : value;
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
		String realString = String.format("%.3f", real);
		String imaginaryString = String.format("%.3f", imaginary);
		
		return realString + (imaginary >= 0 ? "+" : "") + imaginaryString + "i";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(imaginary);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(real);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ComplexNumber other = (ComplexNumber) obj;
		if (Math.abs(imaginary - other.imaginary) > TOLERANCE)
			return false;
		if (Math.abs(real - other.real) > TOLERANCE)
			return false;
		
		return true;
	}
}

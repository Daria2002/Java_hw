package hr.fer.zemris.math;

import java.lang.Math;
import java.util.ArrayList;
import java.util.List;

/**
 * This class models complex number.
 * @author Daria Matković
 *
 */
public class Complex {
	/** real component of complex number **/
	private final double re;
	/** imaginary component of complex number **/
	private final double im;
	/** Complex number of zero **/
	public static final Complex ZERO = new Complex(0, 0);
	/** Complex number of onde **/
	public static final Complex ONE = new Complex(1, 0);
	/** complex number of -1 **/
	public static final Complex ONE_NEG = new Complex(-1, 0);
	/** complex number of i **/
	public static final Complex IM = new Complex(0, 1);
	/** complex number of -i **/
	public static final Complex IM_NEG = new Complex(0, -1);
	
	/**
	 * Gets real value of complex number
	 * @return real value 
	 */
	public double getRe() {
		return re;
	}

	/**
	 * Gets imaginary value of complex number
	 * @return imaginary value
	 */
	public double getIm() {
		return im;
	}

	/**
	 * Constructor that initialize complex number to zero
	 */
	public Complex() {
		re = ZERO.re;
		im = ZERO.im;
	}
	
	/**
	 * Constructor that initialize real and imaginary value of complex number to
	 * given value
	 * @param re real value of complex number
	 * @param im imaginary value of complex number
	 */
	public Complex(double re, double im) {
		this.re = re;
		this.im = im;
	}
	
	/**
	 * Returns module of complex number
	 * @return module of complex number
	 */
	public double module() {
		return Math.sqrt(Math.pow(this.re,2) + Math.pow(this.im,2));
	}
	
	/**
	 * Multiply given complex number with this
	 * @param c complex number to multiply with c
	 * @return result after multiplying c with this
	 */
	public Complex multiply(Complex c) {
		if(c == null) {
			throw new NullPointerException("Can't operate with null.");
		}
		
		double real = re * c.re - im * c.im;
		double imaginary = im * c.re + re * c.im;
		
		return new Complex(real, imaginary);
	}
	
	/**
	 * Divide this with c
	 * @param c complex number to divide with
	 * @return result of division 
	 */
	public Complex divide(Complex c) {
		if(c == null) {
			throw new NullPointerException("Can't operate with null.");
		}
		
		double real = (re * c.re + im * c.im) / (Math.pow(c.re, 2) + Math.pow(c.im, 2));
		double imaginary = (-re * c.im + im * c.re) / (Math.pow(c.re, 2) + Math.pow(c.im, 2));
		
		return new Complex(real, imaginary);
	}

	/**
	 * Add c to this
	 * @param c complex number to add 
	 * @return result of adding c to this
	 */
	public Complex add(Complex c) {
		if(c == null) {
			throw new NullPointerException("Can't operate with null.");
		}
		
		double real = re + c.re;
		double imaginary = im + c.im;
		
		return new Complex(real, imaginary);
	}
	
	/**
	 * Sub this with c
	 * @param c complex number to sub from this
	 * @return result after subtract operation
	 */
	public Complex sub(Complex c) {
		if(c == null) {
			throw new NullPointerException("Can't operate with null.");
		}
		
		double real = re - c.re;
		double imaginary = im - c.im;
		
		return new Complex(real, imaginary);
	}
	
	/**
	 * This method calculate negative value of this
	 * @return negative value of this
	 */
	public Complex negate() {
		return new Complex(-re, -im);
	}
	
	/**
	 * Power of complex number
	 * @param n degree of power
	 * @return power of complex number with degree of n
	 */
	public Complex power(int n) {
		if(n < 0) {
			throw new IllegalArgumentException("n must be larger or equal"
					+ " to 0 for power operation");
		}
		
		double real = Math.pow(this.module(), n) * Math.cos(n * this.getAngle());
		double imaginary = Math.pow(this.module(), n) * Math.sin(n * this.getAngle());
		
		return new Complex(real, imaginary);
	}
	
	/**
	 * nth root of complex number
	 * @param n n is degree
	 * @return complex number that is result of root operation.
	 */
	public List<Complex> root(int n) {
		if(n <= 0) {
			throw new IllegalArgumentException("n must be larger than 0 for"
					+ " root operation.");
		}
		
		List<Complex> complexNumbers = new ArrayList<Complex>();
		double magnitudeRoot = Math.pow(this.module(), 1./n);
		
		for(int i = 0; i < n; i++) { 
			double real =  magnitudeRoot * Math.cos((this.getAngle() + 2*i*Math.PI)/n);
			double imaginary = magnitudeRoot * Math.sin((this.getAngle() + 2*i*Math.PI)/n);

			complexNumbers.add(new Complex(real, imaginary));
		}
		
		return complexNumbers;
	}
	
	@Override
	public String toString() {
		String realString = String.format("%.1f", re);
		String imaginaryString = String.format("%.1f", Math.abs(im));
		
		return "(" + realString + (im >= 0 ? "+" : "-") + "i" + imaginaryString + ")";
	}

	/**
	 * Calculates angle of complex number
	 * @return angle of complex number
	 */
	private double getAngle() {
		double angle = Math.atan2(im ,this.re);
		
		return (angle < 0 ? angle + 2*Math.PI : angle);
	}
}

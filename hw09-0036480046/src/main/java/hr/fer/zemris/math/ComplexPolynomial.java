package hr.fer.zemris.math;

import java.util.Arrays;

/**
 * This class models polynomial on complex number so f(z) = zn*z^n+zn-1*z^(n-1)+..-+z1*z+z0
 * @author Daria Matković
 *
 */
public class ComplexPolynomial {

	/** factors **/
	Complex[] factors;
	
	/**
	 * Constructor for ComplexPolynomial that initialize factors
	 * @param factors coefficients z0...zn
	 */
	public ComplexPolynomial(Complex[] factors) {
		factors = Arrays.copyOf(factors, factors.length);
	}
	
	/**
	 * Calculates order
	 * @return order
	 */
	public short order() {
		return (short) (factors.length-1);
	}
	
	// computes a new polynomial this*p
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		
	}
	
	// computes first derivative of this polynomial; for example, for
	// (7+2i)z^3+2z^2+5z+1 returns (21+6i)z^2+4z+5
	public ComplexPolynomial derive() {
		
	}
	
	// computes polynomial value at given point z
	public Complex apply(Complex z) {
		for(Complex el : factors) {
			
		}
	}
	@Override
	public String toString() {
		
	}
	
}

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
	
	/**
	 * Computes a new polynomial this*p
	 * @param p polynomial to multiply with this
	 * @return result of multiply operation
	 */
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		Complex[] resultFactors = new Complex[p.order() + this.order()];
		
		for(int i = 0; i < resultFactors.length; i++) {
			resultFactors[i] = new Complex();
		}
		
		for(int i = 0; i < this.order(); i++) {
			for(int j = 0; j < p.order(); j++) {
				resultFactors[i + j].add(this.factors[i].multiply(p.factors[j]));
			}
		}
		
		return new ComplexPolynomial(resultFactors);
	}
	
	/**
	 * Computes first derivative of this polynomial
	 * @return first derivative of this polynomial
	 */
	public ComplexPolynomial derive() {
		// no more zn
		Complex[] resultFactors = new Complex[this.order() - 1];
		
		for(int i = 1; i < this.order(); i++) {
			resultFactors[i-1] = new Complex(this.factors[i].getRe() * i, 
					this.factors[i].getIm() * i);
		}
		
		return new ComplexPolynomial(resultFactors);
	}
	
	/**
	 * This method computes polynomial value at given point z
	 * @param z given point
	 * @return polynomial value at given point
	 */
	public Complex apply(Complex z) {
		Complex result = new Complex();
		
		for(int i = 0; i < factors.length; i++) {
			result = result.add(factors[i].multiply(z.power(i)));
		}

		return result;
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		
		for(int i = factors.length-1; i >= 0; i--) {
			result.append(factors[i]);
			if(i != 0) {
				result.append("*" + "z^" + i + "+");
			}
		}
		
		return result.toString();
	}
	
}

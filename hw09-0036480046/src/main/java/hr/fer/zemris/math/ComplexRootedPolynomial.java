package hr.fer.zemris.math;

import java.util.Arrays;

import javax.print.attribute.standard.Copies;

/**
 * This class represents polynomial f(z) = z0*(z-z1)*(z-z2)*...*(z-zn). z0 is 
 * constant and z1...zn are zero points 
 * @author Daria Matković
 *
 */
public class ComplexRootedPolynomial {
	
	/** constant **/
	private final Complex z0;
	/** roots **/
	private final Complex[] roots;
	
	/**
	 * Constructor that initializes zero point and roots
	 * @param constant zero point z0
	 * @param roots roots z1...zn
	 */
	public ComplexRootedPolynomial(Complex constant, Complex[] roots) {
		z0 = constant;
		this.roots = Arrays.copyOf(roots, roots.length);
	}
	
	/**
	 * Calculate polynomial of z
	 * @param z given complex number
	 * @return polynomial of z
	 */
	public Complex apply(Complex z) {
		Complex result = new Complex(z0.getRe(), z0.getIm());
		
		for(Complex complexNumber : roots) {
			result = result.multiply(z.sub(complexNumber));
		}
		
		return result;
	}
	
	/**
	 * Converts this to ComplexPolynom
	 * @return polynomial of complex number
	 */
	public ComplexPolynomial toComplexPolynom() {
		Complex[] factors = new Complex[(int) Math.pow(2, (roots.length + 1 - 1))];
		
		// multiply with z0
		factors[1] = z0;
		factors[0] = z0.multiply(roots[0].negate());
		
		Complex[] tempArray = Arrays.copyOf(factors, factors.length);
		for(int i = 1; i < roots.length; i++) {
			
			tempArray = Arrays.copyOf(factors, factors.length);
			// increase degree because first element of bracket is z
			for(int j = tempArray.length-1; j >= 0 ; j--) {
				if(tempArray[j] != null) {
					tempArray[j+1] = tempArray[j];
					tempArray[j] = null;
				}
			}
			
			// to increased values add product of next root multiplied with every
			// element of result
			for(int j = 0; j < factors.length ; j++) {
				if(factors[j] != null) {
					if(tempArray[j] == null) {
						tempArray[j] = new Complex();
					}
					tempArray[j] = tempArray[j].add(roots[i].negate().multiply(factors[j]));
				}
			}
			
			factors = Arrays.copyOf(tempArray, tempArray.length);
		}
		
		return new ComplexPolynomial(factors);
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append(z0);
	
		for(Complex root : roots) {
			result.append("*");
			result.append("(z-" + root + ")");
		}
		
		return result.toString();
	}
	
	/**
	 * finds index of closest root for given complex number z that is within
	 * threshold; if there is no such root, returns -1
	 * @param z complex number
	 * @param treshold threshold for finding index of closest root
	 * @return index of closest root
	 */
	public int indexOfClosestRootFor(Complex z, double threshold) {
		if(z == null) {
			return -1;
		}
		
		int index = 0;
		double smallestDif = threshold * 1E5;
		int bestIndex = -1;
		
		for(Complex el : roots) {
			if(threshold > (z.sub(el)).module()) {
				if(smallestDif > (z.sub(el)).module()) {
					smallestDif = (z.sub(el)).module();
					bestIndex = index;
				}
			}
			
			index++;
		}
		
		return bestIndex;
	}
}

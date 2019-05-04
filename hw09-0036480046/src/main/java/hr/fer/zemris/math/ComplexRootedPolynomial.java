package hr.fer.zemris.math;

import java.util.Arrays;

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
			result = result.add(z.sub(complexNumber));
		}
		
		return result;
	}
	
	/**
	 * Converts this to ComplexPolynom
	 * @return
	 */
	public ComplexPolynomial toComplexPolynom() {
		
	}
	
	@Override
	public String toString() {
		
	}
	
	/**
	 * finds index of closest root for given complex number z that is within
	 * threshold; if there is no such root, returns -1
	 * @param z complex number
	 * @param treshold threshold for finding index of closest root
	 * @return index of closest root
	 */
	public int indexOfClosestRootFor(Complex z, double threshold) {
		int index = 0;
		
		for(Complex el : roots) {
			if(threshold <= Math.abs(el.sub(z).getRe()) && threshold <= Math.abs(el.sub(z).getIm())) {
				return index;
			}
			
			index++;
		}
		
		return -1;
	}
}

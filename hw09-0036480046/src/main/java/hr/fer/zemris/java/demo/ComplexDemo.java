package hr.fer.zemris.java.demo;

import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * Demonstration program for complex number
 * @author Daria Matković
 *
 */
public class ComplexDemo {

	/**
	 * This method is executed when program is run
	 * @param args takes no arguments
	 */
	public static void main(String[] args) {
		ComplexRootedPolynomial crp = new ComplexRootedPolynomial(
				new Complex(2,0), new Complex[] {Complex.ONE, Complex.ONE_NEG,
						Complex.IM, Complex.IM_NEG});
		
		ComplexPolynomial cp = crp.toComplexPolynom();
		System.out.println(crp);
		System.out.println(cp);
		System.out.println(cp.derive());
	}
	
}

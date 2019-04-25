package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.Tester;

/**
 * Demonstrates tester
 * @author Daria Matković
 *
 */
public class TesterDemo {
	/**
	 * This method executes when program starts
	 * @param args not needed
	 */
	public static void main(String[] args) {
		class EvenIntegerTester implements Tester {
			 public boolean test(Object obj) {
			 if(!(obj instanceof Integer)) return false;
			 Integer i = (Integer)obj;
			 return i % 2 == 0;
			 }
		}
		
		Tester t = new EvenIntegerTester();
		System.out.println(t.test("Ivo"));
		System.out.println(t.test(22));
		System.out.println(t.test(3));
	}
}

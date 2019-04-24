package hr.fer.zemris.java.hw07.demo2;

/**
 * This class represents demo for PrimesCollection
 * @author Daria Matković
 *
 */
public class PrimesDemo1 {
	
	/**
	 * This method is executed when program is run
	 * @param args no arguments
	 */
	public static void main(String[] args) {
		PrimesCollection primesCollection = new PrimesCollection(5); // 5: how many of them
		for(Integer prime : primesCollection) {
			System.out.println("Got prime: " + prime);
		}
	}
}

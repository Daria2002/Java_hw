package hr.fer.zemris.java.hw07.demo2;

/**
 * This class represents demo for PrimesCollection
 * @author Daria Matković
 *
 */
public class PrimesDemo2 {
	
	/**
	 * This method is executed when program is run
	 * @param args no arguments
	 */
	public static void main(String[] args) {
		PrimesCollection primesCollection3 = new PrimesCollection(2);
		for(Integer prime : primesCollection3) {
			for(Integer prime2 : primesCollection3) {
				System.out.println("Got prime pair: " + prime + ", " + prime2);
			}
		}
	}
}

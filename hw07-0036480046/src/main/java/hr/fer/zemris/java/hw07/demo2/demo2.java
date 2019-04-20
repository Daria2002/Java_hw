package hr.fer.zemris.java.hw07.demo2;

public class demo2 {

	public static void main(String[] args) {

		PrimesCollection primesCollection = new PrimesCollection(5); // 5: how many of them
		for(Integer prime : primesCollection) {
			System.out.println("Got prime: " + prime);
		}
		
		System.out.println("8 prime numbers :");
		
		PrimesCollection primesCollection2 = new PrimesCollection(8); // 5: how many of them
		for(Integer prime : primesCollection2) {
			System.out.println("Got prime: " + prime);
		}
	}
	
}

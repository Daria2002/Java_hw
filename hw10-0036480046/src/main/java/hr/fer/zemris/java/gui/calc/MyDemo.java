package hr.fer.zemris.java.gui.calc;

public class MyDemo {

	public static void main(String[] args) {
	
		Double test1 = Double.valueOf("4" + ".");
		Double test2 = Double.valueOf("4");
		
		try {
			System.out.println(Integer.valueOf(String.valueOf(test1)));
		} catch (Exception e) {
			System.out.println("konverzija test1 nije ok");
		}
	
		System.out.println(Integer.valueOf(test2.toString()));
	}
}

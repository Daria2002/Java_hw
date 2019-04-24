package hr.fer.zemris.java.hw07.observer2;

/**
 * Demo program for IntegerStorageObserver, SquareValue and DoubleValue
 * @author Daria Matković
 *
 */
public class ObserverExample {

	/**
	 * Method executed when program is run
	 * @param args no arguments
	 */
	public static void main(String[] args) {
		
		IntegerStorage istorage = new IntegerStorage(20);

		SquareValue sv = new SquareValue();

		istorage.addObserver(new ChangeCounter());
		istorage.addObserver(new SquareValue());
		istorage.addObserver(new DoubleValue(3));

		istorage.addObserver(sv);
		//istorage.addObserver(sv); checks if exception occurs 

		istorage.setValue(5);
		istorage.setValue(2);
		istorage.setValue(25);
		istorage.setValue(13);
		istorage.setValue(22);
		istorage.setValue(15);
	}
}

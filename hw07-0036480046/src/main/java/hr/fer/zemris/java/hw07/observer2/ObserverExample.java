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

		istorage.addObserver(new ChangeCounter());
		istorage.addObserver(new SquareValue());
		istorage.addObserver(new DoubleValue(3));

		istorage.setValue(5);
		istorage.setValue(2);
		istorage.setValue(25);
		istorage.setValue(13);
		istorage.setValue(22);
		istorage.setValue(15);
	}
}

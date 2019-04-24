package hr.fer.zemris.java.hw07.observer1;

/**
 * This program prints number od value changes when value is channged
 * @author Daria Matković
 *
 */
public class ChangeCounter implements IntegerStorageObserver {
	/** counter **/
	private int counter;
	
	/**
	 * Constructor that initialize counter to 0
	 */
	public ChangeCounter() {
		this.counter = 0;
	}
	
	@Override
	public void valueChanged(IntegerStorage istorage) {
		System.out.println("Number of value changes since tracking: " + ++counter);
	}
}

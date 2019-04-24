package hr.fer.zemris.java.hw07.observer2;

/**
 * This program prints double value of value when value is changed, but only n times, 
 * if double value is already printed n times, observer is removed
 * @author Daria Matković
 *
 */
public class DoubleValue implements IntegerStorageObserver {
	/** number of times double value can be printed **/
	private int n;
	/** counter **/
	private int counter;
	
	/**
	 * Sets number of times that double value is printed
	 * @param n
	 */
	public DoubleValue(int n) {
		this.n = n;
		this.counter = 0;
	}

	@Override
	public void valueChanged(IntegerStorageChange isChange) {
		if(this.n > counter++) {
			System.out.println("Double value: " + isChange.getNewValue() * 2);
		} else {
			isChange.getIntegerStorage().removeObserver(this);
		}
	}
}

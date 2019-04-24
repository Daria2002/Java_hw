package hr.fer.zemris.java.hw07.observer2;

/**
 * During the notifications dispatching, for a single change a single instance of the
 * IntegerStorageChange class is created, and a reference to that instance is passed to
 * all registered observers
 * @author Daria Matković
 *
 */
public class IntegerStorageChange {
	/** integer storage **/
	private IntegerStorage integerStorage;
	/** old value **/
	private int oldValue;
	/** new value **/
	private int newValue;
	
	/**
	 * Constructor that initialize integerStorage, oldValue and newValue
	 * @param integerStorage integer storage
	 * @param oldValue old value
	 * @param newValue new value
	 */
	public IntegerStorageChange(IntegerStorage integerStorage, int oldValue, int newValue) {
		this.integerStorage = integerStorage;
		this.oldValue = oldValue;
		this.newValue = newValue;
	}
	
	/**
	 * Gets integer storage
	 * @return integer storage
	 */
	public IntegerStorage getIntegerStorage() {
		return this.integerStorage;
	}
	
	/**
	 * Returns old value
	 * @return old value
	 */
	public int getOldValue() {
		return this.oldValue;
	}
	
	/**
	 * Returns new value
	 * @return new value
	 */
	public int getNewValue() {
		return this.newValue;
	}	
}

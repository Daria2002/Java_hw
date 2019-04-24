package hr.fer.zemris.java.hw07.observer2;

/**
 * This interface defines method that is executed when value is changed.
 * @author Daria Matković
 *
 */
public interface IntegerStorageObserver {
	/**
	 * Method is executed when value is changed
	 * @param istorage storage of value
	 */
	public void valueChanged(IntegerStorageChange istorage);
}

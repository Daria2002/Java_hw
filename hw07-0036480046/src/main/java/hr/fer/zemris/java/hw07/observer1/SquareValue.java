package hr.fer.zemris.java.hw07.observer1;

/**
 * This class implements IntegerStorageObserver and prints square value of value
 * when value is changed
 * @author Daria Matković
 *
 */
public class SquareValue implements IntegerStorageObserver {

	@Override
	public void valueChanged(IntegerStorage istorage) {
		int value = istorage.getValue();
		System.out.println(
				"Provided new value: " + value + ", square is " + value * value);
	}
}

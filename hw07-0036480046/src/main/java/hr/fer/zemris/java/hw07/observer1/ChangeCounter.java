package hr.fer.zemris.java.hw07.observer1;

public class ChangeCounter implements IntegerStorageObserver {

	private int counter;
	
	public ChangeCounter() {
		counter = 0;
	}
	
	@Override
	public void valueChanged(IntegerStorage istorage) {
		System.out.println("Number of value changes since tracking: " + ++counter);
	}
}

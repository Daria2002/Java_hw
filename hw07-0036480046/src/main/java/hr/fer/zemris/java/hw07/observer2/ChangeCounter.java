package hr.fer.zemris.java.hw07.observer2;

public class ChangeCounter implements IntegerStorageObserver {

	private int counter;
	
	public ChangeCounter() {
		this.counter = 0;
	}
	
	@Override
	public void valueChanged(IntegerStorageChange isChange) {
		System.out.println("Number of value changes since tracking: " + ++counter);
	}
}

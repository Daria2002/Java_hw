package hr.fer.zemris.java.hw07.observer1;

public class DoubleValue implements IntegerStorageObserver {

	private int n;
	private int counter;
	
	public DoubleValue(int n) {
		this.n = n;
		this.counter = 0;
	}

	@Override
	public void valueChanged(IntegerStorage istorage) {
		if(this.n < counter++) {
			System.out.println("Double value: " + istorage.getValue() * 2);
		} else {
			istorage.removeObserver(this);
		}
	}
}

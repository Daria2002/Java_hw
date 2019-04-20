package hr.fer.zemris.java.hw07.observer2;

public class DoubleValue implements IntegerStorageObserver {

	private int n;
	private int counter;
	
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

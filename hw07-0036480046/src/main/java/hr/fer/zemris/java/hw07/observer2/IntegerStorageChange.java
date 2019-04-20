package hr.fer.zemris.java.hw07.observer2;

public class IntegerStorageChange {

	private IntegerStorage integerStorage;
	private int oldValue;
	private int newValue;
	
	public IntegerStorageChange(IntegerStorage integerStorage, int oldValue, int newValue) {
		this.integerStorage = integerStorage;
		this.oldValue = oldValue;
		this.newValue = newValue;
	}
	
	public IntegerStorage getIntegerStorage() {
		return this.integerStorage;
	}
	public int getOldValue() {
		return this.oldValue;
	}
	public int getNewValue() {
		return this.newValue;
	}	
}

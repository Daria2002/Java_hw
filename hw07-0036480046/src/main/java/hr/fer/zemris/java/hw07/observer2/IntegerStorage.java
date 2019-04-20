package hr.fer.zemris.java.hw07.observer2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class IntegerStorage {

	private int value;
	private List<IntegerStorageObserver> observers;
	private List<IntegerStorageObserver> removeList;
	
	public IntegerStorage(int initialValue) {
		this.value = initialValue;
		this.observers = new ArrayList<>();
		this.removeList = new ArrayList<>();
	}
	
	// add the observer in observers if not already there ...
	public void addObserver(IntegerStorageObserver observer) {
		observers.add(observer);
	}

	// remove the observer from observers if present ...
	public void removeObserver(IntegerStorageObserver observer) {
		removeList.add(observer);
	}
	
	// remove all observers from observers list ...
	public void clearObservers() {
		observers.clear();
	}
	
	public int getValue() {
		return value;
	}
	
	public void setValue(int value) {
		// Only if new value is different than the current value:
		if(this.value != value) {
			// Update current value
			this.value = value;
			// Notify all registered observers
			if(observers!=null) {
				for(IntegerStorageObserver observer : removeList) {
					observers.remove(observer);
				}
				
				IntegerStorageChange integerStorageChange = new IntegerStorageChange(
						this, getValue(), value);
				
				
				for(IntegerStorageObserver observer : observers) {
					observer.valueChanged(integerStorageChange);
				}
			}
		}
	}
}

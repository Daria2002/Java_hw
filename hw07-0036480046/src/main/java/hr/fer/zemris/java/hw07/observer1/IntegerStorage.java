package hr.fer.zemris.java.hw07.observer1;

import java.util.ArrayList;
import java.util.List;

public class IntegerStorage {

	private int value;
	private List<IntegerStorageObserver> observers;
	
	public IntegerStorage(int initialValue) {
		this.value = initialValue;
		this.observers = new ArrayList<IntegerStorageObserver>();
	}
	
	// add the observer in observers if not already there ...
	public void addObserver(IntegerStorageObserver observer) {
		// if observer is not in observers, add observer in observers
		if(observerIndex(observer) == -1) {
			observers.add(observer);
		}
	}
	
	private int observerIndex(IntegerStorageObserver observer) {
		for(int i = 0; i < observers.size(); i++) {
			if(observer.equals(observers.get(i))) {
				return i;
			}
		}
		return -1;
	}

	// remove the observer from observers if present ...
	public void removeObserver(IntegerStorageObserver observer) {
		int observerIndex = observerIndex(observer);
		
		if(observerIndex != -1) {
			observers.remove(observerIndex);
		}
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
			if(observers != null) {
				for(IntegerStorageObserver observer : observers) {
					observer.valueChanged(this);
				}
			}
		}
	}
}

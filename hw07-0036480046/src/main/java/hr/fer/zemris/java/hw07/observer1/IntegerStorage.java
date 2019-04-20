package hr.fer.zemris.java.hw07.observer1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class IntegerStorage {

	private int value;
	private List<IntegerStorageObserver> observers;
	
	public IntegerStorage(int initialValue) {
		this.value = initialValue;
		this.observers = new ArrayList<>();
	}
	
	// add the observer in observers if not already there ...
	public void addObserver(IntegerStorageObserver observer) {
		observers.add(observer);
	}

	// remove the observer from observers if present ...
	public void removeObserver(IntegerStorageObserver observer) {
		Iterator<IntegerStorageObserver> iterator = observers.iterator();
		
		while(iterator.hasNext()){
			IntegerStorageObserver observerElement = iterator.next();
			
			if (observerElement.equals(observer)) {
		        // Remove the current element from the iterator and the list.
				iterator.remove();
		    }
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
			if(observers!=null) {
				Iterator<IntegerStorageObserver> iterator = observers.iterator();
				
				while(iterator.hasNext()){
					IntegerStorageObserver observer = iterator.next();
					observer.valueChanged(IntegerStorage.this);
					
				}
			}
		}
	}
}

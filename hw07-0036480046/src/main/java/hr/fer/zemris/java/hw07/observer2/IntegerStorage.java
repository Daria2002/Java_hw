package hr.fer.zemris.java.hw07.observer2;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class IntegerStorage {

	/** value **/
	private int value;
	/** list of added observers **/
	private List<IntegerStorageObserver> observers;
	/** list of observers to remove **/
	private List<IntegerStorageObserver> removeList;
	
	/**
	 * Constructor that sets value and initialize observers list and list for
	 * observers to remove
	 * @param initialValue initial value
	 */
	public IntegerStorage(int initialValue) {
		this.value = initialValue;
		this.observers = new ArrayList<>();
		this.removeList = new ArrayList<>();
	}
	
	/**
	 * Add the observer in observers if not already there
	 * @param observer observer to add
	 */
	public void addObserver(IntegerStorageObserver observer) {
		Objects.requireNonNull(observer);
		for(IntegerStorageObserver ob : observers) {
			if(ob.equals(observer)) {
				throw new IllegalArgumentException("Observer already in list.");
			}
		}
		observers.add(observer);
	}

	/**
	 * Remove the observer from observers if present
	 * @param observer observer to remove
	 */
	public void removeObserver(IntegerStorageObserver observer) {
		Objects.requireNonNull(observer);
		for(IntegerStorageObserver ob : observers) {
			if(ob.equals(observer)) {
				removeList.add(observer);
			}
		}
	}
	
	/**
	 * Remove all observers from observers list
	 */
	public void clearObservers() {
		if(observers != null) {
			for(IntegerStorageObserver ob : observers) {
				removeList.add(ob);
			}
		}
	}
	
	/**
	 * Returns observer value
	 * @return observer's value
	 */
	public int getValue() {
		return value;
	}
	
	/**
	 * Sets observer value
	 * @param value value
	 */
	public void setValue(int value) {
		// Only if new value is different than the current value:
		if(this.value != value) {
			// Update current value
			this.value = value;
			// Notify all registered observers
			if(observers!=null) {
				if(removeList != null) {
					for(IntegerStorageObserver observer : removeList) {
						observers.remove(observer);
					}
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

package hr.fer.zemris.java.custom.scripting.exec;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * ObjectMultistack represents implementation similar to Map, but a special kind of Map.
 * While Map allows to store for each key only a single value, ObjectMultistack allows to
 * store multiple values for same key and it provide a stack-like abstraction. Keys for
 * ObjectMultistack are instances of the class String. Values associated with those keys are 
 * instances of class ValueWrapper.
 * @author Daria Matković
 *
 */
public class ObjectMultistack {
	/** Map that represents collection of saved data in ObjectMultistack. **/
	private Map<String, MultistackEntry> collection = 
			new HashMap<String, ObjectMultistack.MultistackEntry>();
	
	/**
	 * This method is implements command for saving given valueWrapper on stack named keyName
	 * @param keyName key of the value
	 * @param valueWrapper value to push on stack with key keyName
	 */
	public void push(String keyName, ValueWrapper valueWrapper) {
		Objects.requireNonNull(keyName, "key can't be null");
		Objects.requireNonNull(valueWrapper, "value can't be null");
		
		if(isEmpty(keyName)) {
			collection.put(keyName, new MultistackEntry(valueWrapper, null));
			return;
		}
		
		MultistackEntry oldEntry = collection.get(keyName);
		MultistackEntry newEntry = new MultistackEntry(valueWrapper, oldEntry);
		
		collection.put(keyName, newEntry);
	}
	
	/**
	 * This method implements command that deletes and returns last saved element
	 * on stack with key keyName
	 * @param keyName key of stack from which last value need to be deleted
	 * @return last saved element on stack, that element also need to be deleted
	 */
	public ValueWrapper pop(String keyName) {
		Objects.requireNonNull(keyName, "key can't be null");
		if(isEmpty(keyName)) {
			throw new IllegalArgumentException("Stack for given keyName is empty");
		}
		
		MultistackEntry entry = collection.get(keyName);
		ValueWrapper value = entry.value;
		
		entry = entry.next;
		collection.put(keyName, entry);
		
		return value;
	}
	
	/**
	 * Returns last element saved on stack with key keyName
	 * @param keyName key of stack from which last value need to be returned
	 * @return last saved element on stack with key keyName
	 */
	public ValueWrapper peek(String keyName) {
		Objects.requireNonNull(keyName, "key can't be null");
		if(isEmpty(keyName)) {
			throw new IllegalArgumentException("Stack for given keyName is empty");
		}

		return collection.get(keyName).value;
	}
	
	/**
	 * Check if stack with key keyName is empty
	 * @param keyName key for stack to be checked
	 * @return true if stack with given key is empty, otherwise false
	 */
	public boolean isEmpty(String keyName) {
		if(collection.get(keyName) == null) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * Nested class that represents entry for ObjectMultistack
	 * @author Daria Matković
	 *
	 */
	private static class MultistackEntry {
		/** value that need to be added on stack **/
		ValueWrapper value;
		/** next entry in linked list **/
		MultistackEntry next;
		
		/**
		 * Constructor for initialization of ObjectMultistack entry
		 * @param value value on stack
		 * @param next next value in linked list
		 */
		public MultistackEntry(ValueWrapper value, MultistackEntry next) {
			this.value = value;
			this.next = next;
		}
	}
}

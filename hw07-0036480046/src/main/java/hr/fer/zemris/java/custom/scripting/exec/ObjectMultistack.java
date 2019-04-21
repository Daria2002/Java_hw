package hr.fer.zemris.java.custom.scripting.exec;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ObjectMultistack {
	private Map<String, MultistackEntry> collection = 
			new HashMap<String, ObjectMultistack.MultistackEntry>();
	
	public void push(String keyName, ValueWrapper valueWrapper) {
		Objects.requireNonNull(keyName);
		
		if(isEmpty(keyName)) {
			collection.put(keyName, new MultistackEntry(valueWrapper, null));
			return;
		}
		
		MultistackEntry oldEntry = collection.get(keyName);
		MultistackEntry newEntry = new MultistackEntry(valueWrapper, oldEntry);
		
		collection.put(keyName, newEntry);
	}
	
	public ValueWrapper pop(String keyName) {
		if(isEmpty(keyName)) {
			throw new IllegalArgumentException("Stack for given keyName is empty");
		}
		
		MultistackEntry entry = collection.get(keyName);
		ValueWrapper value = entry.value;
		
		entry = entry.next;
		collection.put(keyName, entry);
		
		return value;
	}
	
	public ValueWrapper peek(String keyName) {
		if(isEmpty(keyName)) {
			throw new IllegalArgumentException("Stack for given keyName is empty");
		}

		return collection.get(keyName).value;
	}
	
	public boolean isEmpty(String keyName) {
		if(collection.get(keyName) == null) {
			return true;
		}
		
		return false;
	}
	
	private static class MultistackEntry {
		ValueWrapper value;
		MultistackEntry next;
		
		public MultistackEntry(ValueWrapper value, MultistackEntry next) {
			this.value = value;
			this.next = next;
		}
	}
}

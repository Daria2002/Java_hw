package hr.fer.zemris.java.custom.collections;

import javax.swing.text.TabExpander;

public class SimpleHashtable<K, V> {

	private TableEntry<K, V>[] table;
	private int size;
	private static final int INITIAL_CAPACITY = 16;
	
	public static class TableEntry<K, V> {
		private K key;
		private V value;
		private TableEntry<K, V> next;
		
		public TableEntry(K key, V value, TableEntry<K, V> next) {
			this.key = key;
			this.value = value;
			this.next = next;
		}

		public V getValue() {
			return value;
		}

		public void setValue(V value) {
			this.value = value;
		}

		public K getKey() {
			return key;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((key == null) ? 0 : key.hashCode());
			result = prime * result + ((next == null) ? 0 : next.hashCode());
			result = prime * result + ((value == null) ? 0 : value.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			TableEntry other = (TableEntry) obj;
			if (key == null) {
				if (other.key != null)
					return false;
			} else if (!key.equals(other.key))
				return false;
			return true;
		}

		@Override
		public String toString() {
			return key + "=" + value;
		}
	}
	
	public SimpleHashtable() {
		this(INITIAL_CAPACITY);
	}
	
	public SimpleHashtable(int capacity) {
		if(capacity < 1) {
			throw new IllegalArgumentException("Capacity must be at least 1.");
		}
		capacity = getRightCapacity(capacity);
		table = new TableEntry[capacity];
		size = 0;
	}
	
	private int getRightCapacity(int capacity) {
		// power of two
		double power = Math.pow(2, 0);
	
		while(power < capacity) {
			power *= 2;
		}
		
		return (int)power;
	}

	public void put(K key, V value) {
		if(key == null) {
			throw new NullPointerException("Key can't be null.");
		}
		
		int slotIndex = getKeySlot(key);
		TableEntry<K, V> newTableEntry = new TableEntry<K, V>(key, value, null);
		
		// if empty
		if(table[slotIndex] == null) {
			table[slotIndex] = newTableEntry;
			return;
		}
		
		// helpTable points on first tableEntry of slotIndex
		TableEntry<K, V> helpTable = table[slotIndex];
		while(helpTable.next != null && helpTable.equals(newTableEntry)) {
			helpTable = helpTable.next;
		}
		
		// add new tableEntry on end
		if(helpTable.next == null) {
			helpTable.next = newTableEntry;
		} else {
			// if key already saved, change value
			helpTable.value = newTableEntry.value;
		}
	}
	/*
	public V get(Object key) {
		return 
	}
	*/
	private int getKeySlot(K key) {
		int hashNumber = Math.abs(key.hashCode());
		
		return hashNumber % table.length;
	}
}

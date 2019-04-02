package hr.fer.zemris.java.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K, V>>{

	private int modificationCount;
	private TableEntry<K, V>[] table;
	private int size;
	private static final int INITIAL_CAPACITY = 16;
	private static final double OVERFLOW_FACTOR = 0.75;
	
	@Override
	public Iterator<SimpleHashtable.TableEntry<K, V>> iterator() {
		return new SimpleHashtableIterator();
	}
	
	public class SimpleHashtableIterator implements
	Iterator<SimpleHashtable.TableEntry<K, V>> {
		private int slotIndex;
		private TableEntry<K, V> currentHead;
		private int nestedModificationCount;
		private boolean removeMode;
		
		public SimpleHashtableIterator() {
			slotIndex = -1;
			currentHead = null;
			nestedModificationCount = modificationCount;
			removeMode = false;
		}
		/**
		 * Returns true if iteration has more elements
		 */
		public boolean hasNext() {
			checkModification();
			
			if(currentHead != null && currentHead.next != null) {
				return true;
			}
			TableEntry<K, V> helpTableEntry;
			for(int i = slotIndex+1; i < table.length; i++) {
				helpTableEntry = table[i];
				
				if(helpTableEntry != null) {
					return true;
				}
			}
			return false;
	    }

	    public SimpleHashtable.TableEntry next() {
	    	checkModification();
	    	removeMode = false;
	    	
	    	if(currentHead == null || currentHead != null && currentHead.next == null) {
	    		while(slotIndex < table.length) {
	    			slotIndex++;
		    		currentHead = table[slotIndex];
		    		
		    		if(currentHead != null) {
		    			break;
		    		}
	    		}
	    		if(currentHead == null) {
	    			throw new NoSuchElementException("No more elements in table");
	    		}
	    		
	    	} else {
	    		currentHead = currentHead.next;
	    	}
	    	
	    	return currentHead;
	    }

	    public void remove() {
	    	checkModification();
	    	
	    	if(removeMode) {
	    		throw new IllegalStateException("Current element already removed.");
	    	}
	    	removeMode = true;
	    	
	    	if(currentHead == null) {
	    		return;
	    	}
	    	
			TableEntry<K, V> helpTable = table[slotIndex];
			TableEntry<K, V> previousTable = table[slotIndex];
			
			// get all tableEntries in slot
			while(helpTable != null) {
				// if key is found
				if(helpTable.equals(currentHead)) {
					
					// if table entry is first element
					if(previousTable.next == helpTable.next) {
						table[slotIndex] = previousTable.next;
					} else {
						previousTable.next = helpTable.next;
					}
					
					size--;
					currentHead = previousTable;
					
					if(currentHead == null) {
						for(int i = slotIndex-1; i >= 0; i--) {
							if(table[i] != null) {
								currentHead = table[i];
								while (currentHead.next != null) {
									currentHead = currentHead.next;
								}
								slotIndex = i;
								break;
							}
						}
					}
					if(currentHead == null) {
						slotIndex = -1;
					}
					
					nestedModificationCount++;
					modificationCount++;
					return;
				}
				// previousTable is current table
				previousTable = helpTable;
				// current table is next table
				helpTable = helpTable.next;
			}
	    }
	    

		private void checkModification() {
			if(nestedModificationCount != modificationCount) {
				throw new ConcurrentModificationException("Hashtable changed"
						+ " while iterating.");
			}
		}
	}
	
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
		modificationCount = 0;
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
			size++;
			modificationCount++;
			return;
		}
		
		// helpTable points on first tableEntry of slotIndex
		TableEntry<K, V> helpTable = table[slotIndex];
		while(helpTable.next != null && !helpTable.equals(newTableEntry)) {
			helpTable = helpTable.next;
		}
		
		// add new tableEntry on end
		if(helpTable.next == null) {
			helpTable.next = newTableEntry;
			modificationCount++;
			size++;
			sizeCheck();
			
		} else {
			// if key already saved, change value
			helpTable.value = newTableEntry.value;
		}
	}
	
	public int getCapacity() {
		return table.length;
	}
	
	private void sizeCheck() {
		if(size < 0.75 * table.length) {
			return;
		}
		
		SimpleHashtable<K, V> newHashtable = 
				new SimpleHashtable<K, V>(table.length * 2);
		
		modificationCount++;
		
		for(int i = 0; i < table.length; i++) {
			TableEntry<K, V> helpTable = table[i];
			
			while(helpTable != null) {
				newHashtable.put(helpTable.key, helpTable.value);
				helpTable = helpTable.next;
			}
		}
		
		this.table = newHashtable.table;
	}
	
	public V get(Object key) {
		// the key with value null doesn't exists
		if(key == null) {
			return null;
		}
		
		TableEntry<K, V> table = searchTable(key);
		if(table != null) {
			return table.value;
		}
		
		return null;
	}
	
	public int size() {
		return size;
	}
	
	private TableEntry<K, V> searchTable(Object key) {
		int slotIndex = getKeySlot((K)key);
		TableEntry<K, V> helpTable = table[slotIndex];
		
		while(helpTable != null) {
			if(helpTable.key == key) {
				return helpTable;
			}
			helpTable = helpTable.next;
		}
		
		return null;
	}
	
	public boolean containsKey(Object key) {
		return searchTable(key) != null;
	}
	
	public boolean containsValue(Object value) {
		// in every slot search for value
		for(int i = 0; i < table.length; i++) {
			TableEntry<K, V> helpTable = table[i];
			
			while(helpTable != null) {
				if(helpTable.value == value) {
					return true;
				}
				helpTable = helpTable.next;
			}
		}
		return false;
	}
	
	public void remove(Object key) {
		// if key doesn't exists or key is null
		if(key == null || !containsKey(key)) {
			return;
		}
		
		int slotIndex = getKeySlot((K)key);
		TableEntry<K, V> helpTable = table[slotIndex];
		TableEntry<K, V> previousTable = table[slotIndex];
		
		// get all tableEntries in slot
		while(helpTable != null) {
			
			// if key is found
			if(helpTable.key.equals(key)) {
				
				// if table entry is first element
				if(previousTable.next == helpTable.next) {
					table[slotIndex] = previousTable.next;
				} else {
					previousTable.next = helpTable.next;
				}
				
				modificationCount++;
				size--;
				return;
			}
			// previousTable is current table
			previousTable = helpTable;
			// current table is next table
			helpTable = helpTable.next;
		}
	}
	
	public boolean isEmpty() {
		return size <= 0;
	}
	
	@Override
	public String toString() {
		String tableEntryString = "[";
		
		// in every slot search for value
		for(int i = 0; i < table.length; i++) {
			TableEntry<K, V> helpTable = table[i];
			
			while(helpTable != null) {
				tableEntryString += helpTable.toString();
				if(helpTable.next != null) {
					tableEntryString += ", ";
				}
				helpTable = helpTable.next;
			}
		}
		
		return tableEntryString + "]";
	}
	
	public void clear() {
		// in every slot search for value
		for(int i = 0; i < table.length; i++) {
			table[i] = null;
		}
		modificationCount++;
		size = 0;
	}
	
	private int getKeySlot(K key) {
		int hashNumber = Math.abs(key.hashCode());
		
		return hashNumber % table.length;
	}
}

package hr.fer.zemris.java.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This class represents simple hash table that implements iterable. This hash
 * table has space management implemented. The size of hash table is doubling
 * if 75% of table is full. Expected hash table methods are implemented.
 * @author Daria Matković
 *
 * @param <K> generic key type
 * @param <V> generic value type
 */
public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K, V>>{
	/** counts number of modifications **/
	private int modificationCount;
	/** hash table **/
	private TableEntry<K, V>[] table;
	/** number of elements in hash table **/
	private int size;
	/** initial capacity of hash table **/
	private static final int INITIAL_CAPACITY = 16;
	/** factor for controlling free space **/
	private static final double OVERFLOW_FACTOR = 0.75;
	
	@Override
	public Iterator<SimpleHashtable.TableEntry<K, V>> iterator() {
		return new SimpleHashtableIterator();
	}
	
	/**
	 * Class that implements Iterator for elements in hash table.
	 * @author Daria Matković
	 *
	 */
	public class SimpleHashtableIterator implements
	Iterator<SimpleHashtable.TableEntry<K, V>> {
		/** last visited slot index **/
		private int slotIndex;
		/** last visited element **/
		private TableEntry<K, V> currentHead;
		/** checks if changes happened **/
		private int nestedModificationCount;
		/** checks if remove method is called more times for the same element **/
		private boolean removeMode;
		
		/**
		 * Constructor for SimpleHashtableIterator that initialize slotIndex, 
		 * currentHead, nestedModificationCount and removeMode
		 */
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
		
		/**
		 * Returns next element in hash table
		 */
	    public SimpleHashtable.TableEntry next() {
	    	checkModification();
	    	removeMode = false;
	    	
	    	if(currentHead == null || currentHead != null && currentHead.next == null) {
	    		slotIndex++;
	    		while(slotIndex < table.length) {
		    		currentHead = table[slotIndex];
		    		
		    		if(currentHead != null) {
		    			break;
		    		}
		    		slotIndex++;
	    		}
	    		if(currentHead == null) {
	    			throw new NoSuchElementException("No more elements in table");
	    		}
	    		
	    	} else {
	    		currentHead = currentHead.next;
	    	}
	    	
	    	return currentHead;
	    }
	    
	    /**
	     * Removes last visited element in hash table
	     */
	    public void remove() {
	    	checkModification();
	    	
	    	if(removeMode) {
	    		throw new IllegalStateException("Current element already removed.");
	    	}
	    	removeMode = true;
	    	
	    	if(currentHead == null) {
	    		return;
	    	}
	    	
	    	//SimpleHashtable.this.remove(currentHead.key);
	    	
			TableEntry<K, V> helpTable = table[slotIndex];
			TableEntry<K, V> previousTable = table[slotIndex];
			
			// get all tableEntries in slot
			while(helpTable != null) {
				// if current element is currentHead
				if(helpTable.equals(currentHead)) {
					
					replaceElements(previousTable, helpTable, slotIndex);
					
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
					// if currentHead still null, that means the only element
					// was removed and currentHead is null, collection is now
					// empty
					if(currentHead == null) {
						slotIndex = -1;
					}
					
					modificationCount++;
					nestedModificationCount++;
					
					return;
				}
				// previousTable is current table
				previousTable = helpTable;
				// current table is next table
				helpTable = helpTable.next;
			}
	    }
	    
	    /**
	     * Checks if changes happened and throws exception if needed
	     */
		private void checkModification() {
			if(nestedModificationCount != modificationCount) {
				throw new ConcurrentModificationException("Hashtable changed"
						+ " while iterating.");
			}
		}
	}
	
	/**
	 * Class that implements hash table entry
	 * @author Daria Matković
	 *
	 * @param <K> generic key type
	 * @param <V> generic value type
	 */
	public static class TableEntry<K, V> {
		/** entry's key **/
		private K key;
		/** entry's value **/
		private V value;
		/** next element in hash table **/
		private TableEntry<K, V> next;
		
		/**
		 * Constructor for table entry that initialize key, value and next element
		 * @param key entry's key
		 * @param value entry's value 
		 * @param next next element
		 */
		public TableEntry(K key, V value, TableEntry<K, V> next) {
			this.key = key;
			this.value = value;
			this.next = next;
		}

		/**
		 * Returns entry's value
		 * @return value
		 */
		public V getValue() {
			return value;
		}

		/**
		 * Sets entry's value
		 * @param value value to set
		 */
		public void setValue(V value) {
			this.value = value;
		}

		/**
		 * Gets entry's key
		 * @return key
		 */
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
	
	/**
	 * Constructor for SimpleHashtable that initialize table on initial capacity
	 */
	public SimpleHashtable() {
		this(INITIAL_CAPACITY);
	}
	
	/**
	 * Constructor that initialize hash table on given capacity and throws 
	 * exception if given capacity is lower than 1
	 * @param capacity given capacity
	 */
	public SimpleHashtable(int capacity) {
		if(capacity < 1) {
			throw new IllegalArgumentException("Capacity must be at least 1.");
		}
		
		capacity = getRightCapacity(capacity);
		table = new TableEntry[capacity];
		modificationCount = 0;
		size = 0;
	}
	
	/**
	 * Gets first power of 2 that is higher than given capacity
	 * @param capacity given capacity
	 * @return new capacity
	 */
	private int getRightCapacity(int capacity) {
		// power of two
		double power = Math.pow(2, 0);
		while(power < capacity) {
			power *= 2;
		}
		
		return (int)power;
	}

	/**
	 * Inserts element in hash table. If element of given key is already in table
	 * it change value, otherwise add element at the end.
	 * @param key given key
	 * @param value given value
	 */
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
		
		if(helpTable.equals(newTableEntry)) {
			// if key already saved, change value
			helpTable.value = newTableEntry.value;
		} else {
			// add new table on end
			helpTable.next = newTableEntry;
			modificationCount++;
			size++;
			sizeCheck();
		}
	}
	
	/**
	 * Returns hash table capacity
	 * @return capacity
	 */
	public int getCapacity() {
		return table.length;
	}
	
	/**
	 * Checks and adjust table's size if table is 75% full.
	 */
	private void sizeCheck() {
		if(size < OVERFLOW_FACTOR * table.length) {
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
	
	/**
	 * Gets value of element with given key
	 * @param key given key
	 * @return value 
	 */
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
	
	/**
	 * Returns size
	 * @return size
	 */
	public int size() {
		return size;
	}
	
	/**
	 * Searches element with given key
	 * @param key given key
	 * @return null if element is not in table, otherwise element with given key
	 */
	private TableEntry<K, V> searchTable(Object key) {
		if(key == null) {
			return null;
		}
		
		int slotIndex = getKeySlot((K)key);
		TableEntry<K, V> helpTable = table[slotIndex];
		
		while(helpTable != null) {
			if(helpTable.key.equals(key)) {
				return helpTable;
			}
			helpTable = helpTable.next;
		}
		
		return null;
	}
	
	/**
	 * Checks if table contains element with given key
	 * @param key given key
	 * @return true if element with given key is in table, otherwise false
	 */
	public boolean containsKey(Object key) {
		return searchTable(key) != null;
	}
	
	/**
	 * Checks if table contains element with given value
	 * @param value given value
	 * @return true if table contains element with given value, otherwise false
	 */
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
	
	/**
	 * Removes element with given key
	 * @param key given key
	 */
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
				
				replaceElements(previousTable, helpTable, slotIndex);
				
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
	
	/**
	 * When removing element in table this function manages that previous element
	 * becomes the next element of removed element
	 * @param previousTable previous element of removed element in table
	 * @param helpTable next element of removed element
	 * @param slotIndex last visited slot
	 */
	private void replaceElements(TableEntry<K, V> previousTable,
			TableEntry<K, V> helpTable, int slotIndex) {
		// if table entry is first element
		if(previousTable.next == helpTable.next) {
			table[slotIndex] = previousTable.next;
		} else {
			// previous element doesn't point on removed element anymore
			// now it points on element after removed element
			previousTable.next = helpTable.next;
		}
	}
	
	/**
	 * Checks if hash table is empty
	 * @return true is table is empty, otherwise false
	 */
	public boolean isEmpty() {
		return size <= 0;
	}
	
	@Override
	public String toString() {
		int numberOfElements = size;
		String tableEntryString = "[";
		
		// in every slot search for value
		for(int i = 0; i < table.length; i++) {
			TableEntry<K, V> helpTable = table[i];
			
			while(helpTable != null) {
				tableEntryString += helpTable.toString();
				// put , after every element except the last one
				if(numberOfElements > 1) {
					tableEntryString += ", ";
				}
				
				helpTable = helpTable.next;
				numberOfElements--;
			}
		}
		
		return tableEntryString + "]";
	}
	
	/**
	 * Removes all elements from hash table
	 */
	public void clear() {
		// in every slot search for value
		for(int i = 0; i < table.length; i++) {
			table[i] = null;
		}
		
		modificationCount++;
		size = 0;
	}
	
	/**
	 * Calculates slot where element with given key needs to be saved
	 * @param key given key
	 * @return slot index
	 */
	private int getKeySlot(K key) {
		int hashNumber = Math.abs(key.hashCode());
		
		return hashNumber % table.length;
	}
}

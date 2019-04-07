package hr.fer.zemris.lsystems.impl;

/**
 * Class implements dictionary functionality.
 * @author Daria Matković
 *
 * @param <K> type of dictionary key
 * @param <V> type of dictionary value
 */
public class Dictionary<K, V> {
	/**
	 * Class that represents entry in dictionary 
	 * @author Daria Matković
	 *
	 * @param <KeyType> type of dictionary entry key
	 * @param <ValueType> type of dictionary entry value
	 */
	private static class DictionaryEntry<KeyType, ValueType> {
		/** entry key **/
		private KeyType key;
		/** entry value **/
		private ValueType value;
		 
		/**
		 * Constructor for dictionary entry
		 * @param key entry key
		 * @param value entry value
		 */
		public DictionaryEntry(KeyType key, ValueType value) {
			if(key == null) {
				throw new IllegalArgumentException("Key must not be null value.");
			}
			this.key = key;
			this.value = value;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((key == null) ? 0 : key.hashCode());
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
			DictionaryEntry other = (DictionaryEntry) obj;
			if (key == null) {
				if (other.key != null)
					return false;
			} else if (!key.equals(other.key))
				return false;
			return true;
		}
	}

	/**
	 * Array that represents collection of dictionary entries
	 */
	ArrayIndexedCollection<DictionaryEntry<K, V>> array;
	
	/**
	 * Dictionary constructor that initialize array of dictionary entries
	 */
	public Dictionary() {
		 array = new ArrayIndexedCollection<>();
	}
	
	/**
	 * Checks if erray is empty
	 * @return true if array is empty, otherwise false
	 */
	public boolean isEmpty() {
		return array.isEmpty();
	}
	
	/**
	 * Size of array
	 * @return size of array
	 */
	public int size() {
		return array.size();
	}
	
	/**
	 * Removes all elements in array
	 */
	public void clear() {
		array.clear();
	}
	
	/**
	 * Insert key and value in dictionary
	 * @param key entry's key
	 * @param value entry's value
	 */
	public void put(K key, V value) {
		if(key == null) {
			throw new IllegalArgumentException("Key can't be null");
		}
		
		DictionaryEntry<K, V> newDictionaryEntry = new DictionaryEntry<>(key, value);
		int index = array.indexOf(newDictionaryEntry);
		
		if(index < 0) {
			array.add(newDictionaryEntry);
		} else {
			DictionaryEntry<K, V> dictionaryEntry = array.get(index);
			dictionaryEntry.value = value;
		}
	}
	
	/**
	 * Returns value for a given key
	 * @param key of searched element
	 * @return value for a given key
	 */
	public V get(Object key) {
		ElementsGetter<DictionaryEntry<K, V>> elementsGetter = array.createElementsGetter();
		
		if(key == null) {
			return null;
		}
		
		DictionaryEntry<K, V> dictionaryEntry;
		while (elementsGetter.hasNextElement()) {
			dictionaryEntry = elementsGetter.getNextElement();
			if(dictionaryEntry.key.equals(key)) {
				return dictionaryEntry.value;
			}
		}
		return null;
	}
}

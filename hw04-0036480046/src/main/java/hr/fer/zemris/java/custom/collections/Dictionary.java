package hr.fer.zemris.java.custom.collections;

/**
 * Class implements dictionary functionality.
 * @author Daria Matković
 *
 * @param <K> type of dictionary key
 * @param <V> type of dictionary value
 */
public class Dictionary<K, V> {

	private static class DictionaryEntry<KeyType, ValueType> {
		private KeyType key;
		private ValueType value;
		
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

	ArrayIndexedCollection<DictionaryEntry<K, V>> array;
	
	public Dictionary() {
		 array = new ArrayIndexedCollection<>();
	}
	
	public boolean isEmpty() {
		return array.isEmpty();
	}
	
	public int size() {
		return array.size();
	}
	
	public void clear() {
		array.clear();
	}
	
	public void put(K key, V value) {
		DictionaryEntry<K, V> newDictionaryEntry = new DictionaryEntry<>(key, value);
		int index = array.indexOf(newDictionaryEntry);
		
		if(index < 0) {
			array.add(newDictionaryEntry);
		} else {
			DictionaryEntry<K, V> dictionaryEntry = array.get(index);
			dictionaryEntry.value = value;
		}
	}
	
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

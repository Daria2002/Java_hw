package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DictionaryTest {

	private static Dictionary<String, Integer> initializeDictionary() {
		Dictionary<String, Integer> dictionary = new Dictionary<>();
		dictionary.put("Door", 4);
		dictionary.put("Chair", 5);
		dictionary.put("Window", 6);
		
		return dictionary;
	}
	
	@Test
	void testKeyNull() {
		Dictionary<String, Integer> dictionary = initializeDictionary();
		
		assertThrows(IllegalArgumentException.class, () -> dictionary.put(null, 7));
	}

	@Test
	void testIsEmpty() {
		Dictionary<String, Integer> dictionary1 = initializeDictionary();
		Dictionary<String, Integer> dictionary2 = new Dictionary<>();
		
		assertFalse(dictionary1.isEmpty());
		assertTrue(dictionary2.isEmpty());
	}
	
	@Test
	void testSize() {
		Dictionary<String, Integer> dictionary = initializeDictionary();
		
		assertEquals(3, dictionary.size());
	}
	
	@Test
	void testClear() {
		Dictionary<String, Integer> dictionary = initializeDictionary();
		int sizeBeforeClear = dictionary.size();
		dictionary.clear();
		int sizeAfterClear = dictionary.size();
		
		assertEquals(3, sizeBeforeClear);
		assertEquals(0, sizeAfterClear);
	}
	
	@Test
	void testPut() {
		Dictionary<String, Integer> dictionary = initializeDictionary();
		dictionary.put("Chair", 7);
		dictionary.put("Table", 5);
		
		assertEquals(4, dictionary.size());
		assertEquals(7, dictionary.get("Chair"));
		assertEquals(5, dictionary.get("Table"));
	}
	
	@Test
	void testGet() {
		Dictionary<String, Integer> dictionary = initializeDictionary();
		
		assertEquals(3, dictionary.size());
		assertEquals(4, dictionary.get("Door"));
		assertEquals(5, dictionary.get("Chair"));
		assertEquals(6, dictionary.get("Window"));
	}
}

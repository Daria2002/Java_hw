package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

class SimpleHashtableTest {

	@Test
	void testPut() {
		SimpleHashtable<String, Integer> table = new SimpleHashtable<String, Integer>(5);
		
		table.put("sun", 3);
		table.put("rain", 4);
		table.put("wind", 4);
		

		assertEquals(3, table.get("sun"));
		assertEquals(4, table.get("rain"));
		assertEquals(4, table.get("wind"));
	}

	@Test
	void testNonExisting() {
		SimpleHashtable<String, Integer> table = new SimpleHashtable<String, Integer>(5);
		
		table.put("sun", 3);
		table.put("rain", 4);
		table.put("wind", 4);
		

		assertEquals(null, table.get("snow"));
		assertEquals(null, table.get(null));
	}
	
	@Test
	void testSmallCapacity() {
		assertThrows(IllegalArgumentException.class, () -> 
		new SimpleHashtable<String, Integer>(0));
	}
	
	@Test
	void testIllegalState() {
		SimpleHashtable<String, Integer> table = new SimpleHashtable<String, Integer>(3);
		
		table.put("sun", null);
		table.put("rain", 4);
		table.put("wind", 4);
		
		Iterator<SimpleHashtable.TableEntry<String,Integer>> iter = table.iterator();
		
		iter.remove();
		iter.next();
		iter.remove();
		
		assertThrows(IllegalStateException.class, () -> iter.remove());
	}
	
	@Test
	void testContainsNull() {
		SimpleHashtable<String, Integer> table = new SimpleHashtable<String, Integer>(3);
		
		table.put("sun", null);
		table.put("rain", 4);
		table.put("wind", 4);
		
		assertTrue(table.containsValue(null));
		assertFalse(table.containsKey(null));
	}
	
	@Test
	void testCapacity() {
		// test that capacity is 4
		SimpleHashtable<String, Integer> table = new SimpleHashtable<String, Integer>(3);
		
		table.put("sun", 3);
		table.put("rain", 4);
		table.put("wind", 4);
		table.put("storm", 5);
		
		assertEquals(3, table.get("sun"));
		assertEquals(4, table.get("rain"));
		assertEquals(4, table.get("wind"));
		assertEquals(5, table.get("storm"));
	}
	
	@Test
	void testNullException() {
		SimpleHashtable<String, Integer> table = new SimpleHashtable<String, Integer>(4);
		
		table.put("sun", 3);
		table.put("rain", 4);
		table.put("wind", 4);
		table.put("storm", 5);

		assertThrows(NullPointerException.class, () -> table.put(null, 8));
	}
	
	@Test
	void testGet() {
		SimpleHashtable<String, Integer> table = new SimpleHashtable<String, Integer>(4);
		
		table.put("sun", 3);
		table.put("rain", 4);
		table.put("wind", 4);
		table.put("storm", 5);

		assertEquals(null, table.get("snow"));
		assertEquals(4, table.get("rain"));
	}
	
	@Test
	void testContainsKey() {
		SimpleHashtable<String, Integer> table = new SimpleHashtable<String, Integer>(4);
		
		table.put("sun", 3);
		table.put("rain", 4);
		table.put("wind", 4);
		table.put("storm", 5);

		assertTrue(table.containsKey("storm"));
		assertFalse(table.containsKey("snow"));
	}
	
	@Test
	void testContainsValue() {
		SimpleHashtable<String, Integer> table = new SimpleHashtable<String, Integer>(4);
		
		table.put("sun", 3);
		table.put("rain", 4);
		table.put("wind", 4);
		table.put("storm", 5);

		assertTrue(table.containsValue(3));
		assertTrue(table.containsValue(4));
		assertFalse(table.containsKey(10));
	}
	
	@Test
	void testRemove() {
		SimpleHashtable<String, Integer> table = new SimpleHashtable<String, Integer>(7);
		
		table.put("sun", 3);
		table.put("rain", 4);
		table.put("wind", 4);
		table.put("storm", 5);
		table.put("see", 3);
		table.put("lake", 4);
		table.put("tree", 4);
		table.put("river", 5);
		
		table.remove("sun");
		table.remove("wind");
		table.remove("river");
		table.remove("tree");
		table.remove("lake");

		assertTrue(table.containsKey("storm"));
		assertTrue(table.containsValue(5));
		assertTrue(table.containsKey("rain"));
		assertTrue(table.containsValue(4));
		assertTrue(table.containsKey("see"));
		assertTrue(table.containsValue(3));
		assertFalse(table.containsKey("lake"));
		assertFalse(table.containsKey("tree"));
		assertFalse(table.containsKey("river"));
		assertFalse(table.containsKey("sun"));
		assertFalse(table.containsKey("wind"));
	}
	
	@Test
	void testIsEmpty() {
		SimpleHashtable<String, Integer> table = new SimpleHashtable<String, Integer>(7);
		
		table.put("sun", 3);
		table.put("rain", 4);
		table.put("wind", 4);
		table.put("storm", 5);
		
		boolean isEmptyBeforeRemoval = table.isEmpty();
		
		table.remove("sun");
		table.remove("rain");
		table.remove("wind");
		table.remove("storm");
		
		boolean isEmptyAfterRemoval = table.isEmpty();
		
		assertTrue(isEmptyAfterRemoval);
		assertFalse(isEmptyBeforeRemoval);
	}
	
	@Test
	void testToString() {
		SimpleHashtable<String, Integer> table = new SimpleHashtable<String, Integer>(7);
		
		table.put("sun", 3);
		table.put("rain", 4);
		
		assertEquals("[sun=3, rain=4]", table.toString());		
	}
	
	@Test
	void testSize() {
		SimpleHashtable<String, Integer> table = new SimpleHashtable<String, Integer>(7);
		
		int size1 = table.size();
		table.put("sun", 3);
		table.put("rain", 4);
		
		int size2 = table.size();
		table.put("wind", 4);
		table.put("storm", 5);
		
		int size3 = table.size();
		table.remove("sun");
		table.remove("rain");
		table.remove("wind");
		
		int size4 = table.size();
		
		assertEquals(0, size1);
		assertEquals(2, size2);
		assertEquals(4, size3);
		assertEquals(1, size4);
	}
	
	@Test
	void testClear() {
		SimpleHashtable<String, Integer> table = new SimpleHashtable<String, Integer>(7);

		table.put("sun", 3);
		table.put("rain", 4);
		boolean isEmptyBefore = table.isEmpty();
		
		table.clear();
		boolean isEmptyAfter = table.isEmpty();
		
		assertFalse(isEmptyBefore);
		assertTrue(isEmptyAfter);
	}

	@Test
	void testCapacityManagment() {
		SimpleHashtable<String, Integer> table = new SimpleHashtable<String, Integer>(1);
		
		table.put("sun", 3);
		table.put("rain", 4);
		table.put("wind", 4);
		table.put("storm", 5);
		table.remove("storm");
		
		assertEquals(3, table.get("sun"));
		assertEquals(4, table.get("rain"));
		assertTrue(table.containsKey("wind"));
		assertTrue(table.containsValue(4));
		assertFalse(table.containsKey("storm"));
	}
	
	@Test
	void testOverridingValue() {
		SimpleHashtable<String, Integer> table = new SimpleHashtable<String, Integer>(1);
		
		table.put("sun", 3);
		table.put("rain", 4);
		table.put("river", 5);
		table.put("sun", null);
		table.put("rain", 3);
		table.put("tree", 7);
		
		assertEquals(null, table.get("sun"));
		assertEquals(3, table.get("rain"));
		assertEquals(7, table.get("tree"));
		assertEquals(5, table.get("river"));
	}
	
	@Test
	void testDoubling() {
		SimpleHashtable<String, Integer> table = new SimpleHashtable<String, Integer>(1);
		
		table.put("sun", 3);
		table.put("rain", 4);
		table.put("river", 5);
		
		assertEquals(8, table.getCapacity());
	}
	
	@Test
	void testIterator() {
		SimpleHashtable<String, Integer> nature = new SimpleHashtable<String, Integer>(1);
		
		nature.put("sun", 1);
		nature.put("rain", 2);
		nature.put("river", 3);
		
		int i = 1;
		for(SimpleHashtable.TableEntry<String,Integer> element : nature) {
			assertEquals(i++, element.getValue());
		}
	}
	
	@Test
	void testIteratorHasNext() {
		SimpleHashtable<String, Integer> nature = new SimpleHashtable<String, Integer>(1);
		nature.put("sun", 1);
		nature.put("rain", 2);
		nature.put("river", 3);

		Iterator<SimpleHashtable.TableEntry<String,Integer>> iter = nature.iterator();
		for(SimpleHashtable.TableEntry<String,Integer> element : nature) {
			assertTrue(iter.hasNext()); 
		}
		assertTrue(iter.hasNext());
		
		int i = 1;
		for(SimpleHashtable.TableEntry<String,Integer> element : nature) {
			assertTrue(iter.hasNext());
			assertEquals(i++, iter.next().getValue());
		}
		assertFalse(iter.hasNext());
		
		nature.put("sun", 4);
		nature.put("rain", 7);  
		nature.put("river", 8);
		assertEquals(3, nature.size());
		assertFalse(iter.hasNext());
		
		nature.put("snow", 4);
		assertThrows(ConcurrentModificationException.class, () -> iter.hasNext());
	}
	
	@Test
	void testIteratorRemove() {
		SimpleHashtable<String, Integer> nature = new SimpleHashtable<String, Integer>(10);

		nature.put("rain", 1);
		nature.put("river", 2);
		nature.put("sun", 3);
		
		Iterator<SimpleHashtable.TableEntry<String,Integer>> iter = nature.iterator();
		
		int i = 1;
		while(iter.hasNext()) {
			assertTrue(iter.hasNext());
			assertEquals(i++, iter.next().getValue());
			iter.remove();
		}
	}
	
	@Test
	void testRemoveElement() {
		SimpleHashtable<String, Integer> nature = new SimpleHashtable<String, Integer>(10);

		nature.put("rain", 1);
		nature.put("river", 2);
		nature.put("sun", 3);
		
		Iterator<SimpleHashtable.TableEntry<String,Integer>> iter = nature.iterator();
		
		assertTrue(iter.hasNext());
		iter.next();
		assertTrue(iter.hasNext());
		assertTrue(iter.hasNext());
		assertTrue(iter.hasNext());
		iter.remove();
		
		assertFalse(nature.containsKey("rain"));
		assertFalse(nature.containsValue(1));
	}
	
	@Test
	void testNoSuchElement() {
		SimpleHashtable<String, Integer> nature = new SimpleHashtable<String, Integer>(10);

		nature.put("rain", 1);
		Iterator<SimpleHashtable.TableEntry<String,Integer>> iter = nature.iterator();
		iter.next();
		
		assertThrows(NoSuchElementException.class, () -> iter.next());
	}
}

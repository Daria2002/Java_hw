package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

@SuppressWarnings("javadoc")
public class LinkedListIndexedCollectionTest {

	private static final String RAIN = "rain";
	private static final String SUN = "sun";
	private static final String SNOW = "snow";
	private static final String WIND = "wind";
	private static final String STORM = "storm";
	
	@Test
	public void testConstructor1() {
		assertEquals(0, new LinkedListIndexedCollection().size());
	} 
	
	@Test 
	public void testConstructor2() {
		LinkedListIndexedCollection collectionToAdd = 
				new LinkedListIndexedCollection();
		collectionToAdd.add(RAIN);
		collectionToAdd.add(SUN);
		collectionToAdd.add(SNOW);
		collectionToAdd.add(WIND);
		LinkedListIndexedCollection collectionToCheck =
				new LinkedListIndexedCollection(collectionToAdd);
		
		assertEquals(RAIN, collectionToCheck.get(0));
		assertEquals(SUN, collectionToCheck.get(1));
		assertEquals(SNOW, collectionToCheck.get(2));
		assertEquals(WIND, collectionToCheck.get(3));
	}
	
	@Test
	public void testAdd() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add(RAIN);
		collection.add(SUN);
		collection.add(SNOW);
		collection.add(WIND);
		
		assertEquals(RAIN, collection.get(0));
		assertEquals(SUN, collection.get(1));
		assertEquals(SNOW, collection.get(2));
		assertEquals(WIND, collection.get(3));
	}

	@Test
	public void testGet() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add(RAIN);
		
		assertEquals(RAIN, collection.get(0));
	}
	
	@Test
	public void testExceptionGet() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		
		assertThrows(IndexOutOfBoundsException.class, () -> collection.get(-1));
	}
	
	@Test
	public void testClear() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add(RAIN);
		collection.add(SUN);
		collection.add(SNOW);
		collection.add(WIND);
		collection.clear();
		
		assertEquals(collection.size(), 0);
		assertThrows(IndexOutOfBoundsException.class, () -> collection.get(2));
	}
	
	@Test
	public void testInsert() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.insert(RAIN, 0);
		collection.insert(SUN, 0);
		collection.insert(SNOW, 1);
		collection.add(WIND);
		collection.insert(STORM, 3);
		collection.insert(SUN, 5);
		
		assertEquals(SUN, collection.get(0));
		assertEquals(SNOW, collection.get(1));
		assertEquals(RAIN, collection.get(2));
		assertEquals(STORM, collection.get(3));
		assertEquals(WIND, collection.get(4));
		assertEquals(SUN, collection.get(5));
	}
	
	@Test
	public void testInsertOutOfBounds() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add(RAIN);
		collection.insert(SUN, 0);
		collection.insert(SNOW, 1);
		collection.add(WIND);
		
		assertThrows(IndexOutOfBoundsException.class,
				() -> collection.insert(STORM, 20)); 
	}
	
	@Test
	public void testInsertNull() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.insert(RAIN, 0);
		collection.insert(SUN, 0);

		assertThrows(NullPointerException.class,
				() -> collection.insert(null, 1)); 
	}
	
	@Test
	public void testIndexOf() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add(RAIN);
		collection.insert(SUN, 0);
		collection.add(RAIN);
		
		assertEquals(1, collection.indexOf(RAIN));
	}
	
	@Test
	public void testRemove() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add(RAIN);
		collection.insert(SUN, 0);
		collection.insert(SNOW, 1);
		collection.add(WIND);
		collection.insert(STORM, 3);
		collection.remove(0);
		collection.remove(3);
		
		assertEquals(-1, collection.indexOf(SUN));
		assertEquals(-1, collection.indexOf(WIND));
		assertEquals(SNOW, collection.get(0));
		assertEquals(RAIN, collection.get(1));
		assertEquals(STORM, collection.get(2));
	}
	
	@Test
	public void testRemoveFirst() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add(RAIN);
		collection.insert(SUN, 0);
		collection.insert(SNOW, 1);
		collection.add(WIND);
		collection.insert(STORM, 3);
		collection.remove(0);
		
		assertEquals(SNOW, collection.get(0));
		assertEquals(RAIN, collection.get(1));
		assertEquals(STORM, collection.get(2));
		assertEquals(WIND, collection.get(3));
	}
	
	@Test
	public void testRemoveLast() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add(RAIN);
		collection.add(SUN);
		collection.add(SNOW);
		collection.add(WIND);
		collection.remove(collection.size()-1);
		
		assertEquals(RAIN, collection.get(0));
		assertEquals(SUN, collection.get(1));
		assertEquals(SNOW, collection.get(2));
		assertThrows(IndexOutOfBoundsException.class, () -> collection.get(3));
	}
	
	@Test
	public void testRemoveOneElement() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add(RAIN);
		collection.remove(0);
		
		assertThrows(IndexOutOfBoundsException.class, () -> collection.get(0));
	}
	
	/**
	 * Test for remove method that removes given value
	 */
	@Test
	public void testRemoveValue() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add(RAIN);
		collection.add(STORM);
		collection.add(WIND);
		collection.remove(STORM);
		
		assertEquals(RAIN, collection.get(0));
		assertEquals(WIND, collection.get(1));
	}
	
	@Test
	public void testIsEmpty() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		boolean beforeAddingElement = collection.isEmpty();
		collection.add(RAIN);
		boolean afterAddingElement = collection.isEmpty();
		
		assertEquals(true, beforeAddingElement);
		assertEquals(false, afterAddingElement);
	}
	
	@Test
	public void testIndexOfNull() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();

		assertEquals(-1, collection.indexOf(null));
	}
	
	@Test
	public void testAddNull() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();

		assertThrows(NullPointerException.class, () -> collection.add(null));
	}
	
	@Test
	public void testRemoveOutOfBounds() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();

		assertThrows(IndexOutOfBoundsException.class, () -> collection.remove(-1));
	}
	
	@Test
	public void testRemoveNull() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();

		assertFalse(collection.remove(null));
	}
	
	@Test
	public void testContains() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add(RAIN);
		collection.insert(SUN, 0);
		collection.insert(SNOW, 1);
		collection.insert(STORM, 3);
		
		assertTrue(collection.contains(STORM));
		assertFalse(collection.contains(WIND));
	}
	
	@Test
	public void testRemoveNonexisting() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add(RAIN);
		collection.insert(SUN, 0);
		
		assertFalse(collection.remove(WIND));
	}
	
	@Test
	public void testToArray() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add(RAIN);
		collection.insert(SUN, 0);
		collection.insert(SNOW, 1);
		collection.insert(STORM, 3);

		Object[] array = collection.toArray();
		assertArrayEquals(new Object[]{SUN, SNOW, RAIN, STORM}, array);
	}
}

package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

@SuppressWarnings("javadoc")
public class ArrayIndexedCollectionTest {
	private static final int INITIAL_CAPACITY = 16;
	private static final String ATHLETICS = "Athletics";
	private static final String FOOTBALL = "Football";
	private static final String HANDBALL = "Handball";
	private static final int TEST_CAPACITY = 5;
	private static final int TEST_INDEX = 3;

	/**
	 * Tests first constructor that initializes variable capacity to 16.
	 */
	@Test 
	public void testConstructor1() {
		assertEquals(new ArrayIndexedCollection().getCapacity(), INITIAL_CAPACITY);
	}
	
	/**  
	 * Tests second constructor that initializes variable capacity to 16.
	 */
	@Test
	public void testConstructor2() {
		assertEquals(new ArrayIndexedCollection(new Collection()).getCapacity(),
				INITIAL_CAPACITY);
	}
	
	/**
	 * Tests that second constructor adds given collection to elements.
	 */
	@Test
	public void testAddingConstructor2() {
		ArrayIndexedCollection collectionToAdd = new ArrayIndexedCollection();
		collectionToAdd.add(HANDBALL);
		collectionToAdd.add(ATHLETICS);
		collectionToAdd.add(FOOTBALL);
		ArrayIndexedCollection newCollection =
				new ArrayIndexedCollection(collectionToAdd);
		
		assertEquals(HANDBALL, newCollection.get(0));
		assertEquals(ATHLETICS, newCollection.get(1));
		assertEquals(FOOTBALL, newCollection.get(2));
		assertEquals(INITIAL_CAPACITY, newCollection.getCapacity());
	}
	
	/**
	 * Tests that second constructor throws exception if given collection is null.
	 */
	@Test
	public void testExceptionConstructor2() {
		assertThrows(NullPointerException.class,
				() -> new ArrayIndexedCollection(null));
	}
	
	/**
	 * Tests that 3rd constructor throws exception if initial capacity is less 
	 * than 1.
	 */
	@Test
	public void testExceptionConstructor3() {
		assertThrows(IllegalArgumentException.class,
				() -> new ArrayIndexedCollection(0));
	}
	
	/**
	 * Tests that 3rd constructor sets capacity to initial value.
	 */
	@Test
	public void testConstructor3() {
		assertEquals(new ArrayIndexedCollection(TEST_CAPACITY).getCapacity(),
				TEST_CAPACITY);
	}
	
	/**
	 * Tests that 4th constructor throws exception if given collection is null.
	 */
	@Test
	public void testExceptionConstructor4() {
		assertThrows(NullPointerException.class,
				() -> new ArrayIndexedCollection(null, TEST_CAPACITY));	
	}
	
	/**
	 * Tests that 4th constructor adds all elements from given collection in 
	 * elements when initial capacity is less than given collection size.
	 */
	@Test
	public void testLessConstructor4() {
		int capacity1 = 1;
		ArrayIndexedCollection collectionToAdd = new ArrayIndexedCollection();
		collectionToAdd.add(FOOTBALL);
		collectionToAdd.add(ATHLETICS);
		ArrayIndexedCollection newCollection =
				new ArrayIndexedCollection(collectionToAdd, capacity1);
		
		assertEquals(FOOTBALL, newCollection.get(0));
		assertEquals(ATHLETICS, newCollection.get(1));
		assertEquals(collectionToAdd.size(), newCollection.getCapacity());
	}
	
	/**
	 * Tests that 4th constructor adds all elements from given collection in 
	 * elements when initial capacity is greater than given collection size.
	 */
	@Test
	public void testGreaterConstructor4() {
		ArrayIndexedCollection collectionToAdd = new ArrayIndexedCollection();
		collectionToAdd.add(FOOTBALL);
		collectionToAdd.add(ATHLETICS);
		ArrayIndexedCollection newCollection =
				new ArrayIndexedCollection(collectionToAdd, TEST_CAPACITY);
		
		assertEquals(FOOTBALL, newCollection.get(0));
		assertEquals(ATHLETICS, newCollection.get(1));
		assertEquals(TEST_CAPACITY, newCollection.getCapacity());
	}
	
	@Test
	public void testAdd() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		collection.add(FOOTBALL);
		collection.add(ATHLETICS);
		collection.add(HANDBALL);
		
		assertEquals(FOOTBALL, collection.get(0));
		assertEquals(ATHLETICS, collection.get(1));
		assertEquals(HANDBALL, collection.get(2));
	}
	
	@Test
	public void testAddNull() {
		assertThrows(NullPointerException.class, () -> 
		new ArrayIndexedCollection().add(null));
	}
	
	/**
	 * Tests that in add method capacity is doubled if elements is full
	 */
	@Test
	public void testAddDoubleSize() {
		int capacity2 = 2;
		ArrayIndexedCollection collection = new ArrayIndexedCollection(capacity2);
		collection.add(ATHLETICS);
		collection.add(FOOTBALL);
		int capacityBefore = collection.getCapacity();
		collection.add(HANDBALL);
		int capacityAfter = collection.getCapacity();
		
		assertEquals(capacity2, capacityBefore);
		assertEquals(2*capacity2, capacityAfter);
	}
	
	/**
	 * Tests get method throws exception if index is below lower bounds
	 */
	@Test
	public void testLowIndexGet() {
		assertThrows(IndexOutOfBoundsException.class,
				() -> new ArrayIndexedCollection(3).get(-1));
	}
	
	/**
	 * Tests get method throws exception if index is above upper bounds
	 */
	@Test
	public void testHighIndexGet() {
		ArrayIndexedCollection testObject = new ArrayIndexedCollection(3);
		testObject.add(ATHLETICS);
		testObject.add(FOOTBALL);
		testObject.add(HANDBALL);
		
		assertThrows(IndexOutOfBoundsException.class, 
				() -> testObject.get(TEST_INDEX));
	}
	
	@Test
	public void testClear() {
		ArrayIndexedCollection testObject = new ArrayIndexedCollection(3);
		testObject.add(ATHLETICS);
		testObject.add(FOOTBALL);
		testObject.add(HANDBALL);
		testObject.add(ATHLETICS);
		testObject.add(FOOTBALL);
		testObject.add(HANDBALL);
		int sizeBeforeClear = testObject.size();
		testObject.clear();
		
		assertEquals(6, sizeBeforeClear);
		assertEquals(0, testObject.size());
		assertThrows(IndexOutOfBoundsException.class,
				() -> testObject.get(TEST_INDEX));
	}
	
	@Test
	public void testInsert() {
		ArrayIndexedCollection testObject = new ArrayIndexedCollection(3);
		testObject.add(FOOTBALL);
		testObject.insert(ATHLETICS, 0);
		testObject.add(HANDBALL);
		testObject.insert(HANDBALL, 2);
		testObject.insert(FOOTBALL, 2);
		
		assertEquals(ATHLETICS, testObject.get(0));
		assertEquals(FOOTBALL, testObject.get(1));
		assertEquals(FOOTBALL, testObject.get(2));
		assertEquals(HANDBALL, testObject.get(3));
		assertEquals(HANDBALL, testObject.get(4));
	}
	
	@Test
	public void testInsertOutOfBounds() {
		ArrayIndexedCollection testObject = new ArrayIndexedCollection(3);
		testObject.add(FOOTBALL);
		
		assertThrows(IndexOutOfBoundsException.class,
				() -> testObject.insert(HANDBALL, 5));
	}
	
	@Test
	public void testInsertNull() {
		ArrayIndexedCollection testObject = new ArrayIndexedCollection(3);
		
		assertThrows(NullPointerException.class,
				() -> testObject.insert(null, 0));
	}
	
	@Test
	public void testIndexOf() {
		ArrayIndexedCollection testObject = new ArrayIndexedCollection(3);
		testObject.add(FOOTBALL);
		testObject.insert(HANDBALL, 0);
		testObject.add(ATHLETICS);
		testObject.insert(ATHLETICS, 2);
		testObject.insert(HANDBALL, 2);
		
		assertEquals(3, testObject.indexOf(ATHLETICS));
	}
	
	@Test
	public void testIndexOfNonExisting() {
		ArrayIndexedCollection testObject = new ArrayIndexedCollection(3);
		testObject.add(ATHLETICS);
		testObject.insert(FOOTBALL, 0);
		testObject.add(HANDBALL);
		testObject.insert(FOOTBALL, 2);
		testObject.insert(ATHLETICS, 2);
		String testString = "test";
		
		assertEquals(-1, testObject.indexOf(testString));
	}
	
	/**
	 * Test for remove method that removes index
	 */
	@Test
	public void testRemoveIndex() {
		ArrayIndexedCollection testObject = new ArrayIndexedCollection(3);
		testObject.add(HANDBALL);
		testObject.add(ATHLETICS);
		testObject.add(HANDBALL);
		testObject.add(HANDBALL);
		testObject.remove(2);
		
		assertEquals(HANDBALL, testObject.get(0));
		assertEquals(ATHLETICS, testObject.get(1));
		assertEquals(HANDBALL, testObject.get(2));
		assertEquals(3, testObject.size());
	}
	
	/**
	 * Test for remove method that removes value
	 */
	@Test
	public void testRemoveValue() {
		ArrayIndexedCollection testObject = new ArrayIndexedCollection(3);
		testObject.add(HANDBALL);
		testObject.add(ATHLETICS);
		testObject.add(HANDBALL);
		testObject.add(HANDBALL);
		testObject.remove(HANDBALL);
		
		assertEquals(ATHLETICS, testObject.get(0));
		assertEquals(HANDBALL, testObject.get(1));
		assertEquals(HANDBALL, testObject.get(2));
		assertEquals(3, testObject.size());
	}
	
	/**
	 * Test for remove method that removes value
	 */
	@Test
	public void testRemoveNonexistingValue() {
		ArrayIndexedCollection testObject = new ArrayIndexedCollection(3);
		testObject.add(FOOTBALL);
		testObject.add(ATHLETICS);
		
		assertFalse(testObject.remove(HANDBALL));
	}
	
	@Test
	public void testExceptionRemove() {
		ArrayIndexedCollection testObject = new ArrayIndexedCollection(3);
		testObject.add(ATHLETICS);
		testObject.add(HANDBALL);
		testObject.add(FOOTBALL);
		testObject.add(ATHLETICS);
		
		int lowIndex = -1;
		assertThrows(IndexOutOfBoundsException.class,
				() -> testObject.remove(lowIndex));
		int highIndex = 5;
		assertThrows(IndexOutOfBoundsException.class,
				() -> testObject.remove(highIndex));
	}
	
	@Test
	public void testSize() {
		ArrayIndexedCollection testObject = new ArrayIndexedCollection(TEST_CAPACITY);
		int size1 = testObject.size();
		testObject.add(FOOTBALL);
		testObject.add(ATHLETICS);
		int size2 = testObject.size();
		
		assertEquals(0, size1);
		assertEquals(2, size2);
	}
	
	@Test
	public void testContains() {
		ArrayIndexedCollection testObject = new ArrayIndexedCollection(3);
		testObject.add(ATHLETICS);
		testObject.add(FOOTBALL);
		
		assertEquals(true, testObject.contains(ATHLETICS));
		assertEquals(true, testObject.contains(FOOTBALL));
		assertEquals(false, testObject.contains(HANDBALL));
	}
	
	@Test
	public void testToArray() {
		ArrayIndexedCollection testObject = new ArrayIndexedCollection(3);
		testObject.add(ATHLETICS);
		testObject.add(FOOTBALL);
		Object[] array = testObject.toArray();
		assertEquals(ATHLETICS, array[0]);
		assertEquals(FOOTBALL, array[1]);
	}
	
	@Test
	public void testForEach() {
		Object[] array = new Object[2];
		Processor processor = new Processor() {
			private int index = 0; 
			@Override
			public void process(Object value) {
				array[index] = value;
				index++;
			}
		};
		
		ArrayIndexedCollection testObject = new ArrayIndexedCollection(3);
		testObject.add(ATHLETICS);
		testObject.add(FOOTBALL);
		testObject.forEach(processor);
		
		assertEquals(ATHLETICS, array[0]);
		assertEquals(FOOTBALL, array[1]);
	}
	
	@Test
	public void testIsEmpty() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		boolean beforeAddingElement = collection.isEmpty();
		collection.add(ATHLETICS);
		boolean afterAddingElement = collection.isEmpty();
		
		assertEquals(true, beforeAddingElement);
		assertEquals(false, afterAddingElement);
	}
	
	@Test
	public void testIndexOfNull() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();

		assertEquals(-1, collection.indexOf(null));
	}
}

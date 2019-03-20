package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;


public class ArrayIndexedCollectionTest {
	private int initialCapacity = 16;
	private String atletika = "Atletika";
	private String nogomet = "Nogomet";
	private String rukomet = "Rukomet";
	private int testCapacity = 5;
	private int testIndex = 3;
	
	/**
	 * Tests first constructor that initializes variable capacity to 16.
	 */
	@Test
	void testConstructor1() {
		assertEquals(new ArrayIndexedCollection().capacity, initialCapacity);
	}
	
	/**
	 * Tests second constructor that initializes variable capacity to 16.
	 */
	@Test
	void testConstructor2() {
		assertEquals(new ArrayIndexedCollection(new Collection()).capacity,
				initialCapacity);
	}
	
	/**
	 * Tests that second constructor adds given collection to elements.
	 */
	@Test
	void testAddingConstructor2() {
		ArrayIndexedCollection collectionToAdd = new ArrayIndexedCollection();
		collectionToAdd.add(rukomet);
		collectionToAdd.add(atletika);
		collectionToAdd.add(nogomet);
		ArrayIndexedCollection newCollection =
				new ArrayIndexedCollection(collectionToAdd);
		
		assertEquals(rukomet, newCollection.get(0));
		assertEquals(atletika, newCollection.get(1));
		assertEquals(nogomet, newCollection.get(2));
		assertEquals(initialCapacity, newCollection.capacity);
	}
	
	/**
	 * Tests that second constructor throws exception if given collection is null.
	 */
	@Test
	void testExceptionConstructor2() {
		assertThrows(NullPointerException.class,
				() -> new ArrayIndexedCollection(null));
	}
	
	/**
	 * Tests that 3rd constructor throws exception if initial capacity is less 
	 * than 1.
	 */
	@Test
	void testExceptionConstructor3() {
		assertThrows(IllegalArgumentException.class,
				() -> new ArrayIndexedCollection(0));
	}
	
	/**
	 * Tests that 3rd constructor sets capacity to initial value.
	 */
	@Test
	void testConstructor3() {
		assertEquals(new ArrayIndexedCollection(testCapacity).capacity,
				testCapacity);
	}
	
	/**
	 * Tests that 4th constructor throws exception if given collection is null.
	 */
	@Test
	void testExceptionConstructor4() {
		assertThrows(NullPointerException.class,
				() -> new ArrayIndexedCollection(null, testCapacity));	
	}
	
	/**
	 * Tests that 4th constructor adds all elements from given collection in 
	 * elements when initial capacity is less than given collection size.
	 */
	@Test
	void testLessConstructor4() {
		int capacity1 = 1;
		ArrayIndexedCollection collectionToAdd = new ArrayIndexedCollection();
		collectionToAdd.add(nogomet);
		collectionToAdd.add(atletika);
		ArrayIndexedCollection newCollection =
				new ArrayIndexedCollection(collectionToAdd, capacity1);
		
		assertEquals(nogomet, newCollection.get(0));
		assertEquals(atletika, newCollection.get(1));
		assertEquals(collectionToAdd.size(), newCollection.capacity);
	}
	
	/**
	 * Tests that 4th constructor adds all elements from given collection in 
	 * elements when initial capacity is greater than given collection size.
	 */
	@Test
	void testGreaterConstructor4() {
		ArrayIndexedCollection collectionToAdd = new ArrayIndexedCollection();
		collectionToAdd.add(nogomet);
		collectionToAdd.add(atletika);
		ArrayIndexedCollection newCollection =
				new ArrayIndexedCollection(collectionToAdd, testCapacity);
		
		assertEquals(nogomet, newCollection.get(0));
		assertEquals(atletika, newCollection.get(1));
		assertEquals(testCapacity, newCollection.capacity);
	}
	
	@Test
	void testAdd() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		collection.add(nogomet);
		collection.add(atletika);
		collection.add(rukomet);
		
		assertEquals(nogomet, collection.get(0));
		assertEquals(atletika, collection.get(1));
		assertEquals(rukomet, collection.get(2));
	}
	
	@Test
	void testAddNull() {
		assertThrows(NullPointerException.class, () -> 
		{new ArrayIndexedCollection(null);});
	}
	
	/**
	 * Tests that in add method capacity is doubled if elements is full
	 */
	@Test
	void testAddDoubleSize() {
		int capacity2 = 2;
		ArrayIndexedCollection collection = new ArrayIndexedCollection(capacity2);
		collection.add(atletika);
		collection.add(nogomet);
		int capacityBefore = collection.capacity;
		collection.add(rukomet);
		int capacityAfter = collection.capacity;
		
		assertEquals(capacity2, capacityBefore);
		assertEquals(2*capacity2, capacityAfter);
	}
	
	/**
	 * Tests get method throws exception if index is below lower bounds
	 */
	@Test
	void testLowIndexGet() {
		assertThrows(IndexOutOfBoundsException.class,
				() -> new ArrayIndexedCollection(3).get(-1));
	}
	
	/**
	 * Tests get method throws exception if index is above upper bounds
	 */
	@Test
	void testHighIndexGet() {
		ArrayIndexedCollection testObject = new ArrayIndexedCollection(3);
		testObject.add(atletika);
		testObject.add(nogomet);
		testObject.add(rukomet);
		
		assertThrows(IndexOutOfBoundsException.class, 
				() -> testObject.get(testIndex));
	}
	
	@Test
	void testClear() {
		ArrayIndexedCollection testObject = new ArrayIndexedCollection(3);
		testObject.add(atletika);
		testObject.add(nogomet);
		testObject.add(rukomet);
		testObject.add(atletika);
		testObject.add(nogomet);
		testObject.add(rukomet);
		int sizeBeforeClear = testObject.size();
		testObject.clear();
		
		assertEquals(6, sizeBeforeClear);
		assertEquals(0, testObject.size());
		assertThrows(IndexOutOfBoundsException.class,
				() -> testObject.get(testIndex));
	}
	
	@Test
	void testInsert() {
		ArrayIndexedCollection testObject = new ArrayIndexedCollection(3);
		testObject.add(nogomet);
		testObject.insert(atletika, 0);
		testObject.add(rukomet);
		testObject.insert(rukomet, 2);
		testObject.insert(nogomet, 2);
		
		assertEquals(atletika, testObject.get(0));
		assertEquals(nogomet, testObject.get(1));
		assertEquals(nogomet, testObject.get(2));
		assertEquals(rukomet, testObject.get(3));
		assertEquals(rukomet, testObject.get(4));
	}
	
	@Test
	void testIndexOf() {
		ArrayIndexedCollection testObject = new ArrayIndexedCollection(3);
		testObject.add(nogomet);
		testObject.insert(rukomet, 0);
		testObject.add(atletika);
		testObject.insert(atletika, 2);
		testObject.insert(rukomet, 2);
		
		assertEquals(3, testObject.indexOf(atletika));
	}
	
	@Test
	void testIndexOfNonExisting() {
		ArrayIndexedCollection testObject = new ArrayIndexedCollection(3);
		testObject.add(atletika);
		testObject.insert(nogomet, 0);
		testObject.add(rukomet);
		testObject.insert(nogomet, 2);
		testObject.insert(atletika, 2);
		String testString = "test";
		
		assertEquals(-1, testObject.indexOf(testString));
	}
	
	@Test
	void testRemove() {
		ArrayIndexedCollection testObject = new ArrayIndexedCollection(3);
		testObject.add(rukomet);
		testObject.add(atletika);
		testObject.add(rukomet);
		testObject.add(rukomet);
		testObject.remove(2);
		
		assertEquals(rukomet, testObject.get(0));
		assertEquals(atletika, testObject.get(1));
		assertEquals(rukomet, testObject.get(2));
		assertEquals(3, testObject.size());
	}
	
	@Test
	void testExceptionRemove() {
		ArrayIndexedCollection testObject = new ArrayIndexedCollection(3);
		testObject.add(atletika);
		testObject.add(rukomet);
		testObject.add(nogomet);
		testObject.add(atletika);
		
		int lowIndex = -1;
		assertThrows(IndexOutOfBoundsException.class,
				() -> testObject.remove(lowIndex));
		int highIndex = 5;
		assertThrows(IndexOutOfBoundsException.class,
				() -> testObject.remove(highIndex));
	}
	
	@Test
	void testSize() {
		ArrayIndexedCollection testObject = new ArrayIndexedCollection(testCapacity);
		int size1 = testObject.size();
		testObject.add(nogomet);
		testObject.add(atletika);
		int size2 = testObject.size();
		
		assertEquals(0, size1);
		assertEquals(2, size2);
	}
	
	@Test
	void testContains() {
		ArrayIndexedCollection testObject = new ArrayIndexedCollection(3);
		testObject.add(atletika);
		testObject.add(nogomet);
		
		assertEquals(true, testObject.contains(atletika));
		assertEquals(true, testObject.contains(nogomet));
		assertEquals(false, testObject.contains(rukomet));
	}
	
	@Test
	void testToArray() {
		ArrayIndexedCollection testObject = new ArrayIndexedCollection(3);
		testObject.add(atletika);
		testObject.add(nogomet);
		Object[] array = testObject.toArray();
		assertEquals(atletika, array[0]);
		assertEquals(nogomet, array[1]);
	}
	
	@Test
	void testForEach() {
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
		testObject.add(atletika);
		testObject.add(nogomet);
		testObject.forEach(processor);
		
		assertEquals(atletika, array[0]);
		assertEquals(nogomet, array[1]);
	}
	
}

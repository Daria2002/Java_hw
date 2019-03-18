package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;


public class ArrayIndexedCollectionTest {

	/**
	 * Tests first constructor that initializes variable capacity to 16.
	 */
	@Test
	void testConstructor1() {
		ArrayIndexedCollection testObject = new ArrayIndexedCollection();
		assertEquals(testObject.capacity, 16);
	}
	
	/**
	 * Tests second constructor that initializes variable capacity to 16.
	 */
	@Test
	void testConstructor2() {
		Collection collection = new Collection();
		ArrayIndexedCollection testObject = new ArrayIndexedCollection(collection);
		assertEquals(testObject.capacity, 16);
	}
	
	/**
	 * Tests that second constructor adds given collection to elements.
	 */
	@Test
	void testAddingConstructor2() {
		ArrayIndexedCollection collectionToAdd = new ArrayIndexedCollection();
		collectionToAdd.add("Rukomet");
		collectionToAdd.add("Atletika");
		collectionToAdd.add("Nogomet");
		ArrayIndexedCollection newCollection =
				new ArrayIndexedCollection(collectionToAdd);
		assertEquals("Rukomet", newCollection.get(0));
		assertEquals("Atletika", newCollection.get(1));
		assertEquals("Nogomet", newCollection.get(2));
		assertEquals(16, newCollection.capacity);
	}
	
	/**
	 * Tests that second constructor throws exception if given collection is null.
	 */
	@Test
	void testExceptionConstructor2() {
		assertThrows(NullPointerException.class, () -> 
		{new ArrayIndexedCollection(null);});
	}
	
	/**
	 * Tests that 3rd constructor throws exception if initial capaciti is less 
	 * than 1.
	 */
	@Test
	void testExceptionConstructor3() {
		assertThrows(IllegalArgumentException.class, () -> 
		{new ArrayIndexedCollection(0);});
	}
	
	/**
	 * Tests that 3rd constructor sets capacity to initial value.
	 */
	@Test
	void testConstructor3() {
		ArrayIndexedCollection testObject = new ArrayIndexedCollection(5);
		assertEquals(testObject.capacity, 5);
	}
	
	/**
	 * Tests that 4th constructor throws exception if given collection is null.
	 */
	@Test
	void testExceptionConstructor4() {
		assertThrows(NullPointerException.class, () -> 
		{new ArrayIndexedCollection(null, 5);});	
	}
	
	/**
	 * Tests that 4th constructor adds all elements from given collection in 
	 * elements when initial capacity is less than given collection size.
	 */
	@Test
	void testLessConstructor4() {
		ArrayIndexedCollection collectionToAdd = new ArrayIndexedCollection();
		collectionToAdd.add("Mario");
		collectionToAdd.add("Ana");
		ArrayIndexedCollection newCollection =
				new ArrayIndexedCollection(collectionToAdd, 1);
		assertEquals("Mario", newCollection.get(0));
		assertEquals("Ana", newCollection.get(1));
		assertEquals(collectionToAdd.size(), newCollection.capacity);
	}
	
	/**
	 * Tests that 4th constructor adds all elements from given collection in 
	 * elements when initial capacity is greater than given collection size.
	 */
	@Test
	void testGreaterConstructor4() {
		ArrayIndexedCollection collectionToAdd = new ArrayIndexedCollection();
		collectionToAdd.add("Mario");
		collectionToAdd.add("Ana");
		ArrayIndexedCollection newCollection =
				new ArrayIndexedCollection(collectionToAdd, 5);
		assertEquals("Mario", newCollection.get(0));
		assertEquals("Ana", newCollection.get(1));
		assertEquals(5, newCollection.capacity);
	}
	
	@Test
	void testAdd() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		collection.add(4);
		collection.add(5);
		collection.add(9);
		assertEquals(4, collection.get(0));
		assertEquals(5, collection.get(1));
		assertEquals(9, collection.get(2));
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
		ArrayIndexedCollection collection = new ArrayIndexedCollection(2);
		collection.add(5);
		collection.add(15);
		int capacityBefore = collection.capacity;
		collection.add(6);
		int capacityAfter = collection.capacity;
		assert(capacityAfter == 4 && capacityBefore == 2);
	}
	
	/**
	 * Tests get method throws exception if index is below lower bounds
	 */
	@Test
	void testLowIndexGet() {
		ArrayIndexedCollection testObject = new ArrayIndexedCollection(3);
		assertThrows(IndexOutOfBoundsException.class, () -> 
		{testObject.get(-1);});
	}
	
	/**
	 * Tests get method throws exception if index is above upper bounds
	 */
	@Test
	void testHighIndexGet() {
		ArrayIndexedCollection testObject = new ArrayIndexedCollection(3);
		testObject.add(4);
		testObject.add(8);
		testObject.add(12);
		assertThrows(IndexOutOfBoundsException.class, () -> 
		{testObject.get(3);});
	}
	
	@Test
	void testClear() {
		ArrayIndexedCollection testObject = new ArrayIndexedCollection(3);
		testObject.add(4);
		testObject.add(8);
		testObject.add(12);
		testObject.clear();
		assertEquals(0, testObject.size());
		assertThrows(IndexOutOfBoundsException.class, () -> 
		{testObject.get(2);});
	}
	
	@Test
	void testInsert() {
		ArrayIndexedCollection testObject = new ArrayIndexedCollection(3);
		testObject.add(4);
		testObject.insert(10, 0);
		testObject.add(8);
		testObject.insert(10, 2);
		testObject.insert(7, 2);
		assertEquals(10, testObject.get(0));
		assertEquals(4, testObject.get(1));
		assertEquals(7, testObject.get(2));
		assertEquals(10, testObject.get(3));
		assertEquals(8, testObject.get(4));
	}
	
	@Test
	void testIndexOf() {
		ArrayIndexedCollection testObject = new ArrayIndexedCollection(3);
		testObject.add(4);
		testObject.insert(10, 0);
		testObject.add(8);
		testObject.insert(10, 2);
		testObject.insert(7, 2);
		assertEquals(0, testObject.indexOf(10));
	}
	
	@Test
	void testIndexOfNonExisting() {
		ArrayIndexedCollection testObject = new ArrayIndexedCollection(3);
		testObject.add(4);
		testObject.insert(10, 0);
		testObject.add(8);
		testObject.insert(10, 2);
		testObject.insert(7, 2);
		assertEquals(-1, testObject.indexOf(5));
	}
	
	@Test
	void testRemove() {
		ArrayIndexedCollection testObject = new ArrayIndexedCollection(3);
		testObject.add(4);
		testObject.add(8);
		testObject.add(10);
		testObject.add(7);
		testObject.remove(2);
		assertEquals(4, testObject.get(0));
		assertEquals(8, testObject.get(1));
		assertEquals(7, testObject.get(2));
		assertEquals(3, testObject.size());
	}
	
	@Test
	void testExceptionRemove() {
		ArrayIndexedCollection testObject = new ArrayIndexedCollection(3);
		testObject.add(4);
		testObject.add(8);
		testObject.add(10);
		testObject.add(7);
		assertThrows(IndexOutOfBoundsException.class, () -> 
		{testObject.remove(-1);});
		assertThrows(IndexOutOfBoundsException.class, () -> 
		{testObject.remove(5);});
	}
	
	@Test
	void testSize() {
		ArrayIndexedCollection testObject = new ArrayIndexedCollection(3);
		int size1 = testObject.size();
		testObject.add(4);
		testObject.add(8);
		int size2 = testObject.size();
		assertEquals(0, size1);
		assertEquals(2, size2);
	}
	
	@Test
	void testContains() {
		ArrayIndexedCollection testObject = new ArrayIndexedCollection(3);
		testObject.add(4);
		testObject.add(8);
		assertEquals(true, testObject.contains(4));
		assertEquals(true, testObject.contains(8));
		assertEquals(false, testObject.contains(7));
	}
	
	@Test
	void testToArray() {
		ArrayIndexedCollection testObject = new ArrayIndexedCollection(3);
		testObject.add(4);
		testObject.add(8);
		Object[] array = testObject.toArray();
		assertEquals(4, array[0]);
		assertEquals(8, array[1]);
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
		testObject.add(4);
		testObject.add(8);
		testObject.forEach(processor);
		assertEquals(4, array[0]);
		assertEquals(8, array[1]);
	}
	
}

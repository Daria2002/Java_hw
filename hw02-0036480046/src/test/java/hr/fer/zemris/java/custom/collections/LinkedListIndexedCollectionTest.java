package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class LinkedListIndexedCollectionTest {

	private String kisa = "kisa";
	private String sunce = "sunce";
	private String snijeg = "snijeg";
	private String vjetar = "vjetar";
	private String oluja = "oluja";
	
	@Test
	public void testConstructor1() {
		assertEquals(0, new LinkedListIndexedCollection().size());
	}
	
	@Test
	public void testConstructor2() {
		LinkedListIndexedCollection collectionToAdd = 
				new LinkedListIndexedCollection();
		collectionToAdd.add(kisa);
		collectionToAdd.add(sunce);
		collectionToAdd.add(snijeg);
		collectionToAdd.add(vjetar);
		LinkedListIndexedCollection collectionToCheck =
				new LinkedListIndexedCollection(collectionToAdd);
		
		assertEquals(kisa, collectionToCheck.get(0));
		assertEquals(sunce, collectionToCheck.get(1));
		assertEquals(snijeg, collectionToCheck.get(2));
		assertEquals(vjetar, collectionToCheck.get(3));
	}
	
	@Test
	public void testAdd() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add(kisa);
		collection.add(sunce);
		collection.add(snijeg);
		collection.add(vjetar);
		
		assertEquals(kisa, collection.get(0));
		assertEquals(sunce, collection.get(1));
		assertEquals(snijeg, collection.get(2));
		assertEquals(vjetar, collection.get(3));
	}

	@Test
	public void testGet() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add(kisa);
		
		assertEquals(kisa, collection.get(0));
	}
	
	@Test
	public void testExceptionGet() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		
		assertThrows(IndexOutOfBoundsException.class, () -> collection.get(-1));
	}
	
	@Test
	public void testClear() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add(kisa);
		collection.add(sunce);
		collection.add(snijeg);
		collection.add(vjetar);
		collection.clear();
		
		assertEquals(collection.size(), 0);
		assertThrows(IndexOutOfBoundsException.class, () -> collection.get(2));
	}
	
	@Test
	public void testInsert() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add(kisa);
		collection.insert(sunce, 0);
		collection.insert(snijeg, 1);
		collection.add(vjetar);
		collection.insert(oluja, 3);
		
		assertEquals(sunce, collection.get(0));
		assertEquals(snijeg, collection.get(1));
		assertEquals(kisa, collection.get(2));
		assertEquals(oluja, collection.get(3));
		assertEquals(vjetar, collection.get(4));
	}
	
	@Test
	public void testIndexOf() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add(kisa);
		collection.insert(sunce, 0);
		collection.add(kisa);
		
		assertEquals(1, collection.indexOf(kisa));
	}
	
	@Test
	public void testRemove() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add(kisa);
		collection.insert(sunce, 0);
		collection.insert(snijeg, 1);
		collection.add(vjetar);
		collection.insert(oluja, 3);
		collection.remove(0);
		collection.remove(3);
		
		assertEquals(-1, collection.indexOf(sunce));
		assertEquals(-1, collection.indexOf(vjetar));
		assertEquals(snijeg, collection.get(0));
		assertEquals(kisa, collection.get(1));
		assertEquals(oluja, collection.get(2));
	}
	
	@Test
	public void testRemoveFirst() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add(kisa);
		collection.insert(sunce, 0);
		collection.insert(snijeg, 1);
		collection.add(vjetar);
		collection.insert(oluja, 3);
		collection.remove(0);
		
		assertEquals(snijeg, collection.get(0));
		assertEquals(kisa, collection.get(1));
		assertEquals(oluja, collection.get(2));
		assertEquals(vjetar, collection.get(3));
	}
	
	@Test
	public void testRemoveLast() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add(kisa);
		collection.add(sunce);
		collection.add(snijeg);
		collection.add(vjetar);
		collection.remove(collection.size()-1);
		
		assertEquals(kisa, collection.get(0));
		assertEquals(sunce, collection.get(1));
		assertEquals(snijeg, collection.get(2));
		assertThrows(IndexOutOfBoundsException.class, () -> collection.get(3));
	}
	
	@Test
	public void testRemoveOneElement() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add(kisa);
		collection.remove(0);
		
		assertThrows(IndexOutOfBoundsException.class, () -> collection.get(0));
	}
}

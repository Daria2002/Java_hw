package hr.fer.zemris.java.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Implementation of linked list-backed collection of objects.
 * Duplicate elements are allowed, storage of null references is not allowed.
 * @author Daria Matković
 *
 */
public class LinkedListIndexedCollection implements List {
	/**
	 * This class represents one node in list
	 * @author Daria Matković
	 *
	 */
	private static class ListNode {
		// pointer to previous node
		ListNode previous;
		// pointer to next node
		ListNode next;
		// value of current node
		Object value;
		
		/**
		 * Initialize list node.
		 * @param previous previous reference
		 * @param next next reference
		 * @param value node value
		 */
		public ListNode(ListNode previous, ListNode next, Object value) {
			this.previous = previous;
			this.next = next;
			this.value = value;
		}
		
	}
	/** size of node list **/
	private int size;
	/** first node **/
	private ListNode first;
	/** last node **/
	private ListNode last;
	/** count modifications **/
	private long modificationCount = 0;
	
	/**
	 * Nested class that represents getter for elements list
	 * @author Daria Matković
	 *
	 */
	private static class ElementsGetterList implements ElementsGetter {
		/** last visited node **/
		private ListNode lastVisitedNode;
		/** modification count **/
		private long savedModificationCount;
		/** collection **/
		LinkedListIndexedCollection list;
		
		/**
		 * Constructor for elements getter list that initialize local variables
		 * @param list
		 */
		public ElementsGetterList(LinkedListIndexedCollection list) {
			this.list = list;
			savedModificationCount = list.modificationCount;
			lastVisitedNode = list.first;
		}
		
		@Override
		public boolean hasNextElement() {
			if(list.modificationCount != savedModificationCount) {
				throw new ConcurrentModificationException("List is modified.");
			}
			
			return lastVisitedNode != null;
		}
		
		@Override
		public Object getNextElement() {
			if(list.modificationCount != savedModificationCount) {
				throw new ConcurrentModificationException("List is modified.");
			}
			
			if(!hasNextElement()) {
				throw new NoSuchElementException("No more elements");
			}
			
			Object value = lastVisitedNode.value;
			lastVisitedNode = lastVisitedNode.next;
			
			return value;
		}
	}
	
	@Override
	public ElementsGetter createElementsGetter() {
		return new ElementsGetterList(this);
	}
	
	/** 
	 * Default constructor creates empty collection
	 */
	public LinkedListIndexedCollection() {
		this.first = null;
		this.last = null;
	}
	
	/**
	 * Copies given collection into new collection
	 * @param collection collection is reference to other Collection whose 
	 * elements are copied into this newly constructed collection
	 */
	public LinkedListIndexedCollection(Collection collection) {
		Processor processor = (value) -> {
			Objects.requireNonNull(value, "Null can't be saved.");
		};
		
		forEach(processor);
		this.addAll(collection);
	}
	
	/** 
	 * Adds given value at the end of collection
	 * Complexity: O(1)
	 * @param value value added to collection
	 */
	public void add(Object value) {
		Objects.requireNonNull(value, "Can't add null in linked list indexed collection.");
		
		ListNode node = new ListNode(null, null, value);
		
		if(this.size == 0) {
			this.first = node;
		} else {
			node.previous = this.last;
			this.last.next = node;
		}
		
		this.modificationCount++;
		this.last = node;
		this.size++;
	}
	
	/**
	 * Get value of node at position index in linked list
	 * Complexity: n/2+1
	 * @param index index is position in linked list
	 * @return object object is element at position index in linked list
	 */
	public Object get(int index) {
		if(index < 0 || index > size-1) {
			throw new IndexOutOfBoundsException("Index can be value in range"
					+ " from 0 to size-1.");
		}
		
		ListNode node = getNode(index);
		
		return node.value;
	}
	
	/**
	 * Gets list node object at index position.
	 * Complexity: n/2 + 1
	 * @param index position of node
	 * @return node at index
	 */
	private ListNode getNode(int index) {
		ListNode node = new ListNode(null, null, null);
		
		if(index < size/2) {
			node = this.first;
			for(int i = 0; i < index; i++) {
				node = node.next;
			}
		} else {
			node = this.last;
			for(int i = size-1; i > index; i--) {
				node = node.previous;
			}
		}
		
		return node;
	}
	
	/**
	 * Removes all elements from the collection
	 */
	public void clear() {
		if(first == null) {
			return;
		}
		
		ListNode node = first;
		node = node.next;
		
		for(int i = 1; i < this.size; i++) {
			node.previous = null;
			node = node.next;
		}
		
		modificationCount++;
		this.size = 0;
		this.first = null;
		this.last = null;
	}
	
	/**
	 * Insert value at position index.
	 * Complexity: O(n)
	 * @param value value to insert
	 * @param position position is index in list
	 */
	public void insert(Object value, int position) {
		if(position < 0 || position > size) {
			throw new IndexOutOfBoundsException("Position is out of range.");
		}
		// null value can't be inserted
		if(value == null) {
			throw new NullPointerException();
		}
		
		Objects.requireNonNull(value, "Null reference can't be inserted in"
				+ " linked list indeced collection.");
		
		ListNode newNode = new ListNode(null, null, value);
		// if list is empty
		if(this.size == 0) {
			this.first = newNode;
			this.last = newNode;
			this.size += 1;
			return;
		}
		
		// if list is not empty
		if(position == 0) {
			// if first element need to be added
			newNode.next = this.first;
			this.first.previous = newNode;
			this.first = newNode;
			
		} else if(position == this.size) {
			// shifting is not needed because element must be added at the end
			newNode.previous = this.last;
			this.last.next = newNode;
			this.last = newNode;
			
		} else {
			// if shifting is needed
			ListNode helpNode = getNode(position);
			
			newNode.next = helpNode;
			newNode.previous = helpNode.previous;
			helpNode.previous.next = newNode;
			helpNode.previous = newNode;
		}
		modificationCount++;
		this.size += 1;
	}
	
	/**
	 * Search given value in the collection
	 * Complexity: O(n)
	 * @param value value to search in collection
	 * @return index index of first appearance of value in the collection if value 
	 * appears in the collection, otherwise -1
	 */
	public int indexOf(Object value) {
		if(value == null) {
			return -1;
		}
		
		ListNode node = new ListNode(this.first.previous, this.first.next,
				this.first.value);
		for(int i = 0; i < this.size; i++) {
			if(node.value.equals(value)) {
				return i;
			}
			node = node.next;
		}
		
		return -1;
	}
	
	/**
	 * Removes element at given index.
	 * @param index index of element to be removed
	 */
	public void remove(int index) {
		if(index < 0 || index > size-1) {
			throw new IndexOutOfBoundsException("Index out of range");
		}
		
		ListNode currentNode = getNode(index);
		removeObject(currentNode);
		
		modificationCount++;
		this.size -= 1;
	}
	
	@Override
	public boolean remove(Object value) {
		if(value == null) {
			return false;
		}
		
		ListNode currentNode = new ListNode(this.first.previous, this.first.next,
				this.first.value);
		
		while(currentNode != null && !currentNode.value.equals(value)) {
			currentNode = currentNode.next;
		}

		if(currentNode == null) {
			return false;
		}
		
		removeObject(currentNode);
		size -= 1;
		modificationCount++;
		return true;
	}
	
	/**
	 * Removes currentNode from node list
	 * @param currentNode node to remove
	 */
	private void removeObject(ListNode currentNode) {
		// if first node
		if(currentNode.previous == null) {
			this.first = currentNode.next;
		}
		// if last node
		if(currentNode.next == null) {
			this.last = currentNode.previous;
		}
		// if not last node
		if(currentNode.next != null) {
			currentNode.next.previous = currentNode.previous;
		}
		// if not first node
		if(currentNode.previous != null) {
			currentNode.previous.next = currentNode.next; 
		}
		
		modificationCount++;
	}

	@Override
	public int size() {
		return this.size;
	}
	
	@Override
	public boolean contains(Object value) {
		return indexOf(value) != -1;
	}
	
	@Override
	public Object[] toArray() {
		Object[] array = new Object[this.size];
		ListNode node = new ListNode(this.first.previous, this.first.next, 
				this.first.value);
		
		for(int i = 0; i < this.size; i++) {
			array[i] = node.value;
			node = node.next;
		}
		
		return array;
	}
}

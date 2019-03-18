package hr.fer.zemris.java.custom.collections;


/**
 * Implementation of linked list-backed collection of objects.
 * Duplicate elements are allowed, storage of null references is not allowed
 * @author Daria Matković
 *
 */
public class LinkedListIndexedCollection extends Collection {
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
	}
	private int size;
	private ListNode first;
	private ListNode last;
	
	/**
	 * First constructor creates empty collection
	 */
	public LinkedListIndexedCollection() {
		this.first = null;
		this.last = null;
	}
	
	/**
	 * Copies given collection into new collection
	 * @param collection is reference to other Collection whose elements are
	 * copied into this newly constructed collection
	 */
	public LinkedListIndexedCollection(Collection collection) {
		this.addAll(collection);
	}
	
	/**
	 * Adds given value at the end of collection
	 */
	public void add(Object value) {
		if(value == null) {
			throw new NullPointerException();
		}
		
		ListNode node = new ListNode();
		node.next = null;
		node.previous = null;
		node.value = value;
		
		if(this.size == 0) {
			this.first = node;
		}
		else {
			node.previous = this.last;
			this.last.next = node;
		}
		this.last = node;
		this.size += 1;
	}
	
	/**
	 * Get object at position index in linked list
	 * @param index in linked list
	 * @return object at position index
	 */
	public Object get(int index) {
		if(index < 0 || index > size-1) {
			throw new IndexOutOfBoundsException();
		}
		ListNode node = new ListNode();
		node = this.first;
		for(int i = 1; i <= index; i++) {
			node = node.next;
		}
		return node.value;
	}
	
	/**
	 * Removes all elements from the collection
	 */
	public void clear() {
		ListNode node = new ListNode();
		node = this.first;
		for(int i = 0; i < this.size; i++) {
			if(i > 0) {
				node.previous = null;
			}
			node = node.next;
		}
		this.size = 0;
		this.first = null;
		this.last = null;
	}
	
	/**
	 * Insert value at position index
	 * @param value to insert
	 * @param position index in list
	 * complexity: O(n)
	 */
	public void insert(Object value, int position) {
		if(position < 0 || position > size) {
			throw new IndexOutOfBoundsException("Position is out of range");
		}
		if(value == null) {
			throw new NullPointerException();
		}
		ListNode newNode = new ListNode();
		newNode.value = value;
		newNode.next = null;
		newNode.previous = null;
		// if list is empty
		if(this.size == 0) {
			this.first = newNode;
			this.last = newNode;
		}
		// if list is not empty
		else {
			// if first element need to be added
			if(position == 0) {
				newNode.next = this.first;
				this.first.previous = newNode;
				this.first = newNode;
			}
			// shifting is not needed because element must be added at the end
			else if(position == this.size) {
				newNode.previous = this.last;
				this.last.next = newNode;
				this.last = newNode;
			}
			// if shifting is needed
			else {
				ListNode helpNode = new ListNode();
				helpNode = this.first;
				for(int i = 0; i < position; i++) {
					helpNode = helpNode.next;
				}
				newNode.next = helpNode;
				newNode.previous = helpNode.previous;
				helpNode.previous.next = newNode;
				helpNode.previous = newNode;
			}
		}
		this.size += 1;
	}
	
	/**
	 * Search given value in the collection
	 * @param value to search in collection
	 * @return index of first appearance of value in the collection if value 
	 * appears in the collection, otherwise -1
	 * complexity: O(n)
	 */
	public int indexOf(Object value) {
		ListNode node = new ListNode();
		node = this.first;
		for(int i = 0; i < this.size; i++) {
			if(node.value.equals(value)) {
				return i;
			}
			node = node.next;
		}
		return -1;
	}
	
	/**
	 * Removes element at given index
	 * @param index of element to be removed
	 */
	public void remove(int index) {
		if(index < 0 || index > size-1) {
			throw new IndexOutOfBoundsException("Index out of range");
		}
		ListNode node = new ListNode();
		node = this.first;

		if(index == 0) {
			this.first = node.next;
		}
		
		else {
			for(int i = 0; i < index-1 && node != null; i++) {
				node = node.next;
			}
			ListNode helpNode = new ListNode();
			helpNode = node.next.next;
			node.next = helpNode;
		}
		this.size -= 1;
	}
	
	@Override
	public int size() {
		if(this.first == null) {
			return 0;
		}
		ListNode node = new ListNode();
		node = this.first;
		int size = 1;
		while(node != this.last) {
			size++;
			node = node.next;
		}
		return size;
	}
	
	@Override
	public boolean contains(Object value) {
		if(this.indexOf(value) == -1) {
			return false;
		}
		return true;
	}
	
	@Override
	public Object[] toArray() {
		Object[] array = new Object[this.size];
		ListNode node = new ListNode();
		node = this.first;
		for(int i = 0; i < this.size; i++) {
			array[i] = node;
			node = node.next;
		}
		return array;
	}
	
	@Override
	public void forEach(Processor processor) {
		ListNode node = new ListNode();
		node = this.first;
		for(int i = 0; i < this.size; i++) {
			processor.process(node.value);
			node = node.next;
		}
	}
	
}

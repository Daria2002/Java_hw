package hr.fer.zemris.java.custom.collections;

/**
 * Class Collection represents collection of objects.
 * @author Daria Matković
 *
 */
public class Collection {
	
	/**
	 * Constructor with empty body.
	 */
	protected Collection() { 
		
	}
	
	/**
	 * Checks if collection contains any object.
	 * @return true if collection contains no object, otherwise false
	 */ 
	public boolean isEmpty() {
		return size() == 0;
	}
	
	/**
	 * Count number of stored objects in collection.
	 * @return number of currently stored objects in this collection
	 */
	public int size() {
		return 0;
	}
	
	/**
	 * Adds given object into this collection.
	 * @param value value is given object
	 */
	public void add(Object value) {
		// Do nothing
	}
	
	/**
	 * Checks if value is in collection.
	 * @param value value is given value to check if it is in collection
	 * @return true if collection contains given value, otherwise false
	 */
	public boolean contains(Object value) {
		// It is OK to ask if collection contains null
		return false;
	}
	
	/**
	 * Checks if collection contains given value and removes one occurrence of it.
	 * @param value value is given value to check if it is in collection.
	 * @return returns true if collection contains given value, otherwise false.
	 */
	public boolean remove(Object value) {
		return false;
	}
	
	/**
	 * Allocates new array with size equals to the size of this collection and 
	 * fills it with collection content
	 * @return array filled with collection content
	 */
	public Object[] toArray() {
		throw new UnsupportedOperationException("Unsupported operation called.");
	}
	
	/**
	 * Calls processor.process(.) for each element of this collection
	 * @param processor
	 */
	public void forEach(Processor processor) {
		// Empty method
	}
	
	/**
	 * Adds into the current collection all elements from the given collection.
	 * @param other other is collection which elements need to be added in current 
	 * collection
	 */
	public void addAll(Collection other) {
		/**
		 * Local class which extends Processor
		 * @author Daria Matkovic
		 *
		 */
		class ProcessorExtension extends Processor{
			/**
			 * Adds value to collection
			 */
			public void process(Object value) {
				Collection.this.add(value);
			}
		}
		ProcessorExtension processor = new ProcessorExtension();
		other.forEach(processor);
	}
	
	/**
	 * Removes all elements from this collection.
	 */
	public void clear() {
		// Empty method
	}
	
}

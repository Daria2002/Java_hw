package hr.fer.zemris.java.custom.collections;

/**
 * Class Collection represents collection of objects.
 * @author Daria Matković
 *
 */
public interface Collection {
	/**
	 * Count number of stored objects in collection.
	 * @return number of currently stored objects in this collection
	 */
	int size();
	
	/**
	 * Adds given object into this collection.
	 * @param value value is given object
	 */
	void add(Object value);
	
	/**
	 * Checks if value is in collection.
	 * @param value value is given value to check if it is in collection
	 * @return true if collection contains given value, otherwise false
	 */
	boolean contains(Object value);
	
	/**
	 * Checks if collection contains given value and removes one occurrence of it.
	 * @param value value is given value to check if it is in collection.
	 * @return returns true if collection contains given value, otherwise false.
	 */
	boolean remove(Object value);
	
	/**
	 * Allocates new array with size equals to the size of this collection and 
	 * fills it with collection content
	 * @return array filled with collection content
	 */
	Object[] toArray();
	
	/**
	 * Calls processor.process(.) for each element of this collection
	 * @param processor
	 */
	default void forEach(Processor processor) {
		ElementsGetter getter = createElementsGetter();
		getter.processRemaining(processor);
	}
	
	/**
	 * Removes all elements from this collection.
	 */
	void clear();

	/**
	 * Creates element getter
	 * @return created element getter
	 */
	ElementsGetter createElementsGetter();
	
	/**
	 * Adds into the current collection all elements from the given collection.
	 * @param other other is collection which elements need to be added in current 
	 * collection
	 */
	default void addAll(Collection other) {
		/**
		 * Local class which extends Processor
		 * @author Daria Matkovic
		 *
		 */
		class ProcessorExtension implements Processor{
			/**
			 * Adds value to collection
			 */
			@Override
			public void process(Object value) {
				Collection.this.add(value);
			}
		}
		other.forEach(new ProcessorExtension());
	}

	/**
	 * Checks if collection contains any object.
	 * @return true if collection contains no object, otherwise false
	 */ 
	default boolean isEmpty() {
		return size() == 0;
	}
	
	/**
	 * In given collection adds all elements that given tester accepts 
	 * @param col collection to add tested elements
	 * @param tester tester to test elements
	 */
	default void addAllSatisfying(Collection col, Tester tester) {
		ElementsGetter getter = col.createElementsGetter();
		
		while(getter.hasNextElement()) {
			Object help = getter.getNextElement();
			
			if(tester.test(help)) {
				this.add(help);
			}
		}
	}
}

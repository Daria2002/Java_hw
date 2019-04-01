package hr.fer.zemris.java.custom.collections;

/**
 * Class Collection represents collection of objects.
 * @param <T> Generic data type
 * @author Daria Matković
 *
 */
public interface Collection<T> {
	/**
	 * Count number of stored objects in collection.
	 * @return number of currently stored objects in this collection
	 */
	public abstract int size();
	
	/**
	 * Adds given object into this collection.
	 * @param value value is given object
	 */
	public abstract void add(T value);
	
	/**
	 * Checks if value is in collection.
	 * @param value value is given value to check if it is in collection
	 * @return true if collection contains given value, otherwise false
	 */
	public abstract boolean contains(Object value);
	
	/**
	 * Checks if collection contains given value and removes one occurrence of it.
	 * @param value value is given value to check if it is in collection.
	 * @return returns true if collection contains given value, otherwise false.
	 */
	public abstract boolean remove(Object value);
	
	/**
	 * Allocates new array with size equals to the size of this collection and 
	 * fills it with collection content
	 * @return array filled with collection content
	 */
	public abstract Object[] toArray();
	
	/**
	 * Calls processor.process(.) for each element of this collection
	 * @param processor
	 */
	public default void forEach(Processor<? super T> processor) {
		ElementsGetter<T> getter = createElementsGetter();
		getter.processRemaining(processor);
	}
	
	/**
	 * Removes all elements from this collection.
	 */
	public abstract void clear();

	/**
	 * Creates element getter
	 * @return created element getter
	 */
	public abstract ElementsGetter<T> createElementsGetter();
	
	/**
	 * Adds into the current collection all elements from the given collection.
	 * @param other other is collection which elements need to be added in current 
	 * collection
	 */
	public default void addAll(Collection<? extends T> other) {
		/**
		 * Local class which extends Processor
		 * @author Daria Matkovic
		 *
		 */
		class ProcessorExtension implements Processor<T>{
			/**
			 * Adds value to collection
			 */
			@Override
			public void process(T value) {
				Collection.this.add(value);
			}
		}
		ProcessorExtension processor = new ProcessorExtension();
		other.forEach(processor);
	}

	/**
	 * Checks if collection contains any object.
	 * @return true if collection contains no object, otherwise false
	 */ 
	public default boolean isEmpty() {
		return size() == 0;
	}
	
	/**
	 * In given collection adds all elements that given tester accepts 
	 * @param col collection to add tested elements
	 * @param tester tester to test elements
	 */
	public default void addAllSatisfying(Collection<? extends T> col, Tester<? super T> tester) {
		ElementsGetter<? extends T> getter = col.createElementsGetter();
		while(getter.hasNextElement()) {
			T help = getter.getNextElement();
			if(tester.test(help)) {
				this.add(help);
			}
		}
	}
}

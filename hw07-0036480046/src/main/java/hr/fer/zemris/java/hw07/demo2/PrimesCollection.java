package hr.fer.zemris.java.hw07.demo2;

import java.util.Iterator;

/**
 * This class represents collection of prime numbers.
 * @author Daria Matković
 *
 */
public class PrimesCollection implements Iterable<Integer> {

	/** number of prime numbers in collection **/
	private int numberOfElements;
	/** constant that represents first value to check **/
	private static final int FIRST_START = 1;
	
	/**
	 * Constructor that sets number of elements in collection
	 * @param n number of elements in collection
	 */
	public PrimesCollection(int n) {
		this.numberOfElements = n;
	}
	
	/**
	 * Nested class that represents iterator
	 * @author Daria Matković
	 *
	 */
	private class PrimesNestedClass implements Iterator<Integer> {
		/** number of elements in collection **/
		private int totalNumber;
		/** prime number that was last returned **/
		private int lastPrime;
		/** number of already iterated prime numbers**/
		private int numberOfReturned;
		
		/**
		 * Constructor that sets number of elements
		 * @param totalNumber
		 */
		public PrimesNestedClass(int totalNumber) {
			this.totalNumber = totalNumber;
			this.lastPrime = FIRST_START;
			this.numberOfReturned = 0;
		}
		
		@Override
		public boolean hasNext() {
			if(numberOfReturned < totalNumber) {
				return true;
			}
			return false;
		}

		@Override
		public Integer next() {
			lastPrime = primeAtIndex(lastPrime + 1);
			numberOfReturned++;
			return lastPrime;
		}
		
		/**
		 * Returns first prime number that occurs after number start
		 * @param start
		 * @return
		 */
		private Integer primeAtIndex(int start) {
			int i = start;
			
			while(true) {
				int counter = 0;

				for(int num = i; num >= 1; num--) {
					if(i % num == 0) {
						counter = counter + 1;
					}
				}
		         
				if (counter == 2) {
					return i;
				}
				i++;
			}
		}
	}

	@Override
	public Iterator<Integer> iterator() {
		return new PrimesNestedClass(numberOfElements);
	}
}
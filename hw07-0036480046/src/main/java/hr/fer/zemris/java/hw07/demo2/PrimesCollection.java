package hr.fer.zemris.java.hw07.demo2;

import java.util.Iterator;

public class PrimesCollection implements Iterable<Integer> {

	private int numberOfElements;
	private static final int FIRST_START = 1;
	
	public PrimesCollection(int n) {
		this.numberOfElements = n;
	}
	
	private class PrimesNestedClass implements Iterator<Integer> {

		private int totalNumber;
		private int lastPrime;
		private int numberOfReturned;
		
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
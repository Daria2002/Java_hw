package hr.fer.zemris.java.hw07.demo2;

import java.util.Iterator;

public class PrimesCollection implements Iterable<Integer> {

	private static int numberOfElements;
	
	public PrimesCollection(int n) {
		this.numberOfElements = n;
	}
	
	private class PrimesNestedClass implements Iterator<Integer> {

		private int totalNumber;
		private int n = 0;
		
		public PrimesNestedClass(int totalNumber) {
			this.totalNumber = totalNumber;
		}
		
		@Override
		public boolean hasNext() {
			if(n < totalNumber) {
				return true;
			}
			return false;
		}

		@Override
		public Integer next() {
			n++;
			return primeAtIndex(n-1);
		}
		
		private Integer primeAtIndex(int n) {
			int numberOfAddedElem = 0;
			int i = 1;
			
			while(true) {
				int counter = 0;

				for(int num = i; num >= 1; num--) {
					if(i % num == 0) {
						counter = counter + 1;
					}
				}
		         
				if (counter == 2) {
					if(numberOfAddedElem == n) {
						return i;
					}
					numberOfAddedElem++;
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
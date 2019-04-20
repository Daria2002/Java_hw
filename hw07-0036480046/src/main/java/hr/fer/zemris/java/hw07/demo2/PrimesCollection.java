package hr.fer.zemris.java.hw07.demo2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PrimesCollection implements Iterable<Integer> {

	private final List<Integer> primeList;
	
	public PrimesCollection(int number) {
		primeList = setPrimeList(number);
	}
	
	private List<Integer> setPrimeList(int n) {
		List<Integer> primeList = new ArrayList<Integer>();
	
		int numberOfAddedElem = 0;
		int i = 0;
		
		while(numberOfAddedElem < n) {
			int counter = 0;

			for(int num = i; num >= 1; num--) {
				if(i % num==0) {
					counter = counter + 1;
				}
			}
	         
			if (counter == 2) {
				numberOfAddedElem++;
				primeList.add(i);
			}
			i++;
		}
		return primeList;
	}

	@Override
	public Iterator<Integer> iterator() {
		return primeList.iterator();
	}
}
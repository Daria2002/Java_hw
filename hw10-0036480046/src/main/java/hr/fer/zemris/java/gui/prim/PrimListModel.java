package hr.fer.zemris.java.gui.prim;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

public class PrimListModel implements ListModel<Integer> {

	List<Integer> prim = new ArrayList<Integer>();
	List<ListDataListener> ldl = new ArrayList<ListDataListener>();
	private static final int FIRST_START = 0;
	int lastPrime = FIRST_START;
	
	public PrimListModel() {
		prim.add(1);
	}
	
	void next() {
		if(lastPrime == FIRST_START) {
			lastPrime++;
			return;
		}
		lastPrime = primeAtIndex(lastPrime+1);
		prim.add(lastPrime);
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

	@Override
	public int getSize() {
		return prim.size();
	}

	@Override
	public Integer getElementAt(int index) {
		return prim.get(index);
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		ldl.add(l);
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		ldl.remove(l);
	}

}

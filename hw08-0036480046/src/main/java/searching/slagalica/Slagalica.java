package searching.slagalica;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import searching.algorithms.Transition;

/**
 * This class represents program for solving puzzle.
 * @author Daria Matković
 *
 */
public class Slagalica implements Supplier<KonfiguracijaSlagalice>, 
Function<KonfiguracijaSlagalice, List<Transition<KonfiguracijaSlagalice>>>, 
Predicate<KonfiguracijaSlagalice> {

	/** puzzle configuration **/
	KonfiguracijaSlagalice puzzleConfig;
	
	/**
	 * This method represents constructor for KonfiguracijaSlagalice that initialize
	 * object of KonfiguracijaSlagalice
	 * @param puzzleConfig
	 */
	public Slagalica(KonfiguracijaSlagalice puzzleConfig) {
		this.puzzleConfig = puzzleConfig;
	}

	@Override
	public boolean test(KonfiguracijaSlagalice t) {
		for(int i = 0; i < t.getPolje().length; i++) {
			// last element need to be 0, not 9
			if(i == 8 && t.getPolje()[8] == 0) {
				return true;
			}
			
			// check that element is equal to index + 1
			if(t.getPolje()[i] != i + 1) {
				return false;
			}
		}
		
		return true;
	}

	@Override
	public List<Transition<KonfiguracijaSlagalice>> apply(KonfiguracijaSlagalice t) {
		return getTransitions(t.indexOfSpace(), t.getPolje());
	}

	/**
	 * This method gets new transitions in puzzle represented with given int array.
	 * @param starIndex index of space *
	 * @param polje int array that represents puzzle.
	 * @return list of possible transitions
	 */
	private List<Transition<KonfiguracijaSlagalice>> getTransitions(int spaceIndex, int[] polje) {
		switch (spaceIndex) {
		case 0:
			return getNext(new int[]{1, 3}, polje, spaceIndex);
				
		case 1:
			return getNext(new int[]{0, 2, 4}, polje, spaceIndex);
				
		case 2:
			return getNext(new int[]{1, 5}, polje, spaceIndex);
		
		case 3:
			return getNext(new int[]{0, 4, 6}, polje, spaceIndex);
		
		case 4:
			return getNext(new int[]{1, 3, 5, 7}, polje, spaceIndex);

		case 5:
			return getNext(new int[]{2, 4, 8}, polje, spaceIndex);
			
		case 6:
			return getNext(new int[]{3, 7}, polje, spaceIndex);
		
		case 7:
			return getNext(new int[]{4, 6, 8}, polje, spaceIndex);
		
		case 8:
			return getNext(new int[]{5, 7}, polje, spaceIndex);
		
		default:
			System.out.println("Puzzle need to be size 3x3");
			return null;
		}
	}

	/**
	 * This method returns list with next positions
	 * @param indexes array of indexes that represents next possible positions
	 * @param puzzle configuration
	 * @param spaceIndex index of space
	 * @return
	 */
	private List<Transition<KonfiguracijaSlagalice>> getNext(
			int[] indexes, int[] puzzle, int spaceIndex) {
		List<Transition<KonfiguracijaSlagalice>> list = 
				new LinkedList<Transition<KonfiguracijaSlagalice>>();
		
		for(int index : indexes) {
			list.add(new Transition<KonfiguracijaSlagalice>(
					new KonfiguracijaSlagalice(swap(puzzle, spaceIndex, index)), 1));
		}
		
		return list;
	}

	/**
	 * This method swaps space with value on new space index 
	 * @param polje int array where values need to be swapped
	 * @param oldSpaceIndex old index of space
	 * @param newSpaceIndex new index of space
	 * @return new int array where values are swapped
	 */
	private int[] swap(int[] polje, int oldSpaceIndex, int newSpaceIndex) {
		int[] help = Arrays.copyOf(polje, polje.length);
		int temp = help[newSpaceIndex];
		help[newSpaceIndex] = 0;
		help[oldSpaceIndex] = temp;
		
		return help;
	}

	@Override
	public KonfiguracijaSlagalice get() {
		return puzzleConfig;
	}
}

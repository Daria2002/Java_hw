package searching.slagalica;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import searching.algorithms.Transition;

public class Slagalica implements Supplier<KonfiguracijaSlagalice>, 
Function<KonfiguracijaSlagalice, List<Transition<KonfiguracijaSlagalice>>>, 
Predicate<KonfiguracijaSlagalice> {

	KonfiguracijaSlagalice puzzleConfig;
	
	public Slagalica(KonfiguracijaSlagalice puzzleConfig) {
		this.puzzleConfig = puzzleConfig;
	}

	@Override
	public boolean test(KonfiguracijaSlagalice t) {
		for(int i = 0; i < t.getPolje().length; i++) {
			if(t.getPolje()[i] != i) {
				return false;
			}
		}
		
		return true;
	}

	@Override
	public List<Transition<KonfiguracijaSlagalice>> apply(KonfiguracijaSlagalice t) {
		List<Transition<KonfiguracijaSlagalice>> list = new LinkedList<Transition<KonfiguracijaSlagalice>>();
		
		// index of *
		int starIndex = t.indexOfSpace();
		
		list = getTransitions(starIndex, t.getPolje());
		
		return list;
	}

	private List<Transition<KonfiguracijaSlagalice>> getTransitions(int starIndex, int[] polje) {
		List<Transition<KonfiguracijaSlagalice>> list = new LinkedList<Transition<KonfiguracijaSlagalice>>();
		
		switch (starIndex) {
		case 0:
			list.add(new Transition<KonfiguracijaSlagalice>(
					new KonfiguracijaSlagalice(swap(polje, 0, 1)), 1));
			
			list.add(new Transition<KonfiguracijaSlagalice>(
					new KonfiguracijaSlagalice(swap(polje, 0, 3)), 1));
			break;

		case 1:
			list.add(new Transition<KonfiguracijaSlagalice>(
					new KonfiguracijaSlagalice(swap(polje, 1, 0)), 1));
			
			list.add(new Transition<KonfiguracijaSlagalice>(
					new KonfiguracijaSlagalice(swap(polje, 1, 2)), 1));
			
			list.add(new Transition<KonfiguracijaSlagalice>(
					new KonfiguracijaSlagalice(swap(polje, 1, 4)), 1));
			break;
			
		case 2:
			list.add(new Transition<KonfiguracijaSlagalice>(
					new KonfiguracijaSlagalice(swap(polje, 2, 1)), 1));
			
			list.add(new Transition<KonfiguracijaSlagalice>(
					new KonfiguracijaSlagalice(swap(polje, 2, 5)), 1));
			break;	
		
		case 3:
			list.add(new Transition<KonfiguracijaSlagalice>(
					new KonfiguracijaSlagalice(swap(polje, 3, 0)), 1));
			
			list.add(new Transition<KonfiguracijaSlagalice>(
					new KonfiguracijaSlagalice(swap(polje, 3, 4)), 1));
			
			list.add(new Transition<KonfiguracijaSlagalice>(
					new KonfiguracijaSlagalice(swap(polje, 3, 6)), 1));
			break;	
		
		case 4:
			list.add(new Transition<KonfiguracijaSlagalice>(
					new KonfiguracijaSlagalice(swap(polje, 4, 1)), 1));
			
			list.add(new Transition<KonfiguracijaSlagalice>(
					new KonfiguracijaSlagalice(swap(polje, 4, 3)), 1));
			
			list.add(new Transition<KonfiguracijaSlagalice>(
					new KonfiguracijaSlagalice(swap(polje, 4, 5)), 1));
			
			list.add(new Transition<KonfiguracijaSlagalice>(
					new KonfiguracijaSlagalice(swap(polje, 4, 7)), 1));
			break;

		case 5:
			list.add(new Transition<KonfiguracijaSlagalice>(
					new KonfiguracijaSlagalice(swap(polje, 5, 2)), 1));
			
			list.add(new Transition<KonfiguracijaSlagalice>(
					new KonfiguracijaSlagalice(swap(polje, 5, 4)), 1));
			
			list.add(new Transition<KonfiguracijaSlagalice>(
					new KonfiguracijaSlagalice(swap(polje, 5, 8)), 1));
			break;
			
		case 6:
			list.add(new Transition<KonfiguracijaSlagalice>(
					new KonfiguracijaSlagalice(swap(polje, 6, 3)), 1));
			
			list.add(new Transition<KonfiguracijaSlagalice>(
					new KonfiguracijaSlagalice(swap(polje, 6, 7)), 1));
			break;	
		
		case 7:
			list.add(new Transition<KonfiguracijaSlagalice>(
					new KonfiguracijaSlagalice(swap(polje, 7, 4)), 1));
			
			list.add(new Transition<KonfiguracijaSlagalice>(
					new KonfiguracijaSlagalice(swap(polje, 7, 6)), 1));
			
			list.add(new Transition<KonfiguracijaSlagalice>(
					new KonfiguracijaSlagalice(swap(polje, 7, 8)), 1));
			break;
		
		case 8:
			list.add(new Transition<KonfiguracijaSlagalice>(
					new KonfiguracijaSlagalice(swap(polje, 8, 5)), 1));
			
			list.add(new Transition<KonfiguracijaSlagalice>(
					new KonfiguracijaSlagalice(swap(polje, 8, 7)), 1));
			break;
		
		default:
			System.out.println("Puzzle need to be size 3x3");
			break;
		}
		
		return list;
	}

	private int[] swap(int[] polje, int i, int j) {
		int temp = polje[i];
		polje[i] = polje[j];
		polje[j] = temp;
		
		return polje;
	}

	@Override
	public KonfiguracijaSlagalice get() {
		return puzzleConfig;
	}
}

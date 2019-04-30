package searching.slagalica;

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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Transition<KonfiguracijaSlagalice>> apply(KonfiguracijaSlagalice t) {
		return null;
	}

	@Override
	public KonfiguracijaSlagalice get() {
		return puzzleConfig;
	}
}

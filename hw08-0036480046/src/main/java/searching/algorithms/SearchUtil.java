package searching.algorithms;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class SearchUtil {

	public static <S> Node<S> bfs(Supplier<S> s0, 
			Function<S, List<Transition<S>>> succ, Predicate<S> goal) {

		List<Node<S>> toBeSearched = new LinkedList<Node<S>>();
		toBeSearched.add(new Node<S>(null, s0.get(), 0));
		
		Node<S> ni = null;
		
		while(toBeSearched.size() > 0) {
			ni = toBeSearched.get(0);
			toBeSearched.remove(0);
			
			if(goal.test(ni.getState())) {
				return ni;
			}
			
			for(Transition<S> transition : succ.apply(ni.getState())) {
				toBeSearched.add(new Node<S>(ni, transition.getState(),
						ni.getCost() + transition.getCost()));
			}
		}
		
		return ni;
	}
}

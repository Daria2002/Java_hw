package searching.algorithms;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * SearchUtil class represents program for solving puzzle. It has two different 
 * algorithams: bfs and bfsv
 * @author Daria Matković
 *
 */
public class SearchUtil {

	/**
	 * Bfs algorithm for solving puzzle
	 * @param <S>
	 * @param s0 first state
	 * @param succ neighbors
	 * @param goal goal state
	 * @return states
	 */
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
	
	/**
	 * Bfsv algorithm for solving puzzle. It is same as bsv, except it has list
	 * with already visited states.
	 * @param <S>
	 * @param s0 first node
	 * @param succ neighbors
	 * @param goal goal
	 * @return states
	 */
	public static <S> Node<S> bfsv(Supplier<S> s0, 
			Function<S, List<Transition<S>>> succ, Predicate<S> goal) {

		List<Node<S>> toBeSearched = new LinkedList<Node<S>>();
		Set<S> visitedSet = new HashSet<S>();
		
		toBeSearched.add(new Node<S>(null, s0.get(), 0));
		visitedSet.add(toBeSearched.get(0).getState());
		
		Node<S> ni = null;
		
		while(!toBeSearched.isEmpty()) {
			ni = toBeSearched.remove(0);
			
			if(goal.test(ni.getState())) {
				return ni;
			}
			
			for(Transition<S> transition : succ.apply(ni.getState())) {
				if(visitedSet.contains(transition.getState())) {
					continue;
				}
				
				toBeSearched.add(new Node<S>(ni, transition.getState(),
						ni.getCost() + transition.getCost()));
				visitedSet.add(transition.getState());
			}
		}
		
		return ni;
	}
}

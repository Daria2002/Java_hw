package coloring.algorithms;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * This class is used for exploring subspace.
 * @author Daria Matković
 *
 */
public class SubspaceExploreUtil {
	
	/**
	 * This method gets start state, process it if state is acceptable and
	 * do the same for next elements
	 * @param <S>
	 * @param s0 start state
	 * @param process function for processing state s
	 * @param succ function of next states
	 * @param acceptable test if given element can be processed or not
	 */
	public static <S> void bfs(Supplier<S> s0, Consumer<S> process,
			Function<S,List<S>> succ, Predicate<S> acceptable) {
		
		List<S> toExplore = new LinkedList<S>();
		toExplore.add(s0.get());
		
		while(toExplore.size() > 0) {
			S si = toExplore.get(0);
			toExplore.remove(0);
			
			if(!acceptable.test(si)) {
				continue;
			}
			
			process.accept(si);
			for(S el : succ.apply(si)) {
				toExplore.add(el);
			}
		}
	}
}

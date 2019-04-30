package searching.algorithms;

/**
 * This class represents pair (state, cost).
 * @author Daria Matković
 *
 * @param <S>
 */
public class Transition<S> {

	/** current state **/
	private S state;
	/** cost of current state **/
	private double cost;
	
	/**
	 * Constructor that initialize state and cost
	 * @param state state
	 * @param cost cost
	 */
	public Transition(S state, double cost) {
		this.state = state;
		this.cost = cost;
	}

	/**
	 * Gets state
	 * @return state
	 */
	public S getState() {
		return state;
	}

	/**
	 * Gets cost
	 * @return cost
	 */
	public double getCost() {
		return cost;
	}
}

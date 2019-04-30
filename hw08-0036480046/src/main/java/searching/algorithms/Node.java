package searching.algorithms;

/**
 * This class represents node that has reference to current
 * state, reference to parent state and cost paid for current state.
 * @author Daria Matković
 *
 * @param <S>
 */
public class Node<S> {

	/** reference to parent **/
	private Node<S> parent;
	/** current state **/
	private S state;
	/** cost of current state **/
	private double cost;
	
	/**
	 * Constructor that initialize parent, state and cost
	 * @param parent parent
	 * @param state state
	 * @param cost cost
	 */
	public Node(Node<S> parent, S state, double cost) {
		this.parent = parent;
		this.state = state;
		this.cost = cost;
	}
	
	/**
	 * This method gets state
	 * @return state
	 */
	public S getState() {
		return state;
	}
	
	/**
	 * This method gets cost
	 * @return cost
	 */
	public double getCost() {
		return cost;
	}
	
	/**
	 * This method gets parent 
	 * @return reference to parent node
	 */
	public Node<S> getParent() {
		return parent;
	}
}

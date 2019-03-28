package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;

/**
 * A node representing a single for-loop construct.
 * @author Daria Matković
 *
 */
public class ForLoopNode extends Node {
	
	/**
	 * Default constructor that initialize variable, start expression, end
	 * expression and step expression
	 * @param variable variable 
	 * @param startExpression start value
	 * @param endExpression end value
	 * @param stepExpression step
	 */
	public ForLoopNode(ElementVariable variable, Element startExpression,
			Element endExpression, Element stepExpression) {
		super();
		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	}

	private ElementVariable variable;
	private Element startExpression;
	private Element endExpression;
	private Element stepExpression = null;
	
	/**
	 * Returns variable
	 * @return variable
	 */
	public ElementVariable getVariable() {
		return variable;
	}
	
	/**
	 * Returns start expression
	 * @return start expression
	 */
	public Element getStartExpression() {
		return startExpression;
	}
	
	/**
	 * Returns end expression
	 * @return end expression
	 */
	public Element getEndExpression() {
		return endExpression;
	}
	
	/**
	 * Returns step expression
	 * @return step expression
	 */
	public Element getStepExpression() {
		return stepExpression;
	}
	
	@Override
	public String toString() {
		String nodeString = "";
		nodeString += "{$FOR " + this.variable.asText() + " " +
		this.startExpression.asText() + " " + this.endExpression.asText() +
		" " + this.stepExpression.asText() + " $}";
		for(int i = 0; i < this.numberOfChildren(); i++) {
			nodeString += this.getChild(i).toString();
		}
		nodeString += "{$END$}";
		
		return nodeString;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((endExpression == null) ? 0 : endExpression.hashCode());
		result = prime * result + ((startExpression == null) ? 0 : startExpression.hashCode());
		result = prime * result + ((stepExpression == null) ? 0 : stepExpression.hashCode());
		result = prime * result + ((variable == null) ? 0 : variable.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if(getClass() != obj.getClass())
			return false;
		ForLoopNode node = (ForLoopNode)obj;
		if(!(this.variable.equals(node.variable) &&
				this.startExpression.equals(node.startExpression) &&
				this.endExpression.equals(node.endExpression) &&
				this.stepExpression.equals(node.stepExpression))) {
			return false;
		}
		return true;
	}
}

package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;

/**
 * A node representing a single for-loop construct.
 * @author Daria Matković
 *
 */
public class ForLoopNode extends Node {

	
	
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
	
	public ElementVariable getVariable() {
		return variable;
	}
	
	public Element getStartExpression() {
		return startExpression;
	}
	
	public Element getEndExpression() {
		return endExpression;
	}
	
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
	
}

package hr.fer.zemris.java.custom.scripting.nodes;

import java.util.Arrays;

import hr.fer.zemris.java.custom.scripting.elems.Element;

/**
 * A node representing a command which generates some textual output dynamically
 * @author Daria Matković
 *
 */
public class EchoNode extends Node {

	/**
	 * Default constructor, initialize elements
	 * @param elements given value to initialize
	 */
	public EchoNode(Element[] elements) {
		super();
		this.elements = elements;
	}

	private Element[] elements;

	/**
	 * Returns elements
	 * @return elements
	 */
	public Element[] getElements() {
		return elements;
	}
	
	public void accept(INodeVisitor visitor) {
		visitor.visitEchoNode(this);
	}
	
	@Override
	public String toString() {
		String nodeString = "";
		nodeString += "{$= ";
		
		for(int i = 0; i < this.elements.length; i++) {
			nodeString += this.elements[i].asText() + " ";
		}
		
		nodeString += "$}";
		
		return nodeString;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Arrays.hashCode(elements);
		
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (getClass() != obj.getClass())
			return false;
		
		EchoNode other = (EchoNode) obj;
		
		for(int i = 0; i < elements.length; i++) {
			if(!other.elements[i].equals(this.elements[i])) {
				return false;
			}
		}
		
		return true;
	}
}

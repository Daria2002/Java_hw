package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;

/**
 * A node representing a command which generates some textual output dynamically
 * @author Daria Matković
 *
 */
public class EchoNode extends Node {

	public EchoNode(Element[] elements) {
		super();
		this.elements = elements;
	}

	private Element[] elements;

	public Element[] getElements() {
		return elements;
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
}

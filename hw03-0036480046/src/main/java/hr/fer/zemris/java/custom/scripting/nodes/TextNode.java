package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Represents a piece of textual data.
 * @author Daria Matkovic
 *
 */
public class TextNode extends Node {
	
	public TextNode(String text) {
		this.text = text;
	}
	
	private String text;

	public String getText() {
		return text;
	}
	
	@Override
	public String toString() {
		return text;
	}
}

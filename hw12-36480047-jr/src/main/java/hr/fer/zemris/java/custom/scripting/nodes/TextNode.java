package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Represents a piece of textual data.
 * @author Daria Matkovic
 *
 */
public class TextNode extends Node {
	
	/**
	 * Default constructor, initialize text
	 * @param text text to initialize
	 */
	public TextNode(String text) {
		this.text = text;
	}
	
	/** text **/
	private String text;

	/**
	 * Returns text from text node
	 * @return text
	 */
	public String getText() {
		return text;
	}
	
	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitTextNode(this);
	}
	
	@Override
	public String toString() {
		return text;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (getClass() != obj.getClass())
			return false;
		TextNode other = (TextNode) obj;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
	}
}

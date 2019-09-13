package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;

public class NowNode extends Node {

	private Element format;
	
	public NowNode(Element format) {
		this.format = format;
	}

	public Element getFormat() {
		return format;
	}

	public void setFormat(Element format) {
		this.format = format;
	}
}

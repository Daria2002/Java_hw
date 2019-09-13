package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;

public class NowNode extends Node {

	private String format;
	
	public NowNode(Element format) {
		if("yyyy-MM-dd".equals(format.asText()) || "HH:mm:ss".equals(format.asText())) {
			this.format = format.asText();
		} else {
			this.format = "yyyy-MM-dd HH:mm:ss";
		}
	}

	public String getFormat() {
		return format;
	}
	
	@Override
	public String toString() {
		return "bok iz now node";
	}
	
	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitNowNode(this);
	}
}

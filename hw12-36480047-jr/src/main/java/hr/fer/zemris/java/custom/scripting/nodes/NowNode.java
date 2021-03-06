package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;

public class NowNode extends Node {

	private String format;
	
	public NowNode(Element format) {
		this.format = removeQuotes(format.asText());
		if("yyyy-MM-dd".equals(removeQuotes(format.asText())) ||
				"HH:mm:ss".equals(removeQuotes(format.asText()))) {
			this.format = removeQuotes(format.asText());
		} else {
			this.format = "yyyy-MM-dd HH:mm:ss";
		}
	}
	
	private String removeQuotes(String string) {
		String format = "";
		
		for(int i = 0; i < string.length(); i++) {
			if(string.charAt(i) != '"' && string.charAt(i) != '\\') {
				format += string.charAt(i);
			}
		}
		
		return format;
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

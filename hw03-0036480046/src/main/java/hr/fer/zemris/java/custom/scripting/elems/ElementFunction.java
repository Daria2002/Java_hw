package hr.fer.zemris.java.custom.scripting.elems;

public class ElementFunction extends Element {
	
	public ElementFunction(String name) {
		this.name = name;
	}
	
	private String name;

	public String getName() {
		return name;
	}
	
	@Override
	public String asText() {
		return "@" + name;
	}
}

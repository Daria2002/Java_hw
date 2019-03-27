package hr.fer.zemris.java.custom.scripting.elems;

public class ElementConstantInteger extends Element {
	
	public ElementConstantInteger(int value) {
		this.value = value;
	}
	
	private int value;

	public int getValue() {
		return value;
	}
	
	@Override
	public String asText() {
		return String.valueOf(value);
	}
}

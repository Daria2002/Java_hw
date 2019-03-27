package hr.fer.zemris.java.custom.scripting.elems;

public class ElementOperator extends Element {

	public ElementOperator(String symbol) {
		this.symbol = symbol;
	}
	
	private String symbol;

	public String getSymbol() {
		return symbol;
	}
	
	@Override
	public String asText() {
		return symbol;
	}
}

package hr.fer.zemris.java.custom.scripting.elems;

/**
 * This class extends Element, and provides string value that represents symbol
 * @author Daria Matković
 *
 */
public class ElementOperator extends Element {

	/**
	 * Constructor that initialize symbol
	 * @param symbol symbol to initialize 
	 */
	public ElementOperator(String symbol) {
		this.symbol = symbol;
	}
	
	private String symbol;

	/**
	 * Returns symbol
	 * @return symbol
	 */
	public String getSymbol() {
		return symbol;
	}
	
	@Override
	public String asText() {
		return symbol;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((symbol == null) ? 0 : symbol.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ElementOperator other = (ElementOperator) obj;
		if (symbol == null) {
			if (other.symbol != null)
				return false;
		} else if (!symbol.equals(other.symbol))
			return false;
		return true;
	}
}

package hr.fer.zemris.java.custom.scripting.elems;

/**
 * This class extends Element, and provides constant integer value for it
 * @author Daria Matković
 *
 */
public class ElementConstantInteger extends Element {
	
	/**
	 * Constructor that initialize value
	 * @param value
	 */
	public ElementConstantInteger(int value) {
		this.value = value;
	}
	
	private int value;

	/**
	 * Returns int value
	 * @return value
	 */
	public int getValue() {
		return value;
	}
	
	@Override
	public String asText() {
		return String.valueOf(value);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + value;
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
		ElementConstantInteger other = (ElementConstantInteger) obj;
		if (value != other.value)
			return false;
		return true;
	}
}

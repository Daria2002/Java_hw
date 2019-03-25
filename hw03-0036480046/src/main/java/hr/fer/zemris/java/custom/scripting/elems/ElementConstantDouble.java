package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Inherits Element and has single read-only double property: value. 
 * @author Daria Matković
 *
 */
public class ElementConstantDouble extends Element {
	
	/**
	 * Constructor where value is initialized.
	 * @param value value to initialize value
	 */
	public ElementConstantDouble(double value) {
		this.value = value;
	}
	
	private double value;

	public double getValue() {
		return value;
	}
	
	@Override
	public String asText() {
		return String.valueOf(getValue());
	}
}

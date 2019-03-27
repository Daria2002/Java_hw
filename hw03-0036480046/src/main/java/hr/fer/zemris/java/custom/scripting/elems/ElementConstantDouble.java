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
		return String.valueOf(value);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(value);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		ElementConstantDouble other = (ElementConstantDouble) obj;
		if (Double.doubleToLongBits(value) != Double.doubleToLongBits(other.value))
			return false;
		return true;
	}
}

package hr.fer.zemris.java.custom.scripting.elems;

/**
 * This class extends Element, and provides function name
 * @author Daria Matković
 *
 */
public class ElementFunction extends Element {
	
	/**
	 * Constructor that initialize function name
	 * @param name name to initialize
	 */
	public ElementFunction(String name) {
		this.name = name;
	}
	
	private String name;

	/**
	 * Returns function name
	 * @return function name
	 */
	public String getName() {
		return name;
	}
	
	@Override
	public String asText() {
		return "@" + name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		ElementFunction other = (ElementFunction) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}

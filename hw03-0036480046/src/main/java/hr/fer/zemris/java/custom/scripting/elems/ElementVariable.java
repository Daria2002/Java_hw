package hr.fer.zemris.java.custom.scripting.elems;

/**
 * This class extends Element, and provides string value for it
 * @author Daria Matković
 *
 */
public class ElementVariable extends Element {

	/**
	 * Constructor initialize string value
	 * @param name value to initialize
	 */
	public ElementVariable(String name) {
		this.name = name;
	}
	
	private String name;

	/**
	 * Returns straing name
	 * @return name
	 */
	public String getName() {
		return name;
	}
	
	@Override
	public String asText() {
		return name;
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
		ElementVariable other = (ElementVariable) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}

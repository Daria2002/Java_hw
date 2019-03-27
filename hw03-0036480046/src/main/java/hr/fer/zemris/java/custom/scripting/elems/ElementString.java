package hr.fer.zemris.java.custom.scripting.elems;

public class ElementString extends Element {

	public ElementString(String value) {
		this.value = value;
	}
	
	private String value;

	public String getValue() {
		return value;
	}
	
	@Override
	public String asText() {
		return "\"" + value.replace("\\", "\\\\").replace("\n", "\\n")
				.replace("\r", "\\r").replace("\t", "\\t")
				.replace("\"", "\\\"") + "\"";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		ElementString other = (ElementString) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
}

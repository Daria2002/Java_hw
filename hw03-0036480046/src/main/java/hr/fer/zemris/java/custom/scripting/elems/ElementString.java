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
}

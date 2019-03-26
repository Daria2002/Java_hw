package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * This class implements token. Every object of this class has type and value,
 * and methods getValue and getType.
 * 
 * @author Daria Matkovic
 *
 */
public class TokenSmart {
	private Object value;
	private TokenSmartType type;
	
	/**
	 * Constructor initialize token value and type
	 *  
	 * @param type type is token type
	 * @param value token value
	 */
	public TokenSmart(TokenSmartType type, Object value) {
		this.value = value;
		this.type = type;
	}
	
	/**
	 * Gets token value.
	 * 
	 * @return token value.
	 */
	public Object getValue() {
		return value;
	}
	
	/**
	 * Gets token type.
	 * 
	 * @return token type
	 */
	public TokenSmartType getType() {
		return type;
	}
}
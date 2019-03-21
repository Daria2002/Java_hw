package hr.fer.zemris.java.hw03.prob1;

/**
 * This class implements token. Every object of this class has type and value,
 * and methods getValue and getType.
 * 
 * @author Daria Matkovic
 *
 */
public class Token {
	private Object value;
	private TokenType type;
	
	/**
	 * Constructor initialize token value and type
	 *  
	 * @param type type is token type
	 * @param value token value
	 */
	public Token(TokenType type, Object value) {
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
	public TokenType getType() {
		return type;
	}
}
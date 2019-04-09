package hr.fer.zemris.java.hw05.db;

/**
 * This class implements token. Every object of this class has type and value,
 * and methods getValue and getType.
 * 
 * @author Daria Matkovic
 *
 */
public class TokenQuery {
	private Object value;
	private TokenQueryType type;
	
	/**
	 * Constructor initialize token value and type
	 *  
	 * @param type type is token type
	 * @param value token value
	 */
	public TokenQuery(TokenQueryType type, Object value) {
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
	public TokenQueryType getType() {
		return type;
	}
}
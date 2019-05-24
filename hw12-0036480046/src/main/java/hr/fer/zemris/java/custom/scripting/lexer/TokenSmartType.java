package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Enumeration for token type.
 * @author Daria Matkovic
 *
 */
public enum TokenSmartType {
	
	/** End of file **/
	EOF,
	/** text outside of tag **/
	TEXT,
	/** {$ sign **/
	TAG_OPEN,
	/** $} sign **/
	TAG_CLOSE, 
	/** valid name after tag open sign **/
	TAG_NAME, 
	/** elements in tag **/
	TAG_ELEMENT
	
}

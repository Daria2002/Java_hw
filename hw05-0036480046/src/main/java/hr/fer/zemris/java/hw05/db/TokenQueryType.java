package hr.fer.zemris.java.hw05.db;

/**
 * Enumeration for token type.
 * @author Daria Matkovic
 *
 */
public enum TokenQueryType {
	
	/** jmbag, last name, first name are only attributes allowed in query **/
	ATRIBUTE_NAME,
	/**  >, <, >=, <=, =, !=, LIKE **/
	OPERATOR, 
	/** quoted string **/
	STRING_LITERAL,
	/** and **/
	LOGICAL_OPERATOR,
}

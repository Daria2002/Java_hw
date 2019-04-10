package hr.fer.zemris.java.hw05.db;

/**
 * This class implements lexer for queries.
 * @author Daria Matković
 *
 */
public class QueryLexer {

	/** given query **/
	private String query;
	/** logical operator and **/
	private static final String AND = "and";
	/** token **/
	private TokenQuery token;
	/** queries array **/
	private String[] queriesArray;
	/** total number of queries **/
	private int numberOfQueries;
	/** index of last analyzed query **/
	private int lastQueryIndex;
	/** type of next token(attribute = 1, operator = 2, stringLiteral = 3) **/
	private int nextTokenType;
	
	/**
	 * Constructor that initialize variables
	 * @param query given query
	 */
	public QueryLexer(String query) {
		this.query = query.trim().replaceAll("\\s+", " ");
		this.token = new TokenQuery(TokenQueryType.ATRIBUTE_NAME, "");
		
		this.lastQueryIndex = -1;
		this.nextTokenType = 1;
		
		this.numberOfQueries = countQueries(this.query, AND);
		
		if(numberOfQueries == 0) {
			throw new IllegalArgumentException("No queries found");
		}
		
		this.queriesArray = new String[numberOfQueries];
		
		if(numberOfQueries > 1) {
			queriesArray = addQueries(this.query);
			
			if(numberOfQueries > queriesArray.length) {
				throw new IllegalArgumentException("Invalid query");
			}
			
		} else {
			queriesArray[0] = query;
		}
	}
	
	/**
	 * Generates and returns next token.
	 * @return next token
	 */
	public TokenQuery nextToken() {
		
		// if all queries analyzed
		if(lastQueryIndex >= this.queriesArray.length-1) {
			return null;
		}
		
		switch (nextTokenType) {
		case 1:
			token = getAttributeToken();
			if(token == null) {
				throw new IllegalArgumentException("Illegal expression");
			}
			
			nextTokenType++;
			break;
			
		case 2:
			token = getOperatorToken();
			if(token == null) {
				throw new IllegalArgumentException("Illegal expression");
			}
			nextTokenType++;
			break;

		case 3:
			token = getStringLiteralToken();
			
			if(token == null) {
				throw new IllegalArgumentException("Illegal expression");
			}
			
			nextTokenType = 1;
			this.lastQueryIndex++;
			break;
			
		default:
			return null;
		}
		
		return token;
	}
	
	/**
	 * Gets token of type string literal
	 * @return token of type string literal
	 */
	private TokenQuery getStringLiteralToken() {
		String currentQuery = queriesArray[lastQueryIndex + 1].trim(); 
		
		int startIndex = -1;
		int endIndex = -1;
		
		for (int i = 0; i< currentQuery.length(); i++) {
			if (currentQuery.charAt(i) == '"') {
				
				if (startIndex < 0) {
					startIndex = i;
					continue;
				}
				
				if (endIndex < 0) {
					endIndex = i;
					break;
				}
			}
		}
		
		if(startIndex >= endIndex) {
			throw new IllegalArgumentException("Invalid string literal.");
		}
		
		String value = currentQuery.substring(startIndex + 1, endIndex);
		
		checkSymbol(value);
		
		return new TokenQuery(TokenQueryType.STRING_LITERAL, value);
	}
	
	/**
	 * Checks that operator occurred in given query
	 * @param operator operator to check
	 * @param query given query
	 * @return operator if occurred, otherwise false  
	 */
	private String checkOperator(String operator, String query) {
		if(query.matches(".*[a-zA-Z]+(\\s+)?" + operator + "(\\s+)?\".*")) {
			return operator;
		}
		return null;
	}
	
	/**
	 * Sets array of operators
	 * @param operators operators
	 * @return Array of operators
	 */
	private String[] setArray(String[] operators) {
		String[] array = new String[operators.length];
		
		for(int i = 0; i < operators.length; i++) {
			array[i] = operators[i];
		}
		
		return array;
	}
	
	/**
	 * Gets token of type operator
	 * @return token of type operator
	 */
	private TokenQuery getOperatorToken() {
		String[] operators = setArray(new String[]{
				"<=", ">=", "<", ">", "!=", "=", "LIKE"});
				
		for (int i = 0; i < operators.length; i++) {
			if(checkOperator(operators[i], queriesArray[lastQueryIndex + 1]) != null) {
				
				return new TokenQuery(TokenQueryType.OPERATOR, operators[i]);
			}
		}
		throw new IllegalArgumentException("Invalid operator");
	}

	/**
	 * Gets attribute token
	 * @return token of type attribute
	 */
	private TokenQuery getAttributeToken() {
		String value = "";
		String wholeQuery = queriesArray[lastQueryIndex+1].trim(); 

		for(int i = 0; i < wholeQuery.length(); i++) {
			if(wholeQuery.charAt(i) == '<' || wholeQuery.charAt(i) == '=' ||
					(wholeQuery.charAt(i)  == '<' && wholeQuery.charAt(i+1) == '=') ||
					wholeQuery.charAt(i) == '>' || wholeQuery.charAt(i) == ' ' ||
					(wholeQuery.charAt(i) == '>' && wholeQuery.charAt(i) == '=') ||
					(wholeQuery.charAt(i) == '!' && wholeQuery.charAt(i+1) == '=')) {
				
				if(checkSymbol(value)) {
					throw new IllegalArgumentException("Atribute contains *.");
				}
				
				return new TokenQuery(TokenQueryType.ATRIBUTE_NAME, value);
			}
			value += wholeQuery.charAt(i);
		}
		throw new IllegalArgumentException("Invalid atribute.");
	}

	/**
	 * Checks that given value doesn't contain multiple *
	 * @param value given value to check
	 * @return true if symbol occurred once, throws exception if * occurred more times
	 * and false if * didn't occurred
	 */
	private boolean checkSymbol(String value) {
		boolean symbolPosition = false;
		
		for(int i = 0; i < value.length(); i++) {
			
			if(value.charAt(i) == '*') {
				
				if(symbolPosition) {
					throw new IllegalArgumentException("More occurrances of * in string.");
				}
				symbolPosition = true;
			}
		}
		return symbolPosition;
	}
	
	/**
	 * Separate queries in the array
	 * @param query string of queries
	 * @return array of queries
	 */
	private String[] addQueries(String query) {
		String[] operators = setArray(new String[]
				{"and", "AND", "anD", "aND", "And", "ANd", "AnD", "aNd"});
			
		for(int i = 0; i < operators.length; i++) {
			query = query.replaceAll(operators[i], AND);
		}
		
		return query.split(AND);
	}

	/**
	 * Check if there are more queries or only one
	 * @param query given query
	 * @return true if there are more queries, otherwise false
	 */
	private int countQueries(String query, String and) {
		String helpQuery = query.toLowerCase();
		int lastIndex = 0;
		int count = 0;

		while(lastIndex != -1){

		    lastIndex = helpQuery.indexOf(and,lastIndex);

		    if(lastIndex != -1){
		        count ++;
		        lastIndex += and.length();
		    }
		}
		return count+1;
	}

	/**
	 * Gets last generated token. Can be called more times and it doesn't runs
	 * generation of next token.
	 * @return last generated token
	 */
	public TokenQuery getToken() {
		return token;
	}
	
}

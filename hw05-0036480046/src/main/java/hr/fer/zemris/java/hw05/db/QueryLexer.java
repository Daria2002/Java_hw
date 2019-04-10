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
		this.query = query.trim().replaceAll("(\\s)+", " ");
		this.token = new TokenQuery(TokenQueryType.ATRIBUTE_NAME, "");
		
		this.lastQueryIndex = -1;
		this.nextTokenType = 1;
		
		this.numberOfQueries = countQueries(this.query, AND);
		this.queriesArray = new String[numberOfQueries];
		
		if(numberOfQueries > 1) {
			queriesArray = addQueries(this.query);
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
	
	private TokenQuery getStringLiteralToken() {
		String currentQuery = queriesArray[lastQueryIndex + 1].trim(); 
		
		int startIndex = currentQuery.indexOf("\"");
		int endIndex = currentQuery.lastIndexOf("\"");
		String value = currentQuery.substring(startIndex + 1, endIndex);
		
		return new TokenQuery(TokenQueryType.STRING_LITERAL, value);
	}
	
	private String checkOperator(String operator, String query) {
		if(query.contains(operator)) {
			return operator;
		}
		
		return null;
	}
	
	private String[] setArray(String[] operators) {
		String[] array = new String[operators.length];
		
		for(int i = 0; i < operators.length; i++) {
			array[i] = operators[i];
		}
		
		return array;
	}
	
	private TokenQuery getOperatorToken() {
		String[] operators = setArray(new String[]{"<=", "<", ">", ">=", "=", "!=", "LIKE"});
				
		for (int i = 0; i < operators.length; i++) {
			if(checkOperator(operators[i], queriesArray[lastQueryIndex + 1]) != null) {
				return new TokenQuery(TokenQueryType.OPERATOR, operators[i]);
			}
		}
		
		return null;
	}

	private TokenQuery getAttributeToken() {
		String value = "";
		String wholeQuery = queriesArray[lastQueryIndex+1].trim(); 

		for(int i = 0; i < wholeQuery.length(); i++) {
			if(wholeQuery.charAt(i) == '<' || wholeQuery.charAt(i) == '=' ||
					(wholeQuery.charAt(i)  == '<' && wholeQuery.charAt(i+1) == '=') ||
					wholeQuery.charAt(i) == '>' || wholeQuery.charAt(i) == ' ' ||
					(wholeQuery.charAt(i) == '>' && wholeQuery.charAt(i) == '=') ||
					(wholeQuery.charAt(i) == '!' && wholeQuery.charAt(i+1) == '=')) {
				
				return new TokenQuery(TokenQueryType.ATRIBUTE_NAME, value);
			}
			value += wholeQuery.charAt(i);
		}
		return null;
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
			if(checkOperator(operators[i], query) != null) {
				return query.split(operators[i]);
			}
		}
		
		return null;
	}

	/**
	 * Check if there are more queries or only one
	 * @param query given query
	 * @return true if there are more queries, otherwise false
	 */
	private int countQueries(String query, String and) {
		if (query.isEmpty() || and.isEmpty()) {
			return 0;
		}
		
		int count = 0;
		int help = 0;
		
		while ((help = query.toLowerCase().indexOf(and, help)) != -1) {
			count++;
			help += and.length();
		}
		// if there is no logical operator and, only one query
		return count + 1;
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

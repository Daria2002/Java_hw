package hr.fer.zemris.java.hw05.db;

public class QueryLexer {

	private String query;
	private static final String AND = "and";
	
	public QueryLexer(String query) {
		this.query = query;
	}
	
	/**
	 * Generates and returns next token.
	 * @return next token
	 */
	private TokenQuery nextToken() {
		
		//int numberOfQueries = countQueries(this.query, AND);
		
		String[] queriesArray = addQueries(this.query);
		
		
	}
	
	private String[] addQueries(String query) {
		return query.toLowerCase().trim().split("and|\\AND|\\And|\\aNd\\anD\\|"
				+ "\\ANd|\\AnD|\\aND");
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
		
		while ((help = query.indexOf(and, help)) != -1) {
			count++;
			help += and.length();
		}
		return count;
	}

	/**
	 * Gets last generated token. Can be called more times and it doesn't runs
	 * generation of next token.
	 * @return last generated token
	 */
	private TokenQuery getToken() {
		
	}
	
}

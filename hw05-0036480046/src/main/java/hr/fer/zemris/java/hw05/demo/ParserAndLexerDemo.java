package hr.fer.zemris.java.hw05.demo;

import hr.fer.zemris.java.hw05.db.QueryLexer;
import hr.fer.zemris.java.hw05.db.QueryParser;

/**
 * Demo program for parser and lexer
 * @author Daria Maktović
 *
 */
public class ParserAndLexerDemo {

	/**
	 * Method executes when program is run
	 * @param args no arguments
	 */
	public static void main(String[] args) {/*
		QueryLexer lexer = new QueryLexer("firstName = \"pero\" anD jmbag LIKE \"008784\"");
		System.out.println(lexer.nextToken().getValue());
		System.out.println(lexer.nextToken().getValue());
		System.out.println(lexer.nextToken().getValue());
		System.out.println(lexer.nextToken().getValue());
		System.out.println(lexer.nextToken().getValue());
		System.out.println(lexer.nextToken().getValue());
		System.out.println(lexer.nextToken());*/
		
		QueryParser qp1 = new QueryParser(" jmbag =\"0123456789\" ");
		System.out.println("isDirectQuery(): " + qp1.isDirectQuery()); // true
		System.out.println("jmbag was: " + qp1.getQueriedJMBAG()); // 0123456789
		System.out.println("size: " + qp1.getQuery().size()); // 1
		QueryParser qp2 = new QueryParser("jmbag=\"0123456789\" and lastName>\"J\"");
		System.out.println("isDirectQuery(): " + qp2.isDirectQuery()); // false
		//System.out.println(qp2.getQueriedJMBAG()); // would throw!
		System.out.println("size: " + qp2.getQuery().size()); // 2
		//QueryParser qp3 = new QueryParser(" jmbagLIKE\"0123456789\" ");
	}
}

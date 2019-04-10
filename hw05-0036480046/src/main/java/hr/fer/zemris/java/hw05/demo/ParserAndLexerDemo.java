package hr.fer.zemris.java.hw05.demo;

import hr.fer.zemris.java.hw05.db.QueryLexer;

public class ParserAndLexerDemo {

	public static void main(String[] args) {
		QueryLexer lexer = new QueryLexer("firstName = \"pero\" anD jmbag LIKE \"008784\"");
		System.out.println(lexer.nextToken().getValue());
		System.out.println(lexer.nextToken().getValue());
		System.out.println(lexer.nextToken().getValue());
		System.out.println(lexer.nextToken().getValue());
		System.out.println(lexer.nextToken().getValue());
		System.out.println(lexer.nextToken().getValue());
		System.out.println(lexer.nextToken());
	}
}

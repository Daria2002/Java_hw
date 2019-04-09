package hr.fer.zemris.java.hw05.demo;

import hr.fer.zemris.java.hw05.db.ComparisonOperators;
import hr.fer.zemris.java.hw05.db.IComparisonOperator;

public class ComparisonOperatorDemo {

	public static void main(String[] args) {
		IComparisonOperator x = ComparisonOperators.LESS;
		System.out.println(x.satisfied("Ana", "Jasna")); // true
		
		IComparisonOperator oper = ComparisonOperators.LIKE;
		System.out.println(oper.satisfied("Zagreb", "Aba*")); // false
		System.out.println(oper.satisfied("AAA", "AA*AA")); // false
		System.out.println(oper.satisfied("AAAA", "AA*AA")); // true	
	}
}

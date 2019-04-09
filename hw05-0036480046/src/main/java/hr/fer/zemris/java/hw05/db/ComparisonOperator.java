package hr.fer.zemris.java.hw05.db;

public class ComparisonOperator {
	
	private static class LikeComparison implements IComparisonOperator {

		@Override
		public boolean satisfied(String value1, String value2) {
			checkSymbol(value1);
			checkSymbol(value2);
			
			
			
			return false;
		}

		/**
		 * This method checks if * occurred more than once, and if it occurred 
		 * more than once, it throws exception
		 * 
		 * @param value string to check
		 * @return 0 if * is first char in string, 1 if * is somewhere in the middle
		 * and 2 if * is last char in string
		 */
		private int checkSymbol(String value) {
			int symbolPosition = 5;
			
			for(int i = 0; i < value.length(); i++) {
				
				if(value.charAt(i) == '*') {
					
					if(symbolPosition != 5) {
						throw new IllegalArgumentException("More occurrances of * in string.");
					}
					
					if(i == 0) {
						// * is first char
						symbolPosition = 0;
						
					} else if (i == value.length()-1) {
						// * is last char
						symbolPosition = 1;
						
					} else {
						// * is in the middle of string
						symbolPosition = 2;
					}
				}
			}
			return symbolPosition;
		}
	}
	
	public static final IComparisonOperator LESS = (value1, value2) -> {
		return value1.compareTo(value2) < 0;
	};
	
	public static final IComparisonOperator LESS_OR_EQUALS = (value1, value2) -> {
		return value1.compareTo(value2) <= 0;
	};
	
	public static final IComparisonOperator GREATER = (value1, value2) -> {
		return value1.compareTo(value2) > 0;
	};
	
	public static final IComparisonOperator GREATER_OR_EQUALS = (value1, value2) -> {
		return value1.compareTo(value2) >= 0;
	};
	
	public static final IComparisonOperator EQUALS = (value1, value2) -> {
		return value1.compareTo(value2) == 0;
	};
	
	public static final IComparisonOperator NOT_EQUALS = (value1, value2) -> {
		return value1.compareTo(value2) != 0;
	};
	
	public static final IComparisonOperator LIKE = new LikeComparison();
	
	public static void main(String[] args) {
		IComparisonOperator x = ComparisonOperator.LESS;
		System.out.println(x.satisfied("Ana", "Jasna"));
	}
	
}

package hr.fer.zemris.java.hw05.db;

public class ComparisonOperators {
	
	private static class LikeComparison implements IComparisonOperator {

		@Override
		public boolean satisfied(String value1, String value2) {
			checkSymbol(value1);
			// second string contains *
			int position = checkSymbol(value2);
			
			if(position == 0) {
				return checkEnding(value1, value2);
				
			} else if(position == 1) {
				return checkBeginning(value1, value2);
				
			} else if(position == 2) {
				return checkBeginningAndEnding(value1, value2);
			}
			
			return value1.compareTo(value2) == 0;
		}

		/**
		 * If * is in the middle checking beginning and ending
		 * @param value1 string to be checked
		 * @param value2 pattern to be checked
		 * @return result of value like value2 operation
		 */
		private boolean checkBeginningAndEnding(String value1, String value2) {
			String[] array = value2.split("\\*");
			
			boolean beginning = checkBeginning(value1, array[0] + '*');
			boolean end = checkEnding(value1, '*' + array[1]);
			
			return beginning && end && value1.length() >= value2.length()-1;
		}

		/**
		 * If * is last element of value2 pattern check that strings have same
		 * beginning
		 * @param value1 string to be checked
		 * @param value2 pattern to be checked
		 * @return result of value like value2 operation
		 */
		private boolean checkBeginning(String value1, String value2) {
			// take part of string before *
			String value = value2.substring(0, value2.length()-1);
			
			for(int i = 0; i < value.length(); i++) {
				if(value1.charAt(i) != value.charAt(i)) {
					return false;
				}
			}
			return true;
		}

		/**
		 * If * is first char in value2, this method checks that value1 and value2
		 * have same end
		 * @param value1 string to be checked
		 * @param value2 pattern to be checked
		 * @return result of value like value2 operation
		 */
		private boolean checkEnding(String value1, String value2) {
			// take part of string after *
			String value = value2.substring(1, value2.length());
			
			// check if value1 ends with string
			for(int i = value.length()-1; i >= 0; i--) {
				if(value.charAt(i) != value1.charAt(value1.length()+i-value.length()+1-1)) {
					return false;
				}
			}
			return true;
		}

		/**
		 * This method checks if * occurred more than once, and if it occurred 
		 * more than once, it throws exception
		 * 
		 * @param value string to check
		 * @return 0 if * is first char in string, 1 if * is somewhere in the middle,
		 * 2 if * is last char in string and 3 if * didin't occurred
		 */
		private int checkSymbol(String value) {
			int symbolPosition = 3;
			
			for(int i = 0; i < value.length(); i++) {
				
				if(value.charAt(i) == '*') {
					
					if(symbolPosition != 3) {
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
	
}

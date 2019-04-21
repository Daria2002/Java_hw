package hr.fer.zemris.java.custom.scripting.exec;

public class ValueWrapper {
	
	private Object value;

	public ValueWrapper(Object initialValue) {
		this.value = initialValue;
	}
	
	private Object getValue(Object testValue) {
		if(testValue.getClass().equals(String.class)) {
			
			if(isDouble(testValue)) {
				return Double.parseDouble((String) testValue);
			}
			
			try {
				Integer number = Integer.parseInt((String) testValue);
				return number;
			} catch (Exception e) {
				throw new RuntimeException("Given value is not valid argument "
						+ "for arithmetic operations.");
			}
			
		} else if(testValue.getClass().equals(Integer.class) || 
				(testValue.getClass().equals(Double.class))) {
			return testValue;
		}

		throw new RuntimeException("Given value is not valid argument "
				+ "for arithmetic operations.");
	}
	
	private int countOccurencesOf(String testString, String element) {
		return testString.length() - testString.replace(".", "").length();
	}
	
	private boolean isDouble(Object testValue) {
		String stringValue = (String) testValue;
		
		if(stringValue.contains("E") && countOccurencesOf(stringValue, "E") == 1 
				|| stringValue.contains(".") && countOccurencesOf(stringValue, ".") == 1) {
			return true;
		}
			
		return false;
	}

	private Object doOperation(int operation, Object incValue1, Object value1) {
		if(incValue1.getClass().equals(Integer.class) && value1.getClass().equals(Integer.class)) {
			int result = (int) getResult(operation, incValue1, value1);
			return Integer.valueOf(result);
		}
		
		double result = (double) getResult(operation, incValue1, value1);
		return Double.valueOf(result);
	}
	
	/**
	 * 
	 * @param operation 1 for add, 2 for subtract, 3 for multiply, 4 for divide 
	 * @param incValue1
	 * @param value1
	 */
	private Object getResult(int operation, Object incValue1, Object value1) {
		switch (operation) {
		case 1:
			return addOperation(incValue1, value1);
/*
		case 2:
			return subtractOperation(incValue1, value1);
			
		case 3:
			return multiplyOperation(incValue1, value1);
			*/
		default:
			//return divideOperation(incValue1, value1);
			return null;
		}
		
	}
	
	private Object addOperation(Object incValue1, Object value1) {
		if(incValue1.getClass().equals(Integer.class) && value1.getClass().equals(Integer.class)) {
			return (int)incValue1 + (int)value1;
			
		} else if(incValue1.getClass().equals(Double.class) && value1.getClass().equals(Integer.class)) {
			return (double)incValue1 + (int)value1;
			
		} else if(incValue1.getClass().equals(Double.class) && value1.getClass().equals(Double.class)) {
			return (double)incValue1 + (double)value1;
			
		} else if(incValue1.getClass().equals(Integer.class) && value1.getClass().equals(Double.class)) {
			return (int)incValue1 + (double)value1;
			
		}
		return null;
	}

	public void add(Object incValue) {
		Object incValue1 = getValue(incValue);
		Object value1 = getValue(value);
		
		this.value = doOperation(1, incValue1, value1);
	}
	
	public void subtract(Object decValue) {
		
	}
	
	public void multiply(Object mulValue) {
		
	}
	
	public void divide(Object divValue) {
		
	}
	
	public int numCompare(Object withValue) {
		return 1;
	}
	
	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}
}

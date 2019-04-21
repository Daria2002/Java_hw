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
				throw new RuntimeException("Given value is not valid argument");
			}
			
		} else if(testValue.getClass().equals(Integer.class) || 
				(testValue.getClass().equals(Double.class))) {
			return testValue;
		}

		throw new RuntimeException("Given value is not valid argument");
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

	/**
	 * 
	 * @param operation 1 for add, 2 for subtract, 3 for multiply, 4 for divide 
	 * @param incValue1
	 * @param value1
	 */
	private Object getResult(int operation, Object value1, Object givenValue) {
		switch (operation) {
		case 1:
			return addOperation(value1, givenValue);

		case 2:
			return subtractOperation(value1, givenValue);
			
		case 3:
			return multiplyOperation(value1, givenValue);
			
		default:
			return divideOperation(value1, givenValue);
		}
		
	}
	
	private Object divideOperation(Object value1, Object divValue) {
		if(divValue.getClass().equals(Integer.class) && value1.getClass().equals(Integer.class)) {
			return (int)value1 / (int)divValue;
			
		} else if(divValue.getClass().equals(Double.class) && value1.getClass().equals(Integer.class)) {
			return (int)value1 / (double)divValue;
			
		} else if(divValue.getClass().equals(Double.class) && value1.getClass().equals(Double.class)) {
			return (double)value1 / (double)divValue;
			
		}
		return (double)value1 / (int)divValue;
	}

	private Object multiplyOperation(Object value1, Object mulValue) {
		if(mulValue.getClass().equals(Integer.class) && value1.getClass().equals(Integer.class)) {
			return (int)mulValue * (int)value1;
			
		} else if(mulValue.getClass().equals(Double.class) && value1.getClass().equals(Integer.class)) {
			return (double)mulValue * (int)value1;
			
		} else if(mulValue.getClass().equals(Double.class) && value1.getClass().equals(Double.class)) {
			return (double)mulValue * (double)value1;
			
		}
		return (int)mulValue * (double)value1;
	}

	private Object addOperation(Object value1, Object incValue1) {
		if(incValue1.getClass().equals(Integer.class) && value1.getClass().equals(Integer.class)) {
			return (int)incValue1 + (int)value1;
			
		} else if(incValue1.getClass().equals(Double.class) && value1.getClass().equals(Integer.class)) {
			return (double)incValue1 + (int)value1;
			
		} else if(incValue1.getClass().equals(Double.class) && value1.getClass().equals(Double.class)) {
			return (double)incValue1 + (double)value1;
			
		}
		return (int)incValue1 + (double)value1;
	}
	
	private Object subtractOperation(Object value1, Object decValue1) {
		if(decValue1.getClass().equals(Integer.class) && value1.getClass().equals(Integer.class)) {
			return (int)value1 - (int)decValue1;
			
		} else if(decValue1.getClass().equals(Double.class) && value1.getClass().equals(Integer.class)) {
			return (int)value1 - (double)decValue1;
			
		} else if(decValue1.getClass().equals(Double.class) && value1.getClass().equals(Double.class)) {
			return (double)value1 - (double)decValue1;
			
		} 
		return (double)value1 - (int)decValue1;
	}

	private Object CheckValue(Object checkValue) {
		if(checkValue == null) {
			return Integer.valueOf(0);
		}
		return checkValue;
	}
	
	public void add(Object incValue) {
		incValue = CheckValue(incValue);
		value = CheckValue(value);
		
		this.value = getResult(1, getValue(value), getValue(incValue));
	}
	
	public void subtract(Object decValue) {
		decValue = CheckValue(decValue);
		value = CheckValue(value);
		
		this.value = getResult(2, getValue(value), getValue(decValue));
	}
	
	public void multiply(Object mulValue) {
		mulValue = CheckValue(mulValue);
		value = CheckValue(value);
		
		this.value = getResult(3, getValue(value), getValue(mulValue));
	}
	
	public void divide(Object divValue) {
		divValue = CheckValue(divValue);
		value = CheckValue(value);
		
		this.value = getResult(4, getValue(value), getValue(divValue));
	}
	
	public int numCompare(Object withValue) {
		if(this.value == null && withValue == null) {
			return 0;
		}
		withValue = CheckValue(withValue);
		
		Object result = getResult(2, getValue(value), getValue(withValue));
		
		if(result.getClass().equals(Double.class)) {
			if((double)result > 0) {
				return 1;
			} else if((double)result < 0) {
				return -1;
			}
			
		} else if(result.getClass().equals(Integer.class)) {
			if((int)result > 0) {
				return 1;
			} else if((double)result < 0) {
				return -1;
			}
		} 
		return 0;
	}
	
	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}
}

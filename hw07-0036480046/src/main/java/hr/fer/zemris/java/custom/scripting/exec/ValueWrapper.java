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
	
	private boolean isDouble(Object incValue) {
		String stringValue = (String) incValue;
		
		if(stringValue.contains("E") && countOccurencesOf(stringValue, "E") == 1 
				|| stringValue.contains(".") && countOccurencesOf(stringValue, ".") == 1) {
			return true;
		}
			
		return false;
	}

	public void add(Object incValue) {
		Object incValue1 = getValue(incValue);
		Object value1 = getValue(value);
		
		if(incValue1.getClass().equals(Integer.class) && value1.getClass().equals(Integer.class)) {
			int result = (int)incValue1 + (int)value1;
			this.value = Integer.valueOf(result);
		}
		
		double result = (double)incValue1 + (double)value1;
		this.value = Double.valueOf(result);
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

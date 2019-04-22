package hr.fer.zemris.java.custom.scripting.exec;

import java.util.function.BiFunction;

public class ValueWrapper {
	
	private Object value;

	public ValueWrapper(Object initialValue) {
		this.value = initialValue;
	}
	
	private Object getValue(Object testValue) {
		testValue = CheckValue(testValue);
		
		if(testValue.getClass().equals(String.class)) {
			try {
				return Integer.parseInt((String) testValue);
			} catch (Exception e) {
				try {
					if((((String) testValue).contains(".") || ((String) testValue).contains("E"))
							&& !((String) testValue).contains("e")) {
						return Double.parseDouble((String) testValue);
					}
				} catch (Exception e2) {
					throw new RuntimeException("Given value is not valid argument");
				}
			}
			
		} else if(testValue.getClass().equals(Integer.class) || 
				(testValue.getClass().equals(Double.class))) {
			return testValue;
		}

		throw new RuntimeException("Given value is not valid argument");
	}

	private double getRightType(Object value) {
		if(value.getClass().equals(Integer.class)) {
			return (int) value;
		}
		return (double) value;
	}
	
	private Object getResult(Object value1, Object givenValue, BiFunction<Double, Double, Double> operation) {
		double arg1 = getRightType(value1);
		double arg2 = getRightType(givenValue);
		
		if(value1.getClass().equals(Integer.class) && givenValue.getClass().equals(Integer.class)) {
			return operation.apply(arg1, arg2).intValue();
		}
		return operation.apply(arg1, arg2);
	}

	private Object CheckValue(Object checkValue) {
		if(checkValue == null) {
			return Integer.valueOf(0);
		}
		return checkValue;
	}
	
	public void add(Object incValue) {
		this.value = getResult(getValue(value), getValue(incValue), (value1, value2) -> {return (value1 + value2);});
	}
	
	public void subtract(Object decValue) {
		this.value = getResult(getValue(value), getValue(decValue), (value1, value2) -> {return (value1 - value2);});
	}
	
	public void multiply(Object mulValue) {
		this.value = getResult(getValue(value), getValue(mulValue), (value1, value2) -> {return (value1 * value2);});
	}
	
	public void divide(Object divValue) {
		this.value = getResult(getValue(value), getValue(divValue), (value1, value2) -> {
			if(Math.abs(value1) > 0.0000001 && Math.abs(value2) < 0.0000001) {
				throw new RuntimeException("Division by zero.");
			}
			return (value1 / value2);
		});
	}
	
	public int numCompare(Object withValue) {
		double temp = getRightType(getResult(getValue(value), getValue(withValue), (value1, value2) -> {return (value1 - value2);}));
		
		if(temp > 0) {
			return 1;
		} else if(temp < 0) {
			return -1;
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
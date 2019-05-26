package hr.fer.zemris.java.custom.scripting.exec;

import java.util.function.BiFunction;

/**
 * This class represents type of values that need to be added in ObjectMultistack
 * for each key. It implements methods for arithmetic operations (add, subtract,
 * divide, multiply) on value.
 * @author Daria Matković
 *
 */
public class ValueWrapper {
	/** value of ValueWrapper object **/
	private Object value;

	/**
	 * Constructor that initialize value
	 * @param initialValue initial value for variable value
	 */
	public ValueWrapper(Object initialValue) {
		this.value = initialValue;
	}
	
	/**
	 * If given testValue is string this method checks if String value contains 
	 * 'E' or '.', if it contains method returns testValue converted to double.
	 * If testValue is string and it doesn't contains 'E' or '.', method returns
	 * testValue converted to Integer. If given value is not string, method
	 * tries to convert value to string, if it is not possible, method converts
	 * testValue to double. Method throws RuntimeException if testValue is not Integer, 
	 * Double or String that contains Double or Integer
	 * @param testValue value to convert to Integer or Double
	 * @return Double or Integer value
	 */
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
	
	/**
	 * This method returns double value of given value if given value is Double,
	 * otherwise int value of given value
	 * @param value value to convert to int or double
	 * @return given value converted to int or double
	 */
	private double getRightType(Object value) {
		if(value.getClass().equals(Integer.class)) {
			return (int) value;
		}
		return (double) value;
	}
	
	/**
	 * This method calls 
	 * @param value1
	 * @param givenValue
	 * @param operation
	 * @return
	 */
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
	
	/**
	 * This method adds incValue to variable value, and saves result to variable value
	 * @param incValue value to add to variable value
	 */
	public void add(Object incValue) {
		this.value = getResult(getValue(value), getValue(incValue), (value1, value2) -> {return (value1 + value2);});
	}
	
	/**
	 * This method subtracts decValue from variable value, and saves result to variable value
	 * @param decValue value to subtract from variable value
	 */
	public void subtract(Object decValue) {
		this.value = getResult(getValue(value), getValue(decValue), (value1, value2) -> {return (value1 - value2);});
	}
	
	/**
	 * This method multiply mulValue with variable value and saves result to variable value
	 * @param mulValue value to multiply with variable value
	 */
	public void multiply(Object mulValue) {
		this.value = getResult(getValue(value), getValue(mulValue), (value1, value2) -> {return (value1 * value2);});
	}
	
	/**
	 * This method divide variable value by divValue
	 * @param divValue value to divide by variable value with
	 */
	public void divide(Object divValue) {
		this.value = getResult(getValue(value), getValue(divValue), (value1, value2) -> {
			if(Math.abs(value1) > 0.0000001 && Math.abs(value2) < 0.0000001) {
				throw new RuntimeException("Division by zero.");
			}
			return (value1 / value2);
		});
	}
	
	/**
	 * This method compare variable value with withValue
	 * @param withValue value to compare with variable value
	 * @return positive Integer, if variable value is higher than withValue, integer 0 if
	 * variable value is same as withValue, otherwise negative integer value
	 */
	public int numCompare(Object withValue) {
		double temp = getRightType(getResult(getValue(value), getValue(withValue), (value1, value2) -> {return (value1 - value2);}));
		
		if(temp > 0) {
			return 1;
		} else if(temp < 0) {
			return -1;
		}
		return 0;
	}
	
	/**
	 * Gets variable value
	 * @return returns variable value
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Sets variable value
	 * @param value new value of variable value
	 */
	public void setValue(Object value) {
		this.value = value;
	}
}
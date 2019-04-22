package hr.fer.zemris.java.custom.scripting.exec;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class ValueWrapperTest {

	@Test
	public void valueNull() {
		ValueWrapper valueWrapper = new ValueWrapper(null);
		valueWrapper.add(5);
		assertEquals(5, valueWrapper.getValue());
		valueWrapper.subtract(null);
		assertEquals(5, valueWrapper.getValue());
		assertThrows(RuntimeException.class, () -> {valueWrapper.divide(null);});
	}
	
	@Test
	public void valueNull2() {
		ValueWrapper valueWrapper = new ValueWrapper(5);
		valueWrapper.add(null);
		
		assertEquals(5, valueWrapper.getValue());
	}
	
	@Test
	public void divisionByZero() {
		ValueWrapper valueWrapper = new ValueWrapper(5);
		
		assertThrows(RuntimeException.class, () -> {valueWrapper.divide(null);});
		assertThrows(RuntimeException.class, () -> {valueWrapper.divide(0.0E-5);});
		assertThrows(RuntimeException.class, () -> {valueWrapper.divide("0.0E-5");});
		assertThrows(RuntimeException.class, () -> {valueWrapper.divide(0);});
	}
	
	@Test
	public void addTest() {
		ValueWrapper valueWrapperInteger = new ValueWrapper(5);
		valueWrapperInteger.add(10);
		assertEquals(15, valueWrapperInteger.getValue());
		valueWrapperInteger.add("5.5");
		assertEquals(20.5, valueWrapperInteger.getValue());

		ValueWrapper valueWrapperIntegerString = new ValueWrapper("5");
		valueWrapperIntegerString.add("5.5");
		assertEquals(10.5, valueWrapperIntegerString.getValue());
		valueWrapperIntegerString.add(6);
		assertEquals(16.5, valueWrapperIntegerString.getValue());
		
		ValueWrapper valueWrapperDouble = new ValueWrapper(5.5);
		valueWrapperDouble.add("5.5");
		assertEquals(11.0, valueWrapperDouble.getValue());
		valueWrapperDouble.add("1");
		assertEquals(12.0, valueWrapperDouble.getValue());
		
		ValueWrapper valueWrapperDoubleString = new ValueWrapper("5.5");
		valueWrapperDoubleString.add(3);
		assertEquals(8.5, valueWrapperDoubleString.getValue());
		valueWrapperDoubleString.add(4.5);
		assertEquals(13.0, valueWrapperDoubleString.getValue());
	}
	
	@Test
	public void subtractTest() {
		ValueWrapper valueWrapper = new ValueWrapper(10);
		valueWrapper.subtract("2.5");
		assertEquals(7.5, valueWrapper.getValue());
		valueWrapper.subtract("2");
		assertEquals(5.5, valueWrapper.getValue());
		valueWrapper.subtract(1);
		assertEquals(4.5, valueWrapper.getValue());
	}
	
	@Test
	public void divideTest() {
		ValueWrapper valueWrapper = new ValueWrapper(10);
		valueWrapper.divide("2.5");
		assertEquals(4.0, valueWrapper.getValue());
		valueWrapper.divide("2");
		assertEquals(2.0, valueWrapper.getValue());
		valueWrapper.divide(2.0);
		assertEquals(1.0, valueWrapper.getValue());
	}
	
	@Test
	public void multiplyTest() {
		ValueWrapper valueWrapper = new ValueWrapper(10);
		valueWrapper.multiply("2");
		assertEquals(20, valueWrapper.getValue());
		valueWrapper.multiply("2.5");
		assertEquals(50.0, valueWrapper.getValue());
		valueWrapper.multiply(2.0);
		assertEquals(100.0, valueWrapper.getValue());
	}
	
	@Test
	public void numCompareExceptionTest() {
		ValueWrapper valueWrapper = new ValueWrapper(10);
		assertThrows(RuntimeException.class, () -> {valueWrapper.numCompare("test");});
	}
	
	@Test
	public void numCompareTest() {
		ValueWrapper valueWrapper = new ValueWrapper(10.015);
		assertEquals(0, valueWrapper.numCompare(10.015));
		assertEquals(1, valueWrapper.numCompare(10.014));
		assertEquals(-1, valueWrapper.numCompare(10.016));
		assertEquals(1, valueWrapper.numCompare(10));
		assertEquals(-1, valueWrapper.numCompare(11));
	}
}
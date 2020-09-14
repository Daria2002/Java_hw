package hr.fer.zemris.java.hw01;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class FactorialTest {
	
	@Test
	public void testNumberOutOfRangeLess() {
		assertThrows(IllegalArgumentException.class, () -> {Factorial.calculateFactorial(1);});
	}
	
	@Test
	public void testNumberOutOfRangeMore() {
		assertThrows(IllegalArgumentException.class, () -> {Factorial.calculateFactorial(21);});
	}
	
	@Test
	public void testFactorialForNumber5() {
		int n = Factorial.calculateFactorial(5);
		assertEquals(120, n);
	}
	
}

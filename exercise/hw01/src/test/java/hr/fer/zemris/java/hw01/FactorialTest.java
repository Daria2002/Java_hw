package hr.fer.zemris.java.hw01;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class FactorialTest {
	
	@Test
	public void testNegativeNumber() {
		assertThrows(IllegalArgumentException.class, 
				() -> {Factorial.factorial((long) -1);});
	}
	
	@Test
	public void test0() {
		assertEquals(1, Factorial.factorial((long)0));
	}
	
	@Test
	public void test2() {
		assertEquals(2, Factorial.factorial((long)2));
	}
	
	public void test3() {
		assertEquals(6, Factorial.factorial((long)3));
	}
	
	public void test20() {
		assertEquals(2.432902e+18, Factorial.factorial((long)20), 1e17);
	}
}

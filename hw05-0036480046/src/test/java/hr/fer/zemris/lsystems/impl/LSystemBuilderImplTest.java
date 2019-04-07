package hr.fer.zemris.lsystems.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class LSystemBuilderImplTest {

	@Test
	void test() {
		LSystemBuilderImpl x = new LSystemBuilderImpl();
		assertEquals("F", x.build().generate(0));
		assertEquals("F+F--F+F", x.build().generate(1));
		assertEquals("F+F--F+F+F+F--F+F--F+F--F+F+F+F--F+F", x.build().generate(2));
	}
}

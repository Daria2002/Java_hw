package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.hw02.ComplexNumber;

public class ComplexNumberTest {

	private double tolerance = 0.00001;
	
	@Test
	void testConstructor() {
		ComplexNumber complexNumber = new ComplexNumber(1.3, 2.7);
		
		assertEquals(1.3, complexNumber.getReal(), tolerance);
		assertEquals(2.7, complexNumber.getImaginary(), tolerance);
	}
	
	@Test
	void testFromReal() {
		assertEquals(-10.79, ComplexNumber.fromReal(-10.79).getReal(), tolerance);
		assertEquals(0, ComplexNumber.fromReal(-10.79).getImaginary(), tolerance);
	}
	
	@Test
	void testFromImaginary() {
		assertEquals(0, ComplexNumber.fromImaginary(-75.45).getReal(), tolerance);
		assertEquals(-75.45, ComplexNumber.fromImaginary(-75.45).getImaginary(), tolerance);
	}

	@Test
	void testFromMagnitudeAndAngle() {
		assertEquals(new ComplexNumber(-1, 1).getReal(),
				ComplexNumber.fromMagnitudeAndAngle(Math.sqrt(2),
						3*Math.PI/4).getReal(), tolerance);
	}
	
	@Test
	void testParse() {
		assertEquals("0.0+1.0i", ComplexNumber.parse("i").toString());
		assertEquals("1.0+0.0i", ComplexNumber.parse("1").toString());
		assertEquals("0.0-1.0i", ComplexNumber.parse("-i").toString());
		assertEquals("-1.0+0.0i", ComplexNumber.parse("-1").toString());
		assertEquals("2.758+0.0i", ComplexNumber.parse("2.758").toString());
		assertEquals("14.78+54.3i", ComplexNumber.parse("14.78+54.3i").toString());
		assertEquals("-7.2-1.3i", ComplexNumber.parse("-7.2-1.3i").toString());
		assertEquals("-9.4+3.2i", ComplexNumber.parse("-9.4+3.2i").toString());
		assertEquals("351.0+0.0i", ComplexNumber.parse("351").toString());
		assertEquals("0.0+0.0i", ComplexNumber.parse("0").toString());
		assertEquals("0.0+1.0i", ComplexNumber.parse("i+0").toString());
		
		assertThrows(IllegalArgumentException.class, 
				() -> {ComplexNumber.parse("i+i").toString();});
		assertThrows(IllegalArgumentException.class, 
				() -> {ComplexNumber.parse("hello").toString();});
	}
	
	@Test
	void testgetReal() {
		assertEquals(new ComplexNumber(-1.78, 1.98).getReal(), -1.78, tolerance);
	}
	
	@Test
	void testgetImaginary() {
		assertEquals(new ComplexNumber(-1.78, 1.98).getImaginary(), 1.98, tolerance);
	}
	
	@Test
	void testGetMagnitude() {
		assertEquals(new ComplexNumber(-1, 0).getMagnitude(), 1, tolerance);
	}
	
	@Test
	void testGetAngle() {
		assertEquals(new ComplexNumber(-1, 1).getAngle(), 3*Math.PI/4, tolerance);
	}
	
	@Test
	void testAdd() {
		assertEquals(new ComplexNumber(-1, 8).
				add(new ComplexNumber(5, 8)).toString(), "4.0+16.0i");
	}
	
	@Test
	void testSub() {
		assertEquals(new ComplexNumber(-1, 8).
				sub(new ComplexNumber(5, 8)).toString(), "-6.0+0.0i");
	}
	
	@Test
	void testMul() {
		assertEquals(new ComplexNumber(-1, 8).
				mul(new ComplexNumber(5, 8)).toString(), "-69.0+32.0i");
	}

	@Test
	void testDiv() {
		ComplexNumber complexNumber1 = new ComplexNumber(-1, 8);
		ComplexNumber complexNumber2 = new ComplexNumber(5, 8);
		
		assertEquals(complexNumber1.div(complexNumber2).getImaginary(),
				0.53932, tolerance);
		assertEquals(complexNumber1.div(complexNumber2).getReal(),
				0.66292, tolerance);
	}
	
	@Test
	void testPower() {
		ComplexNumber complexNumber1 = new ComplexNumber(-7, 8);
		
		assertEquals(complexNumber1.power(3).getImaginary(), 664.0, tolerance);
		assertEquals(complexNumber1.power(3).getReal(), 1000.99999, tolerance);
	}
	
	@Test
	void testRoot() {
		ComplexNumber complexNumber1 = new ComplexNumber(-7, 8);
		
		assertEquals(complexNumber1.root(3)[0].getImaginary(), 1.51988, tolerance);
		assertEquals(complexNumber1.root(3)[0].getReal(), 1.58887, tolerance);
		assertEquals(complexNumber1.root(3)[1].getImaginary(), 0.61606, tolerance);
		assertEquals(complexNumber1.root(3)[1].getReal(), -2.11069, tolerance);
		assertEquals(complexNumber1.root(3)[2].getImaginary(), -2.13595, tolerance);
		assertEquals(complexNumber1.root(3)[2].getReal(), 0.52182, tolerance);
	}
	
	@Test
	void testToString() {
		assertEquals(new ComplexNumber(-7, 8).toString(), "-7.0+8.0i");
	}
}

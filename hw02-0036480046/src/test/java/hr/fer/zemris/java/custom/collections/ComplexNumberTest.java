package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.hw02.ComplexNumber;

class ComplexNumberTest {

	private double tolerance = 0.00001;
	

	@Test
	public void mulDivTest() {
		ComplexNumber c1 = new ComplexNumber(1,1);
		ComplexNumber c2 = new ComplexNumber(-2.71,-3.15);
		
		ComplexNumber c3 = c1.div(c2);
		c3 = c3.mul(c2);
		
		assertEquals(c3.getImaginary(), 1, 0.001);
		assertEquals(c3.getReal(), 1, 0.001);
	}

	@Test
	public void complexRootTest() {
		ComplexNumber c1 = ComplexNumber.fromMagnitudeAndAngle(2, 2);
		ComplexNumber[] c2 = c1.root(2);
		
		assertEquals(c2[0].getMagnitude(), Math.sqrt(2), 0.001);
		assertEquals(c2[0].getAngle(), 1, 0.001);
		
		assertEquals(c2[1].getMagnitude(), Math.sqrt(2), 0.001);
		assertEquals(c2[1].getAngle(), 1+2*Math.PI/2, 0.001);
	}
	
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
		ComplexNumber complexNumber = new ComplexNumber(-1, 1);
		assertEquals(complexNumber.getReal(),
				ComplexNumber.fromMagnitudeAndAngle(Math.sqrt(2),
						3*Math.PI/4).getReal(), tolerance);
	}
	
	@Test
	public void fromRealTest() {
		ComplexNumber c1 = ComplexNumber.fromReal(1.2);
		
		assertEquals(c1.getImaginary(), 0, 0.001);
		assertEquals(c1.getReal(), 1.2, 0.001);
	}
	
	@Test
	public void fromImaginaryTest() {
		ComplexNumber c1 = ComplexNumber.fromImaginary(1.2);
		
		assertEquals(c1.getImaginary(), 1.2, 0.001);
		assertEquals(c1.getReal(), 0, 0.001);
	}
	
	@Test
	public void fromAmpAndAngleTest() {
		ComplexNumber c1 = ComplexNumber.fromMagnitudeAndAngle(1, Math.PI);
	
		assertEquals(c1.getMagnitude(), 1, 0.001);
		assertEquals(c1.getAngle(), Math.PI, 0.001);
	}
	
	@Test
	public void parseValidString() {
		ComplexNumber c1 = ComplexNumber.parse("3.51");
		ComplexNumber c2 = ComplexNumber.parse("-3.511");
		ComplexNumber c3 = ComplexNumber.parse("-2.51i");
		ComplexNumber c4 = ComplexNumber.parse("i");
		ComplexNumber c5 = ComplexNumber.parse("1");
		ComplexNumber c6 = ComplexNumber.parse("-2.71-3.15i");
		ComplexNumber c7 = ComplexNumber.parse("1-i");
		ComplexNumber c8 = ComplexNumber.parse("1+i");
		ComplexNumber c9 = ComplexNumber.parse("-i");
		ComplexNumber c10 = ComplexNumber.parse("-1");
		ComplexNumber c11 = ComplexNumber.parse("0");
		
		assertEquals(c1.getReal(), 3.51, 0.001);
		assertEquals(c2.getReal(), -3.511, 0.001);
		assertEquals(c3.getImaginary(), -2.51, 0.001);
		assertEquals(c4.getImaginary(), 1, 0.001);
		assertEquals(c5.getReal(), 1, 0.001);
		assertEquals(c6.getReal(), -2.71, 0.001);
		assertEquals(c6.getImaginary(), -3.15, 0.001);
		assertEquals(c7.getReal(), 1, 0.001);
		assertEquals(c7.getImaginary(), -1, 0.001);
		assertEquals(c8.getReal(), 1, 0.001);
		assertEquals(c8.getImaginary(), 1, 0.001);
		assertEquals(c9.getReal(), 0, 0.001);
		assertEquals(c9.getImaginary(), -1, 0.001);
		assertEquals(c10.getReal(), -1, 0.001);
		assertEquals(c10.getImaginary(), 0, 0.001);
		assertEquals(c11.getReal(), 0, 0.001);
		assertEquals(c11.getImaginary(), 0, 0.001);
	}
	
	@Test
	public void parseStringTooMuchElements() {
		assertThrows(IllegalArgumentException.class, () -> {ComplexNumber c1 = ComplexNumber.parse("3.51+5.12i-3");});		
	}
	
	@Test
	public void parseStringTwoReal() {
		assertThrows(IllegalArgumentException.class, () -> {ComplexNumber c1 = ComplexNumber.parse("3.51-3");});		
	}
	
	@Test
	public void parseStringTwoImag() {
		assertThrows(IllegalArgumentException.class, () -> {ComplexNumber c1 = ComplexNumber.parse("3.51i-3i");});	
	}
	
	@Test
	public void addTest() {
		ComplexNumber c1 = ComplexNumber.fromReal(3.51);
		ComplexNumber c2 = ComplexNumber.fromReal(-3.51);
		
		ComplexNumber c3 = c1.add(c2);
		assertEquals(c3.getImaginary(), 0, 0.001);
		assertEquals(c3.getReal(), 0, 0.001);
	}
	
	@Test
	public void subTest() {
		ComplexNumber c1 = new ComplexNumber(1,1);
		ComplexNumber c2 = new ComplexNumber(1,-1);
		
		ComplexNumber c3 = c1.add(c2);
		assertEquals(c3.getImaginary(), 0, 0.001);
		assertEquals(c3.getReal(), 2, 0.001);
	}
	
	
	@Test
	public void divisionByZeroTest() {
		ComplexNumber c1 = new ComplexNumber(1,1);
		
		assertThrows(IllegalArgumentException.class, () -> {c1.div(ComplexNumber.fromImaginary(0));});	
	}
	
	@Test 
	public void complexPowerTest() {
		ComplexNumber c1 = ComplexNumber.fromMagnitudeAndAngle(3, Math.PI);
		ComplexNumber c2 = c1.power(2);
		
		assertEquals(c2.getMagnitude(), 9, 0.001);
		assertEquals(c2.getAngle(), 0, 0.001);	
	}
	
	@Test
	public void rootExceptionTest() {
		ComplexNumber c1 = ComplexNumber.fromMagnitudeAndAngle(3, Math.PI);
		
		assertThrows(IllegalArgumentException.class, () -> {ComplexNumber c2 = c1.power(-2);});
	}
	
	
	@Test
	public void complexRootExceptionTest() {
		ComplexNumber c1 = ComplexNumber.fromMagnitudeAndAngle(3, Math.PI);
		
		assertThrows(IllegalArgumentException.class, () -> {ComplexNumber[] c2 = c1.root(0);});
	}
	
	@Test
	public void addNullException() {
		ComplexNumber c1 = ComplexNumber.fromMagnitudeAndAngle(3, Math.PI);
			
		assertThrows(IllegalArgumentException.class, () -> {c1.add(null);});
	}
	
	@Test
	public void subNullException() {
		ComplexNumber c1 = ComplexNumber.fromMagnitudeAndAngle(3, Math.PI);
		assertThrows(IllegalArgumentException.class, () -> {c1.sub(null);});
		
	}
	
	@Test
	public void mulNullException() {
		ComplexNumber c1 = ComplexNumber.fromMagnitudeAndAngle(3, Math.PI);
		assertThrows(IllegalArgumentException.class, () -> {c1.mul(null);});
		
	}
	
	@Test
	public void divNullException() {
		ComplexNumber c1 = ComplexNumber.fromMagnitudeAndAngle(3, Math.PI);
		assertThrows(IllegalArgumentException.class, () -> {c1.div(null);});
;		
	}
	
	@Test
	public void toStringTest() {
		ComplexNumber c1 = ComplexNumber.parse("i");
		assertEquals(c1.toString(),"0.0+1.0i");
	}
	// TODO: poseban sučaj ako je i ili -i, 0, 1 itd. 
	// replace razmak s praznim mjestom
	@Test
	public void toStringTestZero() {
		ComplexNumber c1 = ComplexNumber.parse("0");
		assertEquals(c1.toString(), "0.0+0.0i");
	}
	
}

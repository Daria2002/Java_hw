package hr.fer.zemris.java.hw06.crypto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class UtilTest {

	@Test
	void testByteToHex() {
		byte[] result = Util.hextobyte("01aE22");
		
		assertEquals(1, result[0]);
		assertEquals(-82, result[1]);
		assertEquals(34, result[2]);
	}

	@Test
	void testHexToByte() {
		assertEquals("01ae22", Util.bytetohex(new byte[] {1, -82, 34}));
	}
	
	@Test
	void testOddHex() {
		assertThrows(IllegalArgumentException.class, () -> Util.hextobyte("01aE224"));
	}
	
	@Test
	void testInvalidHexValue() {
		assertThrows(IllegalArgumentException.class, () -> Util.hextobyte("01kE22"));
	}
	
	@Test
	void testZeroLengthString() {
		byte[] result = Util.hextobyte("01aE22");
		
		assertEquals(1, result[0]);
		assertEquals(-82, result[1]);
		assertEquals(34, result[2]);
		assertThrows(IllegalArgumentException.class, () -> Util.hextobyte("01kE22"));
	}
}
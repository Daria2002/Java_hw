package hr.fer.zemris.java.hw01;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class RectangleTest {

	@Test
	public void testCircumference() {
		double height = 8;
		double width = 2;
		double circumference = Rectangle.calculateCircumference(height, width);
		assertEquals(20.0, circumference);
	}
	
	@Test
	public void testSurfaceArea() {
		double height = 8;
		double width = 2;
		double surfaceArea = Rectangle.calculateSurfaceArea(height, width);
		assertEquals(16.0, surfaceArea);
	}
	
	@Test
	public void testCheckInputMethod() {
		Boolean check = Rectangle.checkInput("štefica");
		assertEquals(false, check);
	}
	
}

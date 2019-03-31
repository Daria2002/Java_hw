package hr.fer.zemris.math;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class Vector2DTest {

	@Test
	void testConstructor() {
		Vector2D vector = new Vector2D(2.5, 4.5);
		assertEquals(2.5, vector.getX());
		assertEquals(4.5, vector.getY());
	}

	@Test
	void testGetX() {
		Vector2D vector1 = new Vector2D(2.5, 4.5);
		Vector2D vector2 = new Vector2D(2500, 4.5);
		Vector2D vector3 = new Vector2D(0.99, 4.5);
		
		assertEquals(2.5, vector1.getX());
		assertEquals(2500, vector2.getX());
		assertEquals(0.99, vector3.getX());
	}
	
	@Test
	void testGetY() {
		Vector2D vector1 = new Vector2D(2.5, 4.5);
		Vector2D vector2 = new Vector2D(2500, 7800);
		Vector2D vector3 = new Vector2D(0.99, 0.007);
		
		assertEquals(4.5, vector1.getY());
		assertEquals(7800, vector2.getY());
		assertEquals(0.007, vector3.getY());
	}
	
	@Test
	void testTranslate() {
		Vector2D vector1 = new Vector2D(2.5, 4.5);
		Vector2D vector2 = new Vector2D(4.3, 7.1);
		vector1.translate(vector2);
		
		assertEquals(new Vector2D(6.8, 11.6), vector1);
	}
	
	@Test
	void testTranslated() {
		Vector2D vector1 = new Vector2D(2.5, 4.5);
		Vector2D vector2 = new Vector2D(4.3, 7.1);
		Vector2D vector3 = vector1.translated(vector2);
		
		assertEquals(new Vector2D(6.8, 11.6), vector3);
	}
	
	@Test
	void testRotate() {
		Vector2D vector = new Vector2D(1.05, 4);
		vector.rotate(Math.PI/2);
		
		assertEquals(new Vector2D(-4, 1.05), vector);
	}
	
	@Test
	void testRotated() {
		Vector2D vector1 = new Vector2D(2.5, 4.5);
		Vector2D vector2 = vector1.rotated(Math.PI/2);
		
		assertEquals(new Vector2D(-4.5, 2.5), vector2);
	}
	
	@Test
	void testScale() {
		Vector2D vector = new Vector2D(1.05, 4);
		vector.scale(0.5);
		
		assertEquals(new Vector2D(1.05*0.5, 4*0.5), vector);
	}
	
	@Test
	void testScaled() {
		Vector2D vector1 = new Vector2D(1.05, 4);
		Vector2D vector2 = vector1.scaled(0.5);
		
		assertEquals(new Vector2D(1.05*0.5, 4*0.5), vector2);
	}
	
	@Test
	void testCopy() {
		Vector2D vector1 = new Vector2D(1.05, 4);
		Vector2D vector2 = vector1.copy();
		
		assertEquals(new Vector2D(1.05, 4), vector2);
	}
}

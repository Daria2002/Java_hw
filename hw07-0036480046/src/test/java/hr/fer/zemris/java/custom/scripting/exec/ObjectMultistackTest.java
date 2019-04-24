package hr.fer.zemris.java.custom.scripting.exec;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ObjectMultistackTest {

	@Test
	public void pushTest() {
		ObjectMultistack objectMultiStack = new ObjectMultistack();
		
		objectMultiStack.push("tri", new ValueWrapper(3.5));
		objectMultiStack.push("tri", new ValueWrapper(3.7));
		objectMultiStack.push("tri", new ValueWrapper(3));
		objectMultiStack.push("dva", new ValueWrapper(2.1));
		
		assertEquals(3, objectMultiStack.peek("tri").getValue());
		assertEquals(3, objectMultiStack.pop("tri").getValue());
		assertEquals(2.1, objectMultiStack.pop("dva").getValue());
		assertEquals(true, objectMultiStack.isEmpty("dva"));
		assertEquals(true, objectMultiStack.isEmpty("jedan"));
		assertEquals(3.7, objectMultiStack.pop("tri").getValue());
		assertEquals(3.5, objectMultiStack.pop("tri").getValue());
		assertEquals(true, objectMultiStack.isEmpty("tri"));
	}
	
	@Test
	public void popTest() {
		ObjectMultistack objectMultiStack = new ObjectMultistack();
		
		objectMultiStack.push("tri", new ValueWrapper(3.5));
		objectMultiStack.push("tri", new ValueWrapper(3.7));
		objectMultiStack.push("tri", new ValueWrapper(3));
		objectMultiStack.push("dva", new ValueWrapper(2.1));
		
		assertEquals(3, objectMultiStack.pop("tri").getValue());
		assertEquals(2.1, objectMultiStack.pop("dva").getValue());
		assertEquals(true, objectMultiStack.isEmpty("dva"));
		assertEquals(3.7, objectMultiStack.pop("tri").getValue());
		assertEquals(3.5, objectMultiStack.pop("tri").getValue());
		assertEquals(true, objectMultiStack.isEmpty("tri"));
		
		objectMultiStack.push("tri", new ValueWrapper(3.2));
		assertEquals(3.2, objectMultiStack.pop("tri").getValue());
		assertEquals(true, objectMultiStack.isEmpty("tri"));
	}
	
	@Test
	public void peekTest() {
		ObjectMultistack objectMultiStack = new ObjectMultistack();
		
		objectMultiStack.push("tri", new ValueWrapper(3.7));
		objectMultiStack.push("tri", new ValueWrapper(3));
		objectMultiStack.push("dva", new ValueWrapper(2.1));
		
		assertEquals(3, objectMultiStack.peek("tri").getValue());
		assertEquals(2.1, objectMultiStack.peek("dva").getValue());
		assertEquals(false, objectMultiStack.isEmpty("dva"));
		assertEquals(3, objectMultiStack.peek("tri").getValue());
		assertEquals(3, objectMultiStack.peek("tri").getValue());
		assertEquals(false, objectMultiStack.isEmpty("tri"));
		
		objectMultiStack.push("tri", new ValueWrapper(3.2));
		assertEquals(3.2, objectMultiStack.peek("tri").getValue());
		assertEquals(false, objectMultiStack.isEmpty("tri"));
	}
	
	@Test
	public void isEmptyTest() {
		ObjectMultistack objectMultiStack = new ObjectMultistack();
		
		objectMultiStack.push("tri", new ValueWrapper(3.7));
		objectMultiStack.push("dva", new ValueWrapper(2.1));
		
		assertEquals(false, objectMultiStack.isEmpty("tri"));
		assertEquals(false, objectMultiStack.isEmpty("dva"));
		
		assertEquals(3.7, objectMultiStack.peek("tri").getValue());
		assertEquals(2.1, objectMultiStack.peek("dva").getValue());
		assertEquals(false, objectMultiStack.isEmpty("dva"));
		assertEquals(false, objectMultiStack.isEmpty("tri"));
		
		assertEquals(2.1, objectMultiStack.pop("dva").getValue());
		assertEquals(3.7, objectMultiStack.pop("tri").getValue());
		assertEquals(true, objectMultiStack.isEmpty("tri"));
		assertEquals(true, objectMultiStack.isEmpty("dva"));
		assertEquals(true, objectMultiStack.isEmpty("test"));
		assertEquals(true, objectMultiStack.isEmpty("null"));
	}
	
	@Test
	public void popOnEmptyStackTest() {
		ObjectMultistack objectMultiStack = new ObjectMultistack();
		assertThrows(RuntimeException.class, () -> {objectMultiStack.pop("test");});
	}
	
	@Test
	public void peekOnEmptyStackTest() {
		ObjectMultistack objectMultiStack = new ObjectMultistack();
		assertThrows(RuntimeException.class, () -> {objectMultiStack.peek("test");});
	}

	@Test
	public void pushNullTest() {
		ObjectMultistack objectMultiStack = new ObjectMultistack();
		assertThrows(RuntimeException.class, () -> {objectMultiStack.push(null, new ValueWrapper(null));});
	}
	
	@Test
	public void popNullTest() {
		ObjectMultistack objectMultiStack = new ObjectMultistack();
		assertThrows(RuntimeException.class, () -> {objectMultiStack.pop(null);});
	}
	
	@Test
	public void peekNullTest() {
		ObjectMultistack objectMultiStack = new ObjectMultistack();
		assertThrows(RuntimeException.class, () -> {objectMultiStack.peek(null);});
	}
}

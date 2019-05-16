package hr.fer.zemris.java.custom.scripting.lexer;

import static org.junit.jupiter.api.Assertions.*;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayNameGenerator.Standard;

public class LexerSmartTest {

	@Test
	void testNullInput() {
		assertThrows(NullPointerException.class, () -> new LexerSmart(null));
	}
	
	@Test
	void testSimpeCode() {
		String text = "This is sample text.{$ FOR i 1 10 1 $}This is {$= i $}"
				+ "-th time this message is generated.{$END$}";
		
		LexerSmart lexer = new LexerSmart(text);
		
		assertEquals(lexer.nextToken().getType(), TokenSmartType.TEXT);
		assertEquals(String.valueOf(lexer.getToken().getValue()), "This is sample text.");
		
		assertEquals(lexer.nextToken().getType(), TokenSmartType.TAG_OPEN);
		assertEquals(lexer.getToken().getValue(), "{$");
		
		assertEquals(lexer.nextToken().getType(), TokenSmartType.TAG_NAME);
		assertEquals(String.valueOf(lexer.getToken().getValue()), "FOR");
		
		assertEquals(lexer.nextToken().getType(), TokenSmartType.TAG_ELEMENT);
		assertEquals(lexer.getToken().getValue().toString().strip(), "i");
		
		assertEquals(lexer.nextToken().getType(), TokenSmartType.TAG_ELEMENT);
		assertEquals(lexer.getToken().getValue().toString().strip(), "1");
		
		assertEquals(lexer.nextToken().getType(), TokenSmartType.TAG_ELEMENT);
		assertEquals(lexer.getToken().getValue().toString().strip(), "10");
		
		assertEquals(lexer.nextToken().getType(), TokenSmartType.TAG_ELEMENT);
		assertEquals(lexer.getToken().getValue().toString().strip(), "1");
		
		assertEquals(lexer.nextToken().getType(), TokenSmartType.TAG_CLOSE);
		assertEquals(lexer.getToken().getValue(), "$}");
		
		assertEquals(lexer.nextToken().getType(), TokenSmartType.TEXT);
		assertEquals(String.valueOf(lexer.getToken().getValue()), "This is ");
		
		assertEquals(lexer.nextToken().getType(), TokenSmartType.TAG_OPEN);
		assertEquals(lexer.getToken().getValue(), "{$");
		
		assertEquals(lexer.nextToken().getType(), TokenSmartType.TAG_NAME);
		assertEquals(String.valueOf(lexer.getToken().getValue()), "=");
		
		assertEquals(lexer.nextToken().getType(), TokenSmartType.TAG_ELEMENT);
		assertEquals(lexer.getToken().getValue().toString().strip(), "i");
		
		assertEquals(lexer.nextToken().getType(), TokenSmartType.TAG_CLOSE);
		assertEquals(lexer.getToken().getValue(), "$}");
		
		assertEquals(lexer.nextToken().getType(), TokenSmartType.TEXT);
		assertEquals(String.valueOf(lexer.getToken().getValue()),
				"-th time this message is generated.");
		
		assertEquals(lexer.nextToken().getType(), TokenSmartType.TAG_OPEN);
		assertEquals(String.valueOf(lexer.getToken().getValue()), "{$");
		
		assertEquals(lexer.nextToken().getType(), TokenSmartType.TAG_NAME);
		assertEquals(lexer.getToken().getValue(), "END");
		
		assertEquals(lexer.nextToken().getType(), TokenSmartType.TAG_CLOSE);
		assertEquals(lexer.getToken().getValue(), "$}");
		
		assertEquals(lexer.nextToken().getType(), TokenSmartType.EOF);
		assertEquals(lexer.getToken().getValue(), null);
	}

	/* test za parser 
	@Test
	void testUnclosedLoop() {
		String text = "This is sample text.{$ FOR i 1 10 1 $}";
		
		LexerSmart lexer = new LexerSmart(text);
		lexer.nextToken();
		lexer.nextToken();
		lexer.nextToken();
		lexer.nextToken();
		lexer.nextToken();
		lexer.nextToken();
		
		assertThrows(LexerSmartException.class, () -> lexer.nextToken());
	}
	**/
	@Test
	void testCode() {
		String text = "This is text.{$ FOR \"i\" \"-101\" 10 1 $}"
				+ "This is {$= i @sin \"h_el\\\\lo\" $}"
				+ "-th time\\\\.\\{$END$}";
		
		LexerSmart lexer = new LexerSmart(text);
		
		assertEquals(lexer.nextToken().getType(), TokenSmartType.TEXT);
		assertEquals(lexer.getToken().getValue(), "This is text.");
		
		assertEquals(lexer.nextToken().getType(), TokenSmartType.TAG_OPEN);
		assertEquals(lexer.getToken().getValue(), "{$");
		
		assertEquals(lexer.nextToken().getType(), TokenSmartType.TAG_NAME);
		assertEquals(lexer.getToken().getValue(), "FOR");
		
		assertEquals(lexer.nextToken().getType(), TokenSmartType.TAG_ELEMENT);
		assertEquals(lexer.getToken().getValue().toString().strip(), "\"i\"");
		
		assertEquals(lexer.nextToken().getType(), TokenSmartType.TAG_ELEMENT);
		assertEquals(lexer.getToken().getValue().toString().strip(), "\"-101\"");
		
		assertEquals(lexer.nextToken().getType(), TokenSmartType.TAG_ELEMENT);
		assertEquals(lexer.getToken().getValue().toString().strip(), "10");
		
		assertEquals(lexer.nextToken().getType(), TokenSmartType.TAG_ELEMENT);
		assertEquals(lexer.getToken().getValue().toString().strip(), "1");
		
		assertEquals(lexer.nextToken().getType(), TokenSmartType.TAG_CLOSE);
		assertEquals(lexer.getToken().getValue(), "$}");
		
		assertEquals(lexer.nextToken().getType(), TokenSmartType.TEXT);
		assertEquals(lexer.getToken().getValue(), "This is ");
		
		assertEquals(lexer.nextToken().getType(), TokenSmartType.TAG_OPEN);
		assertEquals(lexer.getToken().getValue(), "{$");
		
		assertEquals(lexer.nextToken().getType(), TokenSmartType.TAG_NAME);
		assertEquals(lexer.getToken().getValue(), "=");
		
		assertEquals(lexer.nextToken().getType(), TokenSmartType.TAG_ELEMENT);
		assertEquals(lexer.getToken().getValue().toString().strip(), "i");
		
		assertEquals(lexer.nextToken().getType(), TokenSmartType.TAG_ELEMENT);
		assertEquals(lexer.getToken().getValue().toString().strip(), "@sin");
		
		assertEquals(lexer.nextToken().getType(), TokenSmartType.TAG_ELEMENT);
		assertEquals(lexer.getToken().getValue().toString().strip(), "\"h_el\\lo\"");
		
		assertEquals(lexer.nextToken().getType(), TokenSmartType.TAG_CLOSE);
		assertEquals(lexer.getToken().getValue(), "$}");
		
		assertEquals(lexer.nextToken().getType(), TokenSmartType.TEXT);
		assertEquals(lexer.getToken().getValue(), "-th time\\.{$END$}");
		
		assertEquals(lexer.nextToken().getType(), TokenSmartType.EOF);
		assertEquals(lexer.getToken().getValue(), null);
	}
	
	@Test
	void testEmptyString() {
		LexerSmart lexer = new LexerSmart("");
		lexer.nextToken();
		
		assertThrows(NoSuchElementException.class, () -> lexer.nextToken());
	}
}

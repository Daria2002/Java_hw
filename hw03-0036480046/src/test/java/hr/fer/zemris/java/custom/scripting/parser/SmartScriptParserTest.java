/*package hr.fer.zemris.java.hw03.parser;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.custom.scripting.lexer.LexerSmart;
import hr.fer.zemris.java.custom.scripting.lexer.LexerSmartException;
import hr.fer.zemris.java.custom.scripting.lexer.TokenSmart;
import hr.fer.zemris.java.custom.scripting.lexer.TokenSmartType;
import hr.fer.zemris.java.hw03.prob1.LexerException;

public class SmartScriptParserTest {

	@Test
	public void escapeTest() {
		String simpleDoc = "A tag follows {$= \"Joe \\\"Long\\\" Smith\"$}.";
		LexerSmart lexer = new LexerSmart(simpleDoc);
		TokenSmart[] correctData = new TokenSmart[] {
				new TokenSmart(TokenSmartType.TEXT, "A tag follows "),
				new TokenSmart(TokenSmartType.TAG_OPEN, "{$"),
				new TokenSmart(TokenSmartType.TAG_NAME, "="),
				new TokenSmart(TokenSmartType.TAG_ELEMENT, "Joe \\\"Long\\\" Smith"),
				new TokenSmart(TokenSmartType.TAG_CLOSE, "$}")
		};
		
		checkTokenStream(lexer, correctData);
		
	}

	
	@Test
	public void nullLexerText() {
		String simpleDoc = null;
		assertThrows(IllegalArgumentException.class, () -> new LexerSmart(simpleDoc));
	}
	
	@Test
	public void getMoreThanAvaliable() {
		String simpleDoc = "";
		LexerSmart lex = new LexerSmart(simpleDoc);
		
		lex.nextToken();
		assertThrows(LexerSmartException.class, ()->lex.nextToken());
	}
	
	private void checkTokenStream(LexerSmart lexer, TokenSmart[] correctData) {
		int counter = 0;
		for(TokenSmart expected : correctData) {
			TokenSmart actual = lexer.nextToken();
			String msg = "Checking token "+counter + ":";
			assertEquals(expected.getType(), actual.getType());
			assertEquals(expected.getValue(), actual.getValue());
			counter++;
		}
	}
	
}*/
package hr.fer.zemris.java.custom.scripting.parser;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;


import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.lexer.LexerSmart;
import hr.fer.zemris.java.custom.scripting.lexer.TokenSmart;
import hr.fer.zemris.java.custom.scripting.lexer.TokenSmartType;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

public class SmartScriptParserTest {

	@Test
	public void testElementsTypeInForLoop() {
		String string = "{$FOR i-1 5 1.5 $}\n{$end$}";
		SmartScriptParser parser = new SmartScriptParser(string);
		
		DocumentNode docNode = parser.getDocumentNode();
		
		assertEquals(docNode.getChild(0) instanceof ForLoopNode, true);
		
		assertTrue(
				((ForLoopNode)docNode.getChild(0)).getVariable()
				instanceof ElementVariable);
		
		assertTrue(
				((ForLoopNode)docNode.getChild(0)).getStartExpression()
				instanceof ElementConstantInteger);
		
		assertTrue(
				((ForLoopNode)docNode.getChild(0)).getEndExpression()
				instanceof ElementConstantInteger);
		
		assertTrue(
				((ForLoopNode)docNode.getChild(0)).getStepExpression()
				instanceof ElementConstantDouble);
	}
	
	@Test
	public void testNestedLoopNumberOfChildren() {
		String document = loader("document2.txt");
		SmartScriptParser parser = new SmartScriptParser(document);
		DocumentNode documentNode = parser.getDocumentNode();
		
		assertEquals(documentNode.numberOfChildren(), 2);
		assertEquals(documentNode.getChild(0).numberOfChildren(), 2);
		assertEquals(documentNode.getChild(1).numberOfChildren(), 5);
	}
	
	@Test
	public void testNestedLoopChildren() {
		String document = loader("document2.txt");
		SmartScriptParser parser = new SmartScriptParser(document);
		DocumentNode documentNode = parser.getDocumentNode();
		
		ForLoopNode childNode = (ForLoopNode)documentNode.getChild(1);
		assertEquals(childNode.getStartExpression().asText(), "-10");
		assertEquals(childNode.getEndExpression().asText(), "10");
		assertEquals(childNode.getStepExpression().asText(), "2");
		
		assertEquals(true, childNode.getChild(0) instanceof TextNode);
		assertEquals(true, childNode.getChild(1) instanceof EchoNode);
		assertEquals(true, childNode.getChild(2) instanceof TextNode);
		assertEquals(true, childNode.getChild(3) instanceof EchoNode);
		assertEquals(true, childNode.getChild(4) instanceof ForLoopNode);
	}
	
	@Test
	public void testEscape() {
		String document = loader("document3.txt");
		SmartScriptParser parser = new SmartScriptParser(document);
		DocumentNode documentNode = parser.getDocumentNode();
		
		assertEquals(2, documentNode.numberOfChildren());
		assertEquals(true, documentNode.getChild(0) instanceof TextNode);
		
		ForLoopNode childNode = (ForLoopNode) documentNode.getChild(1);
		assertEquals(true, childNode.getChild(0) instanceof TextNode);
		assertEquals(true, childNode.getChild(0) instanceof TextNode);
	}
	
	@Test
	public void testEchoNode() {
		String document = loader("document4.txt");
		SmartScriptParser parser = new SmartScriptParser(document);
		DocumentNode documentNode = parser.getDocumentNode();
		
		assertEquals(documentNode.numberOfChildren(), 3);
	}
	
	@Test
	public void testSmartScriptParserException() {
		assertThrows(SmartScriptParserException.class, 
				() -> new SmartScriptParser(null));
	}
	
	/**
	 * Loads from resources file with given name.
	 * @param filename file name
	 * @return String with document data
	 */
	private String loader(String filename) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try(InputStream is =
				this.getClass().getClassLoader().getResourceAsStream(filename)) {
			byte[] buffer = new byte[1024];
			while(true) {
				int read = is.read(buffer);
				if(read<1) break;
				bos.write(buffer, 0, read);
			}
			return new String(bos.toByteArray(), StandardCharsets.UTF_8);
		} catch(IOException ex) {
			return null;
		}
	}
}

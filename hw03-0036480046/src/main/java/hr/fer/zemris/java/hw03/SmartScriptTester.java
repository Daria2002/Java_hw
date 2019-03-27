package hr.fer.zemris.java.hw03;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import hr.fer.zemris.java.custom.scripting.lexer.LexerSmart;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

public class SmartScriptTester {
	
	public static void main(String[] args) {
		String filePath = args[0];
		//String fileName = "document1";
		SmartScriptTester test = new SmartScriptTester();
		String docBody = "";
		
		try {
			docBody = new String(Files.readAllBytes(Paths.get(filePath)), 
					StandardCharsets.UTF_8);
		} catch (Exception e) {
			System.out.println("Can't open file.");
			System.exit(1);
		}
		
		System.out.println(docBody);
		SmartScriptParser parser = null;
		
		try {
			parser = new SmartScriptParser(docBody);
		} catch(SmartScriptParserException e) {
			System.out.println("Unable to parse document!");
			System.exit(-1);
		} catch(Exception e) {
			System.out.println("If this line ever executes, you have failed this class!");
			System.exit(-1);
		}
		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = createOriginalDocumentBody(document);
		SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
		DocumentNode document2 = parser2.getDocumentNode();
		String originalDocumentBody2 = createOriginalDocumentBody(document2);
		// now document and document2 should be structurally identical trees
		//System.out.println(originalDocumentBody); // should write something like original
		 // content of docBody
	}
	
	private String loader(String filename) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try(InputStream is =
		this.getClass().getClassLoader().getResourceAsStream(filename)) {
			byte[] buffer = new byte[1024];
			while(true) {
				int read = is.read(buffer);
				if(read < 1) break;
				bos.write(buffer, 0, read);
			}	
			return new String(bos.toByteArray(), StandardCharsets.UTF_8);
		} catch(IOException ex) {
			return null;
		}
	}
	
	private static String createOriginalDocumentBody(Node node) {
		String documentString = "";
		documentString += node.toString();
		for(int i = 0; i < node.numberOfChildren(); i++) {
			documentString += node.getChild(i).toString();
		}
		System.out.println("******************************");
		System.out.println(documentString);
		System.out.println("******************************");
		return documentString;
	}
	
}

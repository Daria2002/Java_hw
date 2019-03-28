package hr.fer.zemris.java.hw03;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

/**
 * This class is used for testing functionality of smart script parser
 * @author Daria Matković
 *
 */
public class SmartScriptTester {
	
	/**
	 * Method which executes when program is run
	 * @param args one argument filePath of .txt file
	 */
	public static void main(String[] args) {
		String filePath = args[0];
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
		System.out.println(originalDocumentBody2);
		
		// compare document and document2
		if(document.equals(document2)) {
			System.out.println("documents are the same.");
		} else {
			System.out.println("documents are not the same.");
		}
	}
	
	/**
	 * Creates string from node
	 * @param node given node
	 * @return string created from node
	 */
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
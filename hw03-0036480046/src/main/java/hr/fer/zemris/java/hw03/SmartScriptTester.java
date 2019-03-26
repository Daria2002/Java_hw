package hr.fer.zemris.java.hw03;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import hr.fer.zemris.java.custom.scripting.lexer.LexerSmart;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

public class SmartScriptTester {
	
	public static void main(String[] args) {
		//String fileName = args[0];
		String fileName = "document1";
		SmartScriptTester test = new SmartScriptTester();
		String docBody = test.loader(fileName);
		System.out.println(docBody);
		SmartScriptParser parser = null;
		
		String s = "\" BLA \" \n \\n";
		char[] c = s.toCharArray();

		System.out.println(c[8] == '\n');
		
		System.out.println(s);
		try {
			parser = new SmartScriptParser(docBody);
			//parser = new SmartScriptParser("{$ \"ja\\n volim lovru najvise na svijetu\"$}");
		} catch(SmartScriptParserException e) {
			System.out.println("Unable to parse document!");
			System.exit(-1);
		} catch(Exception e) {
			System.out.println("If this line ever executes, you have failed this class!");
			System.exit(-1);
		}
		DocumentNode document = parser.getDocumentNode();
		//String originalDocumentBody = createOriginalDocumentBody(document);
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
				if(read<1) break;
				bos.write(buffer, 0, read);
			}	
			return new String(bos.toByteArray(), StandardCharsets.UTF_8);
		} catch(IOException ex) {
			return null;
		}
	}
	
}

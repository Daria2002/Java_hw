package hr.fer.zemris.java.custom.scripting.demo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * This is demo program for smart script engine
 * @author Daria Matković
 *
 */
public class SmartScriptEngineDemo4 {
	
	/**
	 * This method is executed when program is run
	 * @param args takes no args
	 */
	public static void main(String[] args) {
		String documentBody = readFromDisk("fibonacci.smscr");
		Map<String,String> parameters = new HashMap<String, String>();
		Map<String,String> persistentParameters = new HashMap<String, String>();
		List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();
		// create engine and execute it
		new SmartScriptEngine(
		new SmartScriptParser(documentBody).getDocumentNode(),
		new RequestContext(System.out, parameters, persistentParameters, cookies)
		).execute();
	}
	
	/**
	 * This method read file data to string
	 * @param fileName file to read
	 * @return string with file contents
	 */
	private static String readFromDisk(String fileName) {
		try {
			String filePath = System.getProperty("user.dir") + 
					"/src/main/java/hr/fer/zemris/java/custom/scripting/demo/"+fileName;
			return new String(Files.readAllBytes(Paths.get(filePath)));
		} catch (IOException e) {
			System.out.println("Can't open file");
			return null;
		}
	}
}

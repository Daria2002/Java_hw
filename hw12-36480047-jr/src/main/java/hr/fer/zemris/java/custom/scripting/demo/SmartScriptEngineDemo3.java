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
 * This is demo program for testing smart script engine
 * @author Daria Matković
 *
 */
public class SmartScriptEngineDemo3 {
	
	/**
	 * This method is executed when program is run
	 * @param args
	 */
	public static void main(String[] args) {
		String documentBody = readFromDisk("brojPoziva.smscr");
		Map<String,String> parameters = new HashMap<String, String>();
		Map<String,String> persistentParameters = new HashMap<String, String>();
		List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();
		persistentParameters.put("brojPoziva", "3");
		RequestContext rc = new RequestContext(System.out, parameters, persistentParameters,
		cookies);
		new SmartScriptEngine(
		new SmartScriptParser(documentBody).getDocumentNode(), rc
		).execute();
		System.out.println("Vrijednost u mapi: "+rc.getPersistentParameter("brojPoziva"));
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

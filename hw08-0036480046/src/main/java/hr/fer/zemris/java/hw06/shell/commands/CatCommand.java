package hr.fer.zemris.java.hw06.shell.commands;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * This command represents cat command that opens given file and write its content
 * to console. This class implements ShellComand interface.
 * @author Daria Matković
 *
 */
public class CatCommand implements ShellCommand {

	/** cat command name **/
	public static final String CAT_COMMAND = "cat";
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		String[] argsArray = CommandUtilityClass.checkArguments(arguments, 2);
		
		if(argsArray == null) {
			argsArray = CommandUtilityClass.checkArguments(arguments, 1);
		}
		
		if(argsArray == null) {
			env.writeln("Input not ok.");
			return ShellStatus.CONTINUE;
		}
		
		String fileName = argsArray[0].trim();
		String charset;
		
		if(argsArray.length == 1) {
			charset = Charset.defaultCharset().toString();
		} else {
			charset = argsArray[1].trim();
		}
		
		
		File file = new File(CommandUtilityClass.resolvePath(fileName, env));
		FileInputStream fstream;
		
		try {
			fstream = new FileInputStream(file);
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
			String strLine;
			
			while ((strLine = br.readLine()) != null) {
				byte[] bytes = null;
				
				try {
					bytes = Charset.forName(charset).encode(CharBuffer.wrap(strLine)).array();
				} catch (Exception e) {
					System.out.println("Please enter valid charset name");
					break;
				}
				
				strLine = new String(bytes, charset);
				env.writeln(strLine);
			}
			
			fstream.close();
			
		} catch (Exception e) {
			env.writeln("Please enter valid file path");
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return CAT_COMMAND;
	}
	
	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<String>();
		
		list.add("Command cat takes one or two arguments.");
		list.add("The first argument is path to some file and is mandatory.");
		list.add("The second argument is charset name used to interpret chars from bytes.");
		list.add("If not provided, a default platform charset should be used.");
		list.add("This command opens given file and writes its content to console.");
		
        return Collections.unmodifiableList(list);
	}
}
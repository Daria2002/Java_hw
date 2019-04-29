package hr.fer.zemris.java.hw06.shell.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * This class represents massrename command.
 * @author Daria Matković
 *
 */
public class MassrenameCommand implements ShellCommand {
	
	/** copy command name **/
	public final static String MASSRENAME_COMMAND = "massrename";
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		if(arguments == null) {
			env.writeln("Please enter arguments");
			return ShellStatus.CONTINUE;
		}
		
		// if cmd is filter or groups, there should be 4 arguments
		if(arguments.contains("filter")) {
			String[] data = CommandUtilityClass.checkArguments(arguments, 4);
			
			if(data != null) {
				List<FilterResult> files = filter(Paths.get(data[0]), data[3]);
				
				for(FilterResult dir : files) {
					env.writeln(dir.toString());
				}
				
			} else {
				env.writeln("Arguments are not valid.");
			}
		
		// if cmd is groups, there should be 4 arguments
		} else if(arguments.contains("groups")) {
			String[] data = CommandUtilityClass.checkArguments(arguments, 4);
			
			if(data != null) {
				List<FilterResult> files = filter(Paths.get(data[0]), data[3]);
				
				for(FilterResult file : files) {
					env.write(file.toString() + " ");
					
					// number of groups + 1 because 0 group is not counted
					for(int i = 0; i < file.numberOfGroups() + 1; i++) {
						env.write(i + ": " + file.group(i) + " ");
					}
					
					env.writeln("");
				}
			} else {
				env.writeln("Arguments are not valid.");
			}
			
		// if cmd is show there should be 5 arguments
		} else if(arguments.contains("show")) {
			String[] data = CommandUtilityClass.checkArguments(arguments, 5);
			
			if(data != null) {
				List<FilterResult> files = filter(Paths.get(data[0]), data[3]);
				
				NameBuilderParser parser = new NameBuilderParser(data[4]);
				NameBuilder builder = parser.getNameBuilder();
				
				for(FilterResult file : files) {
					StringBuilder sb = new StringBuilder();
					builder.execute(file, sb);
					String novoIme = sb.toString();
					
					env.write(file.toString() + " => " + novoIme);
					
					env.writeln("");
				}
			} else {
				env.writeln("Arguments are not valid.");
			}
		
		} else if(arguments.contains("execute")) {
			String[] data = CommandUtilityClass.checkArguments(arguments, 5);
			
			if(data != null) {
				List<FilterResult> files = filter(Paths.get(data[0]), data[3]);
				
				NameBuilderParser parser = new NameBuilderParser(data[4]);
				NameBuilder builder = parser.getNameBuilder();
				
				for(FilterResult file : files) {
					StringBuilder sb = new StringBuilder();
					builder.execute(file, sb);
					String novoIme = sb.toString();
					
					env.write(file.toString() + " => " + novoIme);
					
					env.writeln("");
				}
				
				for(FilterResult file : files) {
					StringBuilder sb = new StringBuilder();
					builder.execute(file, sb);
					String novoIme = sb.toString();
					String oldName = file.toString();

					// move and rename file in destination dir
					execute(data[0], data[1], oldName, novoIme);
				}
				
			} else {
				env.writeln("Arguments are not valid.");
			}
			
		} else {
			env.writeln("Arguments are not valid.");
		}
		
		
		return ShellStatus.CONTINUE;
	} 

	private void execute(String sourceDir, String destDir, String oldName,
			String newName) {
		// rename files
		if(sourceDir.equals(destDir)) {
			renameFile(Paths.get(sourceDir, oldName).toString(),
					Paths.get(sourceDir, newName).toString());
			return;
		}
	
		// copy file
		Path source = Paths.get(sourceDir, oldName);
		Path newdir = Paths.get(destDir, newName);
		
		try {
			Files.move(source, Paths.get(destDir).resolve(Paths.get(destDir, newName)));
		} catch (IOException e) {
			return;
		}
	}


	private void renameFile(String oldName, String newName) {
        boolean success = new File(oldName).renameTo(new File(newName));
        if (!success) {
            System.out.println("Error while trying to rename file");
        }
	}
	
	private List<FilterResult> filter(Path dir, String pattern) {
		File[] filesInSourceDir = new File(dir.toString()).listFiles();
		
		if(pattern.indexOf('"') == 0) {
			pattern = pattern.substring(1, pattern.length()-1);
		}
		
		Pattern p = Pattern.compile(pattern);
	
		List<FilterResult> result = new ArrayList<FilterResult>();
		
		if(filesInSourceDir != null) {
			for(File file : filesInSourceDir) {
				if(file.isDirectory()) {
					continue;
				}
				
				Matcher m = p.matcher(file.getName());
				
				if(m.find()) {
					result.add(new FilterResult(file.getName(), m));
				}
			}
		}
		
		return result;
	}
	
	@Override
	public String getCommandName() {
		return MASSRENAME_COMMAND;
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<String>();
		
		list.add("Massrename command takes 4 or 5 arguments, depending on cmd value.");
		list.add("This is format of calling massrename function: massrename DIR1 DIR2 CMD MASKA ostalo");
		
        return Collections.unmodifiableList(list);
	}
}
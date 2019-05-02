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
 * This class represents massrename command. "Massrename command takes 4 or 5 arguments, 
 * depending on cmd value. Cmd value repsents actions: execute, show, filter and groups.
 * This is format of calling massrename function: massrename DIR1 DIR2 CMD MASKA ostalo.
 * This command has three commands show, filter, execute and groups.
 * Filter prints filtered files. Show prints filtered elements and new names.
 * Execute moves and renames files from source to destination folder. 
 * Prints groups and values for each group.
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
				List<FilterResult> files = filter(
						Paths.get(CommandUtilityClass.resolvePath(data[0], env)), data[3]);
				
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
				List<FilterResult> files = filter(
						Paths.get(CommandUtilityClass.resolvePath(data[0], env)), data[3]);
				
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
				printNewAndOldName(
						filter(Paths.get(CommandUtilityClass.resolvePath(data[0], env)), data[3]),
						env, new NameBuilderParser(data[4]).getNameBuilder());
				
			} else {
				env.writeln("Arguments are not valid.");
			}
		
		} else if(arguments.contains("execute")) {
			String[] data = CommandUtilityClass.checkArguments(arguments, 5);
			
			if(data != null) {
				List<FilterResult> files = filter(
						Paths.get(CommandUtilityClass.resolvePath(data[0], env)), data[3]);
				NameBuilder builder = new NameBuilderParser(data[4]).getNameBuilder();
				
				printNewAndOldName(files, env, builder);
				
				for(FilterResult file : files) {
					StringBuilder sb = new StringBuilder();
					builder.execute(file, sb);
					String novoIme = sb.toString();
					String oldName = file.toString();

					// move and rename file in destination dir
					execute(data[0], data[1], oldName, novoIme, env);
				}
				
			} else {
				env.writeln("Arguments are not valid.");
			}
			
		} else {
			env.writeln("Arguments are not valid.");
		}
		
		return ShellStatus.CONTINUE;
	} 

	/**
	 * This method iterates through files, prints old name, calls method for
	 * generating new name and prints new name.
	 * @param files list of FilterResult objects, where FilterResult represents file
	 * @param env Environment
	 * @param builder NameBuilder
	 */
	private void printNewAndOldName(List<FilterResult> files, Environment env, NameBuilder builder) {
		for(FilterResult file : files) {
			StringBuilder sb = new StringBuilder();
			builder.execute(file, sb);
			
			env.write(file.toString() + " => " + sb.toString());
			env.writeln("");
		}
	}

	/**
	 * This method renames and moves filtered files from source to destination folder
	 * @param source source dir
	 * @param dest destination dir
	 * @param oldName old file name
	 * @param newName new file name
	 * @param env Environment
	 */
	private void execute(String source, String dest, String oldName, String newName, Environment env) {
		try {
			Files.move(Paths.get(source, oldName), Paths.get(dest).resolve(Paths.get(dest, newName)));
		} catch (IOException e) {
			env.writeln("Error during moving files");
			return;
		}
	}
	
	/**
	 * This method filters files in source dir, depending on pattern
	 * @param dir source dir
	 * @param pattern string pattern
	 * @return list of filtered files 
	 */
	private List<FilterResult> filter(Path dir, String pattern) {
		File[] filesInSourceDir = new File(dir.toString()).listFiles();
		List<FilterResult> result = new ArrayList<FilterResult>();
		
		if(filesInSourceDir != null) {
			for(File file : filesInSourceDir) {
				if(file.isDirectory()) {
					continue;
				}
				
				Matcher m = Pattern.compile(pattern).matcher(file.getName());
				
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
		list.add("This command has three commands show, filter, execute and groups.");
		list.add("Filter prints filtered files.");
		list.add("Show prints filtered elements and new names.");
		list.add("Execute moves and renames files from source to destination folder.");
		list.add("Prints groups and values for each group.");
		
        return Collections.unmodifiableList(list);
	}
}
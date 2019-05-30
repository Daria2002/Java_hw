package hr.fer.zemris.java.custom.scripting.demo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;

/**
 * This class represents tree writer.
 * @author Daria Matković
 *
 */
public class TreeWriter {
	
	/**
	 * This method is executed when program is run.
	 * @param args takes one arg - file name
	 */
	public static void main(String[] args) {
		
		if(args.length != 1) {
			throw new IllegalArgumentException("TreeWriter accepts only one "
					+ "argument - file name");
		}
		
		byte[] encoded = null;
		try {
			encoded = Files.readAllBytes(Paths.get(args[0]));
		} catch (IOException e) {
			System.out.println("Can't open given file.");
			System.exit(0);
		}
		
		SmartScriptParser p = new SmartScriptParser(new String(encoded));
		WriterVisitor visitor = new WriterVisitor();
		p.getDocumentNode().accept(visitor);
		
		System.out.println(visitor.getDocumentText());
	}
	
	/**
	 * This is nested class that represents writer visitor
	 * @author Daria Matković
	 *
	 */
	private static class WriterVisitor implements INodeVisitor {
		/** text **/
		private String text = "";
		
		@Override
		public void visitTextNode(TextNode node) {
			text += node.toString();
		}

		@Override
		public void visitForLoopNode(ForLoopNode node) {
			text += node.toString();
		}

		@Override
		public void visitEchoNode(EchoNode node) {
			text += node.toString();
		}

		@Override
		public void visitDocumentNode(DocumentNode node) {
			text += node.toString();
		}
		
		/**
		 * Gets document text
		 * @return return text
		 */
		public String getDocumentText() {
			return text;
		}
	}
}

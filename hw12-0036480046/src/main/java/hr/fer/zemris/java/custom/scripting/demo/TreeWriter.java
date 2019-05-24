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

public class TreeWriter {
	
	public static void main(String[] args) {
		
		if(args.length != 1) {
			throw new IllegalArgumentException("TreeWriter accepts only one "
					+ "argument - file name");
		}
		
		String fileName = args[0];
		byte[] encoded = null;
		try {
			encoded = Files.readAllBytes(Paths.get(fileName));
		} catch (IOException e) {
			System.out.println("Can't open given file.");;
		}
		
		String docBody = new String(encoded);
		
		SmartScriptParser p = new SmartScriptParser(docBody);
		WriterVisitor visitor = new WriterVisitor();
		p.getDocumentNode().accept(visitor);
		
		System.out.println(visitor.getDocumentText());
	}
	
	private static class WriterVisitor implements INodeVisitor{

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
		
		public String getDocumentText() {
			return text;
		}
	}
}

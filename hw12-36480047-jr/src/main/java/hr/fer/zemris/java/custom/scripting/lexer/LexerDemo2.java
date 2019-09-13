package hr.fer.zemris.java.custom.scripting.lexer;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.NowNode;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;

/**
 * Demo program for lexer
 * @author Daria Matković
 *
 */
public class LexerDemo2 {
	
	/**
	 * This method is executed when program is run
	 * @param args takes no args
	 */
	public static void main(String[] args) {
	
		String test = "This is sample text.\n" + 
				"{$ FOR i 1 \"10\" 1 $}\n" + 
				"This is {$= i $}-th time this message is generated.\n" + 
				//"{$NOW$}" +
				"{$END$}\n";
		
		LexerSmart l = new LexerSmart(test);
		SmartScriptParser p = new SmartScriptParser(test);
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

		@Override
		public void visitNowNode(NowNode node) {
			text += node.toString();
		}
	}
}

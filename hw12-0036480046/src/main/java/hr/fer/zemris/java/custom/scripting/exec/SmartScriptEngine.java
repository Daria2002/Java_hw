package hr.fer.zemris.java.custom.scripting.exec;

import java.io.IOException;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.webserver.RequestContext;

public class SmartScriptEngine {

	private DocumentNode documentNode;
	private RequestContext requestContext;
	private ObjectMultistack multistack = new ObjectMultistack();
	
	private INodeVisitor visitor = new INodeVisitor() {

		@Override
		public void visitTextNode(TextNode node) {
			try {
				requestContext.write(node.getText());
			} catch (IOException e) {
				System.out.println("Request context can't write node's text.");
				System.exit(0);
			}
		}

		@Override
		public void visitForLoopNode(ForLoopNode node) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void visitEchoNode(EchoNode node) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void visitDocumentNode(DocumentNode node) {
			for(int i = 0; i < node.numberOfChildren(); i++) {
				node.getChild(i).accept(visitor);
			}
		}
	};
	
	public SmartScriptEngine(DocumentNode documentNode, RequestContext
			requestContext) {
		this.documentNode = documentNode;
		this.requestContext = requestContext;
	}
	
	public void execute() {
		documentNode.accept(visitor);
	}
}

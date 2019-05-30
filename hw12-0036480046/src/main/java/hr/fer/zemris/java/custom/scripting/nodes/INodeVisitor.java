package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Interface for node visitor
 * @author Daria Matković
 *
 */
public interface INodeVisitor {

	/**
	 * Visits text node
	 * @param node node
	 */
	public void visitTextNode(TextNode node);
	
	/**
	 * Visits for loop node
	 * @param node node
	 */
	public void visitForLoopNode(ForLoopNode node);
	
	/**
	 * Visits echo node 
	 * @param node node
	 */
	public void visitEchoNode(EchoNode node);
	
	/**
	 * Visits document node 
	 * @param node node
	 */
	public void visitDocumentNode(DocumentNode node);
}

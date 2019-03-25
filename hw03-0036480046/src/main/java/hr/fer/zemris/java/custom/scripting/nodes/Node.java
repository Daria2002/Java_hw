package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.collections.Collection;

/**
 * Base class for all graph nodes.
 * @author Daria Matkovic
 *
 */
public class Node {
	
	ArrayIndexedCollection collection;
	
	/**
	 * Adds given child node to collection of children.
	 * @param child node to add
	 */
	void addChildNode(Node child) {
		if(collection == null) {
			collection = new ArrayIndexedCollection();
		}
		collection.add(child);
	}
	
	int numberOfChildren() {
		return collection.size();
	}
	
	Node getChild(int index) {
		return (Node)collection.get(index);
	}
}

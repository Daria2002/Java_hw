package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;

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
	public void addChildNode(Node child) {
		if(collection == null) {
			collection = new ArrayIndexedCollection();
		}
		collection.add(child);
	}
	
	/**
	 * returns size
	 * @return returns number of children
	 */
	public int numberOfChildren() {
		return collection.size();
	}
	
	/**
	 * returns child node at given position
	 * @param index position
	 * @return child node
	 */
	public Node getChild(int index) {
		return (Node)collection.get(index);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((collection == null) ? 0 : collection.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		Node other = (Node) obj;
		if(getClass() != obj.getClass()) {
			return false;
		} else if(numberOfChildren() != other.numberOfChildren()) {
			return false;
		} else {
			for(int i = 0; i < numberOfChildren(); i++) {
				if(!other.getChild(i).equals(this.getChild(i))) {
					return false;
				}
			}
		}
		return true;
	}
}

package hr.fer.zemris.java.hw01;

import hr.fer.zemris.java.hw01.UniqueNumbers.TreeNode;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class UniqueNumbersTest {

	@Test
	public void testAddNode() {
		TreeNode node = null;
		node = UniqueNumbers.addNode(node, 42);
		node = UniqueNumbers.addNode(node, 76);
		node = UniqueNumbers.addNode(node, 21);
		node = UniqueNumbers.addNode(node, 76);
		node = UniqueNumbers.addNode(node, 35);
		assertTrue(node.value == 42 && node.left.value == 21
				&& node.left.right.value == 35 && node.right.value == 76);
	}
	
	@Test
	public void testTreeSize() {
		int sizeBeforeAddingNodes, sizeAfterAddingNodes;
		TreeNode node = null;
		sizeBeforeAddingNodes = UniqueNumbers.treeSize(node);
		node = UniqueNumbers.addNode(node, 42);
		node = UniqueNumbers.addNode(node, 76);
		node = UniqueNumbers.addNode(node, 21);
		node = UniqueNumbers.addNode(node, 76);
		node = UniqueNumbers.addNode(node, 35);
		sizeAfterAddingNodes = UniqueNumbers.treeSize(node);
		assertTrue(sizeBeforeAddingNodes == 0 && sizeAfterAddingNodes == 4);
	}
	
	@Test
	public void testContainsValueTrue() {
		TreeNode node = null;
		node = UniqueNumbers.addNode(node, 42);
		node = UniqueNumbers.addNode(node, 76);
		node = UniqueNumbers.addNode(node, 21);
		node = UniqueNumbers.addNode(node, 76);
		node = UniqueNumbers.addNode(node, 35);
		Boolean actualValue = UniqueNumbers.containsValue(node, 76);
		assertEquals(true, actualValue);
	}
	
	@Test
	public void testContainsValueFalse() {
		TreeNode node = null;
		node = UniqueNumbers.addNode(node, 42);
		node = UniqueNumbers.addNode(node, 76);
		node = UniqueNumbers.addNode(node, 21);
		node = UniqueNumbers.addNode(node, 76);
		node = UniqueNumbers.addNode(node, 35);
		Boolean actualValue = UniqueNumbers.containsValue(node, 5);
		assertEquals(false, actualValue);
	}
	
	@Test
	public void testContainsValueNull() {
		assertEquals(false, UniqueNumbers.containsValue(null, 5));
	}
}
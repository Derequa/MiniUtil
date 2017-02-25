package derek.util;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class AVLTreeTest {
	
	LinkedList<Integer> inorder = new LinkedList<Integer>();
	LinkedList<Integer> preorder = new LinkedList<Integer>();
	LinkedList<Integer> postorder = new LinkedList<Integer>();
	
	@Before
	public void setup() {
		inorder = new LinkedList<Integer>();
		preorder = new LinkedList<Integer>();
		postorder = new LinkedList<Integer>();
		
		for (int count = 0 ; count < 10; count++)
			inorder.add(new Integer(count));
		
		preorder.add(new Integer(3));
		preorder.add(new Integer(0));
		preorder.add(new Integer(1));
		preorder.add(new Integer(2));
		preorder.add(new Integer(4));
		preorder.add(new Integer(5));
		preorder.add(new Integer(6));
		preorder.add(new Integer(7));
		preorder.add(new Integer(8));
		preorder.add(new Integer(9));
		
		postorder.add(new Integer(0));
		postorder.add(new Integer(1));
		postorder.add(new Integer(2));
		postorder.add(new Integer(4));
		postorder.add(new Integer(5));
		postorder.add(new Integer(6));
		postorder.add(new Integer(7));
		postorder.add(new Integer(8));
		postorder.add(new Integer(9));
		postorder.add(new Integer(3));
	}

	@Test
	public void genericTreeTest() {
		AVLTree<Integer> tree = new AVLTree<Integer>();
		for (int i = 0; i < 10; i++) {
			Integer integer = new Integer(i);
			tree.insert(integer);
			assertEquals("Tree is not correct size!", i + 1, tree.size());

		}
		System.out.println(tree);
		
		List<Integer> inorderActual = tree.inOrder();
		List<Integer> preorderActual = tree.preOrder();
		List<Integer> postorderActual = tree.postOrder();
		System.out.println("[In-Order Traveral]\n" + inorderActual + "\n");
		assertEquals("In-Order traversal is out of order!", inorder, inorderActual);
		System.out.println("[Pre-Order Traveral]\n" + preorderActual + "\n");
		assertEquals("Pre-Order traversal is out of order!", preorder, preorderActual);
		System.out.println("[Post-Order Traveral]\n" + postorderActual + "\n");
		assertEquals("Post-Order traversal is out of order!", postorder, postorderActual);
		
		tree.remove(new Integer(8));
		assertEquals("Tree is not correct size!", 9, tree.size());
		inorderActual = tree.inOrder();
		inorder.remove(new Integer(8));
		System.out.println(tree);
		System.out.println("[In-Order Traveral]\n" + tree.inOrder() + "\n");
		assertEquals("In-Order traversal is out of order!", inorder, inorderActual);
		
		tree.remove(new Integer(6));
		assertEquals("Tree is not correct size!", 8, tree.size());
		inorderActual = tree.inOrder();
		inorder.remove(new Integer(6));
		System.out.println(tree);
		System.out.println("[In-Order Traveral]\n" + tree.inOrder() + "\n");
		assertEquals("In-Order traversal is out of order!", inorder, inorderActual);
		
		tree.remove(new Integer(3));
		assertEquals("Tree is not correct size!", 7, tree.size());
		inorderActual = tree.inOrder();
		inorder.remove(new Integer(3));
		System.out.println(tree);
		System.out.println("[In-Order Traveral]\n" + tree.inOrder() + "\n");
		assertEquals("In-Order traversal is out of order!", inorder, inorderActual);
	}

}

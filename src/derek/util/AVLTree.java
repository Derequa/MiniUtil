package derek.util;

import java.util.LinkedList;
import java.util.List;

/**
 * This class implements a AVL style balanced binary search tree.
 * It currently acts as a generic datastructure, and primarily implements the Map interface.
 *
 * @author Derek Batts <dsbatts@ncsu.edu>
 */
public class AVLTree<T extends Comparable<T>> {

	/** The root node of the tree. */
	private Node<T> root = null;
	
	/** The number of elements in this tree. */
	private int size = 0;


	/**
	 * This is an inner class to wrap Node functionality for this tree.
	 *
	 * @author Derek Batts <dsbatts@ncsu.edu>
	 */
	private class Node<E> {

		/** The data this node contains. */
		private E data;
		/** The parent node in the tree. */
		private Node<E> parent;
		/** The left child node. */
		private Node<E> leftChild;
		/** The right child node. */
		private Node<E> rightChild;

		/**
		 * This will construct a node with a given parent node.
		 * @param  E data The data this node will contain.
		 * @param  Node<E> parent The parent node this node is under.
		 */
		private Node(E data, Node<E> parent) {
			this(data, parent, null, null);
		}

		/**
		 * This will construct a node with the given data, parent, and children.
		 * @param E data The data this node will contain.
		 * @param Node<E> parent The parent node this node is under.
		 * @param Node<E> leftChild The left child node of this node.
		 * @param Node<E> rightChild The right child node of this node.
		 */
		private Node(E data, Node<E> parent, Node<E> leftChild, Node<E> rightChild) {
			this.data = data;
			this.parent = parent;
			this.leftChild = leftChild;
			this.rightChild = rightChild;
		}

		/**
		 * This creates and returns a simple representation of a node in string form.
		 * @return A string representing this node.
		 */
		public String toString() {
			return "" + data;
		}
	}
	
	public int size() {
		return size;
	}

	public void insert(T element) {
		if (root == null)
			root  = new Node<T>(element, null);
		else {
			Node<T> n = recursiveInsert(element, root);
			maintain(n);
		}
		size++;
	}

	public T find(T element) {
		Node<T> n = find(element, root);

		if (n == null)
			return null;
		else
			return n.data;
	}

	public T remove(T element) {
		T ret = remove(find(element, root));
		if (ret != null)
			size--;
		return ret;
	}

	private T remove(Node<T> n) {
		// Return null if nothing found
		if (n == null)
			return null;
		// Check if the node has no children
		else if ((n.leftChild == null) && (n.rightChild == null)) {
			if (n.parent ==  null)
				root = null;
			else if (n.parent.leftChild == n)
				n.parent.leftChild = null;
			else
				n.parent.rightChild = null;
			return n.data;
		}
		// Check if this node has only a left child
		else if ((n.leftChild == null) && (n.rightChild != null)) {
			Node<T> parent = n.parent;
			// If we are root, set right child to root
			if (parent == null)
				root = n.rightChild;
			else if (n.parent.leftChild == n)
				n.parent.leftChild = n.rightChild;
			else
				n.parent.rightChild = n.rightChild;
			n.rightChild.parent = parent;
			maintain(n.rightChild);
			return n.data;
		}
		// Check if this node has only a right child
		else if ((n.leftChild == null) && (n.rightChild != null)) {
			Node<T> parent = n.parent;
			// If we are root, set right child to root
			if (parent == null)
				root = n.leftChild;
			else if (n.parent.leftChild == n)
				n.parent.leftChild = n.leftChild;
			else
				n.parent.rightChild = n.leftChild;
			n.leftChild.parent = parent;
			maintain(n.leftChild);
			return n.data;
		}
		// Otherwise this node has two children
		else {
			Node<T> min = null;
			for (Node<T> minNode = n.rightChild ; minNode != null ; minNode = minNode.leftChild) {
				if (minNode.leftChild != null)
					min = minNode.leftChild;
			}
			T ret = n.data;
			n.data = min.data;
			remove(min);
			return ret;
		}
	}

	public List<T> inOrder() {
		List<T> l = new LinkedList<T>();
		inOrderRecursive(l, root);
		return l;
	}

	public List<T> preOrder() {
		List<T> l = new LinkedList<T>();
		preOrderRecursive(l, root);
		return l;
	}

	public List<T> postOrder() {
		List<T> l = new LinkedList<T>();
		postOrderRecursive(l, root);
		return l;
	}

	public String toString() {
		String message = "node value: left child Value, right child value\n\n";
		return message + recursiveStringBuilder(root);
	}

	private String recursiveStringBuilder(Node<T> n) {
		if (n == null)
			return "";
		String res = n.data + ": " + n.leftChild + ", " + n.rightChild + "\n";
		return res + recursiveStringBuilder(n.leftChild) + recursiveStringBuilder(n.rightChild);
	}

	private Node<T> find(T element, Node<T> n) {
		if (n == null)
			return null;
		else if (n.data.equals(element))
			return n;
		else if (element.compareTo(n.data) <= 0)
			return find(element, n.leftChild);
		else
			return find(element, n.rightChild);
	}

	private void inOrderRecursive(List<T> currentList, Node<T> n) {
		// Recursively visit left child first
		if (n.leftChild != null)
			inOrderRecursive(currentList, n.leftChild);
		currentList.add(n.data);
		if (n.rightChild != null)
			inOrderRecursive(currentList, n.rightChild);
	}

	private void preOrderRecursive(List<T> currentList, Node<T> n) {
		currentList.add(n.data);
		if (n.leftChild != null)
			inOrderRecursive(currentList, n.leftChild);
		if (n.rightChild != null)
			inOrderRecursive(currentList, n.rightChild);
	}

	private void postOrderRecursive(List<T> currentList, Node<T> n) {
		if (n.leftChild != null)
			inOrderRecursive(currentList, n.leftChild);
		if (n.rightChild != null)
			inOrderRecursive(currentList, n.rightChild);
		currentList.add(n.data);
	}

	private Node<T> recursiveInsert(T element, Node<T> parent) {
		if (element.compareTo(parent.data) <= 0) {
			// Go left
			if (parent.leftChild != null)
				return recursiveInsert(element, parent.leftChild);
			else {
				parent.leftChild = new Node<T>(element, parent);
				return parent.leftChild;
			}
		}
		else {
			// Go Right
			if (parent.rightChild != null)
				return recursiveInsert(element, parent.rightChild);
			else {
				parent.rightChild = new Node<T>(element, parent);
				return parent.rightChild;
			}
		}
	}

	private void maintain(Node<T> newNode) {
		if (newNode.parent.leftChild == newNode) {
			int rightHeight = checkHeight(newNode.parent.rightChild);
			maintain(newNode.parent, 0, rightHeight);
		}
		else if (newNode.parent.rightChild == newNode) {
			int leftHeight = checkHeight(newNode.parent.leftChild);
			maintain(newNode.parent, leftHeight, 0);
		}
	}

	private void maintain(Node<T> n, int leftHeight, int rightHeight) {
		// Walk up parent chain
		// Calculate height for other subtree
		if (Math.abs(leftHeight - rightHeight) <= 1) {
			if (n.parent == null)
				return;
			if (n.parent.leftChild == n) {
				int rightH = checkHeight(n.parent.rightChild);
				maintain(n.parent, leftHeight + 1, rightH);
			}
			else if (n.parent.rightChild == n) {
				int leftH = checkHeight(n.parent.leftChild);
				maintain(n.parent, leftH, rightHeight + 1);
			}
		}
		// Rotate as needed, then go back to checking via recursive call
		else {
			if (leftHeight > rightHeight)
				rightRotate(n);
			else
				leftRotate(n);
			int rightH = checkHeight(n.rightChild);
			int leftH = checkHeight(n.leftChild);
			maintain(n, leftH, rightH);
		}
	}

	private int checkHeight(Node<T> n) {
		// Height for a null child is -1
		if (n == null)
			return -1;

		// Get heights from children
		int leftHeight = checkHeight(n.leftChild);
		int rightHeight = checkHeight(n.rightChild);

		// Return the larger height + 1
		if (leftHeight > rightHeight)
			return leftHeight + 1;
		else
			return rightHeight + 1;
	}

	private void leftRotate(Node<T> n) {
		Node<T> parent = n.parent;
		Node<T> right = n.rightChild;

		if (parent != null) {
			if (parent.rightChild == n)
				parent.rightChild = right;
			else
				parent.leftChild = right;
		}
		else
			root = right;

		right.parent = parent;
		Node<T> temp = right.leftChild;
		right.leftChild = n;
		n.parent = right;
		n.rightChild = temp;
		if (temp != null)
			temp.parent = n;
	}


	// bless wikipedia https://en.wikipedia.org/wiki/Tree_rotation
	private void rightRotate(Node<T> n) {
		Node<T> parent = n.parent;
		Node<T> left = n.leftChild;

		if (parent != null){
			if (parent.rightChild == n)
				parent.rightChild = left;
			else
				parent.leftChild = left;
		}
		else
			root = left;

		left.parent = parent;
		Node<T> temp = left.rightChild;
		left.rightChild = n;
		n.parent = left;
		n.leftChild = temp;
		if (temp != null)
			temp.parent = n;
	}
}

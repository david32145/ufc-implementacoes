package br.ufc.crateus.eda.st;

public class AVLTreeMap<K extends Comparable<K>, V> extends AbstractBinarySearchTreeMap<K, V> {
	private class AVLNode extends Node {
		AVLNode left;
		AVLNode right;
		int heigth;

		public AVLNode(K key, V value) {
			super(key, value);
			heigth = 0;
		}

		@Override
		Node left() {
			return left;
		}

		@Override
		Node right() {
			return right;
		}
	}

	private AVLNode root;

	private AVLNode put(AVLNode root, K key, V value) {
		if (root == null)
			return new AVLNode(key, value);
		int cmp = root.key.compareTo(key);
		if (cmp > 0)
			root.left = put(root.left, key, value);
		else if (cmp < 0)
			root.right = put(root.right, key, value);
		else
			root.value = value;

		root = balance(root);
		return root;
	}

	private AVLNode balance(AVLNode root) {
		int fR = balancingFactor(root);
		if (fR == 2) {
			int fRLeft = balancingFactor(root.left);
			if (fRLeft == 1) {
				root = rotateRight(root);
			}
			if (fRLeft == -1) {
				root.left = rotateLeft(root.left);
				root = rotateRight(root);
			}
		}
		if (fR == -2) {
			int fRRight = balancingFactor(root.right);
			if (fRRight == 1) {
				root.right = rotateRight(root.right);
				root = rotateLeft(root);
			}
			if (fRRight == -1) {
				root = rotateLeft(root);
			}
		}
		root.heigth = height(root);

		root.count = count(root.left) + count(root.right) + 1;
		return root;
	}

	private AVLNode rotateRight(AVLNode node) {
		AVLNode r = node.left;
		node.left = r.right;
		r.right = node;

		node.count = count(node.left) + count(node.right) + 1;
		r.count = count(r.left) + count(r.right) + 1;

		node.heigth = height(node);
		r.heigth = height(r);

		return r;
	}

	private AVLNode rotateLeft(AVLNode node) {
		AVLNode r = node.right;
		node.right = r.left;
		r.left = node;

		node.count = count(node.left) + count(node.right) + 1;
		r.count = count(r.left) + count(r.right) + 1;

		node.heigth = height(node);
		r.heigth = height(r);

		return r;
	}

	public int height() {
		return height(root);
	}

	public int height(AVLNode node) {
		if (node == null)
			return -1;
		int hl, hr;
		if (node.left == null)
			hl = -1;
		else
			hl = node.left.heigth;
		if (node.right == null)
			hr = -1;
		else
			hr = node.right.heigth;
		if (hr > hl)
			return hr + 1;
		return hl + 1;

	}

	public int size() {
		return count(root);
	}

	// Obs. estou sempre passando valores não nulos
	private int balancingFactor(AVLNode node) {
		return height(node.left) - height(node.right);
	}

	private AVLNode removeMax(AVLNode root) {
		if (root == null)
			return null;
		if (root.right == null)
			return root.left;
		root.right = removeMax(root.right);
		root = balance(root);
		return root;
	}

	@Override
	public void removeMax() {
		root = removeMax(root);
	}

	@Override
	public void removeMin() {
		// TODO Auto-generated method stub

	}

	@Override
	public void put(K key, V value) {
		root = put(root, key, value);
	}

	private AVLNode remove(AVLNode root, K key) {
		if (root == null) return null;
		int cmp = root.key.compareTo(key);
		if (cmp > 0) root.left = remove(root.left, key);
		else if (cmp < 0) root.right = remove(root.right, key);
		else {
			if (root.left == null) return root.right;
			if (root.right == null) return root.left;
			else {
				AVLNode t = root;
				root = (AVLNode) maxNode(root.left);
				root.right = t.right;
				root.left = removeMax(t.left);
			}
		}

		root = balance(root);
		return root;
	}

	@Override
	public void remove(K key) {
		root = remove(root, key);
	}
	
	//Aqui eu tava só testando se a altura tava batendo mesmo......
	private int teste(AVLNode root) {
		if (root == null)
			return -1;
		int hl = teste(root.left), hr = teste(root.right);
		if (hl > hr)
			return hl + 1;
		return hr + 1;
	}

	public int teste() {
		return teste(root);
	}

	@Override
	protected Node getRoot() {
		return this.root;
	}
}

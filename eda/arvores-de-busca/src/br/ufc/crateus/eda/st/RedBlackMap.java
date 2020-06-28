package br.ufc.crateus.eda.st;

public class RedBlackMap<K extends Comparable<K>, V> extends AbstractBinarySearchTreeMap<K, V> {
	private static final boolean RED = true;
	private static final boolean BLACK = false;
	
	protected class RBNode extends Node {
		boolean color;
		RBNode left;
		RBNode right;
		
		public RBNode(K key, V value) {
			super(key, value);
			this.color = RED;
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
	
	private RBNode root;
	
	@Override
	public RBNode getRoot() {
		return root;
	}
	
	protected RBNode put(RBNode r, K key, V value) {
		if (r == null) return new RBNode(key, value);
		
		if (key.compareTo(r.key) < 0) r.left = put(r.left, key, value);
		else if (key.compareTo(r.key) > 0) r.right = put(r.right, key, value);
		else r.value = value;
		
		if (!isRed(r.left) && isRed(r.right)) r = rotateLeft(r);
		if (isRed(r.left) && isRed(r.left.left)) r = rotateRight(r);
		if (isRed(r.left) && isRed(r.right)) flipColors(r);
		
		r.count = count(r.left) + count(r.right) + 1;
		return r;
	}
	
	private RBNode rotateLeft(RBNode h) {
		RBNode x = h.right;
		h.right = x.left;
		x.left = h;
		x.color = h.color;
		h.color = RED;
		
		h.count = count(h.left) + count(h.right) + 1;
		x.count = h.count + count(x.right) + 1;
		return x;
	}
	
	private RBNode rotateRight(RBNode h) {
		RBNode x = h.left;
		h.left = x.right;
		x.right = h;
		x.color = h.color;
		h.color = RED;
		
		h.count = count(h.left) + count(h.right) + 1;
		x.count = h.count + count(x.right) + 1;
		return x;
	}
	
	private void flipColors(RBNode h) {
		h.color = RED;
		h.left.color = BLACK;
		h.right.color = BLACK;
	}
	
	private boolean isRed(RBNode node)  {
		if (node == null) return false;
		return node.color == RED;
	}


	@Override
	public void removeMax() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void removeMin() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void put(K key, V value) {
		root = put(root, key, value);
		
	}


	@Override
	public void remove(K key) {
		// TODO Auto-generated method stub
		
	}

}

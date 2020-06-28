package br.ufc.crateus.eda.st;

public abstract class AbstractBinarySearchTreeMap<K extends Comparable<K>, V> implements OrderedMap<K, V> {
	protected abstract class Node {
		K key;
		V value;

		int count;
		
		public Node(K key, V value) {
			this.key = key;
			this.value = value;
			this.count = 1;
		}
		
		abstract Node left();
		abstract Node right();
	}
		
	protected abstract Node getRoot();
	
	protected int count(Node n) {
		return (n != null)? n.count : 0;
	}

	private Node getNode(Node r, K key) {
		if (r == null) return null;
		
		int cmp = key.compareTo(r.key);
		if (cmp < 0) return getNode(r.left(), key);
		if (cmp > 0) return getNode(r.right(), key);
		return r;
	}

	@Override
	public V get(K key) {
		Node node = getNode(getRoot(), key);
		return (node != null)? node.value : null;
	}

	@Override
	public boolean contains(K key) {
		return get(key) != null;
	}
	
	@Override
	public Iterable<K> keys() {
		// TODO Auto-generated method stub
		return null;
	}

	protected Node minNode(Node r) {
		if (r == null) return null;
		while (r.left() != null) r = r.left();
		return r;
	}

	@Override
	public K min() {
		Node m = minNode(getRoot());
		return (m != null)? m.key : null;
	}
	
	protected Node maxNode(Node r) {
		if (r == null) return null;
		while (r.right() != null) r = r.right();
		return r;
	}

	@Override
	public K max() {
		Node m = maxNode(getRoot());
		return (m != null)? m.key : null;
	}
	
	private K floor(Node r, K e) {
		if (r == null) return null;
		int cmp = e.compareTo(r.key);
		if (cmp < 0) return floor(r.left(), e);
		if (cmp > 0) {
			K tmp = floor(r.right(), e);
			return (tmp != null)? tmp : r.key;
		}
		return r.key;
	}

	@Override
	public K floor(K e) {
		return floor(getRoot(), e);
	}
	
	private K ceiling(Node r, K e) {
		if (r == null) return null;
		int cmp = e.compareTo(r.key);
		if (cmp > 0) return ceiling(r.right(), e);
		if (cmp < 0) {
			K tmp = ceiling(r.left(), e);
			return (tmp != null)? tmp : r.key;
		}
		return r.key;
	}

	@Override
	public K ceiling(K e) {
		return ceiling(getRoot(), e);
	}

}

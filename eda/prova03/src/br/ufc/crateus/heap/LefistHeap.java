package br.ufc.crateus.heap;

import java.util.LinkedList;
import java.util.Queue;

public class LefistHeap<T extends Comparable<T>> {
	private class Node {
		Node rigth;
		Node left;
		T value;
		int dist;
	}

	private Node root = null;

	private Node union(Node h1, Node h2) {
		if (h1 == null)
			return h2;
		if (h2 == null)
			return h1;
		if (h1.value.compareTo(h2.value) < 0) {
			Node tmp = h2;
			h2 = h1;
			h1 = tmp;
		}
		if (h1.left == null) {
			h1.left = h2;
		} else {
			h1.rigth = union(h1.rigth, h2);
			if (h1.left.dist < h1.rigth.dist) {
				Node tmp = h1.left;
				h1.left = h1.rigth;
				h1.rigth = tmp;
			}
			h1.dist = h1.rigth.dist + 1;
		}
		return h1;
	}

	public void insert(T value) {
		Node r = new Node();
		r.value = value;
		r.dist = 1;
		r.rigth = null;
		r.left = null;
		root = union(root, r);
	}

	public T extract() {
		T value = root.value;
		root = union(root.rigth, root.left);
		return value;
	}

	public T lookup() {
		return root.value;
	}

	public boolean isEmpty() {
		return root == null;
	}

	private void collect(Queue<T> q, Node r) {
		if (r != null) {
			collect(q, r.left);
			q.add(r.value);
			collect(q, r.rigth);
		}
	}

	public Iterable<T> inOrder() {
		Queue<T> q = new LinkedList<T>();
		collect(q, root);
		return q;
	}
}

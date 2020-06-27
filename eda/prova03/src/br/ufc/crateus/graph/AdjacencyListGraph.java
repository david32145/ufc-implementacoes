package br.ufc.crateus.graph;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;

public class AdjacencyListGraph<T> implements Graph<T> {
	private static class Node {
		int index;
		Node next;

		Node(int index, Node next) {
			this.index = index;
			this.next = next;
		}
	}

	private Node[] edges;
	private ArrayMap<T> symbolTable;
	private int nV;
	private int nE;

	public AdjacencyListGraph(int nV) {
		this.edges = new Node[nV];
		this.nV = nV;
		this.nE = 0;
		this.symbolTable = new ArrayMap<T>(nV);
	}

	@Override
	public int countVertices() {
		return nV;
	}

	@Override
	public int countEdges() {
		return nE;
	}

	@Override
	public int index(T v) {
		return symbolTable.index(v);
	}

	@Override
	public T label(int index) {
		return symbolTable.get(index);
	}

	@Override
	public boolean contains(T v) {
		return symbolTable.contains(v);
	}

	@Override
	public void addEdge(T v1, T v2) {
		int index1 = symbolTable.put(v1);
		int index2 = symbolTable.put(v2);
		if (index1 == edges.length || index2 == edges.length)
			resize(2 * edges.length);
		edges[index1] = new Node(index2, edges[index1]);
		edges[index2] = new Node(index1, edges[index2]);
		nV = symbolTable.size();
		this.nE++;
	}

	private void resize(int newLength) {
		Node[] newEdges = new Node[newLength];
		for (int i = 0; i < edges.length; i++)
			newEdges[i] = edges[i];
		this.edges = newEdges;
	}

	private Collection<T> adj(T v) {
		int index = symbolTable.index(v);
		Queue<T> queue = new LinkedList<T>();
		if (index == -1)
			throw new RuntimeException("O vertice " + v.toString() + " n�o pertence ao grafo!!!");
		for (Node r = edges[index]; r != null; r = r.next) {
			queue.add(symbolTable.get(r.index));
		}
		return queue;
	}

	@Override
	public Iterable<Integer> indexes() {
		return symbolTable.keys();
	}

	@Override
	public Iterable<T> adjacents(T v) {
		return adj(v);
	}

	@Override
	public int degree(T v) {
		return adj(v).size();
	}

}

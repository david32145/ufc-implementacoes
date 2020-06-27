package br.ufc.crateus.graph;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;

public class AdjacencyMatrixGraph<T> implements Graph<T> {

	private boolean[][] edges;
	private int nV;
	private int nE;
	private ArrayMap<T> symbolTable;

	public AdjacencyMatrixGraph(int nV) {
		this.edges = new boolean[nV][nV];
		this.nV = nV;
		this.nE = 0;
		this.symbolTable = new ArrayMap<T>();
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
			resize(edges.length * 2);
		this.edges[index1][index2] = true;
		this.edges[index2][index1] = true;
		nV = symbolTable.size();
		this.nE++;
	}

	private void resize(int newLength) {
		boolean[][] newEdges = new boolean[newLength][newLength];
		for (int i = 0; i < edges.length; i++)
			newEdges[i] = edges[i];
		this.edges = newEdges;
	}

	private Collection<T> adj(T v) {
		int index = symbolTable.index(v);
		if (index == -1)
			throw new RuntimeException("O vertice \"" + v.toString() + "\" não pertence ao grafo!!!");
		Queue<T> queue = new LinkedList<T>();
		boolean adj[] = this.edges[index];
		for (int i = 0; i < adj.length; i++) {
			if (adj[i])
				queue.add(symbolTable.get(i));
		}
		return queue;
	}

	@Override
	public Iterable<T> adjacents(T v) {
		return adj(v);
	}

	@Override
	public int degree(T v) {
		return adj(v).size();
	}

	@Override
	public Iterable<Integer> indexes() {
		return symbolTable.keys();
	}

}

package br.ufc.crateus.graph;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class GraphUtils {
	public static <T> Queue<T> shortestPath(Graph<T> g, T v1, T v2) {
		int length = g.countVertices();
		boolean[] marked = new boolean[length + 1];
		int[] edgeTo = new int[length];
		GraphUtils.breadthFirstSearch(g, v1,v2, marked, edgeTo);
		int i = g.index(v1);
		int j = g.index(v2);
		if (!marked[length])
			return null;
		Queue<T> queue = new LinkedList<T>();
		while (j != i) {
			queue.add(g.label(j));
			j = edgeTo[j];
		}
		queue.add(g.label(j));
		return queue;
	}

	private static <T> void breadthFirstSearch(Graph<T> g, T v1,T v2, boolean[] marked, int[] edgeTo) {
		Queue<T> queue = new LinkedList<T>();
		queue.add(v1);
		while (!queue.isEmpty()) {
			T pop = queue.remove();
			int i = g.index(pop);
			if (pop.equals(v2)) {
				marked[g.countVertices()] = true;
				return;
			}
			for (T adj : g.adjacents(pop)) {
				int j = g.index(adj);
				if (!marked[j]) {
					queue.add(adj);
					marked[j] = true;
					edgeTo[j] = i;
				}
			}
		}
	}
	
	public static Graph<String> readFromFile(InputStream in, String sep){
		Graph<String> g = new AdjacencyListGraph<String>(198990);
		Scanner scn = new Scanner(in);
		while(scn.hasNextLine()) {
			String line = scn.nextLine();
			String [] vert = line.split(sep);
			for(int i = 1; i < vert.length; i++) {
				g.addEdge(vert[0], vert[i]);
			}
		}
		scn.close();
		return g;
	}
	
	private static <T> void deepFirstSearch(Graph<T> g, T v, boolean[] marked) {
		int i = g.index(v);
		marked[i] = true;
		for(T adj : g.adjacents(v)) {
			int j = g.index(adj);
			if(!marked[j]) {
				deepFirstSearch(g, adj, marked);
			}
		}
	}
	
	public static <T> int countConvexComponents(Graph<T> g) {
		boolean [] marked = new boolean[g.countVertices()];
		int c = 0;
		System.out.println(marked.length);
		for(int i = 0; i < marked.length; i++) {
			System.out.println(i+1);
			if(!marked[i]) {
				c++;
				deepFirstSearch(g, g.label(i), marked);
			}
		}
		return c;
	}
}

package br.ufc.crateus.test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import br.ufc.crateus.graph.AdjacencyListGraph;
import br.ufc.crateus.graph.AdjacencyMatrixGraph;
import br.ufc.crateus.graph.Graph;
import br.ufc.crateus.graph.GraphUtils;

@SuppressWarnings("unused")
public class TestGraph {
//387
	public static void main(String[] args) throws IOException {
		InputStream in = new FileInputStream("movies (cópia).txt");
		Graph<String> g = GraphUtils.readFromFile(in, ",");
		System.out.println(GraphUtils.countConvexComponents(g));
	}

}

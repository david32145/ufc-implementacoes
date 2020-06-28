package br.ufc.crateus.eda.test;

import br.ufc.crateus.eda.st.BinarySearchTreeMap;

public class TestBST {

	public static void main(String[] args) {
		BinarySearchTreeMap<Integer, String> tree = null;
		tree = new BinarySearchTreeMap<Integer, String>();
		
		tree.put(30, "Valor 30");
		tree.put(20, "Valor 30");
		tree.put(50, "Valor 30");
		tree.put(10, "Valor 30");
		tree.put(22, "Valor 30");
		tree.put(40, "Valor 30");
		tree.put(80, "Valor 30");
		tree.put(5, "Valor 30");
		tree.put(7, "Valor 30");
		tree.put(21, "Valor 30");
		tree.put(25, "Valor 30");
		
		System.out.println(tree.heightByKey(22));
	}

}

package br.ufc.crateus.eda.test;

import java.util.Random;

import br.ufc.crateus.eda.st.AVLTreeMap;

public class TestAVL {

	public static void main(String[] args) {
		AVLTreeMap<Integer, String> tree = null;
		tree = new AVLTreeMap<Integer, String>();
		
		Integer[] n = rand(1023, 20);
		for(Integer i : n){
			tree.put(i, "Valor" + i);
		}
		
		tree.put(1024, "Valor 1024");
		
		tree.remove(1024);
		
		System.out.println(tree.get(1024));
		
		System.out.println(tree.height());
	}
	
	private static void printArr(Integer[] e)
	{
		for (int i : e)
			System.out.println(i);
	}
	
	private static Integer[] rand(int n, int start)
	{
		Integer[] nums = new Integer[n];
		
		for(int i = 0; i<n; i++)
			nums[i] = i + 1;
		
		return nums;
	}

}

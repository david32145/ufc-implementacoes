package br.ufc.crateus.test;

import br.ufc.crateus.heap.LefistHeap;

public class TestLefistHeap {

	public static void main(String[] args) {
		LefistHeap<Integer> heap = new LefistHeap<Integer>();
		int[] nums = new int[] { -1, 28, -1, 15, 0, -3, -3, 50, 7, 8, 2, 50, 7 };
		for (int i : nums)
			heap.insert(i);

		while (!heap.isEmpty()) {
			System.out.println(heap.extract());
		}

	}

}

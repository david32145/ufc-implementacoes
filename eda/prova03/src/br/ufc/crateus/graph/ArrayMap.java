package br.ufc.crateus.graph;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;


public class ArrayMap<T> {
	private T[] values;
	private Map<T, Integer> mapa;
	
	private int size;

	public ArrayMap() {
		this(50);
	}

	@SuppressWarnings("unchecked")
	public ArrayMap(int length) {
		this.size = 0;
		this.mapa = new TreeMap<>();
		this.values = (T[]) new Object[length];
	}

	public int put(T value) {
		Integer i = mapa.get(value);
		if(i != null) return i;
		mapa.put(value, size++);
		values[size-1] = value;
		return size-1;
		
	}
	
	public int size() {
		return size;
	}

	public int index(T value) {
		return mapa.getOrDefault(value, -1);
	}

	public T get(int i) {
		return i < size ? values[i] : null;
	}

	public boolean contains(T value) {
		return index(value) != -1;
	}
	
	public Iterable<Integer> keys(){
		Queue<Integer> q = new LinkedList<Integer>();
		for(int i = 0; i < size; i++) q.add(i);
		return q;
	}
}

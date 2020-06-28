package br.ufc.crateus.btree;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import br.ufc.crateus.btree.st.Tuple;

public class BinarySearchST<K extends Comparable<K>, V> implements Iterable<Tuple<K, V>>{

	private K[] keys;
	private V[] values;
	
	private int size; 
	
	private class BinarySearchIterator implements Iterator<Tuple<K, V>>{
		private K[] keys;
		private V[] values;
		private int next;
		private int size;
		
		public BinarySearchIterator(K[] keys, V[] values, int size) {
			this.keys = keys;
			this.values = values;
			this.next = 0;
			this.size = size;
		}
		@Override
		public boolean hasNext() {
			return this.next != this.size;
		}
		@Override
		public Tuple<K, V> next() {
			int i = this.next++;
			return new Tuple<K, V>(keys[i], values[i]);
		}
		
		
		
	}
	
	@SuppressWarnings("unchecked")
	public BinarySearchST(int lenght) {
		this.keys 	= (K[]) new Comparable[lenght];
		this.values = (V[]) new Object[lenght];
		this.size = 0;
	}
	
	public BinarySearchST<K, V> split() {
		BinarySearchST<K, V> st = new BinarySearchST<>(keys.length);
		int j = (size/2);
		for(int i = 0; i < size; i++) {
			st.keys[i] = keys[j + i];
			st.values[i] = values[j + i];
			keys[j + i] = null;
			values[j + i] = null;
			size--;
			st.size++;
		}
		return st;
	}

	public void put(K key, V value) {
		int i = floorByIndex(key);
		if(i != -1 && keys[i].equals(key)) {
			values[i] = value;
			return;
		}
		shiftRight(i + 1);
		keys[i+1] = key;
		values[i+1] = value;
	}

	public V remove(K key) {
		int i = floorByIndex(key);
		if(i != -1 && keys[i].equals(key)) {
			V value = values[i];
			shiftLeft(i);
			return value;
		}
		return null;
	}

	public V get(K key) {
		int i = floorByIndex(key);
		return i == -1 ? null : (keys[i].equals(key) ? values[i] : null);
	}
	
	public int floorByIndex(K key) {
		int floor = -1;
		for( int i = 0; i < size; i++) {
			if(keys[i].compareTo(key) > 0) break;
			floor = i;
		}
		return floor;
	}
	
	public K floor(K key) {
		int i = floorByIndex(key);
		return i == -1 ? null : keys[i];
	}
	
	public int ceilingByIndex(K key) {
		int ceiling = -1;
		for( int i = 0; i < size; i++) {
			if(keys[i].compareTo(key) < 0) continue;
			ceiling = i;
			break;
		}
		return ceiling;
	}
	
	public K ceiling(K key) {
		int i = ceilingByIndex(key);
		return i == -1 ? null : keys[i];
	}

	public boolean contains(K key) {
		return get(key) != null;
	}
	
	private void shiftLeft(int i) {
		for(; i < size; i++){
			keys[i] = keys[i+1];
			values[i] = values[i+1];
		}
		keys[i] = null;
		values[i] = null;
		size--;
	}
	
	private void shiftRight(int i) {
		for(int j = size - 1; j >= i; j--) {
			keys[j+1] = keys[j];
			values[j+1] = values[j];
		}
		size++;
	}

	public Iterable<K> keys() {
		Queue<K> keys = new LinkedList<K>();
		for(int i = 0; i < size; i++) keys.add(this.keys[i]);
		return keys;
	}
	
	public Iterable<V> values() {
		Queue<V> values = new LinkedList<V>();
		for(int i = 0; i < size; i++) values.add(this.values[i]);
		return values;
	}
	
	@Override
	public Iterator<Tuple<K, V>> iterator() {
		return new BinarySearchIterator(keys, values, size);
	}
	public int size() {
		return size;
	}
	
	public K min() {
		if(size > 0)
			return keys[0];
		return null;
	}

	public K max() {
		if(size > 0)
			return keys[size-1];
		return null;
	}
	
	public Tuple<K, V> minAll() {
		if(size > 0)
			return new Tuple<>(keys[0], values[0]);
		return null;
	}

	public Tuple<K,V> maxAll() {
		if(size > 0)
			return new Tuple<>(keys[size-1], values[size-1]);
		return null;
	}
	
	public Tuple<K, V> removeMin() {
		if(size > 0) {
			Tuple<K, V> t =new Tuple<>(keys[0], values[0]);
			shiftLeft(0);
			return t;
		}
		return null;
	}
	
	public Tuple<K, V> removeMax(){
		if(size > 0)
			return new Tuple<K, V>(keys[size-1], values[--size]);
		return null;
	}
	
	//Chave antes da chave key
	public Tuple<K, V> before(K key) {
		int i = floorByIndex(key);
		if(i == 0) return null;
		return new Tuple<K, V>(keys[i - 1], values[i - 1]);
	}
	//Chave depois da chave key
	public Tuple<K, V> after(K key){
		int i = floorByIndex(key);
		if(i + 1 == size) return null;
		return new Tuple<K, V>(keys[i + 1], values[i + 1]);
	}
}

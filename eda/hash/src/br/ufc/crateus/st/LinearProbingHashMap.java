package br.ufc.crateus.st;

@SuppressWarnings("unchecked")
public class LinearProbingHashMap<K, V> implements Map<K, V> {

	private K[] keys;
	private V[] values;
	private int size;
	public LinearProbingHashMap() {
		this(397);
	}
	
	public LinearProbingHashMap(int size) {
		this.keys = (K[]) new Object[size];
		this.values = (V[]) new Object[size];
		this.size = 0;
	}
	
	private int hash(K key) {
		return (key.hashCode() & 0x7fffffff) % keys.length;
	}
	
	private void resize(int newSize) {
		LinearProbingHashMap<K, V> tmp = new LinearProbingHashMap<K, V>(newSize);
		for(int i = 0; i < keys.length; i++) {
			if(values[i] == null) continue;
			tmp.put(keys[i], values[i]);
		}
		keys = tmp.keys;
		values = tmp.values;
		size = tmp.size;
	}
	
	@Override
	public void put(K key, V value) {
		int i = hash(key);
		while(keys[i] != null && !keys[i].equals(key))  i = (i+1) % keys.length;
		if(values[i] == null) size++;
		keys[i] = key;
		values[i] = value;
		if((double)(size/keys.length) >= 0.5) resize(2*keys.length);
	}

	@Override
	public V get(K key) {
		int i = hash(key);
		while(keys[i] != null && !keys[i].equals(key))  i = (i+1) % keys.length;
		return values[i];
	}

	@Override
	public void remove(K key) {
		int i = hash(key);
		while(keys[i] != null && !keys[i].equals(key))  i = (i+1) % keys.length;
		if(values[i] != null) size--;
		values[i] = null;
		if((double)(size/keys.length) <= 0.125) resize(keys.length/2);
	}

	@Override
	public Iterable<K> keys() {
		return null;
	}

	@Override
	public boolean contains(K key) {
		return false;
	}
}

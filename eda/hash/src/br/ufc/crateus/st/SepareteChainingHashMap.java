package br.ufc.crateus.st;

@SuppressWarnings("unchecked")
public class SepareteChainingHashMap<K, V> implements Map<K, V> {
	protected static class Node {
		Object key;
		Object value;
		Node next;

		Node(Object key, Object value, Node next) {
			this.key = key;
			this.value = value;
			this.next = next;
		}

	}

	protected Node[] table;

	private int size = 0;

	public SepareteChainingHashMap(int len) {
		this.table = new Node[len];
	}

	public SepareteChainingHashMap() {
		this(97);
	}

	private int hash(K key) {
		return (key.hashCode() & 0x7fffffff) % table.length;
	}

	private Node getNode(K key) {
		int i = hash(key);
		for (Node n = table[i]; n != null; n = n.next)
			if (key.equals(n.key))
				return n;
		return null;
	}

	@Override
	public void put(K key, V value) {
		Node n = getNode(key);
		if (n == null) {
			int i = hash(key);
			table[i] = new Node(key, value, table[i]);
			size++;
			if (this.size / table.length == 8) {
				resize(table.length * 2);
			}
		} else {
			n.value = value;
		}
	}

	private void resize(int newLength) {
		SepareteChainingHashMap<K, V> tmp = new SepareteChainingHashMap<K, V>(newLength);
		for (int i = 0; i < table.length; i++) {
			for (Node n = table[i]; n != null; n = n.next)
				tmp.put((K) n.key, (V) n.value);
		}
		table = tmp.table;
	}
	
	@Override
	public V get(K key) {
		Node n = getNode(key);
		return n == null ? null : (V) n.value;
	}

	@Override
	public void remove(K key) {
		int i = hash(key);
		Node head = new Node(null, null, table[i]);
		for (Node n = head; n.next != null; n = n.next) {
			if (key.equals(n.next.key)) {
				n.next = n.next.next;
				size--;
				if (this.size / table.length == 4)
					resize(table.length / 2);
				break;
			}
		}
		table[i] = head.next;

	}

	@Override
	public Iterable<K> keys() {
		return null;
	}
	
	@Override
	public boolean contains(K key) {
		return get(key) != null;
	}
	
	public int size() {
		return this.size;
	}

	/*
	 * private LinkedList<Entry>[] arrayHash; private int tam;
	 * 
	 * private class Entry{ K key; V value; public Entry(K key, V value) { this.key
	 * = key; this.value = value; }
	 * 
	 * @Override public boolean equals(Object obj) { if(obj == null) return false;
	 * if(obj.getClass() != getClass()) return false; Entry h = (Entry)obj; return
	 * h.key == key; } }
	 * 
	 * public SepareteChainingHashMap(int tam) { this.arrayHash = new
	 * LinkedList[tam]; this.tam = tam; }
	 * 
	 * @Override public void put(K key, V value) { int hash =
	 * (Math.abs(key.hashCode()))%tam; if(this.arrayHash[hash] == null)
	 * this.arrayHash[hash] = new LinkedList<Entry>(); Entry x = getAux(key); if(x
	 * == null) this.arrayHash[hash].add(new Entry(key, value)); else x.value =
	 * value; }
	 * 
	 * private Entry getAux(K key) { int hash = (Math.abs(key.hashCode()))%tam;
	 * if(this.arrayHash[hash] == null) return null; for(Entry aux :
	 * this.arrayHash[hash]) { if(aux.key.equals(key)) return aux; } return null; }
	 * 
	 * @Override public V get(K key) { Entry x = getAux(key); return x == null ?
	 * null : x.value; }
	 * 
	 * @Override public void remove(K key) { int hash =
	 * (Math.abs(key.hashCode()))%tam; if(this.arrayHash[hash] != null)
	 * this.arrayHash[hash].remove(new Entry(key, null)); }
	 */

}

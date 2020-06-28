package br.ufc.crateus.st;

import java.util.LinkedList;
import java.util.Queue;

public class RWayTries<V> implements StringST<V>{
	private static final int R = 256;

	private static class Node {
		Object value;
		Node[] next = new Node[R];
	}

	private Node root = new Node();

	@SuppressWarnings("unchecked")
	private V getValue(Node r) {
		if (r == null)
			return null;
		return (V) r.value;
	}

	private Node put(Node r, String key, int i, V value) {
		if (r == null)
			r = new Node();
		if (key.length() == i) {
			r.value = value;
		} else {
			char ch = key.charAt(i);
			r.next[ch] = put(r.next[ch], key, i + 1, value);
		}
		return r;
	}
	
	@Override
	public void put(String key, V value) {
		root = put(root, key, 0, value);
	}

	private Node getNode(Node r, String key, int i) {
		if (r == null)
			return null;
		if (i == key.length()) {
			return r;
		} else {
			char ch = key.charAt(i);
			return getNode(r.next[ch], key, i + 1);
		}
	}

	@Override
	public V get(String key) {
		Node r = getNode(root, key, 0);
		return r == null ? null : getValue(r);
	}

	private void collect(Queue<String> keys, Node r, String str) {
		if (r != null)
		{
			if(r.value != null) keys.add(str);
			for(char i = 0; i < r.next.length; i++)
				collect(keys, r.next[i], str + i);
		}
	}
	
	@Override
	public Iterable<String> keysWithPrefix(String prefix){
		Node r = getNode(root, prefix, 0);
		Queue<String> keys = new LinkedList<String>();
		collect(keys, r, prefix);
		return keys;
	}
	
	private int search(Node r, String query, int d, int lenght) {
		if(r == null) return lenght;
		if(r.value != null) lenght = d;
		if(query.length() == d) return lenght;
		char ch = query.charAt(d);
		
		
		return search(r.next[ch], query, d+1, lenght);
	}
	
	@Override
	public String longestPrefixOf(String str){
		int lenght = search(root, str, 0, 0);
		
		return str.substring(0, lenght);
		
	}
	
	@Override
	public Iterable<String> keys() {
		Queue<String> keys = new LinkedList<String>();
		collect(keys, root, "");
		return keys;
	}
	
	@Override
	public boolean contains(String key) {
		return get(key) != null;
	}
	
	@Override
	public Iterable<String> keysThatMatch(String str) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void remove(String key) {
		// TODO Auto-generated method stub
		
	}
	
	
}

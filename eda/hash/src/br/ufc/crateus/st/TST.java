package br.ufc.crateus.st;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;

public class TST<V> implements StringST<V> {
	private class Node {
		V value;
		char ch;
		Node left;
		Node middle;
		Node right;

		Node(char ch) {
			this.ch = ch;
		}
	}

	private Node root = null;

	private Node put(Node r, String key, V value, int h) {
		char ch = key.charAt(h);
		if (r == null)
			r = new Node(ch);
		if (ch < r.ch)
			r.left = put(r.left, key, value, h);
		else if (ch > r.ch)
			r.right = put(r.right, key, value, h);
		else {
			if (h == key.length() - 1) {
				r.value = value;
				return r;
			}
			r.middle = put(r.middle, key, value, h + 1);
		}
		return r;
	}

	private Node get(Node r, String key, int h) {
		if (r == null)
			return r;
		char ch = key.charAt(h);
		if (ch < r.ch)
			return get(r.left, key, h);
		else if (ch > r.ch)
			return get(r.right, key, h);
		else {
			if (h == key.length() - 1)
				return r;
			else
				return get(r.middle, key, h + 1);
		}
	}

	@Override
	public void put(String key, V value) {
		root = put(root, key, value, 0);
	}

	public V get(String key) {
		Node r = get(root, key, 0);
		return r == null ? null : r.value;
	}

	private void collect(Queue<String> keys, Node r, String s) {
		if (r != null) {
			collect(keys, r.left, s);
			if (r.value != null)
				keys.add(s + r.ch);
			collect(keys, r.middle, s + r.ch);
			collect(keys, r.right, s);
		}
	}

	@Override
	public Iterable<String> keys() {
		Queue<String> keys = new LinkedList<String>();
		collect(keys, root, "");
		return keys;
	}

	@Override
	public Iterable<String> keysWithPrefix(String prefix) {
		Node r = get(root, prefix, 0);
		Queue<String> keys = new LinkedList<String>();
		if (r != null)
			collect(keys, r.middle, prefix);
		return keys;
	}

	private int search(Node r, String s, int h, int lenght) {
		if (r == null)
			return lenght;
		if (s.length() == h)
			return lenght;
		char ch = s.charAt(h);
		if (ch < r.ch)
			return search(r.left, s, h, lenght);
		else if (ch > r.ch)
			return search(r.right, s, h, lenght);
		else {
			if (r.value != null)
				lenght = h;
			return search(r.middle, s, h + 1, lenght);
		}

	}

	@Override
	public String longestPrefixOf(String str) {
		int lenght = search(root, str, 0, -1);
		if (lenght == -1)
			return "";
		return str.substring(0, ++lenght);
	}

	@Override
	public boolean contains(String key) {
		return get(key) != null;
	}

	private void searchKeysThatMatch(Node r, String str, Queue<String> keys, int h, String acc) {
		if (r != null && str.length() != h) {
			char ch = str.charAt(h);
			if (ch == r.ch || ch == '.') {
				String accM = acc + r.ch;
				searchKeysThatMatch(r.left, str, keys, h, acc);
				if (r.value != null && str.length() == h + 1)
					keys.add(accM);
				searchKeysThatMatch(r.middle, str, keys, h + 1, accM);
				searchKeysThatMatch(r.right, str, keys, h, acc);
			} else if (ch > r.ch) {
				searchKeysThatMatch(r.right, str, keys, h, acc);
			} else {
				searchKeysThatMatch(r.left, str, keys, h, acc);
			}
		}
	}

	@Override
	public Iterable<String> keysThatMatch(String str) {
		Queue<String> keys = new LinkedList<String>();
		searchKeysThatMatch(root, str, keys, 0, "");
		return keys;
	}

	public Iterable<String> searchMovie(String query) {
		Node r = get(root, query, 0);
		Queue<String> keysPrefixOf = new LinkedList<String>();
		if (r != null)
			collect(keysPrefixOf, r.middle, query);

		String logestPrefixOf = longestPrefixOf(query);

		Queue<String> keysThatMatchOf = new LinkedList<String>();
		searchKeysThatMatch(root, query, keysThatMatchOf, 0, "");

		Set<String> keysSearchMovie = new TreeSet<>(keysPrefixOf);
		keysSearchMovie.addAll(keysThatMatchOf);
		keysSearchMovie.add(logestPrefixOf);

		return keysSearchMovie;
	}

	private String searchCeiling(Node r, String str, int h, String acc) {
		if (r == null)
			return null;
		if (str.length() == h)
			return null;
		char ch = str.charAt(h);
		if (r.ch > ch) {
			String ceiLeft = searchCeiling(r.left, str, h, acc);
			String ceiMidlle = searchCeiling(r.middle, str, h+1, acc + r.ch);
		} else if (r.ch < ch) {
			String floorRight = searchCeiling(r.right, str, h, acc);
			if (floorRight != null)
				return floorRight;
			return searchDeepKey(r, acc);
		} else {
			String floorMidlle = searchCeiling(r.middle, str, h + 1, acc + r.ch);
			if (floorMidlle != null)
				return floorMidlle;
			return  searchDeepKey(r, acc);
		}
		return null;
	}
	
	private String max(Node r, String acc) {
		if(r == null) return null;
		
		String maxRight = max(r.right, acc);
		if(maxRight != null) return maxRight;
		
		String maxMidlle = max(r.middle, acc + r.ch);
		
		if(maxMidlle != null) return maxMidlle;
		
		if(r.value != null) return acc + r.ch;
		
		return null;
	}
	
	private String min(Node r, String acc) {
		if(r == null) return null;
		
		String minLeft = min(r.left, acc);
		if(minLeft != null) return minLeft;
		
		String maxMidlle = min(r.middle, acc + r.ch);
		
		if(maxMidlle != null) return maxMidlle;
		
		if(r.value != null) return acc + r.ch;
		
		return null;
	}
	
	public String max() {
		return max(root, "");
	}
	
	public String min() {
		return min(root, "");
	}
	
	private String searchFloor(Node r, String str, int h, String acc) {
		if (r == null)
			return null;
		if (str.length() == h)
			return null;
		char ch = str.charAt(h);
		if (r.ch > ch) {
			String floorRight = searchFloor(r.left, str, h, acc);
			if (floorRight != null)
				return floorRight;
			return searchDeepKey(r, acc);
		} else if (r.ch < ch) {
			return searchFloor(r.right, str, h, acc);
		} else {
			String floorMidlle = searchFloor(r.middle, str, h + 1, acc + r.ch);
			if (floorMidlle != null)
				return floorMidlle;
			return  searchDeepKey(r, acc);
		}
	}
	
	private String searchDeepKey(Node r, String acc) {
		if(r == null) return null;
		if(r.value != null) return acc + r.ch;
		return searchDeepKey(r.middle, acc+r.ch);
	}

	public String floor(String str) {
		return searchFloor(root, str, 0, "");
	}
	
	public String ceiling(String str) {
		return searchCeiling(root, str, 0, "");
	}

	@Override
	public void remove(String key) {
		// TODO Auto-generated method stub

	}
}

package br.ufc.crateus.st;

public interface StringST<V> extends Map<String, V> {
	Iterable<String> keysWithPrefix(String prefix);
	
	Iterable<String> keysThatMatch(String str);
	
	String longestPrefixOf(String str);
}

package br.ufc.crateus.btree.st;

public class Tuple<E, T> {
	private E first;
	private T second;
	public Tuple(E first, T second) {
		this.first = first;
		this.second = second;
	}
	
	
	public E first() {return first;}
	public T second() {return second;}
	
}

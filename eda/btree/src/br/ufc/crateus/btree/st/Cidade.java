package br.ufc.crateus.btree.st;

public class Cidade {
	private String nome;
	private int pop;
	
	public Cidade(String nome, int pop) {
		this.nome = nome;
		this.pop = pop;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getPop() {
		return pop;
	}

	public void setPop(int pop) {
		this.pop = pop;
	}
	
	@Override
	public String toString() {
		return nome + " - " + pop + " pessoas";
	}
}

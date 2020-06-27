package br.ufc.carteus.models;

public abstract class Pessoa {
	private String nome;
	public Pessoa(String nome) {
		this.nome = nome;
	}
	abstract boolean validaDocumento();
	abstract int calculaIdade();
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@Override
	public String toString() {
		return "Nome: " + nome;
	}
}

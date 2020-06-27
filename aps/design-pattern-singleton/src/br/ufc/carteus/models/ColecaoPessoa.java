package br.ufc.carteus.models;

public class ColecaoPessoa implements Colecao{
	private Pessoa[] dados = new Pessoa[100];
	private int posicaoCorrente = 0;
	
	private static String version = "1.0";
	private static ColecaoPessoa instace = new ColecaoPessoa();
	
	private ColecaoPessoa() {	}
	
	public static ColecaoPessoa getInstace() {
		return instace;
	}
	
	@Override
	public boolean inserir(Pessoa p) {
		if(posicaoCorrente < dados.length ) {
			dados[posicaoCorrente++] = p;
			return true;
		}
		return false;
	}
	@Override
	public boolean remover() {
		if(dados.length > 0) {
			dados[--posicaoCorrente] = null;
			return true;
		}
		return false;
	}
	@Override
	public boolean remover(int indice) {
		if(indice < posicaoCorrente && indice >= 0) {
			dados[indice] = dados[posicaoCorrente - 1];
			dados[--posicaoCorrente] = null;
			return true;
		}
		return false;
	}
	@Override
	public void atualizar(int indice, Pessoa p) {
		if(indice < posicaoCorrente && indice >= 0) 
			dados[indice] = p;
	}
	@Override
	public boolean pesquisar(Pessoa p) {
		for(int i = 0; i <  posicaoCorrente; i++) {
			if(dados[i].equals(p))
				return true;
		}
		return false;
	}
	@Override
	public boolean colecaoEstaVazia() {
		return posicaoCorrente == 0;
	}
	@Override
	public void imprimirDadosColecao() {
		for(int i = 0; i <  posicaoCorrente; i++) {
			System.out.println(dados[i].toString());			
		}
		
	}
	@Override
	public Pessoa retornarObjeto(int indice) {
		if(indice < posicaoCorrente && indice >= 0)
			return dados[indice];
		return null;
	}
	
	
}

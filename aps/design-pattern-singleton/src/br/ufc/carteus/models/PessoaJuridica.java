package br.ufc.carteus.models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class PessoaJuridica extends Pessoa{
	private String cnpj;
	private LocalDate dataCriacao;
	
	public PessoaJuridica(String nome, String cnpj, LocalDate dataCriacao) {
		super(nome);
		this.cnpj = cnpj;
		this.dataCriacao = dataCriacao;
	}
	
	@Override
	boolean validaDocumento() {
		return cnpj.length() == 18;
	}
	@Override
	int calculaIdade() {
		return (int) ChronoUnit.YEARS.between(dataCriacao, LocalDate.now());
	}
	
	@Override
	public boolean equals(Object arg0) {
		if(arg0 == null) return false;
		if(arg0.getClass() != this.getClass()) return false;
		return cnpj.equals(arg0);
	}
	
	@Override
	public String toString() {
		return super.toString() + ", Cnpj: " + cnpj + ", Data Criacao: " + dataCriacao.format(DateTimeFormatter.BASIC_ISO_DATE);
	}
	
}

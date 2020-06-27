package br.ufc.carteus.models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class PessoaFisica extends Pessoa {
	private String cpf;
	private LocalDate dataNascimento;
	
	public PessoaFisica(String nome, String cpf, LocalDate dataNascimento) {
		super(nome);
		this.cpf = cpf;
		this.dataNascimento = dataNascimento;
	}
	@Override
	boolean validaDocumento() {
		return cpf.length() == 14;
	}
	@Override
	int calculaIdade() {
		return (int) ChronoUnit.YEARS.between(dataNascimento, LocalDate.now());
	}
	
	@Override
	public boolean equals(Object arg0) {
		if(arg0 == null) return false;
		if(arg0.getClass() != this.getClass()) return false;
		return cpf.equals(arg0);
	}
	
	@Override
	public String toString() {
		return super.toString() + ", Cnpj: " + cpf + ", Idade: " + calculaIdade();
	}
	
	
}

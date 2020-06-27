package br.ufc.carteus.exec;

import java.time.LocalDate;

import br.ufc.carteus.models.ColecaoPessoa;
import br.ufc.carteus.models.PessoaFisica;
import br.ufc.carteus.models.PessoaJuridica;

public class App {

	public static void main(String[] args) {
		ColecaoPessoa cp = ColecaoPessoa.getInstace();
		
		cp.inserir(new PessoaFisica("Josefa", "000.000.000-00", LocalDate.of(1990, 10, 1)));
		cp.inserir(new PessoaJuridica("Tio do Pastel", "000.000.000-00", LocalDate.of(1990, 10, 1)));
		cp.inserir(new PessoaFisica("Afonso", "000.000.000-00", LocalDate.of(1990, 10, 1)));
		cp.inserir(new PessoaJuridica("Pradaria Sao Vicente", "000.000.000-00", LocalDate.of(1990, 10, 1)));
		cp.inserir(new PessoaFisica("Amarildo", "000.000.000-00", LocalDate.of(1990, 10, 1)));
		cp.inserir(new PessoaJuridica("Mercada Mao de Onca", "000.000.000-00", LocalDate.of(1990, 10, 1)));
		cp.inserir(new PessoaFisica("Injilda", "000.000.000-00", LocalDate.of(1990, 10, 1)));
		cp.imprimirDadosColecao();
		
		System.out.println();
		
		ColecaoPessoa cp2 = ColecaoPessoa.getInstace();
		System.out.println(cp2.retornarObjeto(3));
	}

}

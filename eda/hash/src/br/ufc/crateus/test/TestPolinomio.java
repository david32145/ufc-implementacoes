package br.ufc.crateus.test;

import br.ufc.crateus.st.q2.Polinomio;

public class TestPolinomio {

	public static void main(String[] args) {
		Polinomio p = new Polinomio();
		Polinomio m = new Polinomio();
		p.add(3, 1);
		m.add(5, 2).add(3, 1).add(1, 0);
		System.out.println(p.multiply(m));
	}

}

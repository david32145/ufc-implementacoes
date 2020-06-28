package br.ufc.crateus.test;

import br.ufc.crateus.st.q3.MatchString;

public class TestMatchString {

	public static void main(String[] args) {
		MatchString ms = new MatchString("josedavidamarild");
		System.out.println(ms.substring("david"));
	}

}

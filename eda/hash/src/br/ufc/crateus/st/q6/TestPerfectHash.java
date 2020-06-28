package br.ufc.crateus.st.q6;

import java.util.Arrays;

public class TestPerfectHash {

	public static void main(String[] args) {
		char[] letters = new char[] { 'S', 'E', 'A', 'R', 'C', 'H', 'X', 'M', 'P', 'L' };
		Arrays.sort(letters);
		int M = 13;
		int a = 1;
		
		for(char c : letters)
			System.out.print(c + " ");
		
		System.out.println();
		
		for(int c : letters)
			System.out.print(c + " ");
		
		System.out.println();
		
		for(int c : letters)
			System.out.print((a*c)%M + " ");
	}

}

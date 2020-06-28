package br.ufc.crateus.test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.String;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;

import br.ufc.crateus.st.TST;

public class TestTST {

	public static void main(String[] args) throws IOException {
		TST<Integer> tst = new TST<Integer>();
		TreeSet<String> tree = new TreeSet<String>();
		int id = 1;
		for (String fruit : getFruits("frutas.txt")) {
			tst.put(fruit, id++);
			tree.add(fruit);
		}
		
		/*String str = "banm"; 
		
		System.out.println("Tree => Ceiling("+ str +"): " + tree.ceiling(str));
		System.out.println("TST => Ceiling("+ str +"): " + tst.ceiling(str));
		
		System.out.println();
		
		System.out.println("Tree => Floor("+ str +"): " + tree.floor(str));
		System.out.println("TST => Floor("+ str +"): " + tst.floor(str));*/
	}

	public static List<String> getFruits(String file) throws IOException {
		InputStream in = new FileInputStream(file);
		Scanner scn = new Scanner(in);
		List<String> fruits = new LinkedList<String>();
		while (scn.hasNextLine())
			fruits.add(scn.nextLine().toLowerCase());
		scn.close();
		return fruits;

	}

}

package br.ufc.crateus.btree.st;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Scanner;

public class CidadeReader implements Iterable<Tuple<Integer, Cidade>>{
	private Scanner scn;
	
	private class CidadeReaderIterator implements Iterator<Tuple<Integer, Cidade>>{
		private Scanner scn;
		public CidadeReaderIterator(Scanner scn) {
			this.scn = scn;
		}
		@Override
		public boolean hasNext() {
			return scn.hasNext();
		}
		@Override
		public Tuple<Integer, Cidade> next() {
			String line = scn.nextLine();
			String [] lines = line.split(",");
			int cod = Integer.parseInt(lines[0]);
			String nome = lines[1];
			int pop = Integer.parseInt(lines[2]);
			return new Tuple<Integer, Cidade>(cod, new Cidade(nome, pop));
		}
	}
	
	public CidadeReader(String fileName) throws IOException {
		InputStream in = new FileInputStream(fileName);
		this.scn = new Scanner(in);
	}

	@Override
	public Iterator<Tuple<Integer, Cidade>> iterator() {
		return new CidadeReaderIterator(scn);
	}
	
}

package br.ufc.crateus.btree.dtypes;

import java.io.IOException;
import java.io.RandomAccessFile;

import br.ufc.crateus.btree.st.Cidade;

public class CidadeDT extends DataType<Cidade> {
	private StringDT strDT = new StringDT(20);
	private IntegerDT intDT = new IntegerDT();
	@Override
	public void write(RandomAccessFile file, Cidade value) throws IOException {
		strDT.write(file, value.getNome());
		intDT.write(file, value.getPop());
		
	}

	@Override
	public Cidade read(RandomAccessFile file) throws IOException {
		String nome = strDT.read(file);
		int pop = intDT.read(file);
		return new Cidade(nome, pop);
	}

	@Override
	public Cidade defaultValue() {
		return new Cidade(strDT.defaultValue(), intDT.defaultValue());
	}

	@Override
	public int size() {
		return strDT.size() + intDT.size();
	}

}

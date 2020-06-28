package br.ufc.crateus.btree.dtypes;

import java.io.IOException;
import java.io.RandomAccessFile;

public class StringDT extends DataType<String> {
	
	private int size;
	
	public StringDT(int size) {
		this.size = size;
	}

	private static String normalize(String s, int size) {
		if(s.length() > size) return s.substring(0, size);
		else return  String.format("%1$" + size + "s", s);
	}
	
	@Override
	public void write(RandomAccessFile file, String value) throws IOException {
		file.write(normalize(value, size).getBytes("ISO-8859-1"));
	}

	@Override
	public String read(RandomAccessFile file) throws IOException {
		byte[] strBytes = new byte[size];
		file.read(strBytes);
		return new String(strBytes, "ISO-8859-1").trim();
	}

	@Override
	public String defaultValue() {
		return "";
	}

	@Override
	public int size() {
		return size;
	}

}

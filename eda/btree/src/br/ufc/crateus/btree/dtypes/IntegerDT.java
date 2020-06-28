package br.ufc.crateus.btree.dtypes;

import java.io.IOException;
import java.io.RandomAccessFile;

public class IntegerDT extends DataType<Integer> {

	@Override
	public void write(RandomAccessFile file, Integer value) throws IOException {
		file.writeInt(value);

	}

	@Override
	public Integer read(RandomAccessFile file) throws IOException {
		return file.readInt();
	}

	@Override
	public Integer defaultValue() {
		return 0;
	}

	@Override
	public int size() {
		return 4;
	}

}

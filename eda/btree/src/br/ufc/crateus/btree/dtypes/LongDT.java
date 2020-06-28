package br.ufc.crateus.btree.dtypes;

import java.io.IOException;
import java.io.RandomAccessFile;

public class LongDT extends DataType<Long> {

	@Override
	public void write(RandomAccessFile file, Long value) throws IOException {
		file.writeLong(value);
	}

	@Override
	public Long read(RandomAccessFile file) throws IOException {
		return file.readLong();
	}

	@Override
	public Long defaultValue() {
		return 0L;
	}

	@Override
	public int size() {
		return 8;
	}

}

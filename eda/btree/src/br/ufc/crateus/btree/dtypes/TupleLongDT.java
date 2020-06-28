package br.ufc.crateus.btree.dtypes;

import java.io.IOException;
import java.io.RandomAccessFile;

import br.ufc.crateus.btree.st.Tuple;

public class TupleLongDT extends DataType<Tuple<Long, Long>> {

	@Override
	public void write(RandomAccessFile file, Tuple<Long, Long> value) throws IOException {
		file.writeLong(value.first());
		file.writeLong(value.second());
	}

	@Override
	public Tuple<Long, Long> read(RandomAccessFile file) throws IOException {
		long first = file.readLong();
		long second = file.readLong();
		return new Tuple<Long, Long>(first, second);
	}

	@Override
	public Tuple<Long, Long> defaultValue() {
		return new Tuple<Long, Long>(0L, 0L);
	}

	@Override
	public int size() {
		return 16;
	}

}

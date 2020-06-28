package br.ufc.crateus.btree.dtypes;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public abstract class DataType<T> {
	
	public abstract void write(RandomAccessFile file, T value) throws IOException;
	
	public void writeAll(RandomAccessFile file, Iterable<T> values) throws IOException{
		for(T v : values)
			write(file, v);
	}
	
	public void writeAll(RandomAccessFile file, T[] values) throws IOException{
		for(T v : values)
			write(file, v);
	}
	
	public abstract T read(RandomAccessFile file) throws IOException;
	
	public List<T> readAll(RandomAccessFile file, long length) throws IOException{
		List<T> values = new ArrayList<T>();
		for(long i = 0; i < length; i++)
			values.add(read(file));
		return values;
	}
	
	public abstract T defaultValue();
	
	public abstract int size();
}

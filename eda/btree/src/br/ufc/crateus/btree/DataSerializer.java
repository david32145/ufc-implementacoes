package br.ufc.crateus.btree;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import br.ufc.crateus.btree.dtypes.DataType;

public class DataSerializer<V> {
	private DataType<V> valueDT;
	private File file;
	
	static final long FILE_SIZE_OFFSET = 0;
	
	private long fileSize;
	
	public DataSerializer(DataType<V> valueDT, File file) throws IOException {
		this.valueDT = valueDT;
		this.file = file;
		this.fileSize = createFile();
	}
	
	private void write(long offset, V value, boolean append) throws IOException {
		RandomAccessFile raf = new RandomAccessFile(this.file, "rw");
		raf.seek(offset);
		valueDT.write(raf, value);
		
		if(append) {
			fileSize += valueDT.size();
			raf.seek(FILE_SIZE_OFFSET);
			raf.writeLong(fileSize);
		}
		raf.close();
	}
	
	public void write(long offset, V value) throws IOException {
		write(offset, value, false);
	}
	
	public long append(V value) throws IOException {
		long lastSize = fileSize;
		write(fileSize, value, true);
		return lastSize;
	}
	
	public V read(long offset) throws IOException {
		RandomAccessFile raf = new RandomAccessFile(this.file, "r");
		raf.seek(offset);
		
		V value = valueDT.read(raf);
		raf.close();
		return value;
	}
	
	private long createFile() throws IOException {
		if(!this.file.exists()) {
			RandomAccessFile raf = new RandomAccessFile(this.file, "rw");
			raf.seek(FILE_SIZE_OFFSET);
			raf.writeLong(8L);
			raf.close();
			return 8L;
		}else {
			RandomAccessFile raf = new RandomAccessFile(this.file, "r");
			raf.seek(FILE_SIZE_OFFSET);
			long fileSize = raf.readLong();
			raf.close();
			return fileSize;
		}
	}
}

package br.ufc.crateus.btree;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import br.ufc.crateus.btree.dtypes.DataType;
import br.ufc.crateus.btree.dtypes.LongDT;
import br.ufc.crateus.btree.dtypes.TupleLongDT;
import br.ufc.crateus.btree.st.Tuple;

public class PageSerializer<K extends Comparable<K>> {
	private DataType<K> keyDT;
	private LongDT valueDT;
	private TupleLongDT tupleDT;
	private int m;
	private File file;
	
	static final int FILE_SIZE_OFFSET = 0;
	static final int NUM_KEYS_OFFSET = 8;
	static final int ROOT_OFFSET = 12;
	
	static final boolean KEYS_INCREMENT = true;
	static final boolean KEYS_DECREMENT = false;
	
	private long fileSize;
	private int keysSize;
	
	public PageSerializer(File file, DataType<K> keyDT, int m) throws IOException {
		this.file = file;
		this.keyDT = keyDT;
		this.m = m;
		this.valueDT = new LongDT();
		this.tupleDT = new TupleLongDT();
		if(this.file.exists()) {
			RandomAccessFile raf = new RandomAccessFile(this.file, "r");
			this.fileSize = raf.readLong();
			this.keysSize = raf.readInt();
			raf.close();
		}else {
			System.out.println();
			RandomAccessFile raf = new RandomAccessFile(this.file, "rw");
			raf.writeLong(20L);
			raf.writeInt(0);
			raf.writeLong(0L);
			this.fileSize = 20L;
			this.keysSize = 0;
			raf.close();
		}
	}
	
	public int pageSize() {return m;}
	
	private int getSizePage() {
		return 1 + 4 + m * (keyDT.size() + 2*valueDT.size());
	}
	
	public void write(long left, long offset, Page<K> page, boolean append) throws IOException {
		RandomAccessFile raf = new RandomAccessFile(this.file, "rw");
		raf.seek(offset);
		raf.writeBoolean(page.leaf());
		raf.writeLong(page.getLeft());
		BinarySearchST<K, Tuple<Long, Long>> st = page.pageST();
		raf.writeInt(st.size());
		keyDT.writeAll(raf, st.keys());
		tupleDT.writeAll(raf, st.values());
		if(append) {
			fileSize += getSizePage();
			raf.seek(FILE_SIZE_OFFSET);
			raf.writeLong(fileSize);
		}
		raf.close();
	}
	
	public void write(long left, long offset, Page<K> page) throws IOException{
		write(left, offset, page, false);
	}
	
	public Page<K> append(long left, BinarySearchST<K, Tuple<Long, Long>> st, boolean leaf) throws IOException{
		Page<K> p = new Page<K>(leaf, st, this, fileSize);
		write(left, fileSize, p, true);
		return p;
	}
	
	@SuppressWarnings("unchecked")
	public Page<K> read(long offset) throws IOException{
		RandomAccessFile raf = new RandomAccessFile(this.file, "r");
		raf.seek(offset);
		boolean leaf = raf.readBoolean();
		long left = raf.readLong();
		int size = raf.readInt();
		K[] keys = (K[]) keyDT.readAll(raf, size).toArray(new Comparable[size]);
		Tuple<Long, Long> [] values = (Tuple<Long, Long>[]) tupleDT.readAll(raf, size).toArray(new Tuple[size]);
		BinarySearchST<K, Tuple<Long, Long>> st = new BinarySearchST<>(pageSize());
		for(int i = 0; i < size; i++)
			st.put(keys[i], values[i]);
		Page<K> page = new Page<K>(leaf, st, this, offset);
		page.setLeft(left);
		raf.close();
		return page;
			
	}
	
	public Page<K> root() throws IOException{
		RandomAccessFile raf = new RandomAccessFile(this.file, "rw");
		Page<K> page = null;
		if(fileSize > 20L) {
			raf.seek(ROOT_OFFSET);
			long rootOffset = raf.readLong();
			page = read(rootOffset);
		}else {
			BinarySearchST<K, Tuple<Long, Long>> st = new BinarySearchST<>(pageSize());
			page = append(-1L,st, true);
			raf.seek(ROOT_OFFSET);
			raf.writeLong(page.offset());
		}
		raf.close();
		return page;
	}
	
	public void updateKeys(boolean more) throws IOException {
		RandomAccessFile raf = new RandomAccessFile(this.file, "rw");
		raf.seek(NUM_KEYS_OFFSET);
		if(more) keysSize++;
		else keysSize--;
		raf.writeInt(keysSize);
		raf.close();
	}
	
	public void setRoot(long offset) throws IOException {
		RandomAccessFile raf = new RandomAccessFile(this.file, "rw");
		raf.seek(ROOT_OFFSET);
		raf.writeLong(offset);
		raf.close();
	}
	
	public int readSize() {
		return keysSize;
	}
}

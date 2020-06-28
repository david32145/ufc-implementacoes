package br.ufc.crateus.btree;

import java.io.IOException;

import br.ufc.crateus.btree.st.Tuple;

public class Page<K extends Comparable<K>> {
	
	//Se a página é uma folha
	private boolean leaf;
	
	//Estrutura que gerência a página em memória
	private BinarySearchST<K, Tuple<Long, Long>> pageST;
	
	//Leitor e Escritor das Páginas no Disco
	private PageSerializer<K> serializer;
	
	//Endereço da Página no Disco
	private long offset;
	
	//Referência a Página mais a Esquerda (Setinela)
	private Long left;
	
	private final static long NULL = -1;
	
	public Page(boolean leaf, PageSerializer<K> serializer, long offset) {
		this(leaf, new BinarySearchST<K, Tuple<Long, Long>>(serializer.pageSize()), serializer, offset);
	}

	public Page(boolean leaf, BinarySearchST<K, Tuple<Long, Long>> pageST, PageSerializer<K> serializer, long offset) {
		this.leaf = leaf;
		this.pageST = pageST;
		this.serializer = serializer;
		this.offset = offset;
		this.left = NULL;
	}
	
	/*
	 * Como eu não fiz uma class para encapsular as três
	 * referências, foi preferível usar aquela BinarySearch e no tipo
	 * V dela passar um tipo composto por dois long's, assim dá no mesmo!!! 
	 * */
	
	//value é a refência para o arquivo de data, e
	//right e para a próxima página
	public void put(K key, Long value, Long right) {
		pageST.put(key, new Tuple<Long, Long>(value, right));
	}
	
	public Tuple<Long, Long> getAll(K key){
		return pageST.get(key);
	}
	
	public Long get(K key) {
		Tuple<Long, Long> t = pageST.get(key);
		return t == null ? null : t.first();
	}
	
	
	//Retorna o página next e chave pela qual ela 'desceu'
	public Tuple<Page<K>, K> nextAndKey(K key) throws IOException {
		K floor = pageST.floor(key);
		if(floor == null)
			return new Tuple<>(serializer.read(left), pageST.min());
		if(key.equals(floor))
			return null;
		Tuple<Long, Long> t = pageST.get(floor);
		return new Tuple<>(serializer.read(t.second()), floor);
		
	}
	
	public Page<K> next(K key) throws IOException{
		Tuple<Page<K>, K> pageAndKey = nextAndKey(key);
		return pageAndKey.first();
	}
	
	//Dada uma página page que é uma das página filha desta, retorna 
	//a irmã a esquerda daquela chave key em relação a page,
	//caso page seja a página de setinela, retorna a página mais a esquerda,
	//de forma geral retorna a irmã de page
	public Page<K> syster(Page<K> page, K key) throws IOException{
		Tuple<Long, Long> t = pageST.get(key);
		if(page.offset == t.second()) {
			return before(key);
		}else {
			Tuple<K,Tuple<Long, Long>> min = pageST.minAll();
			return serializer.read(min.second().second());
		}
	}
	
	/*public Page<K> next(K key) throws IOException {
		K floor = pageST.floor(key);
		if(floor == null)
			return serializer.read(left);
		if(key.equals(floor))
			return null;
		Tuple<Long, Long> t = pageST.get(floor);
		return serializer.read(t.second());
		
	}*/
	
	public void save() throws IOException{
		serializer.write(left, offset, this);
	}
	
	public Page<K> split() throws IOException{
		BinarySearchST<K, Tuple<Long, Long>> newST = pageST.split();
		save();
		Page<K> np = serializer.append(-1L, newST, leaf);
		np.left = newST.get(newST.min()).second();
		return np;
	}
	
	public boolean leaf() {
		return leaf;
	}
	
	public BinarySearchST<K, Tuple<Long, Long>> pageST(){
		return pageST;
	}
	
	public long offset() {
		return offset;
	}
	
	public boolean overflow() {
		return pageST.size() == serializer.pageSize();
	}
	
	public boolean underflow() {
		return pageST.size() == (Math.ceil((double) serializer.pageSize() / 2.0) - 2);
	}
	
	
	//se o número de chaves for o mínimo possível
	public boolean minimal() {
		return pageST.size() == (Math.ceil((double) serializer.pageSize() / 2.0) - 1);
	}
	
	public boolean contains(K key) {
		return get(key) != null;
	}
	
	//Referência para a página mais a esquerda
	public Long right() {
		Tuple<Long, Long> t = pageST.get(pageST.max());
		return t.second();
	}
	
	public void setLeft(Long left) {
		this.left = left;
	}
	
	public Long getLeft() {
		return left;
	}
	
	//Junta a página atual com page
	public void merge(Page<K> page) {
		for(Tuple<K, Tuple<Long, Long>> t : page.pageST) {
			put(t.first(), t.second().first(), t.second().second());
		}
	}
	
	public Page<K> before(K key) throws IOException{
		Tuple<K, Tuple<Long, Long>> bf = pageST.before(key);
		if(bf == null) return serializer.read(left);
		return serializer.read(bf.second().second());
	}
	
	public Page<K> after(K key) throws IOException{
		Tuple<K, Tuple<Long, Long>> af = pageST.after(key);
		if(af == null) return serializer.read(right());
		return serializer.read(af.second().second());
	}
	
	//Aqui deveria liberar a página, sobrescrevendo os valores por valores default,
	//mas não implementei!!!!!, até por que só fazeria sentido se eu fosse
	//reutiliza-la, porém também não implementei uma solução para reusar as páginas
	//que foram apagadas.
	public void flushPage() {
		
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) return false;
		if(obj.getClass() != Long.class) return false;
		long x = (long) obj;
		return this.offset == x;
	}
}

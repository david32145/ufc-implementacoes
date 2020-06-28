package br.ufc.crateus.exec;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import br.ufc.crateus.btree.Btree;
import br.ufc.crateus.btree.DataSerializer;
import br.ufc.crateus.btree.Page;
import br.ufc.crateus.btree.PageSerializer;
import br.ufc.crateus.btree.BinarySearchST;
import br.ufc.crateus.btree.dtypes.CidadeDT;
import br.ufc.crateus.btree.dtypes.IntegerDT;
import br.ufc.crateus.btree.dtypes.StringDT;
import br.ufc.crateus.btree.st.Cidade;
import br.ufc.crateus.btree.st.CidadeReader;
import br.ufc.crateus.btree.st.Tuple;

@SuppressWarnings("unused")
public class Main {
	public static void main(String[] args) throws IOException {
		
		String database = "nomes";
		
		File fileKeys = new File(database + "Key.dat");
		File fileValues = new File(database + "Value.dat");
		IntegerDT keyDT = new IntegerDT();
		StringDT valueDT = new StringDT(15);
		PageSerializer<Integer> ps = new PageSerializer<>(fileKeys, keyDT, 7);
		DataSerializer<String> ds = new DataSerializer<>(valueDT, fileValues);
		
		Btree<Integer, String> tree = new Btree<>(ps, ds);
		CidadeReader cr = new CidadeReader("Base_de_dados_dos_municipios.csv");
		
		
		/*
		//Código para fazer a inserção dos dados de teste na Btree
		for(Tuple<Integer, Cidade> t : cr) {
			//Chave id da cidade, valor nome da cidade
			tree.put(t.first(), t.second().getNome());
		}
		*/
		
		/*
		//Código para fazer a busca dos dados de teste na Btree
		for(Tuple<Integer, Cidade> t : cr) {
			//Chave id da cidade
			System.out.println(tree.get(t.first()));
		}
		*/
		
		
		
		System.out.println(tree.size());
		
		
		/*
		 * tree.debug();
		 * 
		 * for(Tuple<Integer, Cidade> t : cr) {
		 * 		System.out.println(tree.get(t.first()));
		 * }
		 * System.out.println(i);
		 * System.out.println(tree.size());
		 */
	}

}

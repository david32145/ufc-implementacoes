package br.ufc.crateus.st.q3;

public class MatchString {
	
	private String str;
	
	public MatchString(String str) {
		this.str = str;
	}
	
	public int substring(String sub) {
		if(str.length() < sub.length()) return -1;
		int hashSub = sub.hashCode();
		int hashParcial = hash(0, sub.length());
		for(int i = 0; i < str.length() - sub.length(); i++) {
			char ch = str.charAt(i);
			hashParcial = (hashParcial - (ch * (int)Math.pow(31, sub.length() - 1)))*31 + str.charAt(i + sub.length()-1);
			if(hashParcial == hashSub) {
				if(equals(sub, i, sub.length())) return i;
			}
		}
		return -1;
	}
	
	private boolean equals(String s, int begin, int lenght) {
		for(int i = 0; i < lenght; i++) {
			if(str.charAt(i+begin) != s.charAt(i)) return false;
		}
		return true;
	}
	
	private int hash(int begin, int lenght) {
		int hash = 0;
		for(int i = 0; i < lenght; i++) {
			char ch = str.charAt(i+begin);
			hash = ch + (31 * hash);
		}
		return hash;
	}
}

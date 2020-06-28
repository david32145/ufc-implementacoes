package br.ufc.crateus.st.q2;

public class Polinomio {
	private static class Node{
		int degree;
		double coefficient;
		Node next;
		public Node(int degree, double coefficient, Node next) {
			this.degree = degree;
			this.coefficient = coefficient;
			this.next = next;
		}
		
	}
	
	private int degree;
	private Node root;
	
	public Polinomio multiply(Polinomio p) {
		Polinomio mult = new Polinomio();
		Node [] list = new Node[degree + p.degree + 1];
		for(Node r = root; r != null; r = r.next) {
			for(Node m = p.root; m!= null; m = m.next) {
				double coef = r.coefficient*m.coefficient;
				int degree = r.degree + m.degree;
				list[degree] = new Node(degree, coef, list[degree]);
			}
		}
		
		for(int i = 1; i < list.length; i++) {
			double acc = 0;
			for(Node r = list[i]; r != null; r = r.next) {
				acc += r.coefficient;
			}
			if(acc != 0) mult.root = new Node(i, acc, mult.root);
		}
		return mult;
		
	}
	
	public Polinomio add(double coefficient, int degree) {
		Node head = new Node(0, 0, root);
		for(Node r = head; r != null; r = r.next) {
			if(r.next == null || r.next.degree < degree) {
				r.next = new Node(degree, coefficient, r.next);
				break;
			}
		}
		root = head.next;
		this.degree = root.degree;
		return this;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for(Node r = root; r != null;  r = r.next) {
			if(first) {
				sb.append(r.coefficient).append("^").append(r.degree);
				first = false;
			}else {
				if(r.coefficient < 0) {
					sb.append(r.coefficient).append("^").append(r.degree);
				}else {
					sb.append("+").append(r.coefficient).append("^").append(r.degree);
				}
			}
		}
		return sb.toString();
	}
}

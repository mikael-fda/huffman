package Tas;

import java.util.ArrayList;


public class Tas {

	private Element[] tab;
	private int fin;
	
	public Tas(int k) {
		this.tab = new Element[k];
		this.fin = 0;
	}
	
	public void insert(Element e) {
		int fils = fin;
		int pere = (fils - 1) / 2;
		while(fils > 0 && tab[pere].isSuperior(e)) {
			tab[fils] = tab[pere];
			fils = pere;
			pere = (fils - 1)/2;
		}
		tab[fils] = e;
		fin++;
	}
	
	public void removeMin() {
		fin = fin - 1;
		Element e = tab[fin];
		int fils, pere = 0;
		while(true) {
			if((2 * pere + 1) < fin) fils = 2 * pere + 1;
			else break;
			
			if( (2 * pere + 2) < fin)
				if(tab[2 * pere + 2 ].isInferiorEqual(tab[2 * pere + 1]))
					fils = 2 * pere + 2;
			
			if(e.isInferiorEqual(tab[fils])) break;
			
			tab[pere] = tab[fils];
			pere = fils;
		}
		tab[pere] = e;
	
	}
	
	public Element getMinTas() {
		return this.tab[0];
	}
	
	
	public static void triParTas(ArrayList<Element> l, Element[] res) {
		int size = l.size();
		Element val;
		Tas tas = new Tas(size);
		
		for(int i = 0; i < size; i++) {
			val = l.get(i);
			tas.insert(val);
		}
		
		for(int i = 0; i < size; i++) {
			val = tas.getMinTas();
			res[i] = val;
			tas.removeMin();
		}
	}
	
	public boolean isEmpty() { return fin == 0; }
	
	public int size() { return tab.length; }

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append("Tas size of " + this.fin + "\n[");
		for(Element e : tab) {
			if(e == null) break;
			s.append(e).append("\n");
		}
		s.append(']');
		return s.toString();
	}
	
	/**
	 * Method used for testing
	 * @param c
	 * @param fqr
	 */
	public void addEle(char c, int fqr) {
		Element e = new Element(c);

		for(int i = 0; i < fqr -1 ; i++) 
			e.increment();
		this.insert(e);
		
	}
}

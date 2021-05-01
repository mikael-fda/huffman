package Tas;

public class Element{
		private int freq;
		private char element;
		
		public Element(char element) {
			this.element = element;
			this.freq = 1;
		}
		
		public Element() {
			this.element = '_';
			this.freq = Integer.MAX_VALUE;
		}
		
		public void increment() {
			this.freq++;
		}
		
		public boolean isSuperior(Element e) {
			return this.freq > e.getFreq();
		}
		
		public boolean isInferior(Element e) {
			return this.freq < e.getFreq();
		}
		
		public boolean isInferiorEqual(Element e) {
			return this.freq <= e.getFreq();
		}
		
		public char getElement(){
			return this.element;
		}
		public int getFreq() {
			return this.freq;
		}
		
		@Override
		public boolean equals(Object obj) {
			if(!(obj instanceof Element)) return false;
			Element e = (Element)obj;
			if(this.element == e.getElement()) return true;
			return false;
			
		}
		@Override
		public String toString() {
			if( this.element == '\n')
				return "'\\n': " + this.freq;
				
			
			return "'" + this.element + "': " + this.freq;
		}
		
		
	}
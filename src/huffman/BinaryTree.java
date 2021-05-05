package huffman;

import java.util.Vector;

import FileReader.FileReader;
import Tas.Element;

public class BinaryTree {
	private BinaryTree left;
	private BinaryTree right;
	
	private int freq;
	private char car;
	private String bytes;
	
	public BinaryTree(int a, char car) {

		this(a, null, null, car);
	}
	
	public BinaryTree(int a, BinaryTree left, BinaryTree right) {

		this(a, left, right, '\0');
	}
	public BinaryTree(int a, BinaryTree left, BinaryTree right, char car) {
		this.freq = a;
		this.left = left;
		this.right = right;
		this.car = car;
	}
	
	public static BinaryTree huffman(Element[] res) {
		int size = res.length;
		Vector<BinaryTree> tree = new Vector<BinaryTree>();
		for(Element e : res)
			tree.add(new BinaryTree(e.getFreq(), e.getElement()));

		while(tree.size() > 1) {
			var x = BinaryTree.min(tree);
			tree.remove(x);
			var y = BinaryTree.min(tree);
			tree.remove(y);
			
			BinaryTree z;
			int freq = x.getFreq() + y.getFreq();
			if(x.inferiorTo(y)) {
				z = new BinaryTree(freq, x, y, y.getChar());
			}
			else
				z = new BinaryTree(freq, y, x, x.getChar());
			
			tree.add(z);
		}
		return tree.get(0);
		
	}
	
	private static BinaryTree min(Vector<BinaryTree> vec) {
		BinaryTree min = vec.get(0);
		System.out.println(vec);
		for(BinaryTree b : vec) {
			if(b.inferiorTo(min)) {
				min = b;
			}
		}
		System.out.println("Min is = " + min.onlyLeaf() + "\n");
		return min;
	}
	
	public int getFreq() { return this.freq; }	
	public char getChar() { return this.car; }
	
	public boolean inferiorTo(BinaryTree b) {		
		if(this.freq < b.getFreq()) return true;
		if(this.freq > b.getFreq()) return false;
		
		return this.getChar() < b.getChar();
	}
	
	
	public void applyBytes(String b) {
		this.bytes = b;
		if(left != null) this.left.applyBytes( b + "0");
		if(right != null) this.right.applyBytes( b + "1");
		
	}
	
	@Override
	public String toString() {
		if(left == null && right == null) {
			return "(" + this.car + "," + this.freq + ")";
		}
		
		StringBuilder s = new StringBuilder();
		s.append("[");
		if(this.left != null)
			s.append(this.left.toString());
		s.append("(" + this.car + "," + this.freq + ")");
		if(this.right != null)
			s.append(this.right.toString());
		s.append("]");
		
		return s.toString();
	}
	
	public String onlyLeaf() {
		if(left == null && right == null) {
			return "(" + this.car + "," + this.freq + "," + this.bytes + ")";
		}
		StringBuilder s = new StringBuilder();
		if(this.left != null)
			s.append(this.left.onlyLeaf());
		if(this.right != null)
			s.append(this.right.onlyLeaf());
		
		return s.toString();
	}
    public void print(StringBuilder buffer, String prefix, String childrenPrefix) {
        buffer.append(prefix);
        buffer.append("(" + this.car + ", " + this.freq + ")");
        buffer.append('\n');
        if(this.left != null) {
        	left.print(buffer, childrenPrefix + "├── ", childrenPrefix + "│   ");
        }
        if(right != null)
        {
        	right.print(buffer, childrenPrefix + "├── ", childrenPrefix + "│   ");
        }
    }
    
    public String printer() {
    	StringBuilder s = new StringBuilder();
    	this.print(s, "", "");
    	return s.toString();
    }
	
	public static void main(String[] args) {
		FileReader fr = new FileReader("hufman.txt");
		Element[] res = fr.readFile();
		BinaryTree a = BinaryTree.huffman(res);
		System.out.println("\n\n");
		a.applyBytes("");
		System.out.println(a.printer());
	}
}

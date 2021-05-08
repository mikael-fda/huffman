package huffman;


import java.util.Vector;

import FileProcessor.FileReaderDecode;
import FileProcessor.FileReaderEncode;
import FileProcessor.FileWritterDecode;
import FileProcessor.FileWritterEncode;
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
	
	public boolean isLeaf() {
		return this.left == null && this.right == null;
	}
	
	public BinaryTree getLeft() { return this.left; }
	public BinaryTree getRight() { return this.right; }
	
	
	public static BinaryTree huffman(Element[] res) {
		int size = res.length;
		Vector<BinaryTree> tree = new Vector<BinaryTree>();
		for(Element e : res) {
			tree.add(new BinaryTree(e.getFreq(), e.getElement()));
		}
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
		tree.get(0).applyBytes();
		return tree.get(0);
		
	}
	
	private static BinaryTree min(Vector<BinaryTree> vec) {
		BinaryTree min = vec.get(0);
		for(BinaryTree b : vec) {
			if(b.inferiorTo(min)) {
				min = b;
			}
		}
		return min;
	}
	
	public int getFreq() { return this.freq; }	
	public char getChar() { return this.car; }
	public String getBytes() { return this.bytes; }
	
	public boolean inferiorTo(BinaryTree b) {		
		if(this.freq < b.getFreq()) return true;
		if(this.freq > b.getFreq()) return false;
		
		return this.getChar() < b.getChar();
	}
	
	public void applyBytes() {
		this.applyBytes("");
	}
	
	private void applyBytes(String bits) {
		this.bytes = bits;
		if(left != null)  {  this.left.applyBytes(bits + "0"); }
		if(right != null) { this.right.applyBytes(bits + "1"); }
		
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

			if(bytes != null) {
				String s = "";
				s = this.bytes;
				return "(" + this.car + "," + this.freq + "," + s + ")\n";
			}
			return "(" + this.car + "," + this.freq + "," + this.bytes + ")\n";
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
		FileReaderEncode fr = new FileReaderEncode("fichier4.txt");
		Element[] res = fr.getEncodings();
		BinaryTree a = BinaryTree.huffman(res);
		System.out.println("\n\n");
		
		a.applyBytes();

		System.out.println("\n\n");
		System.out.println(a.onlyLeaf());
		FileWritterEncode fwe = new FileWritterEncode(fr.getFilePath(), a, fr);
	
	
		FileReaderDecode frd = new FileReaderDecode("fichier4.txt.huf");
		frd.readFile();
		
		FileWritterDecode fwd = new FileWritterDecode(frd);
		fwd.writeFile();
	}
}

package FileReader;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import huffman.BinaryTree;



public class FileWritterEncode extends FileWritter{
	private static final String EXTENSION = ".huf";
	private static final String  CHAR_SEPARATOR = "\0\0";
	private static final String  ENC_SEPARATOR = "\0";
	
	private FileReader fr;
	private BinaryTree huffman;
	private Map<Character, BinaryTree> nodes;
	
	public FileWritterEncode(String path, BinaryTree huffman, FileReader fr) {
		super(path + EXTENSION);
		this.huffman = huffman;
		this.fr = fr;
		this.nodes = new HashMap<Character, BinaryTree>();
		
		this.fetchValues(this.nodes, this.huffman);
		System.out.println("Nodes=" + nodes);
		this.writeEncodingTranslation();
	}
	
	private void fetchValues(Map<Character, BinaryTree> nodes, BinaryTree node) {
		if(node.isLeaf()) {
			nodes.put(node.getChar(), node);
		}
		else {
			this.fetchValues(nodes, node.getLeft());
			this.fetchValues(nodes, node.getRight());
		}
	}
	
	public void writeEncodingTranslation() {
		try {
			OutputStream out = new BufferedOutputStream(new FileOutputStream(this.file));
			for(BinaryTree n : this.nodes.values()) {
				out.write(n.getChar());
				out.write(ENC_SEPARATOR.getBytes());
				out.write(n.getBytes());
				out.write(CHAR_SEPARATOR.getBytes());
			}
			out.write("\n".getBytes());
			this.writeFileEncoded(out);
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void writeFileEncoded(OutputStream out) {
		for(char c : this.fr.getFileContent().toString().toCharArray()) {
			BinaryTree curr = this.nodes.get(c);
			try {
//				System.out.print(curr.getChar());
				out.write(curr.getBytes());
//				out.write(curr.getChar());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	
	

}

package FileReader;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import huffman.BinaryTree;



public class FileWritterEncode extends FileWritter{
	private static final String EXTENSION = ".huf";
	private static final String  CHAR_SEPARATOR = "\0\0";
	private static final String  ENC_SEPARATOR = "\0";
	private static final String  END_LINE = "\n";
	
	
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
		this.writeEncoding();
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
	
	public void writeEncoding() {
		try {
			FileOutputStream fos = new FileOutputStream(this.file);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			
			// écrit les fréquences
			for(BinaryTree n : this.nodes.values()) {
				baos.write(n.getChar());
				baos.write(ENC_SEPARATOR.getBytes());
				baos.write(n.getBytes().getBytes());
				baos.write(CHAR_SEPARATOR.getBytes());
			}
			baos.write(END_LINE.getBytes());
			
			// masque binaire
			byte zeroM = 0B0, oneM = 0B1;
			String bits = "";
			
			for(char c : this.fr.getFileContent().toString().toCharArray()) {
				BinaryTree curr = this.nodes.get(c);
				bits += curr.getBytes();
				// Tout les 8 bits on écrit
				if(bits.length() >= 8) {
					int nb = 8;
					String val = bits.substring(0, nb);

					byte octet = 0;
					for(int i = 0; i < 8; i++) {
						octet <<= 1;
						if(val.charAt(i) == '0') { 	octet |= zeroM;	}
						else { 						octet |= oneM;	}
					}

					baos.write(octet);
//					System.out.println(bits +"\n" + bits.substring(0, nb) + "-" + bits.substring(nb));
					bits = bits.substring(nb);
				}
			}
			
			baos.writeTo(fos);
			baos.close();
			fos.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}

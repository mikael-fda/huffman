package FileProcessor;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import huffman.BinaryTree;



public class FileWritterEncode extends FileWritter implements HuffmanFile{
	private FileReaderEncode fr;
	private BinaryTree huffman;
	private Map<Character, BinaryTree> nodes;
	
	public FileWritterEncode(String path, BinaryTree huffman, FileReaderEncode fr) {
		super(path + ENC_EXTENSION);
		this.huffman = huffman;
		this.fr = fr;
		this.nodes = new HashMap<Character, BinaryTree>();
		
		this.fetchValues(this.nodes, this.huffman);
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
				baos.write(this.ENC_SEPARATOR.getBytes());
				baos.write(n.getBytes().getBytes());
				baos.write(this.CHAR_SEPARATOR.getBytes());
			}
			baos.write(this.END_LINE.getBytes());
			
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

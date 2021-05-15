package FileProcessor;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import huffman.BinaryTree;



public class FileWritterEncode extends FileWritter{
	private FileReaderEncode fr;
	private BinaryTree huffman;
	private Map<Character, BinaryTree> nodes;
	
	public FileWritterEncode(String path, BinaryTree huffman, FileReaderEncode fr) {
		super(path + ENC_EXTENSION);
		this.huffman = huffman;
		this.fr = fr;
		this.nodes = new HashMap<Character, BinaryTree>();
		
		this.fetchValues(this.nodes, this.huffman);
		this.showChars();
	}

	/**
	 * Requierments
	 */
	public void fileCompression(){
		double txt = this.fr.fileSize();
		double enc = this.file.length();

		double compression = 100.0 - (enc / txt) * 100.0;

		System.out.println("Fichier  d'entrée:\t" + txt + " octets");
		System.out.println("Fichier de sortie:\t" + enc + " octets");
		System.out.println("Taux de compression:\t" + String.format("%.2f", compression) + "%");
	}

	/**
	 * Recursively find all leaf in node and write them in nodes
	 * @param nodes Map<Character, BinaryTree>
	 * @param node BinaryTree
	 */
	private void fetchValues(Map<Character, BinaryTree> nodes, BinaryTree node) {
		if(node.isLeaf()) {
			nodes.put(node.getChar(), node);
		}
		else {
			this.fetchValues(nodes, node.getLeft());
			this.fetchValues(nodes, node.getRight());
		}

	}

	/**
	 * requierments
	 */
	private void showChars(){
		char charsToShow[] = {'A', 'B', 'C'};
		for(char c : charsToShow){
			BinaryTree node = this.nodes.get(c);
			if(node != null){
				System.out.println(c + " : " + node.getBytes());
			}
		}
	}
	
	public void writeEncoding() {
		try {
			FileOutputStream fos = new FileOutputStream(this.file);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			
			// write frequency on the file
			for(BinaryTree n : this.nodes.values()) {
				baos.write(n.getChar());
				baos.write(ENC_SEPARATOR.getBytes());
				baos.write(n.getBytes().getBytes());
				baos.write(CHAR_SEPARATOR.getBytes());
			}
			baos.write(END_LINE.getBytes());
			
			// binary masks
			byte zeroM = 0B0, oneM = 0B1;
			String bits = "";
			
			for(char c : this.fr.getFileContent().toString().toCharArray()) {
				BinaryTree curr = this.nodes.get(c);
				bits += curr.getBytes();
				// every 8 bits we write on boas
				if(bits.length() >= 8) {
					int nb = 8;
					String val = bits.substring(0, nb);

					byte octet = 0;
					for(int i = 0; i < 8; i++) {
						// 1 left shifting
						octet <<= 1;

						if(val.charAt(i) == '0') { 	
							// octet or 0. eg: 110 or 0 = 110
							octet |= zeroM;	
						}
						else { 	
							// octet or 1. eg: 110 or 1 = 111				
							octet |= oneM;	
						}
					}

					baos.write(octet);
					bits = bits.substring(nb);
				}
			}
			// write result 
			baos.writeTo(fos);
			baos.close();
			fos.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}

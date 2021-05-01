package huffman;

import FileReader.FileReader;

public class Main {
	public static void main(String[] args){		
		FileReader fr = new FileReader("README.md");
		fr.readFile();
	}
}

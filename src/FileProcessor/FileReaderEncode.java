package FileProcessor;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import Tas.Element;

public class FileReaderEncode extends FileReader{

	private StringBuilder fileContent;
	private ArrayList<Element> chars;

	public FileReaderEncode(String filePath) {
		super(filePath);
	}

	public Element[] getEncodings() {
		this.readFile();
		Element[] result = this.sortByTas();
		return result;
		
	}
	
	public void readFile(){
		this.chars = new ArrayList<Element>();
		this.fileContent = new StringBuilder(); 
		int nb_chars = 0;
		BufferedReader lnr;
		try {
			lnr = new BufferedReader(
					new InputStreamReader(
							new FileInputStream(this.file), StandardCharsets.UTF_8));
			
			String tempLine;
			Element cr = new Element('\n');
			this.chars.add(cr);
			while( (tempLine = lnr.readLine()) != null ) {
				for(char c : tempLine.toCharArray()) {
					nb_chars++;
					Element e = new Element(c);
					if(this.chars.contains(e)) {
						this.chars.get(chars.indexOf(e)).increment();
					}
					else {
						this.chars.add(e);
					}
				}
				fileContent.append(tempLine);
				fileContent.append('\n');
				cr.increment();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Element[] sortByTas() {
		Element[] res = new Element[chars.size()];
		Tas.Tas.triParTas(chars, res);
		return res;
	}
	
	public void display(Element[] res) {
		int pos = 1;
		for(int i = 0; i < res.length; i++) {
			if((i+1) == pos) {
				pos *= 2;
				System.out.println();
			}
			System.out.print(res[i] + "; ");
		}
		System.out.println();
	}

	public StringBuilder getFileContent() {
		return this.fileContent;
	}
}

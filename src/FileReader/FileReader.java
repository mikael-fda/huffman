package FileReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import Tas.Element;

public class FileReader {
	private String filePath;
	private File file;
	private StringBuilder fileContent;
	private ArrayList<Element> chars;
	
	public FileReader(String filePath) {
		this.filePath = filePath;
		this.file = new File(filePath);
	}
	
	public Element[] readFile(){
		this.chars = new ArrayList<Element>();
		this.fileContent = new StringBuilder();
		int nb_chars = 0;
		BufferedReader lnr;
		try {
			lnr = new BufferedReader(
					new InputStreamReader(
							new FileInputStream(this.file), StandardCharsets.UTF_8));
			
			String tempLine;
			while( (tempLine = lnr.readLine()) != null ) {
//				tempLine += '\n';
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
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
//		System.out.println("Before=" + chars + "\n" + chars.size());
		Element[] res = new Element[chars.size()];
		
		
		Tas.Tas.triParTas(chars, res);
		
//		System.out.println("After=" + chars + "\n" + chars.size());
		this.display(res);
		System.out.println("" + this.filePath);
		System.out.println("Nb chars=" + nb_chars);
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
	
	public String getFilePath() {
		return this.filePath;
	}
	
	public StringBuilder getFileContent() {
		return this.fileContent;
	}
	
	@Override
	public String toString() {	
		return this.getClass() + " " + this.filePath;
	}

}

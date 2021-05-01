package FileReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;

public class FileReader {
	private String filePath;
	private File file;
	private StringBuilder fileContent;
	private ArrayList<Element> chars;
	
	public FileReader(String filePath) {
		this.filePath = filePath;
		this.file = new File(filePath);
	}
	
	public void readFile(){
		this.chars = new ArrayList<Element>();
		this.fileContent = new StringBuilder();
		BufferedReader lnr;
		try {
			lnr = new BufferedReader(
					new InputStreamReader(
							new FileInputStream(this.file), StandardCharsets.UTF_8));
			
			String tempLine;
			while( (tempLine = lnr.readLine()) != null ) {
				tempLine += '\n';
				for(char c : tempLine.toCharArray()) {
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
	}


	@Override
	public String toString() {
		return this.getClass() + " " + this.filePath;
	}
	
	public class Element{
		private int freq;
		private char element;
		
		public Element(char element) {
			this.element = element;
			this.freq = 1;
		}
		
		public void increment() {
			this.freq++;
		}
		
		public char getElement(){
			return this.element;
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
			return "'" + this.element + "': " + this.freq;
		}
		
		
	}

}

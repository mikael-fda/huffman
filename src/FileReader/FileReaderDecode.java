package FileReader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FileReaderDecode extends FileReader implements HuffmanFile{
	Map<String, Character> translation;
	
	
	public FileReaderDecode(String filePath) {
		super(filePath);
		this.translation = new HashMap<String, Character>();
	}
	
	public void readFile() {
		try {
			FileInputStream fis = new FileInputStream(this.file);
			this.readEncoding(fis);
			this.readContent(fis);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void readEncoding(FileInputStream in) {
		try {			
			char bit;
			String curr = "";
			while((bit = (char) in.read()) != -1) {
				curr += bit;
				
				if(curr.contains(END_LINE)) {
					break;
				}
				if(curr.contains(CHAR_SEPARATOR)) {
					String value = curr.replace(ENC_SEPARATOR, "");
					this.translation.put(value.substring(1), value.charAt(0));
					curr = "";
				}
			}
			System.out.println(this.translation);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void readContent(FileInputStream in) {
		StringBuilder sb = new StringBuilder();
		try {			
			int id = 0;
			char bit;
			String curr = "";
			while((bit = (char) in.read()) != -1) {
				curr = Integer.toBinaryString(bit);
				while(curr.length() < 8) {
					curr = "0" + curr;
				}
				sb.append(curr);
				if(id == 50) break;
					id++;

				System.out.println(curr + " -> " + this.translation.get(curr));
			}
			System.out.println(this.translation);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		int deb =0, fin = 0;
		String bytes = "";
		for(char c : sb.toString().toCharArray()) {
			bytes += c;
			Character res = this.translation.get(bytes);
			if(res != null) {
				System.out.print(res);
				bytes = "";
			}
		}
	}
	
	public Map<String, Character> getTranslations(){
		return this.translation;
	}
	
}

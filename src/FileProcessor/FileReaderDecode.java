package FileProcessor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FileReaderDecode extends FileReader implements HuffmanFile{
	Map<String, Character> translation;
	StringBuilder content;
	
	
	public FileReaderDecode(String filePath) {
		super(filePath);
		this.translation = new HashMap<String, Character>();
		this.content = new StringBuilder();
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

			System.out.println("BEGIN ONE");
			while( (bit = (char) in.read()) != -1) {
				curr = Integer.toBinaryString(bit);
				if(curr.equals("1111111111111111"))
					break;
				while(curr.length() < 8) {
					curr = "0" + curr;
				}
				sb.append(curr);
//				if(id == 50) break;
//					id++;

//				System.out.println(curr + " -> " + this.translation.get(curr));
				if(id % 1000 == 0)
					System.out.println("ONE->" + curr + " : " + bit + " --> " + id);
				id++;
			}
//			System.out.println(this.translation);
			System.out.println("END ONE");
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		int id = 0;
		String bytes = "";

		System.out.println("BEGIN TWO");
		for(char c : sb.toString().toCharArray()) {
			bytes += c;
			Character res = this.translation.get(bytes);
			if(res != null) {
				this.content.append(res);
//				System.out.print(res);
				bytes = "";
			}
			if(id % 1000 == 0)
				System.out.println("TWO->" + bytes + " <--> " + res);
			id++;
		}
		System.out.println("END TWO");
	}
	
	public StringBuilder getContent() {
		return this.content;
	}
	
	public Map<String, Character> getTranslations(){
		return this.translation;
	}
	
}

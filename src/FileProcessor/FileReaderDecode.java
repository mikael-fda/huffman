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
	
	/**
	 * Read the first line of the file which correspond to the encoding translations
	 * @param in FileInputStream
	 */
	public void readEncoding(FileInputStream in) {
		try {			
			char bit;
			String curr = "";
			while((bit = (char) in.read()) != -1) {
				curr += bit;
				// case end of encoding sections
				if(curr.contains(END_LINE)) {
					break;
				}
				// case a new char
				if(curr.contains(CHAR_SEPARATOR)) {
					String value = curr.replace(ENC_SEPARATOR, "");
					this.translation.put(value.substring(1), value.charAt(0));
					curr = "";
				}
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Read bytes of a file and translate into char
	 * @param in FileInputStream
	 */
	public void readContent(FileInputStream in) {
		StringBuilder sb = new StringBuilder();
		try {
			char bit;
			String curr = "";

			while( (bit = (char) in.read()) != -1) {
				curr = Integer.toBinaryString(bit);
				// end of file
				if(curr.equals("1111111111111111"))
					break;
				while(curr.length() < 8) {
					// complete missing 0. eg: 111 -> 00000111
					curr = "0" + curr;
				}
				sb.append(curr);
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}

		String bytes = "";
		Character res;
		// read bit by bit
		for(char c : sb.toString().toCharArray()) {
			bytes += c;
			res = this.translation.get(bytes);
			if(res != null) {
				this.content.append(res);
				bytes = "";
			}
		}
	}
	
	public StringBuilder getContent() {
		return this.content;
	}
	
	public Map<String, Character> getTranslations(){
		return this.translation;
	}
	
}

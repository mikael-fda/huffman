package FileProcessor;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FileWritterDecode extends FileWritter implements HuffmanFile{
	FileReaderDecode input;
	
	public FileWritterDecode(FileReaderDecode frd) {
		super(frd.getFilePath(), DEC_PREFIX, ENC_EXTENSION);
		this.input = frd;
	
	}
	
	public void writeFile() {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(file.getAbsolutePath()));
			bw.write(this.input.getContent().toString());
			bw.close();
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}

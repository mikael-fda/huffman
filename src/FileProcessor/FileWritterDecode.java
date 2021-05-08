package FileProcessor;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FileWritterDecode extends FileWritter{
	final static String PREFIX = "dec_";
	FileReaderDecode input;
	
	public FileWritterDecode(FileReaderDecode frd) {
		super(frd.getFilePath() + PREFIX);
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

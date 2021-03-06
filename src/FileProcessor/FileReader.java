package FileProcessor;

import java.io.File;

public class FileReader {
	protected String filePath;
	protected File file;
	
	public FileReader(String filePath) {
		this.filePath = filePath;
		this.file = new File(filePath);
	}
	
	public String getFilePath() {
		return this.filePath;
	}

	public long fileSize(){
		return this.file.length();
	}

	@Override
	public String toString() {	
		return this.getClass() + " " + this.filePath;
	} 

}

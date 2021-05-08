package FileProcessor;

import java.io.File;
import java.io.IOException;

public class FileWritter implements HuffmanFile{
	protected File file;
	protected String filePath;
	
	public FileWritter(String path) {
		this.filePath = path;
		this.file = new File(this.filePath);

		try {
			this.file.createNewFile();
			System.out.println(this.file.getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}

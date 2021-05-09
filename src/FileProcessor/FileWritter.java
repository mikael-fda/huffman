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
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public FileWritter(String path, String prefix, String remove) {
		File toRead = new File(path);
		String dirname = toRead.getParent();
		String filename = prefix + toRead.getName();
		filename = filename.replace(remove, "");
		this.file = new File(dirname, filename);
		
	}
	
}

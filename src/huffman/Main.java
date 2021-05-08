package huffman;


import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileFilter;

import FileProcessor.FileReader;
import FileProcessor.FileReaderDecode;
import FileProcessor.FileReaderEncode;
import FileProcessor.FileWritterDecode;
import FileProcessor.FileWritterEncode;
import Tas.Element;

public class Main {
	public static void main(String[] args){		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		FileReader fr;
		JFileChooser path = new JFileChooser();
		String[] choices = {"Quitter", "Décoder un texte", "Coder un texte"};


		int res = JOptionPane.showOptionDialog(null,
				"Que voulez-vous faire ?", 
				"Algorithme de Huffman", 
				JOptionPane.YES_NO_CANCEL_OPTION, 
				JOptionPane.QUESTION_MESSAGE, 
				null, 
				choices, 
				choices[0]
			);
		
		if(res == 2) {
			path.setDialogTitle("Choisis un fichier à coder");
			
			if(path.showOpenDialog(null) != JFileChooser.APPROVE_OPTION ) {
				System.exit(1);
			}
			// Encoding 
			FileReaderEncode fre = new FileReaderEncode(path.getSelectedFile().getAbsolutePath());
			Element[] freq = fre.getEncodings();
			// Arbre de Huffman
			BinaryTree huffman = BinaryTree.huffman(freq);
			
			// Write file
			FileWritterEncode fwe = new FileWritterEncode(fre.getFilePath(), huffman, fre);
			
			
		}
		else if(res == 1) {
			path.setDialogTitle("Choisis un fichier à décoder");
			path.addChoosableFileFilter(new FileFilter() {
				
				@Override
				public String getDescription() {
					// TODO Auto-generated method stub
					return "huffman encoded (.huf)";
				}
				
				@Override
				public boolean accept(File f) {
					if(f.isDirectory()) return true;
					return f.getName().endsWith(".huf");
				}
			});
			if(path.showOpenDialog(null) != JFileChooser.APPROVE_OPTION ) {
				System.exit(1);
			}
			// Read file encoded
			FileReaderDecode frd = new FileReaderDecode(path.getSelectedFile().getAbsolutePath());
			frd.readFile();
			
			// Write file decoded
			FileWritterDecode fwd = new FileWritterDecode(frd);
			fwd.writeFile();

		}

	}
}

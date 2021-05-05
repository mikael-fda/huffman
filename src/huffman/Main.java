package huffman;


import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import FileReader.FileReader;
import FileReader.FileWritterEncode;
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
			fr = new FileReader(path.getSelectedFile().getAbsolutePath());
			
			Element[] freq = fr.readFile();
			BinaryTree huffman = BinaryTree.huffman(freq);
			FileWritterEncode fwe = new FileWritterEncode(fr.getFilePath(), huffman, fr);
			
			
		}
		else if(res == 1) {
			path.setDialogTitle("Choisis un fichier à décoder");
			if(path.showOpenDialog(null) != JFileChooser.APPROVE_OPTION ) {
				System.exit(1);
			}

		}

	}
}

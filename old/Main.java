import javax.swing.JOptionPane;

/**
 * 
 * @author Mikael FERREIRA DE ALMEIDA 3F1 et Jeremie HENRION 3F1
 *
 */
public class Main {

	public static void main(String[] args) {
		LecteurFichier lf;

		JOptionPane jop = new JOptionPane();
		String[] choix = {"Coder un texte", "D�coder un texte", "Quitter"};
		
		while(true) {
			int res = jop.showOptionDialog(null,"Que voulez-vous faire ?", "Algorithme de Huffman", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, choix, choix[2]);

			if(res == 0) {

				lf = new LecteurFichier();
				lf.texteToHuffman();
			}
			else if(res == 1) {

				lf = new LecteurFichier();
				lf.huffmanToTexte();
			}
			else if(res == 2)
				break;
		}
	}
}

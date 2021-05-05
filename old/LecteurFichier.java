/**
 * 
 * @author Mikael FERREIRA DE ALMEIDA 3F1 et Jeremie HENRION 3F1
 *
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class LecteurFichier {

	private File fichier;
	private JFileChooser path;
	private JOptionPane jop;

	private Liste listeFreq;
	private ArbreBin huffman;

	private String texte;
	private String txtFreq = "";
	private String newTexte = "";

	public LecteurFichier() {
		listeFreq = new Liste();
		path = new JFileChooser();
		texte = "";

	}

	/**
	 * Permet de selectionner le fichier qui sera lu
	 */
	public void choixFichier() {
		jop = new JOptionPane();
		jop.showMessageDialog(null, "Choissisez le fichier texte à lire", "Texte à encoder", JOptionPane.INFORMATION_MESSAGE);

		path.setDialogTitle("Choisir un fichier texte");
		if(path.showOpenDialog(null) == JFileChooser.APPROVE_OPTION ) {
			fichier = path.getSelectedFile();
		}
		else {
			System.err.println("Aucun fichier selctionné...");
			return;
		}
	}

	/**
	 * Lire le fichier selectionne et sauvegarde le contenu dans "texte"
	 */
	public void lireFichierTexte() {
		try {
			BufferedReader lnr = new BufferedReader(new InputStreamReader(new FileInputStream(fichier), StandardCharsets.UTF_8));
			String car = "";

			while((car = lnr.readLine()) != null)
				texte += car;
		} 
		catch (FileNotFoundException e) {
			System.out.println("Erreur, fichier non trouvé..");
			e.printStackTrace();
		} 
		catch (IOException e) {
			System.err.println("Erreur, lecture de ligne non faite..");
			e.printStackTrace();
		}

	}

	/**
	 * Calcul la frequence des caractere du fichier
	 */
	public void calculFrequenceFichier() {
			
		//Remplie l'ArrayList avec les ArbreBinaire (caractere + frequence), en parcourant le texte sauvegarde
		String lst = "";
		for(int i = 0 ; i < texte.length() ; i++	) {
			if(!existeDeja(texte.charAt(i), lst)) {
				lst += texte.charAt(i);
			}					
		}
		
		//Initialise le tableau des frequences
		int size = lst.length();
		String[][] tabFreq = new String[size][2];
		for(int i = 0; i < size; i++) {
			tabFreq[i][0] = String.valueOf(lst.charAt(i));
			tabFreq[i][1] = Integer.toString(0);
		}
		
		//Calcul des frequences
		for(int i = 0; i < texte.length(); i++) {
			for(int j = 0; j < size ; j++) {
				String c = String.valueOf(texte.charAt(i));
				if(c.equals(tabFreq[j][0])) {
					int x = Integer.parseInt(tabFreq[j][1]);
					x++;
					tabFreq[j][1] = Integer.toString(x);
					j = size + 1;
				}
			}
		}
		
		//Creer la liste ordonnee
		for(int i = 0; i < size ; i++) {
			ArbreBin arbre = new ArbreBin(lst.charAt(i));
			arbre.setFrequence(Integer.parseInt(tabFreq[i][1]));
			this.listeFreq = this.listeFreq.insererOrd(arbre);
		}
	}

	/**
	 * Creer un arbre de huffman avec les valeurs et frequence associes au caractere
	 */
	public void creationArbreHuffman() {
		this.huffman = new ArbreBin();
		this.huffman = new ArbreBin().huffman(listeFreq);

		huffman.setValBinaire("");
		huffman.cleanCar();
	}

	/**
	 * Crer un fichier et met dedans le contenu de newTexte
	 */
	public void creerFichierEncode() {
		String nom = (this.fichier.getAbsolutePath()).subSequence(0, this.fichier.getAbsolutePath().length() -4) + "_Encoder.txt";
		try {
			FileWriter fichierLu = new FileWriter(nom);
			BufferedWriter ecritFichier = new BufferedWriter(fichierLu);
			ecritFichier.write(newTexte);
			ecritFichier.close();

		} catch (IOException e) {
			System.err.println("Erreur, impossible de créer le fichier");
			e.printStackTrace();
		}
		jop = new JOptionPane();
		jop.showMessageDialog(null, "Le texte à été encodé et enregistrer à coté du texte originel", "Texte encoder", JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Transforme le texte en newTexte avec le codage de  Huffman
	 */
	public void encodageHuffman() {
		for(int i = 0; i < texte.length() ; i++) {
			newTexte += huffman.chercheValEncodage(texte.charAt(i));
		}
	}

	/**
	 * Creer un fichier avec les frequences des caracteres
	 */
	public void creerFichierFrequence() {
		jop = new JOptionPane();
		int option = jop.showConfirmDialog(null, "Voulez-vous générer un fichier avec les fréquences", "Fréquence", JOptionPane.YES_NO_OPTION);
		if(option == JOptionPane.NO_OPTION)
			return;

		//Permet de connaitre le chemin de creation du fichier
		String nom = (this.fichier.getAbsolutePath()).subSequence(0, this.fichier.getAbsolutePath().length() -4) + "_Frequence.txt";			

		//Creer le fichier et y met le texte modifie
		try {
			FileWriter fichierLu = new FileWriter(nom);
			BufferedWriter ecritFichier = new BufferedWriter(fichierLu);
			ecritFichier.write(this.listeFreq.toString());
			ecritFichier.close();
		} 
		catch (IOException e) {
			System.err.println("Erreur, impossible de créer le fichier");
			e.printStackTrace();
		}

		jop.showMessageDialog(null, "Le fichier des fréquences à été créer à cotés du fichier originel", "Fichier des fréquence", JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Passe un fichier texte en texte code avec l'algorithme de Huffman
	 */
	public void texteToHuffman(){

		this.choixFichier();

		this.lireFichierTexte();

		this.calculFrequenceFichier();

		this.creationArbreHuffman();

		this.encodageHuffman();

		this.creerFichierEncode();

		this.creerFichierFrequence();

//		texte = this.newTexte;
//		this.decoderHuffman();
//		
//		this.creerFichierDecoder();

		JOptionPane jop = new JOptionPane();
		jop.showMessageDialog(null, "Le traitement du texte est terminé", "Fin", JOptionPane.PLAIN_MESSAGE);
	}

	/**
	 * Choisi le fichier des frequences a lire
	 */
	public void choixFichierFrequence() {
		jop = new JOptionPane();
		jop.showMessageDialog(null, "Choissisez le fichier des fréquences à lire", "Fréquence du texte", JOptionPane.INFORMATION_MESSAGE);

		path.setDialogTitle("Choisir le fichier des fréquences");
		if(path.showOpenDialog(null) == JFileChooser.APPROVE_OPTION ) {
			fichier = path.getSelectedFile();			
		}
		else {
			System.err.println("Aucun fichier selctionné...");
			return;
		}
	}


	/**
	 * Lis le fichier des frequences et sauvegarde le contenu dans texte
	 */
	public void lireFichierFrequence() {
		try {
			LineNumberReader lnr = new LineNumberReader(new FileReader(fichier));
			int car;

			//Passage du texte du fichier vers la variable "texte"
			while((car = lnr.read()) != -1) {
				if(lnr.getLineNumber() == -1)
					break;
				txtFreq += (char)car;		
			}

		} 
		catch (FileNotFoundException e) {
			System.out.println("Erreur, fichier non trouvé..");
			e.printStackTrace();
		} 
		catch (IOException e) {
			System.err.println("Erreur, lecture de ligne non faite..");
			e.printStackTrace();
		}
	}


	/**
	 * Creer la Liste des frequences et l'arbre de hufman associe
	 */
	public void fichierFrequenceToHuffman() {
		String freqTemp = "";
		char charTemp = ' ';
		boolean car = false;

		int debut =0, fin = 1;

		//Lis le fichier et quand la ligne est fini ajoute la valeur dans la Liste, valeur = (caractere, frequence)
		while(fin < txtFreq.length()) {
			if(!car) {
				charTemp = txtFreq.charAt(fin-1);
				debut = fin;
				fin++; //Saute la virgule
				car = true;
			}
			else {
				if(txtFreq.charAt(fin) == '\n') {
					debut++;
					freqTemp = new String(txtFreq.substring(debut, fin));

					ArbreBin arbre = new ArbreBin(charTemp);
					arbre.setFrequence(Integer.parseInt(freqTemp));
					this.listeFreq = this.listeFreq.insererOrd(arbre);
					car = false;
					debut = fin;
					fin++;
				}
				fin++;
			}
		}
		//Creation de l'arbre de huffman
		this.huffman = new ArbreBin();
		this.huffman = new ArbreBin().huffman(this.listeFreq);

		//Definit les valeurs de chaque sommet de l'arbre
		huffman.setValBinaire("");
		huffman.cleanCar();
	}


	/**
	 * Decode le texte enregistrer dans la variable texte
	 */
	public void decoderHuffman() {
		String rep = "";
		String temp = "";
		int debut = 0, fin = 1;
		while(fin <= texte.length()) {
			temp = huffman.chercherCharEncodage(texte.substring(debut, fin));
			if(temp != "" ) {
				rep += temp;
				debut = fin;
				fin++;
			}
			else
				fin++;
		}
		newTexte = rep;
	}


	/**
	 * Creer le fichier avec le texte decoder
	 */
	public void creerFichierDecoder() {

		//Permet de connaitre le chemin de creation du fichier
		String nom = (this.fichier.getAbsolutePath()).subSequence(0, this.fichier.getAbsolutePath().length() -4) + "_Decoder.txt";			

		//Creer le fichier et y met le texte modifie
		try {
			Writer fichierLu;
			fichierLu = new OutputStreamWriter(new FileOutputStream(nom), StandardCharsets.UTF_8);

			BufferedWriter ecritFichier = new BufferedWriter(fichierLu);
			ecritFichier.write(this.newTexte);
			ecritFichier.close();
		} 
		catch (IOException e) {
			System.err.println("Erreur, impossible de créer le fichier");
			e.printStackTrace();
		}
		jop.showMessageDialog(null, "Le fichier décodé a été crée à cotés du fichier originel", "Fichier décoder", JOptionPane.INFORMATION_MESSAGE);
	}


	/**
	 * Passe un texte encoder en phrase, a partir d'un tableau de frequence
	 */
	public void huffmanToTexte() {

		this.choixFichierFrequence();

		this.lireFichierFrequence();

		this.fichierFrequenceToHuffman();

		this.choixFichier();

		this.lireFichierTexte();

		this.decoderHuffman();

		this.creerFichierDecoder();
	}

	/**
	 * Verifie si un caractere est present dans tabFrequence, s'il existe sa frequence est incremente
	 * @param char car
	 * @return boolean
	 */
	private boolean existeDeja(char car, String lst) {
		for(int i = 0 ; i < lst.length(); i++)
			if(lst.charAt(i) == car)
				return true;
		
		return false;
	}


	/***
	 * Renvoie la liste des fréquences
	 * @return Liste tabFreq
	 */
	public Liste getListe() {
		return this.listeFreq;
	}


	public static void main(String[] args) {

		LecteurFichier fd = new LecteurFichier();	
		fd.texteToHuffman();

		System.out.println("\nAffiche Liste d'ArbreBin (PAS AarrayList)");
		System.out.println(fd.getListe());
		System.out.println("Affichage Liste d'arbreBin Fin....");

		ArbreBin arb = new ArbreBin();

		arb = arb.huffman(fd.getListe());

		ArbreBin huf;
		huf = arb.huffman(fd.getListe());


		System.out.println("\nAffichage huffman");	
		System.out.println(huf);
		System.out.println("Affichage huffman fini.......");


		System.out.println("\nAffiche val binaire");
		huf.setValBinaire("");
		huf.cleanCar();
		huf.afficheValBin();
		System.out.println("Affiche val binaire fin .........");


		System.out.println("\nAffichage profondeur");
		System.out.println(huf.profondeur());
		System.out.println("Fin profondeur.........");

		arb.huffman(fd.getListe()).afficheInfixe();
	}
}

/**
 * 
 * @author Mikael FERREIRA DE ALMEIDA 3F1 et Jeremie HENRION 3F1
 *
 */
public class ArbreBin {
	private int freq = 1;
	private char caractere;
	private String valEncodage = "";

	private boolean vide;
	private boolean existe = true;//permet de savoir si c'est une feuile
	private ArbreBin filsGauche;
	private ArbreBin filsDroit;


	public ArbreBin(){
		this.vide = true;
	}	
	public ArbreBin(char caractere) {
		this.caractere = caractere;
		this.vide = false;
		this.filsDroit = new ArbreBin();
		this.filsGauche = new ArbreBin();
	}
	public ArbreBin(char caractere, ArbreBin filsGauche, ArbreBin filsDroit) {
		this.caractere = caractere;
		this.vide = false;
		this.filsGauche = filsGauche;
		this.filsDroit = filsDroit;
	}

	public boolean vide() {
		return vide;
	}
	public ArbreBin filsDroit() {
		return this.filsDroit;
	}
	public ArbreBin filsGauche() {
		return this.filsGauche;
	}

	public char getCaractere() {
		return caractere;
	}		
	public int getFrequence(){
		return freq;
	}
	public String getValEncodage() {
		return this.valEncodage;
	}

	public void setFrequence(int freq) {
		this.freq = freq;
	}
	public void setGauche(ArbreBin fg) {
		vide = false;
		this.filsGauche = fg;
	}
	public void setDroit(ArbreBin fd) {
		vide = false;
		this.filsDroit = fd;
	}
	
	/**
	 * Recherche un caractere passe en parametre et renvoi sa valeur d'encodage
	 * @param c
	 * @return
	 */
	public String chercheValEncodage(char c) {	
		if(c == this.getCaractere())
			return this.valEncodage;
		if(!this.vide()) {
			if(this.filsGauche().chercheValEncodage(c) != "")
				return this.filsGauche().chercheValEncodage(c);
			
			if(this.filsDroit().chercheValEncodage(c) != "")
				return this.filsDroit().chercheValEncodage(c);		
		}
		return "";
	}

	/**
	 * Renvoie le caractere associe à une valeur d'encodage, exemple : 00101 -> e
	 * @param encodage
	 * @return caractere
	 */
	public String chercherCharEncodage(String encodage) {
		if( this.existe && this.valEncodage.equals(encodage))
			return String.valueOf(this.caractere);
		
		if(!this.vide()) {
			String ret = this.filsGauche().chercherCharEncodage(encodage);
			if( ret != "")
				return ret;
			
			ret = this.filsDroit().chercherCharEncodage(encodage);
			if(ret != "")
				return ret;
		}
		return "";
	}
 
	/**
	 * Donne une valeur binaire à chaque sommet de l'arbre 
	 * @param String ; a initialiser avec "" comme parametre
	 */
	public void setValBinaire(String bin) {
		if(!this.vide()){
			this.filsGauche().setValBinaire(bin + "0");
			this.filsDroit().setValBinaire(bin + "1");
			this.valEncodage = bin ;
		}
	} 
 
	/**
	 * Affiche caractere,frequence
	 */
	@Override
	public String toString() {
	
		return caractere + "," + freq;
	}
	
	/**
	 * Retourne un arbre de Hufmann (cf : Fin Poly Algo Glouton)
	 * @return
	 */
	public ArbreBin huffman(Liste liste) {

		if(liste.reste() != null && !liste.reste().vide()) {

			ArbreBin z = new ArbreBin();

			//On prend le premier arbre de liste
			ArbreBin fg = liste.tete();
			// devient le fils gauche du nouvelle arbre
			z.setGauche(fg); 

			//On prend le second arbre de liste
			ArbreBin fd = liste.reste().tete();
			// devient le fils droit du nouvelle arbre
			z.setDroit(fd);

			//Calcul de la fréquence à la racine du nouvelle arbre
			z.setFrequence(fg.getFrequence() + fd.getFrequence());
			//System.out.println(fg.getFrequence() + fd.getFrequence());
			return huffman(liste.reste().reste().insererOrd(z));
		} 	
		//Un seul arbre dans liste --> arbre de huffman
		return liste.tete();
	}

	/**
	 * Retire les valeurs des sommes ne contenant pas de caractere
	 * Je pense qu'il va être inutile
	 */
	public void cleanCar() {
		if(this.filsDroit.vide() && this.filsGauche().vide()) {

		}
		else {
			this.valEncodage = "";
			this.existe = false;
			this.filsDroit().cleanCar();
			this.filsGauche().cleanCar();
		}
	}

	/**
	 * Affiche les valeurs binaires des caractere
	 */
	public void afficheValBin() {
		if(!this.filsGauche().vide()) 
			this.filsGauche().afficheValBin();

		if(!this.vide())
			System.out.print(this.getCaractere() + " - " + this.getFrequence() + " : " + this.getValEncodage() +"\n");

		if(!this.filsDroit().vide())
			this.filsDroit().afficheValBin();

	}

	public void afficheInfixe() {
		if(!this.filsGauche().vide()) 
			this.filsGauche().afficheInfixe();

		if(!this.vide())
			System.out.print(this.getCaractere() + " - " + this.getFrequence() + "\n");

		if(!this.filsDroit().vide())
			this.filsDroit().afficheInfixe();

	}

	/**
	 * Incremente la frequence d'un caractere
	 */
	public void incrementeFrequence() {
		freq++;
	}

	/**
	 * Mesure la profondeur
	 * @return
	 */
	public int profondeur() {
		if(!vide)
			return this.filsDroit().profondeur() + 1;
		else
			return 0;
	}

	public static void main(String[] args) {
		ArbreBin a,b,c,d,e,f,g;
		a = new ArbreBin();

		System.out.println(a.vide());

		a = new ArbreBin('E');
		b = new ArbreBin('Z');
		c = new ArbreBin('Q');
		d = new ArbreBin('N');
		e = new ArbreBin('M');
		f = new ArbreBin('D');
		g = new ArbreBin('W');
		for(int i = 0; i < 1000 ; i++) {
			if(Math.random() < 0.378545805)
				a.incrementeFrequence();
			else
				b.incrementeFrequence();			
		}
		System.out.println(a + "\t" + b);
		b.setGauche(e); 
		b.setDroit(f);
		c.setGauche(d);
		c.setDroit(g);
		a.setGauche(b);
		a.setDroit(c);
		/*
		  		________a_______
		  	____b___		____c____
		  e(00)   f(01)   d(10)	g(01)
		 */ 


		System.out.println("\nAffiche infixe");
		a.afficheInfixe();

		a.setValBinaire("");
		System.out.println("\nAffiche val binaire");
		a.afficheValBin();
		System.out.println("Fin val binaire........");

		a.cleanCar();
		System.out.println("\nClean des valeur ");
		System.out.println("     ________a_______\r\n" 
				+ " ____b___        ____c____\r\n" 
				+ "e(00)   f(01)  d(10)	g(01)\n");
		a.afficheValBin();
		System.out.println("Fin clean valeur......");


		a = new ArbreBin('E');
		b = new ArbreBin('Z');
		c = new ArbreBin('Q');
		d = new ArbreBin('N');
		e = new ArbreBin('M');
		for(int i = 0; i < 1000 ; i++) {
			double t = Math.random();
			if( t < 0.1)
				a.incrementeFrequence();
			else if (t< 0.3)
				b.incrementeFrequence();
			else if(t < 0.7)
				c.incrementeFrequence();
			else if(t < 0.75)
				d.incrementeFrequence();
			else
				e.incrementeFrequence();
		}
		System.out.println("\nNouveau ArbrebIn pour la Liste");
		System.out.println(a + ""+ b + c + d + e);
		System.out.println("Fin des nouveau ArbreBin......");


		Liste l; 
		l = new Liste(a);
		l = l.insererOrd(b);
		l = l.insererOrd(c);
		l = l.insererOrd(d);
		l = l.insererOrd(e);	
		System.out.println("\nListe ordonnée");
		System.out.println(l);
		System.out.println("Fin liste ordonnée");


		ArbreBin huf;
		huf = new ArbreBin().huffman(l);
		System.out.println("\n\nCreation ArbreHuffman");
		System.out.println(huf);
		System.out.println("Fin creation huffman.....");
	}

}

/**
 * 
 * @author Mikael FERREIRA DE ALMEIDA 3F1 et Jeremie HENRION 3F1
 *
 */
public class Liste {
	private ArbreBin tete;
	private boolean vide;
	private Liste reste;

	public Liste() {
		vide = true;
	}
	public Liste(ArbreBin tete, Liste reste) {
		this.tete = tete;
		this.reste = reste;
		this.vide = false;
	}
	public Liste(ArbreBin tete) {
		this.tete = tete;
		this.reste = new Liste();
		this.vide = false;
	}

	/**
	 * Retourne vrai si la liste est vide
	 * @return
	 */
	public boolean vide() {
		return this.vide;
	}

	/**
	 * Retourne la valeur contenue dans la tete
	 * @return
	 */
	public ArbreBin tete(){
		return this.tete;
	}

	/**
	 * Retourne la liste obtenue en enlevant la tete
	 * @return
	 */
	public Liste reste(){
		return this.reste;
	}

	/**
	 * Retourne la liste obtenue en inserant val en tete
	 * @param val : Object
	 * @return
	 */
	public Liste prefixerVal(ArbreBin val) {
		Liste lst = new Liste(val, this);
		return lst;
	}

	/**
	 * Affiche les valeurs contenues dans une Liste
	 * @param uneListe
	 */
	public static void afficherListe(Liste uneListe) {
		if( !uneListe.vide()) {
			System.out.print(uneListe.tete() + "\n");
			afficherListe(uneListe.reste());
		}
	}

	/**
	 * Retourne la Liste résultant de l’insertion d’une information val dans une liste non ordonnée passée en paramètre, l'insertion se fait en fin de liste. 
	 * @param ArbreBin val
	 * @return Liste
	 */
	public Liste inserer(ArbreBin val) {
		if(!vide) 
			return this.reste.inserer(val).prefixerVal(this.tete);
		else
			return this.prefixerVal(val);	
	}

	/**
	 * Retourne la Liste résultant de l’insertion d’une information val dans une liste ordonnée (par ordre croissant) passée en paramètre.
	 * @param ArbreBin val
	 * @return Liste
	 */
	public Liste insererOrd(ArbreBin val) {
		if(!vide) {
			if(this.tete.getFrequence() < val.getFrequence())
				return (this.reste.insererOrd(val)).prefixerVal(this.tete);

			else if(this.tete.getFrequence() == val.getFrequence()) {
				if(this.tete.getCaractere() < val.getCaractere())
					return (this.reste.insererOrd(val).prefixerVal(this.tete));
				else
					return this.prefixerVal(val);
			}
			else
				return this.prefixerVal(val);
		}
		else
			return this.inserer(val);
	}

	/**
	 * Renvoie vrai si une information val donnée se trouve dans une liste passée en paramètre et incremente sa fréquence également
	 * @param Liste liste
	 * @param ArbreBin val
	 * @return boolean
	 */
	public boolean appartenance(Liste liste, ArbreBin val) {
		if(!liste.vide())
			if(liste.tete().getCaractere() == val.getCaractere()) {
				liste.tete().incrementeFrequence();
				return true;
			}				
			else
				return appartenance(liste.reste(), val);
		else { 
			return false;
		}
	} 

	@Override
	public String toString() {
		if(vide)
			return "";
		else
			return tete + "\n" + reste.toString();
	}

	public static void main(String[] args) {
		Liste l = new Liste();
		/*		
		l = insererOrd(l, new ArbreBin('a'));		
		l = insererOrd(l, new ArbreBin('b'));	
		l = insererOrd(l, new ArbreBin('c'));
		 */		
		l = l.insererOrd( new ArbreBin('a'));		
		l = l.insererOrd( new ArbreBin('b'));	
		l = l.insererOrd( new ArbreBin('c'));

		System.out.println(l);
		Liste.afficherListe(l);
	}

}

import java.util.ArrayList;
import java.util.Random;

/** La classe Plateau correspond a l'agancement des cases d'un plateau
 * de taquin
 * @author      Philippe Leleux
 * @version     1.0
 */

public class Plateau {
	public int[][] plateau;       //Le plateau en tableau d'entiers
	public int n;                 //dimension d'un coté du plateau
	public Plateau parent;        //parent du Plateau (précédent)
	public int choixHeuristique;  //choix de l'heuristique
	public int heuristique;       //heuristique du Plateau
	
	/** Constructeur de Plateau avec sa dimension et le choix de l'heuristique
	 * @param _n dimension d'un coté du plateau
	 * @param _choixHeuristique choix de l'heuristique du plateau
	 */
	public Plateau (int _n, int _choixHeuristique) {
		this.plateau = new int[_n][_n];
		this.n = _n;
		this.parent = null;
		this.choixHeuristique = _choixHeuristique;
	}
	
	/** Constructeur de Plateau avec sa dimension,le choix de l'heuristique et
	 * le contenu du plateau
	 * @param _n dimension d'un coté du plateau
	 * @param _choixHeuristique choix de l'heuristique du plateau
	 * @param formeAParser le contenu du plateau sous forme de chaine de caracteres
	 * @exception FormatPlateauIncorrect pour un format de plateau incorrect
	 */	
	public Plateau (int _n, int _choixHeuristique, String formeAParser) throws FormatPlateauIncorrect {
		String[] temp = formeAParser.split(", ");  //les contenus des cases
		if (temp.length == _n*_n) {
			this.n = _n;
			this.choixHeuristique = _choixHeuristique;
			this.plateau = new int[n][n];
			this.parent = null;
			for(int i=0; i<n*n; ++i) {
				this.plateau[i/n][i%n] = Integer.parseInt(temp[i]);
			}
		} else {
			throw new FormatPlateauIncorrect();
		}
	}
	
	/** renvoie la liste des voisins d'un plateau : les plateaux que l'on peut 
	 * obtenir a partir de l'actuel en deplaçant la case vide
	 * @return la liste des voisins du plateau actuel
	 * @exception ChoixHeuristiqueIncorrect pour un mauvais choix d'heuristique
	 */
	public ArrayList<Plateau> getVoisins() throws ChoixHeuristiqueIncorrect {
		Position vide = this.getPositionVide();  //position de la case vide
		ArrayList<Plateau> voisins = new ArrayList<Plateau>();
		                                         //liste des voisins du plateau
		//premiere ligne
		if(vide.x==0) {
			//premiere colonne
			if(vide.y==0) {
				voisins.add(this.creerVoisin(new Position(0, 1), vide));
				voisins.add(this.creerVoisin(new Position(1, 0), vide));
			//derniere colonne
			} else if(vide.y==n-1) {
				voisins.add(this.creerVoisin(new Position(0, n-2), vide));
				voisins.add(this.creerVoisin(new Position(1, n-1), vide));
			//milieu
			} else {
				voisins.add(this.creerVoisin(new Position(0, vide.y-1), vide));
				voisins.add(this.creerVoisin(new Position(0, vide.y+1), vide));
				voisins.add(this.creerVoisin(new Position(1, vide.y), vide));		
			}
		//derniere ligne
		} else if(vide.x==n-1) {
			//premiere colonne
			if(vide.y==0) {
				voisins.add(this.creerVoisin(new Position(n-1, 1), vide));
				voisins.add(this.creerVoisin(new Position(n-2, 0), vide));
			//derniere colonne
			} else if(vide.y==n-1){
				voisins.add(this.creerVoisin(new Position(n-1, n-2), vide));
				voisins.add(this.creerVoisin(new Position(n-2, n-1), vide));
			//milieu
			} else {
				voisins.add(this.creerVoisin(new Position(n-1, vide.y-1), vide));
				voisins.add(this.creerVoisin(new Position(n-1, vide.y+1), vide));
				voisins.add(this.creerVoisin(new Position(n-2, vide.y), vide));
			}
		//milieu
		} else {
			//premiere colonne
			if(vide.y==0) {
				voisins.add(this.creerVoisin(new Position(vide.x-1, 0), vide));
				voisins.add(this.creerVoisin(new Position(vide.x+1, 0), vide));
				voisins.add(this.creerVoisin(new Position(vide.x, 1), vide));
			//derniere colonne
			} else if (vide.y==n-1){
				voisins.add(this.creerVoisin(new Position(vide.x-1, n-1), vide));
				voisins.add(this.creerVoisin(new Position(vide.x+1, n-1), vide));
				voisins.add(this.creerVoisin(new Position(vide.x, n-2), vide));
			//milieu
			} else {
				voisins.add(this.creerVoisin(new Position(vide.x-1, vide.y), vide));
				voisins.add(this.creerVoisin(new Position(vide.x+1, vide.y), vide));
				voisins.add(this.creerVoisin(new Position(vide.x, vide.y-1), vide));
				voisins.add(this.creerVoisin(new Position(vide.x, vide.y+1), vide));			
			}
		}
		return voisins;
	}
	
	/** renvoie la position vide du plateau
	 * @return la position vide
	 */
	public Position getPositionVide () {
		Position result = null;  //résultat
		for(int i=0; i<n*n; ++i) {
			if (this.plateau[i/n][i%n]==0) {
				result = new Position(i/n, i%n);
			}
		}
		return result;
	}
	
	/** Crée un voisin à partir de la position à mettre au vide et de la position
	 * de la case vide
	 * @param position la position que l'on va amener à la place vide
	 * @param vide la position de la case vide
	 * @exception ChoixHeuristiqueIncorrect pour un choix d'heuristique incorrect
	 */ 
	public Plateau creerVoisin(Position position, Position vide) throws ChoixHeuristiqueIncorrect {
		Plateau voisin = this.clone();
		voisin.plateau[vide.x][vide.y] = this.plateau[position.x][position.y];
		voisin.plateau[position.x][position.y] = 0;
		voisin.parent = this;
		voisin.calculHeuristique(this.choixHeuristique, this);
		return voisin;
	}
	
	/** Renvoie un clone du plateau : plateau avec les même cases
	 * @return le plateau clone
	 */
	public Plateau clone() {
		Plateau clone = new Plateau(n, choixHeuristique);
		                                  //résultat
		for(int i=0; i<n*n; i++) {
			clone.plateau[i/n][i%n] = this.plateau[i/n][i%n];
		}
		return clone;
	}
	
	/** renvoie un plateau contenant les mêmes cases que la destination
	 * @param n dimension d'un coté du plateau
	 * @param choixHeuristique le choix de l'heuristique
	 * @return le plateau destination
	 */
	public static Plateau destination(int n, int choixHeuristique) {
		Plateau destination = new Plateau(n, choixHeuristique);
		for(int i=0; i<n*n-1; ++i) {
			destination.plateau[i/n][i%n] = i+1;
		}
		destination.plateau[n-1][n-1] = 0;
		return destination;
	}
	
	/** vérifie si deux plateau ont les mêmes cases
	 * @param plateau le plateau avec lequel on compare
	 * @return booléen vrai si les deux plateau sont identiques
	 */
	public boolean matches (Plateau plateau) {
		for(int i=0; i<n*n; ++i) {
			if(this.plateau[i/n][i%n]!=plateau.plateau[i/n][i%n]) {
				return false;
			}
		}
		return true;
	}
	
	/** vérifie si le plateau appartient à la liste de plateaux et renvoie
	 * sa position si c'est le cas
	 * @param liste la liste de plateaux
	 * @return la position du plateau si il appartiuent a la liste, -1 sinon
	 */
	public int appartient (ArrayList<Plateau> liste) {
		for(int i=0; i<liste.size(); ++i) {
			if(this.matches(liste.get(i))) {
				return i;
			}
		}
		return -1;
	}
	
	/** renvoie la dimension d'un coté du plateau
	 * @return la dimension d'un coté du plateau
	 */
	public int getN() {
		return this.n;
	}
	
	/** calcule l'heuristique du plateau courant
	 * @param choix l'heuristique choisie
	 * @param courant le Plateau courant
	 */
	public void calculHeuristique(int choix, Plateau courant) throws ChoixHeuristiqueIncorrect {
		Plateau destination = Plateau.destination(n, choixHeuristique);
		int g = 0;
		//Heuristique
			//Nombre de cases mal placées
			if(choix==0) {
				for(int i=0; i<n*n; ++i) {
					if(this.plateau[i/n][i%n]!=destination.plateau[i/n][i%n]) {
						g++;
					}
				}
			//Distance euclidienne avec la bonne position
			} else if(choix==1) {
				Position temp;
				for(int i=0; i<n*n; ++i) {
					temp = destination.situer(this.plateau[i/n][i%n]);
					g+=(i/n-temp.x)^2;
					g+=(i%n-temp.y)^2;
				}
			//Distance Manhattan (ou TaxiBlock)			
			} else if(choix==2) {
				Position temp;
				for(int i=0; i<n*n; ++i) {
					temp = destination.situer(this.plateau[i/n][i%n]);
					g+=Math.abs(i/n-temp.x);
					g+=Math.abs(i%n-temp.y);
				}
			} else {
				throw new ChoixHeuristiqueIncorrect();
			}
		//Distance avec le plateau précédent
		int h = 0;
		Position temp;
		for(int i=0; i<n*n; ++i) {
			temp = courant.situer(this.plateau[i/n][i%n]);
			h+=(i/n-temp.x)^2;
			h+=(i%n-temp.y)^2;
		}
		this.heuristique = g+h;
	}
	
	/** renvoie la position de la première occurence de num
	 * @param num la case a situer
	 * @return la position de cette case
	 */
	public Position situer(int num) {
		for(int i=0; i<n*n; ++i) {
			if(this.plateau[i/n][i%n]==num) {
				return new Position(i/n, i%n);
			}
		}
		return null;
	}
	
	/** renvoie un plateau mélangé aléatoirement à partir du plateau final
	 * @param nombreMelanges le nombres de déplacements aléatoires depuis l'état final
	 * @return le plateau mélangé
	 * @exception ChoixHeuristiqueIncorrect pour un choix d'heuristique incorrect
	 */	
	public Plateau aleatoire(int nombreMelanges) throws ChoixHeuristiqueIncorrect {
		Plateau aleatoire = Plateau.destination(n, choixHeuristique);
		                               //Plateau mélangé
		Random random = new Random();  //fonction aléatoire 
		ArrayList<Plateau> voisins;    //liste des voisins du plateau
		int indice;                    //rang aléatoire du voisin
		for (int i = 0; i < nombreMelanges; i++)
		{
			voisins = aleatoire.getVoisins();
			indice = random.nextInt(voisins.size());
			aleatoire = voisins.get(indice);
		}
		aleatoire.parent = null;
		return aleatoire;
	}
	
	/** Renvoie une représentation du plateau sous forme de chaine de caractère
	 * @return la plateau en string
	 */
	public String toString() {
		String ligne = " ";
		for(int i=0; i<n*4-1; ++i) {
			ligne += "-";
		}
		ligne += "\n";
		String plateau = ligne;
		for(int i=0; i<n; ++i) {
			plateau += "| ";
			for(int j=0; j<n; ++j) {
				plateau+=this.plateau[i][j];
				plateau+=" | ";
			}
			plateau += "\n";
			plateau += ligne;
		}
		return plateau;
	}
}

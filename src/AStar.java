import java.util.ArrayList;

/** La classe AStar correspond a l'implantation de l'algorithme A* et des méthodes
 * nécessaires
 * @author      Philippe Leleux
 * @version     1.0
 */

public class AStar {

	ArrayList<Plateau> listeFermee;   //La liste des plateau examinés
	ArrayList<Plateau> listeOuverte;  //La liste des tableaux potentiellement examinables
	
	/** Constructeur de AStar avec le plateau initial
	 * @param initial le plateau initial pour l'algorithme
	 */
	public AStar(Plateau initial) {
		listeFermee = new ArrayList<Plateau>();   //La liste des plateau examinés
		listeOuverte = new ArrayList<Plateau>();  //La liste des tableaux potentiellement examinables
		this.listeFermee.add(initial);
		this.listeOuverte = new ArrayList<Plateau>();
	}
	
	/** l'implantation de l'algorithme A*
	 * @return le chemin de résolution du taquin
	 * @exception PasDeSolution si le taquin n'est pas résoluble
	 * @exception ChoixHeuristiqueIncorrect pour un choix d'heuristique incorrect
	 */
	public ArrayList<Plateau> algo() throws PasDeSolution, ChoixHeuristiqueIncorrect {
		Plateau courant = listeFermee.get(0);  //le plateau courant
		Plateau destination = Plateau.destination(courant.getN(), courant.choixHeuristique);
		                                       //le plateau solution du taquin
		Plateau temp;                          //Plateau temporaire pour les calculs
		ArrayList<Plateau> voisins;            //liste des voisins du plateau courant
		int rang;                              //rang du plateau courant dans la liste ouverte
		int count = 0;                         //compteur des itérations
		while(!courant.matches(destination)) {
			if (count%1000==0) {
				System.out.println(count);
			}
			voisins = courant.getVoisins();
			//pour tous les voisins
			for(int i=0; i<voisins.size(); ++i) {
				temp = voisins.get(i);
				//si il appartient a la liste fermée on oublie
				if(temp.appartient(listeFermee)!=-1) {					
				} else {
					//calcul des heuristiques de la liste ouverte et du plateau courant
					for(int j=0; j<listeOuverte.size(); ++j) {
						listeOuverte.get(j).calculHeuristique(courant.choixHeuristique, courant);
					}
					courant.calculHeuristique(courant.choixHeuristique, courant);
					//si il appartient a la liste ouverte on prend le meilleur
					if(temp.appartient(listeOuverte)!=-1) {
						rang = temp.appartient(listeOuverte);
						if(listeOuverte.get(rang).heuristique > temp.heuristique) {
							listeOuverte.get(rang).heuristique = temp.heuristique;
							listeOuverte.get(rang).parent = temp.parent;
						}
					//sinon on l'ajoute
					} else {
						listeOuverte.add(temp);
					}
				}
			}
			//si la liste ouverte est vide il n'y a pas de solution
			if(listeOuverte.size()==0) {
				throw new PasDeSolution();
			//sinon on prend le meilleur de la liste ouverte en courant
			} else {
					courant = listeOuverte.remove(this.meilleurPlateau());
					listeFermee.add(courant);
			}
			++count;
		}
		return this.reconstruireChemin(destination);
	}
	
	/** renvoie la position du meilleur plateau : celui qui a la plus petite
	 * heuristique
	 * @return la position du meilleur plateau dans la liste
	 */
	public int meilleurPlateau() {
		int min = 1000;  //résultat
		for(int i=0; i<listeOuverte.size(); ++i) {
			if(min>listeOuverte.get(i).heuristique) {
				min = i;
			}
		}
		return min;
	}
	
	/** reconstruit et renvoi le chemin de résolution du taquin
	 * @param destination le plateau solution du taquin
	 * @return la liste des Plateau constituants le chemin de résolution
	 */
	public ArrayList<Plateau> reconstruireChemin(Plateau destination) {
		Plateau courant = listeFermee.get(destination.appartient(listeFermee));
		                                    //le plateau courant
		ArrayList<Plateau> chemin = new ArrayList<Plateau>();
		                                    //résultat
		while(courant!=null) {
			chemin.add(courant);
			courant = courant.parent;
		}
		return chemin;
	}
}

import java.util.ArrayList;

/** Classe de test de réolution de taquin avec l'algorithme A*
 * Pour lancer l'application : java MainProbleme [dimension d'un cote du plateau] [choix de l'heuristique]
 * Paramètres : dimension d'un des coté du plateau
 *              choix de l'heuristique
 * @author      Philippe Leleux
 * @version     1.0
 */ 

public class MainResolution {
	
	public static void main(String[] args) {
		try {
			long t0=System.currentTimeMillis();
			if(args.length!=3) {
				throw new ChoixEnEntreeInvalide();
			}
			Plateau aleatoire = (new Plateau(Integer.parseInt(args[0]), Integer.parseInt(args[2]))).aleatoire(Integer.parseInt(args[1]));
			System.out.println("Le plateau de départ est :\n" + aleatoire + "\nAvec l'heuristique " + args[2]);
			AStar solver = new AStar(aleatoire);
			ArrayList<Plateau> solution = solver.algo();
			System.out.println("Chemin pour y arriver :\n");
			for(int i=solution.size()-1; i>=0; --i) {
				System.out.println(solution.get(i).toString());
			}
			long t1=System.currentTimeMillis();
			System.out.println("durée : "+(t1-t0)+"ms");
		}
		catch (ChoixEnEntreeInvalide e) {
			System.out.println("Pour lancer l'application vous devez entrer : java MainResolution [dimension du taquin] [nombres de déplacements aléatoires au départ] [heuristique]");
		}
		catch (ChoixHeuristiqueIncorrect e) {
			System.out.println("L'heuristique peut etre soit 0 soit 1 soit 2 : \n0 -> nombre de cases mal placées par rapport au plateau final\n1 -> distance euclidienne de chaque case par rapport à sa destination finale\2 -> distance Manhattan (ou Taxi Block) de chaque case par rapport à sa destination finale");
		}
		catch (PasDeSolution e) {
			System.out.println("Le plateau initial n'etait pas valide");
		}
	}

}

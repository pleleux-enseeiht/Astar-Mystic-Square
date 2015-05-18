/** La classe position correspond au contenu d'une case de sudoku 
 * avec ses coordonnées (x, y)
 * @author      Philippe Leleux
 * @version     1.0
 */

public class Position {

	public int x;     //numéro de ligne
	public int y;     //numéro de colonne
	
	/** Constructeur de Position
	 * @param _x numéro de ligne
	 * @param _y numéro de colonne
	 */
	public Position(int _x, int _y) {
		this.x = _x;
		this.y = _y;
	}
}

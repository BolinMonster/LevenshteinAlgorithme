import java.lang.reflect.Array;
import java.util.Arrays;

/**
	* Classe Utilitaire Levenshtein
*/

public final class Levenshtein
{
	/**
		* @return Retourne lun tableau dont les élements ont été décalés.
		* La première case est un espace vide, puis les élements qui suivent.
		* @param chaine1 : la chaîne à insérer dans le tableau. 
	*/
	private static char[] decalageTableau(String chaine1)
	{
		char[] tab = new char[chaine1.length()+1];

		for(int cpt=tab.length-1; cpt>0; cpt--)
			tab[cpt] = chaine1.charAt(cpt-1);

		tab[0] = ' ';

		return tab;
	}

	/**
	 * Affiche le tableau.
	 * @param d : Le tableau contenant les valeurs.
	 * @param tabChaine1 : Le tableau contenant les caractères de la chaîne1.
	 * @param tabChaine2 : Le tableau contenant les caractères de la chaîne2.
	 */
	private static void afficherTableau(int[][] d, char[] tabChaine1, char[] tabChaine2)
	{
		System.out.print( String.format("%2s", " ") );
		for(int cptColonne=0; cptColonne<tabChaine1.length; cptColonne++)
		{
			System.out.print( String.format("%s", tabChaine1[cptColonne]) + " ");
		}
		System.out.println();

		for(int cptLigne=0; cptLigne<tabChaine2.length; cptLigne++)
		{
			if ( cptLigne < tabChaine2.length )
				System.out.print( tabChaine2[cptLigne] + " ");

			for(int cptColonne=0; cptColonne<tabChaine1.length; cptColonne++)
			{
				System.out.print(d[cptLigne][cptColonne] + " ");
			}
			System.out.println();
		}
	}

	/**
		* @return Retourne la distance de Levenshtein.
		* @param chaine1 : La chaîne de caractère numéro 1.
		* @param chaine2 : La chaîne de caractère numéro 2. 
	*/
	public static int distanceLevenshtein(String chaine1, String chaine2)
	{
		char[] tabChaine1 = decalageTableau(chaine1); // colonne
		char[] tabChaine2 = decalageTableau(chaine2); // ligne

		int[][] d = new int[tabChaine2.length][tabChaine1.length];
		int i=0, j=0, coutSubstitution=0; 

		for(i=0; i<tabChaine2.length; i++)
			d[i][0] = i;
		
		for(j=0; j<tabChaine1.length; j++)
			d[0][j] = j;

		for(i=1; i<tabChaine2.length; i++)
		{
			for(j=1; j<tabChaine1.length; j++)
			{
				coutSubstitution = tabChaine2[i] == tabChaine1[j] ? 0 : 1;
				// Retourne le minimum
				d[i][j] = Arrays.stream( new int[] 
						{
							d[i-1][j]+1, // effacement du nouveau caractère (chaine2)
							d[i][j-1]+1, // insertion dans chaine1 du nouveau caractère de chaine2
							d[i-1][j-1]+coutSubstitution, // substitution
						}
				).min().getAsInt();
			}
		}

		// Affichage
		afficherTableau(d, tabChaine1, tabChaine2);

		return d[tabChaine2.length-1][tabChaine1.length-1];
	}

	/**
		* Constructeur privée 
	*/
	private Levenshtein() {}

	public static void main(String[] args) 
	{
		/**	
		System.out.println("Test de la fonction : private static char[] decalageTableau(String chaine1)");
		char[] tab = decalageTableau("test");
		System.out.println(Arrays.toString(tab)); 
		*/

		System.out.println( String.format("%-10s", "=").replace(" ", "=") );
		System.out.println("Distance de Levenshtein");

		String chaine1 = "chiens";
		String chaine2 = "niche";

		System.out.println("Chaine 1 : " + chaine1 + "(taille=" + chaine1.length() + ")");
		System.out.println("Chaine 2 : " + chaine2 + "(taille=" + chaine2.length() + ")");
		
		System.out.println( "Distance : " + distanceLevenshtein(chaine1, chaine2) );
	}
}
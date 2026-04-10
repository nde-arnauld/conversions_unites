package base;

import exceptions.UniteIllegaleException;

import java.util.Arrays;

public class Dimension {
    public static int SIZE = 8;
    private final int[] vecteur;

    /**
     * Constructeur par défaut.
     */
    public Dimension() {
        int[] vec = new int[] {0, 0, 0, 0, 0, 0, 0, 0};
        this(vec);
    }

    /**
     * Ce constructeur permet de créer les dimensions de chaque unité,
     * aidant à vérifier si deux unités sont compatibles.
     * Chaque espace (position) du vecteur définit l'une des 7 dimensions
     * proposées par le SI, plus une dimension pour la monnaie.
     * [LONGUEUR, MASSE, TEMPS, COURANT, TEMPERATURE, SUBSTANCE, INTENSITE, MONNAIE]
     * <br>
     * Exemple :
     * <ul>
     *     <li>[1, 0, 0, 0, 0, 0, 0, 0] : permettra de représenter une unité de longueur.</li>
     *     <li>[1, 0, -1, 0, 0, 0, 0, 0] : permettra de représenter une unité de vitesse (m.s<sup>-1</sup>).</li>
     * </ul>
     * @param vecteur tableau de 8 entiers pour chacun des espaces du vecteur.
     */
    public Dimension(int[] vecteur) {
        if (vecteur == null || vecteur.length != Dimension.SIZE)
            throw new IllegalArgumentException("Le vecteur dimensionnel doit contenir " + Dimension.SIZE + " éléments.");
        this.vecteur = Arrays.copyOf(vecteur, Dimension.SIZE);
    }

    /**
     * Ce constructeur permet de créer une dimension en mettant
     * @param indice
     * @return
     */
    public static Dimension parIndice(int indice) {
        int[] vec = new int[] {0, 0, 0, 0, 0, 0, 0, 0};
        vec[indice] = 1;
        return new Dimension(vec);
    }

    /**
     * Getter permettant de récupérer le vecteur de dimension.
     * @return le vecteur de dimension
     */
    public int[] getVecteur() {
        return this.vecteur;
    }

    /**
     * Cette méthode permet de déterminer si une dimension est simple.
     * Note : Une dimension simple contient au plus un espace de valeur dans son vecteur.
     * @return <b>Vrai</b> si la dimension est simple.
     * <br>Exemple:
     * <br>m^3 → [3, 0, 0, 0, 0, 0, 0, 0]
     * <br>$ → [0, 0, 0, 0, 0, 0, 0, 1]
     */
    public boolean estSimple() {
        int nbDim = 0;
        for (int i = 0; i < this.vecteur.length; i++)
            if (this.vecteur[i] != 0)
                nbDim += 1;
        return nbDim == 1;
    }

    /**
     * Cette méthode permet de multiplier deux vecteurs.
     * @param autre Le deuxième vecteur.
     * @return Le nouveau vecteur obtenu.
     */
    public Dimension multiplier(Dimension autre) {
        int[] resultat = new int[Dimension.SIZE];
        for (int i = 0; i < Dimension.SIZE; i++)
            resultat[i] = this.vecteur[i] + autre.vecteur[i];

        return new Dimension(resultat);
    }

    /**
     * Cette méthode permet de diviser deux vecteurs.
     * @param autre Le deuxième vecteur.
     * @return Le nouveau vecteur obtenu.
     */
    public Dimension diviser(Dimension autre) {
        int[] resultat = new int[Dimension.SIZE];
        for (int i = 0; i < Dimension.SIZE; i++)
            resultat[i] = this.vecteur[i] - autre.vecteur[i];

        return new Dimension(resultat);
    }

    /**
     * Cette méthode permet d'appliquer la puissance à une dimension.
     * @param n L'exposant à appliquer
     * @return La nouvelle dimension obtenue
     */
    public Dimension puissance(int n) {
        int[] resultat = new int[Dimension.SIZE];
        for (int i = 0; i < Dimension.SIZE; i++)
            resultat[i] = this.vecteur[i] * n;
        return new Dimension(resultat);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        Dimension dim = (Dimension) obj;
        return Arrays.equals(this.vecteur, dim.vecteur);
    }

    @Override
    public String toString() {
        String symbole = "";
        symbole += Arrays.toString(vecteur);
        return symbole;
    }

    @Override
    public  int hashCode() {
        return Arrays.hashCode(this.vecteur);
    }
}

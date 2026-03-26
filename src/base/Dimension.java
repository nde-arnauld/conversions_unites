package base;

import java.util.Arrays;

public class Dimension {
    public static int SIZE = 8;
    private final int[] vecteur;

    public Dimension() {
        int[] vect = new int[] {0, 0, 0, 0, 0, 0, 0, 0};
        this(vect);
    }

    /**
     * Cette méthode permet d'instancier une dimension simple en passant le vecteur à l'indice
     * demandé à 1.
     * @param indice La position du vecteur à passer à 1.
     * @return La nouvelle dimension créée.
     */
    public static Dimension simple(int indice) {
        int[] vect = new int[Dimension.SIZE];
        vect[indice] = 1;
        return new Dimension(vect);
    }

    public Dimension(int[] vecteur) {
        if (vecteur.length != Dimension.SIZE)
            throw new IllegalArgumentException("Erreur : La dimension du vecteur passé en paramètre doit être égale à "+ Dimension.SIZE);
        this.vecteur = vecteur.clone();
    }

    public Dimension multiplier(Dimension autre) {
        int[] resultat = new int[Dimension.SIZE];
        for (int i = 0; i < Dimension.SIZE; i++)
            resultat[i] = this.vecteur[i] + autre.vecteur[i];
        return new Dimension(resultat);
    }

    public Dimension diviser(Dimension autre) {
        int[] resultat = new int[Dimension.SIZE];
        for (int i = 0; i < Dimension.SIZE; i++)
            resultat[i] = this.vecteur[i] - autre.vecteur[i];
        return new Dimension(resultat);
    }

    public Dimension puissance(int n) {
        int[] resultat = new int[Dimension.SIZE];
        for (int i = 0; i < Dimension.SIZE; i++)
            resultat[i] = this.vecteur[i] * n;
        return new Dimension(resultat);
    }

    public int[] getVecteur() {
        return Arrays.copyOf(this.vecteur, this.vecteur.length);
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        Dimension dim = (Dimension) obj;
        return Arrays.equals(this.vecteur, dim.vecteur);
    }

    @Override
    public String toString() {
        return Arrays.toString(this.vecteur);
    }
}

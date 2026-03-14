package base;

import java.util.Arrays;

public class Dimension {
    public static int SIZE = 8;
    private int[] vecteur;

    public Dimension() {
        int[] dim = new int[] {0, 0, 0, 0, 0, 0, 0, 0};
        this(dim);
    }

    public Dimension(int[] dimensions) {
        this.vecteur = dimensions;
    }

    public int[] getVecteur() {
        return this.vecteur;
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        Dimension dim = (Dimension) obj;
        return Arrays.equals(this.vecteur, dim.vecteur);
    }

    @Override
    public String toString() {
        String symbole = "";
        symbole += "";
        return symbole;
    }
}

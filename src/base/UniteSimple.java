package base;

import exceptions.DimensionIllegaleException;

public class UniteSimple extends Unite {
    double tauxConversion;

    public UniteSimple() throws DimensionIllegaleException {
        this("", "", new Dimension(), 1.);
    }

    public UniteSimple(String symbole, String nom, Dimension dimension, double taux) throws DimensionIllegaleException {
        for (int i = 0; i < Dimension.SIZE; i++)
            if (dimension.getVecteur()[i] > 1)
                throw new DimensionIllegaleException("Erreur d'instanciation: Une unité simple n'a pas de valeur de dimension > 1.");
        this.symbole = symbole;
        this.nom = nom;
        this.dimension = dimension;
        this.tauxConversion = taux;
    }

    @Override
    public double getTauxConversion() {
        return this.tauxConversion;
    }
}

package base;

import design_patterns.visiteur.Visiteur;
import exceptions.DimensionIllegaleException;
import exceptions.UniteIllegaleException;

public class UniteSimple extends Unite {
    double tauxConversion;

    public UniteSimple() throws DimensionIllegaleException {
        this("", "", new Dimension(), 1.);
    }

    public UniteSimple(String symbole, String nom, Dimension dimension, double taux) throws DimensionIllegaleException {
        if (!dimension.estSimple())
            throw new DimensionIllegaleException("Erreur d'instanciation: Une unité simple n'a pas de valeur de dimension > 1.");
        super(symbole, nom, dimension);
        this.tauxConversion = taux;
    }

    @Override
    public double getTauxConversion() {
        return this.tauxConversion;
    }

    @Override
    public void accepteVisiteur(Visiteur visiteur) throws UniteIllegaleException {
        visiteur.visite(this);
    }
}

package base;

import design_patterns.visiteur.SimplificateurVisiteur;
import design_patterns.visiteur.Visiteur;
import exceptions.UniteIllegaleException;

public class UniteDerivee extends Unite {
    private final double tauxConversion;

    public UniteDerivee(Dimension dimension, double taux) throws UniteIllegaleException {
        super("", dimension);
        this.tauxConversion = taux;
        this.accepteVisiteur(new SimplificateurVisiteur());
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

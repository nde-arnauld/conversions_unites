package base;

import design_patterns.visiteur.SimplificateurVisiteur;
import exceptions.UniteIllegaleException;

import java.util.Arrays;

public class Quantite {
    private double valeur;
    private Unite unite;

    public Quantite(double valeur) {
        this(valeur, null);
    }

    public Quantite(double valeur, Unite unite) {
        this.valeur = valeur;
        this.unite = unite;
    }

    public Quantite addition(Quantite autre) throws UniteIllegaleException {
        if (!this.unite.estCompatible(autre.unite))
            throw new UniteIllegaleException("Erreur : l'addition est impossible car les unités '" + this.unite.getSymbole() + "' et '" + autre.unite.getSymbole() + "' sont incompatibles!");
        // On uniformise les unités
        double taux = autre.unite.convertirVers(this.unite);
        // On additionne les valeurs
        double resultat = this.valeur + autre.valeur * taux;
        return new Quantite(resultat, this.unite);
    }

    public Quantite soustraction(Quantite autre) throws UniteIllegaleException {
        if (!this.unite.estCompatible(autre.unite))
            throw new UniteIllegaleException("Erreur : la soustraction est impossible car les unités '" + this.unite.getSymbole() + "' et '" + autre.unite.getSymbole() + "' sont incompatibles!");
        // On uniformise les unités
        double taux = autre.unite.convertirVers(this.unite);
        // On soustrait les valeurs
        double resultat = this.valeur - autre.valeur * taux;
        return new Quantite(resultat, this.unite);
    }

    public Quantite multiplication(Quantite autre) throws UniteIllegaleException {
        double resultat = this.valeur * this.unite.getTauxConversion() * autre.valeur * autre.unite.getTauxConversion();
        Dimension dimension = this.unite.getDimension().multiplier(autre.unite.getDimension());
        UniteDerivee u = new UniteDerivee(dimension, 1.0);
        Quantite finale = new Quantite(resultat, u);
        finale.simplifier();
        return finale;
    }

    public Quantite division(Quantite autre) throws UniteIllegaleException {
        double resultat = (this.valeur * this.unite.getTauxConversion()) / (autre.valeur * autre.unite.getTauxConversion());
        Dimension dimension = this.unite.getDimension().diviser(autre.unite.getDimension());
        UniteDerivee u = new UniteDerivee(dimension, 1.0);
        Quantite finale = new Quantite(resultat, u);
        finale.simplifier();
        return finale;
    }

    public void simplifier() throws UniteIllegaleException {
        this.unite.accepteVisiteur(new SimplificateurVisiteur());
    }

    public Quantite convertirVers(Unite cible) throws UniteIllegaleException {
        double resultat = this.valeur * this.unite.convertirVers(cible);
        return new Quantite(resultat, cible);
    }

    public String toString() {
        return this.valeur + " " + this.unite;
    }
}

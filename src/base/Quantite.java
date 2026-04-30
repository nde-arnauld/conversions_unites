package base;
import exceptions.DimensionIllegaleException;
import exceptions.UniteIllegaleException;

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

    /**
     * Cette méthode permet de convertir une quantité vers une autre en fonction de l'unité cible.
     * @param cible L'unité de la quantité obtenue en résultat.
     * @return La nouvelle quantité.
     * @throws UniteIllegaleException Retourne une exception si l'unité cible n'est pas compatible
     * avec l'unité de départ.
     */
    public Quantite convertirVers(Unite cible) throws UniteIllegaleException {
        double resultat = this.valeur * this.unite.convertirVers(cible);
        return new Quantite(resultat, cible);
    }

    public Quantite addition(Quantite autre) throws UniteIllegaleException, DimensionIllegaleException {
        if (!this.unite.getDimension().equals(autre.unite.getDimension()))
            throw new UniteIllegaleException("addition impossible, les grandeurs sont incompatibles.");
        Quantite operateurDroite = autre.alignerSur(this.unite);
        return new Quantite(this.valeur + operateurDroite.valeur, this.unite);
    }

    public Quantite soustraction(Quantite autre) throws UniteIllegaleException, DimensionIllegaleException {
        if (!this.unite.getDimension().equals(autre.unite.getDimension()))
            throw new UniteIllegaleException("soustraction impossible, les grandeurs sont incompatibles.");
        Quantite operateurDroite = autre.alignerSur(this.unite);
        return new Quantite(this.valeur - operateurDroite.valeur, this.unite);
    }

    public Quantite multiplication(Quantite autre) throws DimensionIllegaleException, UniteIllegaleException {
        Quantite operateurDroite = autre.alignerSur(this.unite);

        double resultat = this.valeur * operateurDroite.valeur;
        UniteComposee u = OperationsUnites.multiplier(this.unite, operateurDroite.unite);
        return new Quantite(resultat, u);
    }

    public Quantite division(Quantite autre) throws DimensionIllegaleException, UniteIllegaleException {
        Quantite operateurDroite = autre.alignerSur(this.unite);

        double resultat = this.valeur / operateurDroite.valeur;
        UniteComposee u = OperationsUnites.diviser(this.unite, operateurDroite.unite);
        return new Quantite(resultat, u);
    }

    public Quantite alignerSur(Unite modele) throws UniteIllegaleException {
        Unite nouvelleUnite = OperationsUnites.aligner(this.unite, modele);
        double taux = this.unite.convertirVers(nouvelleUnite);
        return new Quantite(this.valeur * taux, nouvelleUnite);
    }

    @Override
    public String toString() {
        return String.format("%g %s", this.valeur, this.unite);
    }
}

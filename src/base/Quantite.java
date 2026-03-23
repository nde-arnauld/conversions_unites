package base;

import design_patterns.visiteur.SimplificateurVisiteur;
import exceptions.DimensionIllegaleException;
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

    public Quantite addition(Quantite autre) throws UniteIllegaleException {
        if (!this.unite.getDimension().estMemeDimension(autre.unite.getDimension()))
            throw new UniteIllegaleException("Addition impossible : les grandeurs sont incompatibles.");
        double resultat = this.valeur + autre.convertirVers(this.unite).valeur;
        return new Quantite(resultat, this.unite);
    }

    public Quantite soustraction(Quantite autre) throws UniteIllegaleException {
        if (!this.unite.getDimension().estMemeDimension(autre.unite.getDimension()))
            throw new UniteIllegaleException("Soustraction impossible : les grandeurs sont incompatibles.");
        double resultat = this.valeur - autre.convertirVers(this.unite).valeur;
        return new Quantite(resultat, this.unite);
    }

    public Quantite multiplication(Quantite autre) throws DimensionIllegaleException, UniteIllegaleException {
        double resultat = this.valeur * autre.valeur;
        Dimension nDim = this.unite.getDimension().multiplier(autre.unite.getDimension());
        Unite nUnite;
        if (!nDim.estSimple()) {
            UniteComposee nUniteComposee = new UniteComposee(this.unite.getSymbole() + "." + autre.unite.getSymbole(),
                    this.unite.getNom() + "." + autre.unite.getNom(), nDim);
            nUniteComposee.ajouterUnite(this.unite, 1);
            nUniteComposee.ajouterUnite(autre.unite, 1);
            nUnite = nUniteComposee;
        } else {
            nUnite = new UniteSimple(this.unite.getSymbole() + "." + autre.unite.getSymbole(),
                    this.unite.getNom() + "." + autre.unite.getNom(), nDim, 1.0);
        }
        nUnite.accepteVisiteur(new SimplificateurVisiteur());
        return new Quantite(resultat, nUnite);
    }

    public Quantite division(Quantite autre) throws DimensionIllegaleException, UniteIllegaleException {
        double resultat = this.valeur / autre.valeur;
        Dimension nDim = this.unite.getDimension().diviser(autre.unite.getDimension());

        Unite nUnite;
        if (!nDim.estSimple()) {
            UniteComposee nUniteComposee = new UniteComposee(this.unite.getSymbole() + "/" + autre.unite.getSymbole(),
                    this.unite.getNom() + "." + autre.unite.getNom(), nDim);
            nUniteComposee.ajouterUnite(this.unite, 1);
            nUniteComposee.ajouterUnite(autre.unite, -1);
            nUnite = nUniteComposee;
        }
        else nUnite = new UniteSimple(this.unite.getSymbole() + "." + autre.unite.getSymbole(),
                this.unite.getNom() + "." + autre.unite.getNom(), nDim, 1.0);
        nUnite.accepteVisiteur(new SimplificateurVisiteur());
        return new Quantite(resultat, nUnite);
    }

    public void aff_vecteur() {
        System.out.println(unite.getDimension());
    }

    @Override
    public String toString() {
        return this.valeur + "" + this.unite;
    }
}

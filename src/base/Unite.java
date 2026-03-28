package base;

import design_patterns.visiteur.Visiteur;
import exceptions.DimensionIllegaleException;
import exceptions.UniteIllegaleException;

import java.util.Arrays;
import java.util.Map;

public abstract class Unite {
    protected String symbole;
    protected Dimension dimension;
    protected double tauxConversion;

    public Unite(String symbole, Dimension dimension) {
        this.symbole = symbole;
        this.dimension = dimension;
    }

    public Dimension getDimension() {
        return this.dimension;
    }
    public String getSymbole() {
        return this.symbole;
    }
    public void setSymbole(String symbole) { this.symbole = symbole; }

    /**
     * Cette méthode calcule le taux de conversion lors du passage de l'unité actuelle vers l'unité cible.
     * @param cible L'unité vers laquelle la conversion s'effectue
     * @return La valeur de la conversion.
     * @throws UniteIllegaleException Si les unités ne sont pas compatible.
     */
    public double convertirVers(Unite cible) throws UniteIllegaleException {
        if (!this.getDimension().equals(cible.getDimension()))
            throw new UniteIllegaleException("Unités incompatibles: vous essayez de convertir '" + this.getSymbole() + "' en '" + cible.getSymbole() + "' !");
        return this.getTauxConversion() / cible.getTauxConversion();
    }

    /**
     * Cette méthode calcule et retourne le taux de conversion vers l'unité de référence du SI.
     * @return La valeur obtenue.
     */
    public abstract double getTauxConversion();
    public abstract Map<Unite, Integer> getMapComposantes();

    /**
     * Cette méthode permet de créer une nouvelle unité qui sera alignée sur l'unité cible.
     * <ul>
     *     <b>Exemple</b>:
     *     <li>Unité actuelle : g</li>
     *     <li>Unité cible : kg.m/s^2</li>
     * </ul>
     * @param modele
     * @return
     * @throws UniteIllegaleException
     */
    public abstract Unite alignerSur(Unite modele) throws UniteIllegaleException, DimensionIllegaleException;
    public abstract void accepteVisiteur(Visiteur visiteur) throws UniteIllegaleException;

    @Override
    public String toString() {
        return " " + this.symbole;
    }
}

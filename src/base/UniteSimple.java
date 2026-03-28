package base;

import design_patterns.visiteur.Visiteur;
import exceptions.DimensionIllegaleException;
import exceptions.UniteIllegaleException;

import java.util.HashMap;
import java.util.Map;

public class UniteSimple extends Unite {
    /**
     * Constructeur de la classe des unités simples.
     * @param symbole Le symbole de l'unité (km, g, h)
     * @param dimension La dimension correspondante de l'unité
     * @param taux Le facteur par lequel il doit être multiplié pour revenir à l'unité de base du SI.
     * @throws DimensionIllegaleException Si la dimension passée en paramètre est invalide.
     */
    public UniteSimple(String symbole, Dimension dimension, double taux) throws DimensionIllegaleException {
        if (!dimension.estSimple())
            throw new DimensionIllegaleException("Erreur d'instanciation: Une unité simple n'a pas de valeur de dimension > 1.");
        super(symbole, dimension);
        this.tauxConversion = taux;
    }

    public static UniteSimple metre() throws DimensionIllegaleException {
        return new UniteSimple(SI.LONGUEUR.getSymbole(), Dimension.parIndice(0), 1.);
    }

    public static UniteSimple kilogramme() throws DimensionIllegaleException {
        return new UniteSimple(SI.MASSE.getSymbole(), Dimension.parIndice(1), 1.);
    }

    public static UniteSimple seconde() throws DimensionIllegaleException {
        return new UniteSimple(SI.TEMPS.getSymbole(), Dimension.parIndice(2), 1.);
    }

    public static UniteSimple ampere() throws DimensionIllegaleException {
        return new UniteSimple(SI.COURANT.getSymbole(), Dimension.parIndice(3), 1.);
    }

    public static UniteSimple kelvin() throws DimensionIllegaleException {
        return new UniteSimple(SI.TEMPERATURE.getSymbole(), Dimension.parIndice(4), 1.);
    }

    public static UniteSimple mole() throws DimensionIllegaleException {
        return new UniteSimple(SI.SUBSTANCE.getSymbole(), Dimension.parIndice(5), 1.);
    }

    public static UniteSimple candela() throws DimensionIllegaleException {
        return new UniteSimple(SI.INTENSITE.getSymbole(), Dimension.parIndice(6), 1.);
    }

    public static UniteSimple dollar() throws DimensionIllegaleException {
        return new UniteSimple(SI.MONNAIE.getSymbole(), Dimension.parIndice(7), 1.);
    }

    public static UniteSimple deLongueur(String symbole, double taux) throws DimensionIllegaleException {
        return new UniteSimple(symbole, Dimension.parIndice(0), taux);
    }

    public static UniteSimple deMasse(String symbole, double taux) throws DimensionIllegaleException {
        return new UniteSimple(symbole, Dimension.parIndice(1), taux);
    }

    public static UniteSimple deTemps(String symbole, double taux) throws DimensionIllegaleException {
        return new UniteSimple(symbole, Dimension.parIndice(2), taux);
    }

    public static UniteSimple dIntensiteElectrique(String symbole, double taux) throws DimensionIllegaleException {
        return new UniteSimple(symbole, Dimension.parIndice(3), taux);
    }

    public static UniteSimple deTemperature(String symbole, double taux) throws DimensionIllegaleException {
        return new UniteSimple(symbole, Dimension.parIndice(4), taux);
    }

    public static UniteSimple deMatiere(String symbole, double taux) throws DimensionIllegaleException {
        return new UniteSimple(symbole, Dimension.parIndice(5), taux);
    }

    public static UniteSimple dIntensiteLumineuse(String symbole, double taux) throws DimensionIllegaleException {
        return new UniteSimple(symbole, Dimension.parIndice(6), taux);
    }

    public static UniteSimple deMonnaie(String symbole, double taux) throws DimensionIllegaleException {
        return new UniteSimple(symbole, Dimension.parIndice(7), taux);
    }

    @Override
    public double getTauxConversion() {
        return this.tauxConversion;
    }

    /**
     * Cette méthode retourne l'unité actuelle sous forme de Map.
     * Cela nous permet d'éviter toute sorte de comparaison
     * pour savoir s'il s'agit d'une unité simple ou composée.
     * @return L'unité sous forme de Map.
     */
    @Override
    public Map<Unite, Integer> getMapComposantes() {
        Map<Unite, Integer> map = new HashMap<>();
        map.put(this, 1);
        return map;
    }

    /**
     * Cette méthode vérifie si dans l'unité modèle, une unité a la même dimension
     * que l'unité actuelle. Si c'est le cas, l'unité qui se trouve dans le modèle
     * est retournée sinon c'est l'unité actuelle qui l'est.
     * @param modele L'unité sur laquelle la comparaison s'effectue.
     * @return L'unité modèle de même dimension ou this.
     */
    @Override
    public Unite alignerSur(Unite modele) {
        for (Unite unite : modele.getMapComposantes().keySet()) {
            if (unite.getDimension().equals(this.getDimension())) {
                return unite;
            }
        }
        return this;
    }

    @Override
    public void accepteVisiteur(Visiteur visiteur) throws UniteIllegaleException {
        visiteur.visite(this);
    }
}

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
            throw new DimensionIllegaleException("Erreur d'instanciation: Une unité simple n'a pas plus d'une valeur de dimension > 1.");
        super(symbole, dimension, taux);
    }

    /**
     * Ce constructeur permet de créer une unité simple à partir d'une autre unité simple.
     * @param symbole Le symbole de l'unité (km, g, h)
     * @param taux Le facteur par lequel il doit être multiplié pour revenir à l'unité de base de référence.
     * @param uniteReference L'unité sur laquelle s'appuie la nouvelle unité.
     * @throws DimensionIllegaleException
     * <br>Exemple : <br>
     * Pour créer une unité comme le Gallon (gal) qui a pour référence le litre (l).
     * <br>On commence par créer le volume (m<sup>3</sup>).
     * <pre>{@code
     *     // Création du mètre
     *     Unite m3 = UniteSimple.deLongueur("m^3", 1, 3);
     *     // Création du litre 1 l => 1/1000 m^3
     *     Unite litre = new UniteSimple("l", 1.0/1000, m3);
     *     //Création du gallon 1 gal => 3.78541 l
     *     Unite gallon = new UniteSimple("gal", 3.78541, litre);
     *     }
     * </pre>
     */
    public UniteSimple(String symbole, double taux, Unite uniteReference) throws DimensionIllegaleException {
        this(symbole, uniteReference.getDimension(), taux * uniteReference.getTauxConversion());
    }

    public static UniteSimple metre() throws DimensionIllegaleException {
        return new UniteSimple(SI.LONGUEUR.getSymbole(), Dimension.parIndice(SI.LONGUEUR.getIndice(), 1), 1.);
    }

    public static UniteSimple kilogramme() throws DimensionIllegaleException {
        return new UniteSimple(SI.MASSE.getSymbole(), Dimension.parIndice(SI.MASSE.getIndice(), 1), 1.);
    }

    public static UniteSimple seconde() throws DimensionIllegaleException {
        return new UniteSimple(SI.TEMPS.getSymbole(), Dimension.parIndice(SI.TEMPS.getIndice(), 1), 1.);
    }

    public static UniteSimple ampere() throws DimensionIllegaleException {
        return new UniteSimple(SI.COURANT.getSymbole(), Dimension.parIndice(SI.COURANT.getIndice(), 1), 1.);
    }

    public static UniteSimple kelvin() throws DimensionIllegaleException {
        return new UniteSimple(SI.TEMPERATURE.getSymbole(), Dimension.parIndice(SI.TEMPERATURE.getIndice(), 1), 1.);
    }

    public static UniteSimple mole() throws DimensionIllegaleException {
        return new UniteSimple(SI.SUBSTANCE.getSymbole(), Dimension.parIndice(SI.SUBSTANCE.getIndice(), 1), 1.);
    }

    public static UniteSimple candela() throws DimensionIllegaleException {
        return new UniteSimple(SI.INTENSITE.getSymbole(), Dimension.parIndice(SI.INTENSITE.getIndice(), 1), 1.);
    }

    public static UniteSimple euro() throws DimensionIllegaleException {
        return new UniteSimple(SI.MONNAIE.getSymbole(), Dimension.parIndice(SI.MONNAIE.getIndice(), 1), 1.);
    }

    public static UniteSimple deLongueur(String symbole, double taux, int puissance) throws DimensionIllegaleException {
        return new UniteSimple(symbole, Dimension.parIndice(SI.LONGUEUR.getIndice(), puissance), Math.pow(taux, puissance));
    }

    public static UniteSimple deMasse(String symbole, double taux, int puissance) throws DimensionIllegaleException {
        return new UniteSimple(symbole, Dimension.parIndice(SI.MASSE.getIndice(), puissance), Math.pow(taux, puissance));
    }

    public static UniteSimple deTemps(String symbole, double taux, int puissance) throws DimensionIllegaleException {
        return new UniteSimple(symbole, Dimension.parIndice(SI.TEMPS.getIndice(), puissance), Math.pow(taux, puissance));
    }

    public static UniteSimple dIntensiteElectrique(String symbole, double taux, int puissance) throws DimensionIllegaleException {
        return new UniteSimple(symbole, Dimension.parIndice(SI.COURANT.getIndice(), puissance), Math.pow(taux, puissance));
    }

    public static UniteSimple deTemperature(String symbole, double taux, int puissance) throws DimensionIllegaleException {
        return new UniteSimple(symbole, Dimension.parIndice(SI.TEMPERATURE.getIndice(), puissance), Math.pow(taux, puissance));
    }

    public static UniteSimple deMatiere(String symbole, double taux, int puissance) throws DimensionIllegaleException {
        return new UniteSimple(symbole, Dimension.parIndice(SI.SUBSTANCE.getIndice(), puissance), Math.pow(taux, puissance));
    }

    public static UniteSimple dIntensiteLumineuse(String symbole, double taux, int puissance) throws DimensionIllegaleException {
        return new UniteSimple(symbole, Dimension.parIndice(SI.INTENSITE.getIndice(), puissance), Math.pow(taux, puissance));
    }

    public static UniteSimple deMonnaie(String symbole, double taux) throws DimensionIllegaleException {
        return new UniteSimple(symbole, Dimension.parIndice(SI.MONNAIE.getIndice(), 1), taux);
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

    @Override
    public void accepteVisiteur(Visiteur visiteur) throws UniteIllegaleException {
        visiteur.visite(this);
    }
}

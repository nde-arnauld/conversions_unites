package base;

import design_patterns.visiteur.Visiteur;
import exceptions.DimensionIllegaleException;
import exceptions.UniteIllegaleException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class UniteComposee extends Unite {
    private Map<Unite, Integer> composantes = new HashMap<>();

    public UniteComposee(UniteComposee uniteComposee, double taux) {
        this(uniteComposee.composantes);
        this.tauxConversion = taux;
    }

    /**
     * Constructeur prenant un ensemble d'unité et leur exposant respectif.
     * @param unites L'ensemble des unités composantes de l'unité composée.
     */
    public UniteComposee(Map<Unite, Integer> unites) {
        super("", new Dimension(), 0.0);
        this.composantes = nettoyerComposantes(unites);
        miseAjourPropriete();
    }

    /**
     * Ce constructeur permet de créer une unité simple à partir d'une autre unité simple.
     * @param symbole Le symbole de l'unité (m/s, g/mol, h)
     * @param taux Le facteur par lequel il doit être multiplié pour revenir à l'unité de base de référence.
     * @param uniteReference L'unité sur laquelle s'appuie la nouvelle unité.
     * @throws DimensionIllegaleException
     * <br>Exemple : <br>
     * Pour créer une unité comme le Gallon (gal) qui a pour référence le litre (l).
     * <br>On commence par créer le volume (m<sup>3</sup>).
     * <pre>{@code
     *      // Création du mètre
     *     Unite m3 = new UniteSimple("m^3", new Dimension(), 1);
     *     // Création du litre 1 l => 1/1000 m^3
     *     Unite litre = new UniteSimple("l", 0.001, m.getDimension().puissance(3));
     *     //Création du gallon 1 gal => 3.78541 l
     *     Unite gallon = new UniteSimple("gal", 3.78541, litre);
     *     }
     * </pre>
     */
    public UniteComposee(String symbole, double taux, Unite uniteReference) throws DimensionIllegaleException {
        this(uniteReference.getMapComposantes());
        this.tauxConversion = taux * uniteReference.getTauxConversion();
        this.symbole = symbole;
    }

    /**
     * Cette méthode permet de recalculer les attributs de l'unité
     */
    private void miseAjourPropriete() {
        Dimension dim = new Dimension();
        double taux = 1.0;
        for (Map.Entry<Unite, Integer> entree : this.composantes.entrySet()) {
            Unite unite = entree.getKey();
            int puissance = entree.getValue();

            dim = dim.multiplier(unite.getDimension().puissance(puissance));
            taux *= Math.pow(unite.getTauxConversion(), puissance);
        }
        this.dimension = dim;
        this.tauxConversion = taux;
        this.symbole = genererSymbole();
    }

    /**
     * Cette méthode permet de générer le symbole final à partir des unités composantes et leur puissance.
     * @return Le symbole obtenu
     */
    private String genererSymbole() {
        if (this.composantes.isEmpty()) return "Sans unité.";
        StringBuilder numerateur = new StringBuilder();
        StringBuilder denominateur = new StringBuilder();

        for (Map.Entry<Unite, Integer> entree : this.composantes.entrySet()) {
            String symbole = entree.getKey().getSymbole();
            int puissance = entree.getValue();
            if (puissance > 0) {
                if (!numerateur.isEmpty()) numerateur.append(".");
                numerateur.append(symbole);
                if (puissance > 1) numerateur.append("^").append(puissance);
            } else if (puissance < 0) {
                if (!denominateur.isEmpty()) denominateur.append(".");
                denominateur.append(symbole);
                if (puissance < -1) denominateur.append("^").append(Math.abs(puissance));
            }
        }
        if (numerateur.isEmpty()) numerateur.append("1");
        if (!denominateur.isEmpty())
            return numerateur + "/" + denominateur;
        return numerateur.toString();
    }

    /**
     * Cette méthode permet de supprimer les unités dont la puissance est égale à zéro.
     * @param map Le dictionnaire des unités de départ.
     * @return Le dictionnaire des unités nettoyé.
     */
    private Map<Unite, Integer> nettoyerComposantes(Map<Unite, Integer> map) {
        Map<Unite, Integer> mapFinale = new HashMap<>();
        for (Map.Entry<Unite, Integer> entree : map.entrySet()) {
            if (entree.getValue() != 0)
                mapFinale.put(entree.getKey(), entree.getValue());
        }
        return mapFinale;
    }

    @Override
    public void accepteVisiteur(Visiteur visiteur) throws UniteIllegaleException {
        visiteur.visite(this);
    }

    @Override
    public double getTauxConversion() {
        return this.tauxConversion;
    }

    /**
     * Cette méthode retourne une <b>vue</b> de la liste des unités composantes cette actuelle.
     * Cette vue n'est pas modifiable.
     * @return Une vue sur la map des.
     */
    @Override
    public Map<Unite, Integer> getMapComposantes() {
        return Collections.unmodifiableMap(this.composantes);
    }
}

package base;

import design_patterns.visiteur.Visiteur;
import exceptions.DimensionIllegaleException;
import exceptions.UniteIllegaleException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class UniteComposee extends Unite {
    private Map<Unite, Integer> composantes = new HashMap<>();

    /**
     * Constructeur par défaut
     * @throws DimensionIllegaleException
     */
    public UniteComposee() throws DimensionIllegaleException {
        super("", new Dimension());
    }

    /**
     * Contructeur prenant un ensemble d'unité et leur exposant respectif.
     * @param unites L'ensemble des unités composantes de l'unité composée.
     */
    public UniteComposee(Map<Unite, Integer> unites) {
        super("", new Dimension());
        this.composantes = nettoyerComposantes(unites);
        miseAjourPropriete();
    }

    /**
     * Cette méthode permet de recalculer les attributs de l'unité
     */
    private void miseAjourPropriete() {
        Dimension dim = new Dimension();
        double taux = 1.0;
        for (Map.Entry<Unite, Integer> entree : this.composantes.entrySet()) {
            Unite u = entree.getKey();
            int puissance = entree.getValue();

            dim = dim.multiplier(u.getDimension().puissance(puissance));
            taux *= Math.pow(u.getTauxConversion(), puissance);
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
        String numerateur = "";
        String denominateur = "";

        for (Map.Entry<Unite, Integer> entree : this.composantes.entrySet()) {
            String symbole = entree.getKey().getSymbole();
            int puissance = entree.getValue();
            if (puissance > 0) {
                if (numerateur.length() > 0) numerateur += ".";
                numerateur += symbole;
                if (puissance > 1) numerateur += "^" + puissance;
            } else if (puissance < 0) {
                if (denominateur.length() > 0) denominateur += ".";
                denominateur += symbole;
                if (puissance < -1) denominateur += "^" + Math.abs(puissance);
            }
        }
        if (numerateur.length() == 0) numerateur += "1";
        if (denominateur.length() > 0)
            return numerateur + "/" + denominateur;
        return numerateur;
    }

    /**
     * Cette méthode permet de supprimer les unités dont la puissance est égale à zéro.
     * @param map Le dictionnaire des unités de départ.
     * @return Le dictionnaire des unités nettoyé.
     */
    private Map<Unite, Integer> nettoyerComposantes(Map<Unite, Integer> map) {
        Map<Unite, Integer> finalMap = new HashMap<>();
        for (Map.Entry<Unite, Integer> entree : map.entrySet()) {
            if (entree.getValue() != 0)
                finalMap.put(entree.getKey(), entree.getValue());
        }
        return finalMap;
    }

    @Override
    public void accepteVisiteur(Visiteur visiteur) throws UniteIllegaleException {
        visiteur.visite(this);
    }

    /**
     * Cette méthode permet d'ajouter une nouvelle unité dans l'unité composée.
     * @param unite l'unité à ajouter
     * @param exposant son exposant
     */
    public void ajouterUnite(Unite unite, int exposant) {
        if (detecterCycle(unite))
            throw new IllegalArgumentException("Calcul impossible : l'unité '" + unite.getSymbole() + "' créerait une dépendance circulaire.");
        this.composantes.merge(unite, exposant, Integer::sum);
        this.composantes = nettoyerComposantes(this.composantes);
        miseAjourPropriete();
    }

    /**
     * Cette méthode permet de détecter si une unité est contenue dans une autre.
     * @param cible l'unité à détecter
     * @return Vraie si l'unité est trouvée.
     */
    private boolean detecterCycle(Unite cible) {
        if (this == cible) return true;
        if (cible instanceof  UniteComposee) {
            for (Unite enfant : ((UniteComposee) cible).getComposants().keySet()) {
                if (detecterCycle(enfant)) return true;
            }
        }
        return false;
    }

    /**
     * Cette méthode retourne une <b>vue</b> de la map.
     * Cette vue n'est pas modifiable.
     * @return Une vue sur la map.
     */
    public Map<Unite, Integer> getComposants() {
        return Collections.unmodifiableMap(this.composantes);
    }

    @Override
    public double getTauxConversion() {
        return this.tauxConversion;
    }

    public static UniteComposee multiplier(Unite u1, Unite u2) throws UniteIllegaleException {
        return combiner(u1, u2, 1);
    }

    public static UniteComposee diviser(Unite u1, Unite u2) throws UniteIllegaleException {
        return combiner(u1, u2, -1);
    }

    private  static UniteComposee combiner(Unite u1, Unite u2, int positionU2) {
        Map<Unite, Integer> map = new HashMap<>();
        ajouter(map, u1, 1);
        ajouter(map, u2, positionU2);
        return new UniteComposee(map);
    }

    private static void ajouter(Map<Unite, Integer> map, Unite u, int position) {
        if (u instanceof UniteComposee) {
            for (Map.Entry<Unite, Integer> entree : ((UniteComposee)u).composantes.entrySet()) {
                map.merge(entree.getKey(), entree.getValue() * position, Integer::sum);
            }
        } else {
            map.merge(u, position, Integer::sum);
        }
    }
}

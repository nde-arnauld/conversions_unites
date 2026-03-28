package base;

import design_patterns.visiteur.Visiteur;
import exceptions.UniteIllegaleException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class UniteComposee extends Unite {
    private Map<Unite, Integer> composantes = new HashMap<>();

    /**
     * Constructeur par défaut
     */
    public UniteComposee() {
        super("", new Dimension());
    }

    /**
     * Constructeur prenant un ensemble d'unité et leur exposant respectif.
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
            for (Unite enfant : cible.getMapComposantes().keySet()) {
                if (detecterCycle(enfant)) return true;
            }
        }
        return false;
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

    /**
     * Cette méthode permet de conserver une liste de toutes les unités de même
     * dimension que celles présentent dans l'unité actuelle pour pouvoir effectuer
     * des simplifications plus tard. <br>
     * Exemple : <br>
     * - Unité actuelle : m <br>
     * - Unité modèle : km/s <br>
     * L'unité 'km' du modèle sera retournée, car elle est de même dimension que 'm'.
     * Cela permettra de conserver uniquement 'km' dans l'unité finale.
     * @param modele L'unité de référence.
     * @return La liste des unités de même dimension que l'unité actuelle.
     */
    @Override
    public Unite alignerSur(Unite modele) {
        UniteComposee nouvelleUnite = new UniteComposee();
        Map<Unite, Integer> composantesModele = modele.getMapComposantes();

        for (Map.Entry<Unite, Integer> entree : this.composantes.entrySet()) {
            Unite uniteActuelle = entree.getKey();
            int puissance = entree.getValue();

            Unite uniteMatch = uniteActuelle;
            for (Unite uniteModele : composantesModele.keySet()) {
                if (uniteModele.getDimension().equals(uniteActuelle.getDimension())) {
                    uniteMatch = uniteModele;
                    break;
                }
            }
            nouvelleUnite.ajouterUnite(uniteMatch, puissance);
        }
        return nouvelleUnite;
    }

    public static UniteComposee multiplier(Unite u1, Unite u2) {
        return combiner(u1, u2, 1);
    }

    public static UniteComposee diviser(Unite u1, Unite u2) {
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

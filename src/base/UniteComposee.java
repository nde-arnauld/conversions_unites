package base;

import design_patterns.visiteur.Visiteur;
import exceptions.DimensionIllegaleException;
import exceptions.UniteIllegaleException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class UniteComposee extends Unite {
    private final Map<Unite, Integer> unites = new HashMap<>();

    public UniteComposee() throws DimensionIllegaleException {
        this("", "", null);
    }

    public UniteComposee(String symbole, String nom, Dimension dimension) throws DimensionIllegaleException {
        System.out.println("Constructeur unité : " + dimension);
        if (dimension.estSimple())
            throw new DimensionIllegaleException("Erreur d'instanciation: Une unité composée nécessite un vecteur de dimension avec une valeur > 1 ou 2 valeurs différentes.");
        super(symbole, nom, dimension);
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
        this.unites.put(unite, exposant);
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
        return Collections.unmodifiableMap(this.unites);
    }

    @Override
    public double getTauxConversion() {
        double total = 1.0;
        for (Map.Entry<Unite, Integer> unite : unites.entrySet()) {
            total *= Math.pow(unite.getKey().getTauxConversion(), unite.getValue());
        }
        return total;
    }
}

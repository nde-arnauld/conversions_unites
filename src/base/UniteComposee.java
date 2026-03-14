package base;

import java.util.HashMap;
import java.util.Map;

public class UniteComposee extends Unite {
    Map<Unite, Integer> unites = new HashMap<>();

    public UniteComposee() {
        this("", "", null);
    }

    public UniteComposee(String symbole, String nom, Dimension dimension) {
        this.unites = new HashMap<>();
        this.symbole = symbole;
        this.nom = nom;
        this.dimension = dimension;
    }

    /**
     * Cette méthode permet d'ajouter une nouvelle unité dans l'unité composé
     * @param unite l'unité à ajouter
     * @param exposant son exposant
     */
    public void ajouterUnite(Unite unite, int exposant) {
        this.unites.put(unite, exposant);
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

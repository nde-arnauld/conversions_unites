package base;

import java.util.HashMap;
import java.util.Map;

public final class OperationsUnites {

    private OperationsUnites() {
    }

    public static UniteComposee multiplier(Unite u1, Unite u2) {
        return combiner(u1, u2, 1);
    }

    public static UniteComposee diviser(Unite u1, Unite u2) {
        return combiner(u1, u2, -1);
    }

    public static Unite aligner(Unite unite, Unite modele) {
        Map<Unite, Integer> composantesResultat = new HashMap<>();
        Map<Unite, Integer> composantesModele = modele.getMapComposantes();

        for (Map.Entry<Unite, Integer> entree : unite.getMapComposantes().entrySet()) {
            Unite uniteActuelle = entree.getKey();
            int puissance = entree.getValue();

            Unite uniteMatch = uniteActuelle;
            for (Unite uniteModele : composantesModele.keySet()) {
                if (uniteModele.getDimension().equals(uniteActuelle.getDimension())) {
                    uniteMatch = uniteModele;
                    break;
                }
            }
            composantesResultat.merge(uniteMatch, puissance, Integer::sum);
        }

        composantesResultat.values().removeIf(valeur -> valeur == 0);
        if (composantesResultat.size() == 1) {
            Map.Entry<Unite, Integer> unique = composantesResultat.entrySet().iterator().next();
            if (unique.getValue() == 1) {
                return unique.getKey();
            }
        }
        return new UniteComposee(composantesResultat);
    }

    private static UniteComposee combiner(Unite u1, Unite u2, int positionU2) {
        Map<Unite, Integer> map = new HashMap<>();
        ajouterComposantes(map, u1, 1);
        ajouterComposantes(map, u2, positionU2);
        return new UniteComposee(map);
    }

    private static void ajouterComposantes(Map<Unite, Integer> map, Unite unite, int position) {
        for (Map.Entry<Unite, Integer> entree : unite.getMapComposantes().entrySet()) {
            map.merge(entree.getKey(), entree.getValue() * position, Integer::sum);
        }
    }
}

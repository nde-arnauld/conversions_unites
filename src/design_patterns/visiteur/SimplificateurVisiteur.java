package design_patterns.visiteur;

import base.*;
import exceptions.UniteIllegaleException;

import java.util.Map;

public class SimplificateurVisiteur implements Visiteur {
    @Override
    public void visite(UniteSimple uniteSimple) throws UniteIllegaleException {
        if (uniteSimple == null) throw new UniteIllegaleException("Erreur: ");
        System.out.println("Visiteur simple");
        String symbole = "";
        int[] vecteur = uniteSimple.getDimension().getVecteur();
        for(int i = 0; i < Dimension.SIZE; i++) {
            if (vecteur[i] != 0) {
                switch (i) {
                    case 0:
                        symbole += SI.LONGUEUR.getSymbole();
                        break;
                    case 1:
                        symbole += SI.MASSE.getSymbole();
                        break;
                    case 2:
                        symbole += SI.TEMPS.getSymbole();
                        break;
                    case 3:
                        symbole += SI.COURANT.getSymbole();
                        break;
                    case 4:
                        symbole += SI.TEMPERATURE.getSymbole();
                        break;
                    case 5:
                        symbole += SI.SUBSTANCE.getSymbole();
                        break;
                    case 6:
                        symbole += SI.INTENSITE.getSymbole();
                        break;
                    case 7:
                        symbole += SI.MONNAIE.getSymbole();
                        break;
                    default:
                        symbole += "-";
                        break;
                }
            }
        }
        uniteSimple.setSymbole(symbole);
    }

    @Override
    public void visite(UniteComposee uniteComposee) throws UniteIllegaleException {
        if (uniteComposee == null) throw new UniteIllegaleException("Erreur: Impossible de visiter une unité 'null'!");
        System.out.println("Visiteur unité composée");
        String symbole = "";
        boolean seul = true; // Pour vérifier qu'on a un seul symbole, sinon on rajoute le '.' pour combiner les autres.
        int[] vecteur = uniteComposee.getDimension().getVecteur();
        // On parcourt chaque position du vecteur
        for (int i = 0; i < Dimension.SIZE; i++) {
            // Si une position n'est pas vide alors, on doit afficher l'unité correspondante.
            if (vecteur[i] != 0) {
                int puissance = 0;
                String sym = "";
                // On recherche cette unité dans la liste
                for (Map.Entry<Unite, Integer> unite: uniteComposee.getComposants().entrySet()) {
                    Unite u = unite.getKey();
                    int p = unite.getValue();
                    if (u.getDimension().getVecteur()[i] != 0) {
                        System.out.println(": u :");
                        puissance += p;
                        sym = u.getSymbole();
                    }
                }
                symbole += sym;
                if (puissance > 1 || puissance < 1) symbole += "^" + puissance;
                if (!seul) symbole += ".";
                seul = false;
            }
        }

        System.out.println("Symbole : "+ symbole);
        uniteComposee.setSymbole(symbole);
    }
}

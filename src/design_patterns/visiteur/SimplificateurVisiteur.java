package design_patterns.visiteur;

import base.Dimension;
import base.SI;
import base.Unite;
import exceptions.UniteIllegaleException;

import java.util.Arrays;

public class SimplificateurVisiteur implements Visiteur {
    @Override
    public void visite(Unite unite) throws UniteIllegaleException {
        if (unite == null)
            throw new UniteIllegaleException("Erreur : l'unité à simplifier est nulle.");
        String symbole = "";
        int[] vecteur = unite.getDimension().getVecteur();
        boolean premier = true; // Pour savoir s'il s'agit d'une seule unité (UniteSimple).
        for (int i = 0; i < Dimension.SIZE; i++) {
            int puissance = vecteur[i];
            if (puissance == 0) continue;
            if (!premier) symbole += ".";
            symbole += SI.values()[i].getSymbole();
            if (puissance != 1) symbole += "^" + puissance;
            premier = false;
        }
        if (symbole.length() == 0) symbole += "::";
        unite.setSymbole(symbole);
    }
}

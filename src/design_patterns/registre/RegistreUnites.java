package design_patterns.registre;

import base.Dimension;
import base.Unite;

import java.util.HashMap;
import java.util.Map;

public class RegistreUnites {
    // Map pour le Lexer/Parser (Saisie -> Objet)
    private static final Map<String, Unite> mapSaisie = new HashMap<>();

    // Map pour l'affichage (Dimension -< Symbole compact)
    private static final Map<Dimension, String> tableSymboles = new HashMap<>();

    /**
     * Cette méthode permet d'enregistrer une unité de base ou dérivée.
     * Cette unité sera reconnue à la saisie.
     * @param unite
     */
    public static void enregistrer(Unite unite) {
        mapSaisie.put(unite.getSymbole(),  unite);
        tableSymboles.putIfAbsent(unite.getDimension(), unite.getSymbole());
    }

    /**
     * Cette méthode permet d'enregistrer des symboles compacts dans la Map.
     * @param dimension La dimension du symbole
     * @param symbole La représentation finale
     * <br>Exemple:<br>
     *
     */
    public static void enregistrerSymbole(Dimension dimension, String symbole) {
        tableSymboles.put(dimension, symbole);
    }

    /**
     * Cette méthode permet de récupérer une unité à partir d'une chaîne de caractère.
     * @param symbole Le symbole de l'unité.
     * @return L'unité corresponde.
     */
    public static Unite recupererUnite(String symbole) {
        return mapSaisie.get(symbole);
    }

    /**
     * Cette méthode permet de retrouver le symbole correspondant à une dimension
     * @param dimension La dimension recherchée.
     * @return Le symbole compact correspondant
     */
    public static String trouverSymbole(Dimension dimension) {
        return tableSymboles.get(dimension);
    }
}

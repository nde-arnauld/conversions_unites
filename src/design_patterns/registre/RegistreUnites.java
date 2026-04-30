package design_patterns.registre;

import base.Dimension;
import base.Unite;
import base.UniteComposee;
import base.UniteSimple;
import exceptions.DimensionIllegaleException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegistreUnites {
    // Map pour le Lexer/Parser (Saisie -> Objet)
    private static final Map<String, Unite> mapSaisie = new HashMap<>();

    // Map pour l'affichage (Dimension -> Symbole compact)
    private static final Map<Dimension, String> tableSymboles = new HashMap<>();

    private static final String TYPE_SIMPLE = "simple";
    private static final String TYPE_COMPOSE = "composee";

    /**
     * Cette méthode permet d'enregistrer une unité de base ou dérivée.
     * Cette unité sera reconnue à la saisie.
     * @param unite
     */
    public static void enregistrer(Unite unite) {
        mapSaisie.put(unite.getSymbole(),  unite);
        tableSymboles.putIfAbsent(unite.getDimension(), unite.getSymbole());
    }

    public static void vider() {
        mapSaisie.clear();
        tableSymboles.clear();
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

    /**
     * Charge les unités depuis un fichier CSV utilisant ';' comme séparateur.
     * Format attendu :
     * type;symbole;taux;dimension;composantes;symboleCompact
     * - type : simple|composee
     * - dimension : vecteur "d0,d1,d2,d3,d4,d5,d6,d7" (pour type simple)
    * - composantes : "symbole:expo|symbole:expo" (pour type composee)
     */
    public static void chargerDepuisFichier(String chemin)
            throws IOException, DimensionIllegaleException {
        List<String> lignes = Files.readAllLines(Path.of(chemin), StandardCharsets.UTF_8);
        List<LigneUnite> definitions = new ArrayList<>();

        for (int i = 0; i < lignes.size(); i++) {
            String ligne = lignes.get(i).trim();
            if (ligne.isEmpty() || ligne.startsWith("#")) {
                continue;
            }

            String[] colonnes = ligne.split(";", -1);
            if (colonnes.length < 6) {
                throw new IllegalArgumentException("Ligne " + (i + 1) + " invalide : 6 colonnes attendues.");
            }
            String type = colonnes[0].trim().toLowerCase();
            if (!TYPE_SIMPLE.equals(type) && !TYPE_COMPOSE.equals(type)) {
                throw new IllegalArgumentException("Ligne " + (i + 1) + " : type invalide '" + colonnes[0].trim() + "'.");
            }
            definitions.add(new LigneUnite(
                    i + 1,
                    type,
                    colonnes[1].trim(),
                    colonnes[2].trim(),
                    colonnes[3].trim(),
                    colonnes[4].trim(),
                    colonnes[5].trim()
            ));
        }

        chargerUnitesSimples(definitions);
        chargerUnitesComposees(definitions);
    }

    private static void chargerUnitesSimples(List<LigneUnite> definitions)
            throws DimensionIllegaleException {
        for (LigneUnite definition : definitions) {
            if (!TYPE_SIMPLE.equals(definition.type)) {
                continue;
            }

            if (definition.symbole.isEmpty()) {
                throw new IllegalArgumentException("Ligne " + definition.numeroLigne + " : symbole manquant.");
            }

            Dimension dimension = parseDimension(definition.dimension, definition.numeroLigne);
            double taux = parseTaux(definition.taux, definition.numeroLigne);
            UniteSimple unite = new UniteSimple(definition.symbole, dimension, taux);
            enregistrer(unite);

            if (!definition.symboleCompact.isEmpty()) {
                enregistrerSymbole(unite.getDimension(), definition.symboleCompact);
            }
        }
    }

    private static void chargerUnitesComposees(List<LigneUnite> definitions)
            throws DimensionIllegaleException {
        for (LigneUnite definition : definitions) {
            if (!TYPE_COMPOSE.equals(definition.type)) {
                continue;
            }

            if (definition.symbole.isEmpty()) {
                throw new IllegalArgumentException("Ligne " + definition.numeroLigne + " : symbole manquant.");
            }
            if (definition.composantes.isEmpty()) {
                throw new IllegalArgumentException("Ligne " + definition.numeroLigne + " : composantes manquantes.");
            }

            Map<Unite, Integer> composantes = parseComposantes(definition.composantes, definition.numeroLigne);
            UniteComposee baseComposee = new UniteComposee(composantes);
            double taux = parseTauxOuDefaut(definition.taux, 1.0, definition.numeroLigne);
            UniteComposee unite = new UniteComposee(definition.symbole, taux, baseComposee);

            enregistrer(unite);
            if (!definition.symboleCompact.isEmpty()) {
                enregistrerSymbole(unite.getDimension(), definition.symboleCompact);
            }
        }
    }

    private static Dimension parseDimension(String brute, int numeroLigne) {
        if (brute.isEmpty()) {
            throw new IllegalArgumentException("Ligne " + numeroLigne + " : dimension manquante.");
        }
        String[] morceaux = brute.split(",");
        if (morceaux.length != Dimension.SIZE) {
            throw new IllegalArgumentException("Ligne " + numeroLigne + " : dimension invalide, "
                    + Dimension.SIZE + " composantes attendues.");
        }

        int[] vecteur = new int[Dimension.SIZE];
        for (int i = 0; i < morceaux.length; i++) {
            try {
                vecteur[i] = Integer.parseInt(morceaux[i].trim());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Ligne " + numeroLigne + " : dimension invalide.", e);
            }
        }
        return new Dimension(vecteur);
    }

    private static Map<Unite, Integer> parseComposantes(String brute, int numeroLigne) {
        Map<Unite, Integer> composantes = new HashMap<>();
        String[] elements = brute.split("\\|");

        for (String element : elements) {
            String[] detail = element.trim().split(":", -1);
            if (detail.length != 2) {
                throw new IllegalArgumentException("Ligne " + numeroLigne + " : composante invalide '" + element + "'.");
            }
            String symbole = detail[0].trim();
            if (symbole.isEmpty()) {
                throw new IllegalArgumentException("Ligne " + numeroLigne + " : symbole de composante manquant.");
            }

            Unite unite = recupererUnite(symbole);
            if (unite == null) {
                throw new IllegalArgumentException("Ligne " + numeroLigne + " : unité inconnue '" + symbole + "'.");
            }

            int exposant;
            try {
                exposant = Integer.parseInt(detail[1].trim());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Ligne " + numeroLigne + " : exposant invalide pour '" + symbole + "'.", e);
            }
            composantes.merge(unite, exposant, Integer::sum);
        }

        return composantes;
    }

    private static double parseTaux(String brute, int numeroLigne) {
        if (brute.isEmpty()) {
            throw new IllegalArgumentException("Ligne " + numeroLigne + " : taux manquant.");
        }
        try {
            return Double.parseDouble(brute);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Ligne " + numeroLigne + " : taux invalide.", e);
        }
    }

    private static double parseTauxOuDefaut(String brute, double valeurDefaut, int numeroLigne) {
        if (brute.isEmpty()) {
            return valeurDefaut;
        }
        return parseTaux(brute, numeroLigne);
    }

    private static class LigneUnite {
        private final int numeroLigne;
        private final String type;
        private final String symbole;
        private final String taux;
        private final String dimension;
        private final String composantes;
        private final String symboleCompact;

        private LigneUnite(int numeroLigne,
                           String type,
                           String symbole,
                           String taux,
                           String dimension,
                           String composantes,
                           String symboleCompact) {
            this.numeroLigne = numeroLigne;
            this.type = type;
            this.symbole = symbole;
            this.taux = taux;
            this.dimension = dimension;
            this.composantes = composantes;
            this.symboleCompact = symboleCompact;
        }
    }
}

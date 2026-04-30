import base.*;
import design_patterns.registre.RegistreUnites;
import exceptions.DimensionIllegaleException;
import exceptions.UniteIllegaleException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        IO.println("=== TEST DU SYSTÈME DE CONVERSION ===\n");

        try {
            Unite m3 = UniteSimple.deLongueur("m^3", 1., 3);
            Unite litre = new UniteSimple("l", 1.0/1000, m3);
            Unite gallon = new UniteSimple("gal", 3.78541, litre);
            Unite euro = UniteSimple.deMonnaie("euro", 1.);
            Unite dollar = UniteSimple.deMonnaie("$", 1/1.17);

            Map<Unite, Integer> dolgal = new HashMap<Unite, Integer>();
            dolgal.put(dollar, 1);
            dolgal.put(gallon, -1);
            Unite dollar_gallon = new UniteComposee(dolgal);

            Map<Unite, Integer> eurol = new HashMap<Unite, Integer>();
            eurol.put(euro, 1);
            eurol.put(litre, -1);
            Unite euro_litre = new UniteComposee(eurol);

            Quantite pu = new Quantite(5.2, dollar_gallon);
            Quantite p = pu.convertirVers(euro_litre);

            IO.println(pu + " -> " + p);

            RegistreUnites.vider();
            RegistreUnites.chargerDepuisFichier("unites.csv");

            Unite kmh = recupererOuEchouer("km/h");
            IO.println("Unité km/h : " + kmh);
            Unite ms = recupererOuEchouer("m/s");
            IO.println("Unité m/s : " + ms);
            Unite s = recupererOuEchouer("s");
            IO.println("Unité s : " + s);

            IO.println("Scénario 1 : conversion de vitesse");
            Quantite vitesseKmh = new Quantite(72, kmh);
            IO.println("Quantité km/h : " + vitesseKmh);
            Quantite vitesseMs = vitesseKmh.convertirVers(ms);
            IO.println(vitesseKmh + " = " + vitesseMs + "\n");

            IO.println("Scénario 2 : distance = vitesse x durée");
            Quantite vitesse = new Quantite(8, kmh);
            Quantite duree = new Quantite(18, s);

            Quantite distance = vitesse.multiplication(duree);
            IO.println(distance);

        } catch (DimensionIllegaleException dim_e) {
            System.err.println(dim_e.getMessage());
        } catch (IOException io_e) {
            System.err.println("Erreur de lecture du fichier d'unités : " + io_e.getMessage());
        } catch (UniteIllegaleException e) {
            IO.println(e);
        }
    }

    private static Unite recupererOuEchouer(String symbole) {
        Unite unite = RegistreUnites.recupererUnite(symbole);
        if (unite == null) {
            throw new IllegalStateException("Unité introuvable dans le registre : " + symbole);
        }
        return unite;
    }
}

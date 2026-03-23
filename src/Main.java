import base.*;
import exceptions.DimensionIllegaleException;
import exceptions.UniteIllegaleException;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== TEST DU SYSTÈME DE CONVERSION ===\n");

        try {
            Dimension dimMasse = new Dimension(new int[]{0, 1, 0, 0, 0, 0, 0, 0});
            Dimension dimPrix = new Dimension(new int[]{0, 0, 0, 0, 0, 0, 0, 1});

            // Dimension dérivée : Prix/Masse (ex : €/kg)
            Dimension dimPrixMasse = dimPrix.diviser(dimMasse); // [0, -1, 0, 0, 0, 0, 0, 1]

            // Unités
            UniteSimple kg = new UniteSimple("kg", "kilogramme", dimMasse, 1.0);
            UniteSimple eur = new UniteSimple("€", "euro", dimPrix, 1.0);

            UniteComposee eurParKg = new UniteComposee("€/kg", "euro par kilogramme", dimPrixMasse);
            eurParKg.ajouterUnite(eur, 1);
            eurParKg.ajouterUnite(kg, -1);

            // Opération : Prix = Pu * Masse
            Quantite pu = new Quantite(2.5, eurParKg);
            Quantite masse = new Quantite(0.7, eurParKg);

            Quantite prixTotal = pu.multiplication(masse);
            prixTotal.aff_vecteur();
            System.out.println("Calcul : " + pu + " * " + masse + " = " + prixTotal);
            // Le résultat affichera 1.75 €/kg.kg
        } catch (DimensionIllegaleException dim_e) {
            System.err.println(dim_e.getMessage());
        } catch (UniteIllegaleException e) {
            throw new RuntimeException(e);
        }
    }
}

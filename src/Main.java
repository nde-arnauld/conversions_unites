import base.Dimension;
import base.Quantite;
import base.UniteComposee;
import base.UniteSimple;
import exceptions.DimensionIllegaleException;
import exceptions.UniteIllegaleException;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== TEST DU SYSTÈME DE CONVERSION ===\n");

        try {
            // Dimensions
            Dimension dimLong = new Dimension(new int[]{1, 0, 0, 0, 0, 0, 0, 0});
            Dimension dimSurf = new Dimension(new int[]{2, 0, 0, 0, 0, 0, 0, 0});

            // Unités simples
            UniteSimple m = new UniteSimple("m", "mètre", dimLong, 1.0);
            UniteSimple km = new UniteSimple("km", "kilomètre", dimLong, 1000.0);

            // Test 1 : km -> m
            Quantite q1 = new Quantite(2.0, km);
            System.out.println("Test 1 : " + q1 + " = " + q1.convertirVers(m));

            // Test 2 : km² -> m² (Unité composée)
            UniteComposee km2 = new UniteComposee("km²", "kilomètre carré", dimSurf);
            km2.ajouterUnite(km, 2); // C'est ici que le '2' est vital

            UniteComposee m2 = new UniteComposee("m²", "mètre carré", dimSurf);
            m2.ajouterUnite(m, 2);

            Quantite surface = new Quantite(1.0, km2);
            System.out.println("Test 2 : " + surface + " = " + surface.convertirVers(m2));
            // Résultat attendu : 1 000 000 m²

        } catch (UniteIllegaleException e) {
            System.err.println(e.getMessage());
        } catch (DimensionIllegaleException dim_e) {
            System.err.println(dim_e.getMessage());
        }
    }
}

import base.*;
import exceptions.DimensionIllegaleException;
import exceptions.UniteIllegaleException;

void main() {
    IO.println("=== TEST DU SYSTÈME DE CONVERSION ===\n");

    try {
        Unite euro = UniteSimple.euro();
        Unite m = UniteSimple.metre();
        Unite s = UniteSimple.seconde();

        Unite dollar = new UniteSimple("$", 0.92, euro);
        Unite km = new UniteSimple("km", 1000.0, m);
        Unite h = new UniteSimple("h", 3600.0, s);

        Unite m3 = new UniteSimple("m^3", 1.0, m);

        Unite litre = new UniteSimple("l", 0.001, m3);
        Unite gallon = new UniteSimple("gal", 3.78541, litre);

        Unite dollar_gal = UniteComposee.diviser(dollar, gallon);
        Unite euro_gal = UniteComposee.diviser(euro, gallon);
        Unite euro_L = UniteComposee.diviser(euro, litre);

        Unite m_s = UniteComposee.diviser(m, s);
        Unite km_h = UniteComposee.diviser(km, h);

        Unite kt = new UniteComposee("kt", 0.514444, m_s);

        // --- EXÉCUTION DES CONVERSIONS ---

        IO.println("--- Scénario 1 : Essence  ---");
        Quantite prixUS = new Quantite(5.2, dollar_gal);

        Quantite prixEuroGal = prixUS.convertirVers(euro_gal);
        Quantite prixEuroLitre = prixUS.convertirVers(euro_L);

        IO.println(prixUS + " = " + prixEuroGal);
        IO.println(prixUS + " = " + prixEuroLitre);


        IO.println("\n--- Scénario 2 : Vitesse marine ---");
        Quantite vitesseMarine = new Quantite(100, kt);

        Quantite vitesseTerre = vitesseMarine.convertirVers(km_h);

        IO.println(vitesseMarine + " = " + vitesseTerre);

    } catch (DimensionIllegaleException dim_e) {
        System.err.println(dim_e.getMessage());
    } catch (UniteIllegaleException e) {
        throw new RuntimeException(e);
    }
}

import base.*;
import design_patterns.visiteur.SimplificateurVisiteur;
import exceptions.DimensionIllegaleException;
import exceptions.UniteIllegaleException;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== TEST DU SYSTÈME DE CONVERSION ===\n");

        try {
            SimplificateurVisiteur visiteur = new SimplificateurVisiteur();
            Unite km = new UniteSimple("km", Dimension.simple(0), 1000.);
            Unite s = new UniteSimple("s", Dimension.simple(2), 1.);
            Unite m = new UniteSimple("m", Dimension.simple(0), 1.);

            Unite km_s = new UniteDerivee(km.getDimension().diviser(s.getDimension()), km.getTauxConversion()/s.getTauxConversion());
            Unite m_s = new UniteDerivee(m.getDimension().diviser(s.getDimension()), m.getTauxConversion()/s.getTauxConversion());

            System.out.println(km_s);
            System.out.println(m_s);

            Quantite q1 = new Quantite(1000, km_s);
            Quantite q2 = new Quantite(350, m_s);

            Quantite q3 = q1.addition(q2);

            System.out.println(q1 + " + " + q2 + " = " + q3);
            System.out.println(q3.convertirVers(m_s));

        } catch (DimensionIllegaleException | UniteIllegaleException e) {
            System.err.println(e.getMessage());
        }
    }
}

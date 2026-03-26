import base.*;
import exceptions.DimensionIllegaleException;
import exceptions.UniteIllegaleException;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== TEST DU SYSTÈME DE CONVERSION ===\n");

        try {
            Unite km = new UniteSimple("km", Dimension.parIndice(0), 1000.);
            Unite m = new UniteSimple("m", Dimension.parIndice(0), 1.);
            Unite s = new UniteSimple("s", Dimension.parIndice(2), 1.);

            UniteComposee km_s = new UniteComposee();
            km_s.ajouterUnite(km, 1);
            km_s.ajouterUnite(s, -1);

            UniteComposee m_s = new UniteComposee();
            m_s.ajouterUnite(m, 1);
            m_s.ajouterUnite(s, -1);

            Quantite q1 = new Quantite(100, km_s);
            Quantite q2 = new Quantite(30, m);

            Quantite q3 = q1.division(q2);

            System.out.println(q1 + " / " + q2 + " = " + q3);
            q1.aff_vecteur();
            q2.aff_vecteur();
            q3.aff_vecteur();
        } catch (DimensionIllegaleException dim_e) {
            System.err.println(dim_e.getMessage());
        } catch (UniteIllegaleException e) {
            throw new RuntimeException(e);
        }
    }
}

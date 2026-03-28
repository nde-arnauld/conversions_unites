import base.*;
import exceptions.DimensionIllegaleException;
import exceptions.UniteIllegaleException;

void main() {
    System.out.println("=== TEST DU SYSTÈME DE CONVERSION ===\n");

    try {
        Unite km = UniteSimple.deLongueur("km", 1000.);
        Unite m = UniteSimple.metre();
        Unite s = UniteSimple.seconde();
        Unite min = UniteSimple.deTemps("min", 60.);

        UniteComposee km_s = new UniteComposee();
        km_s.ajouterUnite(km, 1);
        km_s.ajouterUnite(s, -1);

        UniteComposee m_min = new UniteComposee();
        m_min.ajouterUnite(m, 1);
        m_min.ajouterUnite(min, -1);

        Quantite q1 = new Quantite(100, km_s);
        Quantite q2 = new Quantite(60, m_min);

        Quantite q3 = q1.multiplication(q2);

        IO.println(q1 + " / " + q2 + " = " + q3);
    } catch (DimensionIllegaleException dim_e) {
        System.err.println(dim_e.getMessage());
    } catch (UniteIllegaleException e) {
        throw new RuntimeException(e);
    }
}

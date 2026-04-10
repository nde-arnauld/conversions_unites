package design_patterns.visiteur;

import base.*;
import design_patterns.registre.RegistreUnites;
import exceptions.UniteIllegaleException;

public class SimplificateurVisiteur implements Visiteur {

    @Override
    public void visite(Unite unite) throws UniteIllegaleException {
        if (unite == null) throw new UniteIllegaleException("l'unité à simplifier est nulle!");
        String symboleFinal = RegistreUnites.trouverSymbole(unite.getDimension());
        
        if (symboleFinal != null) {
            unite.setSymbole(symboleFinal);
        }
    }
}

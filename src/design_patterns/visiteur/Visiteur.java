package design_patterns.visiteur;

import base.Unite;
import exceptions.UniteIllegaleException;

public interface Visiteur {
    public void visite(Unite unite) throws UniteIllegaleException;
}

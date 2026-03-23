package design_patterns.visiteur;

import base.UniteComposee;
import base.UniteSimple;
import exceptions.UniteIllegaleException;

public interface Visiteur {
    public void visite(UniteSimple uniteSimple) throws UniteIllegaleException;
    public void visite(UniteComposee uniteComposee) throws UniteIllegaleException;
}

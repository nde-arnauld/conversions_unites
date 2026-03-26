package base;

import design_patterns.visiteur.Visiteur;
import exceptions.UniteIllegaleException;

import java.util.Arrays;

public abstract class Unite {
    protected String symbole;
    protected Dimension dimension;
    protected double tauxConversion;

    public Unite(String symbole, Dimension dimension) {
        this.symbole = symbole;
        this.dimension = dimension;
    }

    public Dimension getDimension() {
        return this.dimension;
    }
    public String getSymbole() {
        return this.symbole;
    }
    public void setSymbole(String symbole) { this.symbole = symbole; }

    public double convertirVers(Unite cible) throws UniteIllegaleException {
        if (!this.dimension.estMemeDimension(cible.dimension))
            throw new UniteIllegaleException("Unités incompatibles: vous essayez de convertir '" + this.getSymbole() + "' en '" + cible.getSymbole() + "' !");
        return this.getTauxConversion() / cible.getTauxConversion();
    }

    public abstract double getTauxConversion(); // Taux par rapport à l'unité de référence du SI.
    public abstract void accepteVisiteur(Visiteur visiteur) throws UniteIllegaleException;

    @Override
    public String toString() {
        return " " + this.symbole;
    }
}

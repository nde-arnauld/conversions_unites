package base;

import exceptions.UniteIllegaleException;

import java.util.Arrays;

public abstract class Unite {
    protected String symbole;
    protected String nom;
    protected Dimension dimension;
    public abstract double getTauxConversion(); // Taux par rapport à l'unité de référence du SI.

    public Dimension getDimension() {
        return this.dimension;
    }

    public String getSymbole() {
        return this.symbole;
    }

    public double convertirVers(Unite cible) throws UniteIllegaleException {
        if (!Arrays.equals(this.dimension.getVecteur(), cible.getDimension().getVecteur()))
            throw new UniteIllegaleException("Unités incompatibles: vous essayez de convertir '" + this.getSymbole() + "' en '" + cible.getSymbole() + "' !");
        return this.getTauxConversion() / cible.getTauxConversion();
    }

    @Override
    public String toString() {
        return this.symbole + " (" + this.nom + ")";
    }
}

package base;

import design_patterns.visiteur.Visiteur;
import exceptions.UniteIllegaleException;

public abstract class Unite {
    protected String symbole;
    protected Dimension dimension;

    public Unite(String symbole, Dimension dimension) {
        this.symbole = symbole;
        this.dimension = dimension;
    }

    public Dimension getDimension() { return this.dimension; }

    public String getSymbole() { return this.symbole; }

    public void setSymbole(String symbole) { this.symbole = symbole; }

    public boolean estCompatible(Unite autre) {
        return this.dimension.equals(autre.getDimension());
    }

    public abstract double getTauxConversion();

    public double convertirVers(Unite cible) throws UniteIllegaleException {
        if (!this.dimension.equals(cible.getDimension()))
            throw new UniteIllegaleException("Unités incompatibles: vous essayez de convertir '" + this.getSymbole() + "' en '" + cible.getSymbole() + "' !");
        return this.getTauxConversion() / cible.getTauxConversion();
    }

    public abstract void accepteVisiteur(Visiteur visiteur) throws UniteIllegaleException;

    @Override
    public String toString() {
        return this.symbole;
    }
}

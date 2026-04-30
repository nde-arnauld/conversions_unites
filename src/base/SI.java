package base;

public enum SI {
    LONGUEUR("m", 0),
    MASSE("kg", 1),
    TEMPS("s", 2),
    COURANT("A", 3),
    TEMPERATURE("K", 4),
    SUBSTANCE("mol", 5),
    INTENSITE("cd", 6),
    MONNAIE("€", 7);

    private String symbole;
    private int indice;

    private SI(String symbole, int indice) { this.symbole = symbole; this.indice = indice; }

    public String getSymbole() { return this.symbole; }
    public int getIndice() { return this.indice; }
    public static int getNombreDimensions() { return SI.values().length; }
}

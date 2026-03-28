package base;

public enum SI {
    LONGUEUR("m"),
    MASSE("kg"),
    TEMPS("s"),
    COURANT("A"),
    TEMPERATURE("K"),
    SUBSTANCE("mol"),
    INTENSITE("cd"),
    MONNAIE("€");

    private String symbole;
    private SI(String symbole) { this.symbole = symbole; }
    public String getSymbole() { return this.symbole; }
}

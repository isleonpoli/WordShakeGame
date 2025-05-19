package model;

public class wordFound {
    
    private String palabra;
    private int puntaje;

    public wordFound(String palabra, int puntaje) {
        this.palabra = palabra;
        this.puntaje = puntaje;
    }

    public String getPalabra() {
        return palabra;
    }

    public int getPuntaje() {
        return puntaje;
    }
}
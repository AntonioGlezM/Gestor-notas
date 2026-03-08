package utils;

public class Validaciones {

    public static boolean estaVacio(String texto) {
        return texto == null || texto.trim().isEmpty();
    }
}
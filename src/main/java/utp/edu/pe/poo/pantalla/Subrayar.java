package utp.edu.pe.poo.pantalla;

public class Subrayar {
    public static String Subrayar(int tamaño, char caracter) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < tamaño; i++) {
            builder.append(caracter);
        }
        return builder.toString();
    }
}

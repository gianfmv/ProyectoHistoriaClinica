package utp.edu.pe.poo.pantalla;

public class Pantalla {
    private LecturaInformacion lecturaInformacion = new LecturaInformacion();
    
    public int Menu(String titulo, String[] opciones){
        Titulo.Mensaje(titulo);
        for (String opcion: opciones) {
            System.out.println(opcion);
        }
        int opcion = lecturaInformacion.lecturaInt();
        return opcion;
    }
}
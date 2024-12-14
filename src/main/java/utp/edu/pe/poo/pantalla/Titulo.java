
package utp.edu.pe.poo.pantalla;
public  class Titulo {
    public static void Mensaje(String mensaje){
        if(mensaje.length()>0)
        {
            System.out.println(mensaje.toUpperCase());
            System.out.println(
                    Subrayar.Subrayar(mensaje.length(),'=')
            );
        }        
    }
}

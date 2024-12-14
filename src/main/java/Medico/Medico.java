/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Medico;

//import Encriptar.EncriptadorCesar;
import Usuario.Usuario;
import java.util.ArrayList;

/**
 *
 * @author MATEO
 */
//La clase Medico hereda de Usuario y representa a un médico en el sistema.
public class Medico extends Usuario{

    private String especialidad;      

    public Medico(String nombre, String apellido, String dni, String password, String especialidad) {
        super(nombre,apellido, dni, password);
        this.especialidad = especialidad;
    }

    //Método estático para iniciar sesión del médico
    public static Medico login(ArrayList<Medico> listaMedicos, String dni, String password) {   
        System.out.println(listaMedicos.size());// lista vacía revisar
       for (Medico medico : listaMedicos) {            
             if (medico.verificarPassword(dni, password)) {
                    return medico;
                }
            }
        return null;
    }
        
    // Sobreescribimos los metódos abstractos de la clase usuario
    @Override
    public boolean verificarPassword(String dni, String inputPassword) {
        // Utiliza el método encriptarPassword de la clase base abstracta Usuario para verificar la contraseña
        return this.getDni().equals(dni) && this.getPassword().equals(this.encriptarPassword(inputPassword));
    }
       
    public String getEspecialidad() {
        return especialidad;
    }
    // Método para el diagnóstico genera un diagnóstico basado en síntomas
    public String diagnosticar(String sintomas) {
        return "Diagnóstico basado en: " + sintomas;
    }
    // Método para recetar tratamiento
    public String recetarTratamiento(String tratamiento) {
        return tratamiento;  // Devuelve el tratamiento que fue ingresado
    } 
}

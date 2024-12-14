/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Paciente;

import Interfaces_HClinica.IHistoriaClinica;
import Interfaces_HClinica.IHistorial;
import Usuario.Usuario;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Fernando Risco 
 */
//La clase Paciente también hereda de Usuario y representa a un paciente en el sistema, además implementa la interface IHistorial para gestionar las historias clínicas.

public class Paciente extends Usuario implements IHistorial {
    // Composición: Un paciente "tiene" una lista de historias clínicas.
    // Si el paciente deja de existir, sus historias clínicas también lo hacen.
    private String direccion;
    private String telefono;

    private List<IHistoriaClinica> historiasClinicas; // Composición: Cada paciente tiene sus propias historias clínicas}
   //  private List<String> historialConsultas;
    
    public Paciente(String nombre, String apellido,String dni, String password, String direccion, String telefono) {
        super(nombre,apellido, dni, password);
        this.direccion = direccion;
        this.telefono = telefono;
        this.historiasClinicas = new ArrayList<>(); // Inicializar la lista
    }
    
    public String getDireccion() {
        return direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    // Sobreescribimos los metódos abstractos de la clase usuario
    @Override
    public boolean verificarPassword(String dni, String inputPassword) {
        // Utiliza el método encriptarPassword de la clase base Usuario
        return this.getDni().equals(dni) && this.getPassword().equals(this.encriptarPassword(inputPassword));
    }
      
    @Override
    public String toString() {  
        return getNombre() + "," + getApellido() + "," + getDni() + "," + direccion + "," + telefono + "," + this.getPassword();
    }

    @Override
    public void agregarHistoriaClinica(IHistoriaClinica historia) {
        this.historiasClinicas.add(historia); 
    }

    @Override
    public List<IHistoriaClinica> obtenerHistoriasClinicas() {
         return historiasClinicas;
    }
}

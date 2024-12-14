/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;


import Medico.Medico;
import Paciente.Paciente;
import Usuario.Usuario;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Gian Marrufo
 */
public class PersisteUsuario {
    
    private static File archivo;
    private static FileReader fr;
    private static BufferedReader br;
    private static ArrayList<Medico> listaMedicos = new ArrayList<>();
    private static ArrayList<Paciente> listaPacientes = new ArrayList<>();
    private static final String PATH_ARCHIVO = "D:/usuarios.txt"; 

    // Método para agregar un usuario (ya sea Médico o Paciente)
    public static void agrega(Usuario usuario, String tipo) throws IOException {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(PATH_ARCHIVO, true))) { // `true` activa el modo de "append"
            bufferedWriter.write(usuario.toString() + "," + tipo);
            bufferedWriter.newLine(); // Añade una nueva línea después de cada usuario
        } catch (IOException e) {
            System.out.print("Error al escribir en el archivo " + e.getMessage());
        }
    }

    // Método para leer usuarios (Médico o Paciente)
    public static Map<String, Usuario> leeUsuario(ArrayList<Medico> listaMedicos, ArrayList<Paciente> listaPacientes) throws IOException {
        Usuario usuario = null;
        String[] tokensLinea = null;
        String tipo = "";

        Map<String, Usuario> lectura = new HashMap<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(PATH_ARCHIVO))) {
            String linea;

            while ((linea = bufferedReader.readLine()) != null) {
                tokensLinea = linea.split(",");
                String nombre = tokensLinea[0];
                String apellido = tokensLinea[1];
                String dni = tokensLinea[2];
                String direccion= tokensLinea[3];
                String telefono= tokensLinea[4];
                String password = tokensLinea[5];
                tipo = tokensLinea[6];

                if (tipo.equals("Paciente")) {                    
                   usuario = new Paciente(nombre, apellido, dni, password, direccion, telefono);
                   
                } else if (tipo.equals("Medico")) {                    
                     usuario = new Medico(nombre, apellido, dni, password, direccion);   
                     System.out.println(usuario.getNombre()+" "+usuario.getPassword());
                     Medico medicoLogueado = (Medico) usuario;                 
                     listaMedicos.add(medicoLogueado);
                     System.out.println(medicoLogueado.getNombre()+" " +medicoLogueado.getPassword() + listaMedicos.size());
                }
            }
        } catch (IOException e) {
            System.out.print("Error al leer el archivo " + e.getMessage());
        }

        if (usuario != null) {
            lectura.put(tipo, usuario);
        }
        return lectura;
    }
}
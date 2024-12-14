/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Paciente;

import Interfaces_HClinica.HistoriaClinica;
import Utils.PersisteUsuario;
import Interfaces_HClinica.IHistoriaClinica;
import Medico.Medico;
import java.io.BufferedReader;
import java.io.FileReader;
import utp.edu.pe.poo.pantalla.LecturaInformacion;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
//import java.util.Map;

/**
 * En esta clase agrupamos los métodos que nos servirán para la atención del paciente
 * 
 * @author Gian Marrufo
 */
public class GestionaPaciente {

    private static final String FILE_PATH = "D:/usuarios.txt"; // Ruta del archivo de persistencia que almacena los usuarios
    private static LecturaInformacion lecturaInformacion = new LecturaInformacion();     
    private List<Paciente> pacientes;
 
    public GestionaPaciente(List<Paciente> pacientes) {
        this.pacientes = cargarPacientesDesdeArchivo(FILE_PATH);
    }
    /*
     * Cargamos los pacientes desde un archivo de texto y grabamos en una Arraylist listaPacientes los pacientes encontrados.
     * El archivo debe tener un formato específico separados por comas y
     * el último campo indica si es un Paciente o Medico.
    */
    public List<Paciente> cargarPacientesDesdeArchivo(String rutaArchivo) {
     List<Paciente> listaPacientes = new ArrayList<>();// array para guardar los pacientes

            try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
                String linea;
                while ((linea = br.readLine()) != null) {
                    String[] datos = linea.split(",");

                    // Verificamos si es un paciente y que la línea tenga la longitud adecuada en este caso debe tener 7 campos
                    if (datos.length == 7 && datos[6].equals("Paciente"))  {
                        String nombre = datos[0].trim();
                        String apellido = datos[1].trim();
                        String dni = datos[2].trim();
                        String direccion = datos[3].trim();
                        String telefono = datos[4].trim();
                        String password = datos[5].trim();  // Clave encriptada

                        // Creamos un nuevo objeto Paciente con los datos leídos
                        Paciente paciente = new Paciente(nombre, apellido, dni, password, direccion, telefono);
                        listaPacientes.add(paciente);//lo agregamos al array
                        //pacientes.add(paciente);
                    }
                }
            } catch (IOException e) {
                System.out.println("Error al leer el archivo de pacientes: " + e.getMessage());
            }        
            return listaPacientes;  // Devolvemos la lista de pacientes cargados 
    }    
    
       // metodo para verificar el inicio de sesión del paciente, validamos el dni y la contraseña
       public Paciente login(String dni, String password) {         //List<Paciente> listaPacientes
         System.out.println(pacientes.size());
           for (Paciente paciente : pacientes) {              
             if (paciente.verificarPassword(dni, password)) {                             
                return paciente; // retornamos al paciente si las credenciales son correctas
            }
        }
        return null; // sino retornamos null
    }
           
    // Método para registrar un nuevo paciente
    public void registrarNuevoPaciente(Scanner sc) throws IOException {
        String dni = lecturaInformacion.lecturaString("Registro de Paciente", "DNI: ");
        // Verificamos si el paciente ya existe buscando por DNI
        Paciente pacienteExistente = buscarPacientePorDni(dni);//,FILE_PATH);
        if (pacienteExistente != null) {
            System.out.println("Error: Ya existe un paciente registrado con el DNI: " + dni);
            return;  // Salimos del método si ya existe
        }

        // Si no existe, procedemos a registrar el nuevo paciente
        String nombre = lecturaInformacion.lecturaString("", "Nombre: ");
        String apellido = lecturaInformacion.lecturaString("", "Apellido: ");
        String direccion = lecturaInformacion.lecturaString("", "Dirección: ");
        String telefono = lecturaInformacion.lecturaString("", "Teléfono: ");
        String password = lecturaInformacion.lecturaString("", "Password: ");

        Paciente nuevoPaciente = new Paciente(nombre, apellido, dni, password, direccion, telefono);
        //Encriptamos la contraseña usando el método de setPassword de la clase abstracta Usuario, la cual se grabará encriptada en el archivo.
        nuevoPaciente.setPassword(password);        
       // listaPacientes.add(nuevoPaciente);//Agregamos a la lista el nuevo paciente   
        pacientes.add(nuevoPaciente);//Agregamos a la lista el nuevo paciente       
        System.out.println("Paciente registrado con éxito.");

        // Persistir el nuevo paciente en el archivo (se graba en el archivo de txt el paciente)
        PersisteUsuario.agrega(nuevoPaciente, "Paciente");
}   
    
    // buscamos un paciente en el archivo usando su DNI.
    public Paciente buscarPacientePorDni(String dni) {
       // List<Paciente> listaPacientes = cargarPacientesDesdeArchivo(rutaArchivo);

        for (Paciente paciente : pacientes) {
            if (paciente.getDni().equals(dni)) {
                return paciente;  // Retornar el paciente si el DNI coincide
            }
        }
        return null;  // Si no se encuentra, retornamos null
    }
    
    // Método para ver la lista de pacientes
    public void verListaPacientes() {
       // List<Paciente> listaPacientes = cargarPacientesDesdeArchivo(rutaArchivo);

        if (pacientes.isEmpty()) {
            System.out.println("No hay pacientes registrados.");
        } else {
            System.out.println("Se encontraron " + pacientes.size() + " pacientes."); 
            for (Paciente paciente : pacientes/*listaPacientes*/) {
                System.out.println("Nombre: " + paciente.getNombre() + " " + paciente.getApellido() + ", DNI: " + paciente.getDni());
            }
        }
    }

       
       
    // Método para ver el historial de consultas de un paciente logueado
    public void verHistorialPaciente(Paciente paciente) {
        List<IHistoriaClinica> historial = paciente.obtenerHistoriasClinicas();
        if (historial.isEmpty()) {
            System.out.println("No hay consultas registradas para este paciente.");
        } else {
            for (IHistoriaClinica hc : historial) {
                System.out.println(hc);
            }
        }
    }
    
    
        public void registrarConsulta(Scanner sc, Medico medico) throws IOException {
        System.out.print("DNI del paciente: ");
        String dni = sc.nextLine();
      //  GestionaPaciente ges =new GestionaPaciente(pacientes);
        
            Paciente paciente = buscarPacientePorDni(dni);

            if (paciente == null) {
                System.out.println("Paciente no encontrado. Registra al paciente primero.");
            } else {
                System.out.println("Paciente: " + paciente.getNombre() + " " + paciente.getApellido());

                String sintomas = lecturaInformacion.lecturaString("SINTOMAS", "Ingrese síntomas del paciente: ");

                LocalDate fechaConsulta = LocalDate.now();
                String diagnostico = medico.diagnosticar(sintomas);
                String tratamiento = lecturaInformacion.lecturaString("Recetar tratamiento", "Ingrese el tratamiento: ");

                // Creamos la historia clínica y registramos la consulta (upcasting implícito)
                IHistoriaClinica historiaClinica = new HistoriaClinica(paciente, medico, fechaConsulta);
                historiaClinica.registrarAtencion(diagnostico, tratamiento);

                paciente.agregarHistoriaClinica(historiaClinica);

                System.out.println("Consulta registrada con éxito.");
                System.out.println(historiaClinica.obtenerInformacionConsulta());
                System.out.println("Número de historias clínicas del paciente: " + paciente.obtenerHistoriasClinicas().size());
            }
    }
        
        
        // Método para ver historial de consultas
    public void verHistorialConsultas(Scanner sc) {
        System.out.print("Ingrese el DNI del paciente: ");
        String dni = sc.nextLine();
       
        // Buscar el paciente por DNI
        Paciente paciente =  buscarPacientePorDni(dni);//new GestionaPaciente(listaPacientes).buscarPacientePorDni(dni);

        if (paciente == null) {
            System.out.println("Paciente no encontrado.");
            return;
        }
        
        List<IHistoriaClinica> historial = paciente.obtenerHistoriasClinicas();
            if (historial.isEmpty()) {
                System.out.println("Este paciente no tiene consultas registradas.");
            } else {
                for (IHistoriaClinica consulta : historial) {
                    System.out.println(consulta.obtenerInformacionConsulta());
                }
            }
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.proyectoclinica;

import CGenerica.GestionaConsulta;
import CGenerica.MantenimientoUsuario;
import Medico.Medico;
import Paciente.GestionaPaciente;
import Paciente.Paciente;
import Tablas.Consultas;
import Tablas.Usuarios;
import Usuario.Usuario;
import Utils.PersisteUsuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import utp.edu.pe.poo.pantalla.Pantalla;

/**
 *
 * @author Gian Marrufo
 */
public class ProyectoClinica {

    private static EntityManagerFactory emf;
    private static EntityManager em;
    private static Pantalla pantalla = new Pantalla();
    
    public static void main(String[] args) throws IOException {
     
        emf = Persistence.createEntityManagerFactory("clinica");
        em = emf.createEntityManager();
        
        Scanner sc = new Scanner(System.in);
        boolean salir = false; // variable para controlar la salida del programa
        Usuarios usuarioLogueado = null; // Variable para almacenar el usuario que inicia sesión

        // Bucle principal del programa que se ejecuta mientras no se desee salir
        while (!salir) {
            // Si no hay un usuario logueado, solicitamos las credenciales
            if (usuarioLogueado == null) {
                System.out.println("******* INGRESO AL SISTEMA CLINICA UTP ***********");
                System.out.print("Ingrese su DNI para iniciar sesión: ");
                String dni = sc.nextLine();
                System.out.print("Ingrese su contraseña: ");
                String password = sc.nextLine();

                // Intentamos iniciar sesión
                MantenimientoUsuario servicioUsuario = new MantenimientoUsuario(em);
                usuarioLogueado = servicioUsuario.login(dni, password);

                // Verificamos si el usuario no fue encontrado
                if (usuarioLogueado == null) {
                    System.out.println("DNI o contraseña incorrectos. Intente nuevamente.");
                } else if ("Medico".equals(usuarioLogueado.getTipo())) {
                    // si es médico, mostramos el menú de médico
                    System.out.println("\nMédico " + usuarioLogueado.getDni() + " ha iniciado sesión.");
                    salir = mostrarMenuMedico(sc, usuarioLogueado);
                    usuarioLogueado = null; // Reiniciamos el usuario logueado a null para pedir credenciales nuevamente si se cierra la sesión
                } else if ("Paciente".equals(usuarioLogueado.getTipo())) {
                    // si es paciente, mostramos el menú de paciente
                    System.out.println("\nPaciente " + usuarioLogueado.getDni() + " ha iniciado sesión.");
                    salir = mostrarMenuPaciente(sc, usuarioLogueado);
                    usuarioLogueado = null; // Volvemos a pedir credenciales tras cerrar sesión
                }
            }
        }
        em.close();
        emf.close();
        sc.close();
    
    }
    
        // Método estático para mostrar el menú del médico
    private static boolean mostrarMenuMedico(Scanner sc, Usuarios medicoLogueado) {
        boolean salir = false; // Variable para controlar la salida del menú médico
        MantenimientoUsuario servicioUsuario = new MantenimientoUsuario(em);
        GestionaConsulta gestionConsulta = new GestionaConsulta(em);

        while (!salir) {
            System.out.println("\n====== Menú Médico ======");
            System.out.println("1. Ver pacientes");
            System.out.println("2. Registrar consulta");
            System.out.println("3. Registrar paciente");
            System.out.println("4. Cerrar sesión");
            System.out.print("Seleccione una opción: ");
            int opcion = sc.nextInt();
            sc.nextLine();  // Limpiar el buffer

            switch (opcion) {
                case 1:
                    System.out.println("Ver pacientes");
                    verPacientes(servicioUsuario);
                    break;
                case 2:
                    System.out.println("Registrar consulta");
                    registrarConsulta(sc, medicoLogueado, gestionConsulta);
                    break;
                case 3:
                    System.out.println("Registrar paciente");
                    registrarPaciente(sc, servicioUsuario);
                    break;
                case 4:
                    System.out.println("Sesión cerrada.");
                    salir = true;
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }
        return salir;
    }

    // Método para ver pacientes registrados
    private static void verPacientes(MantenimientoUsuario servicioUsuario) {
        List<Usuarios> pacientes = servicioUsuario.obtenerTodosUsuarios();
        System.out.println("Lista de pacientes:");
        for (Usuarios paciente : pacientes) {
            if (paciente.getTipo() == Usuarios.TipoUsuario.PACIENTE) {
                System.out.println(paciente.getNombre() + " " + paciente.getApellido());
            }
        }
    }

    // Método para registrar un nuevo paciente
    private static void registrarPaciente(Scanner sc, MantenimientoUsuario servicioUsuario) {
        System.out.print("Ingrese nombre: ");
        String nombre = sc.nextLine();
        System.out.print("Ingrese apellido: ");
        String apellido = sc.nextLine();
        System.out.print("Ingrese DNI: ");
        String dni = sc.nextLine();
        System.out.print("Ingrese contraseña: ");
        String password = sc.nextLine();
        System.out.print("Ingrese dirección: ");
        String direccion = sc.nextLine();
        System.out.print("Ingrese teléfono: ");
        String telefono = sc.nextLine();

        // Crear un nuevo paciente
        Usuarios nuevoPaciente = new Usuarios(nombre, apellido, dni, password, direccion, telefono, Usuarios.TipoUsuario.PACIENTE);
        servicioUsuario.registrarUsuario(nuevoPaciente);
        System.out.println("Paciente registrado exitosamente.");
    }

    // Método para registrar una consulta
    private static void registrarConsulta(Scanner sc, Usuarios medicoLogueado, GestionaConsulta gestionConsulta) {
        System.out.print("Ingrese ID del paciente: ");
        Long pacienteId = sc.nextLong();
        sc.nextLine();  // Limpiar el buffer
        System.out.print("Ingrese diagnóstico: ");
        String diagnostico = sc.nextLine();
        System.out.print("Ingrese tratamiento: ");
        String tratamiento = sc.nextLine();

        // Crear consulta
        Consultas nuevaConsulta = new Consultas(LocalDate.now(), medicoLogueado, obtenerPaciente(pacienteId), diagnostico, tratamiento);
        gestionConsulta.registrarConsulta(nuevaConsulta);
        System.out.println("Consulta registrada exitosamente.");
    }

    // Método para obtener un paciente por su ID
    private static Usuarios obtenerPaciente(Long pacienteId) {
        MantenimientoUsuario servicioUsuario = new MantenimientoUsuario(em);
        return servicioUsuario.obtenerUsuarioPorId(pacienteId);
    }

    // Menú paciente
    private static boolean mostrarMenuPaciente(Scanner sc, Usuarios pacienteLogueado) {
        boolean salir = false; // Variable para controlar la salida del menú paciente
        GestionaConsulta gestionConsulta = new GestionaConsulta(em); // Para gestionar las consultas del paciente

        while (!salir) {
            System.out.println("\n====== Menú Paciente ======");
            System.out.println("1. Ver historial");
            System.out.println("2. Cerrar sesión");
            System.out.print("Seleccione una opción: ");
            int opcion = sc.nextInt();
            sc.nextLine();  // Limpiar el buffer

            switch (opcion) {
                case 1:
                    System.out.println("Ver historial");
                    verHistorialPaciente(pacienteLogueado, gestionConsulta);
                    break;
                case 2:
                    System.out.println("Sesión cerrada.");
                    salir = true;
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }
        return salir;
    }
    
    
    private static void verHistorialPaciente(Usuarios pacienteLogueado, GestionaConsulta gestionConsulta) {
    // Recuperamos todas las consultas asociadas al paciente
    List<Consultas> consultas = gestionConsulta.obtenerConsultasPorPaciente(pacienteLogueado.getId());

    if (consultas.isEmpty()) {
        System.out.println("No tienes consultas registradas.");
    } else {
        System.out.println("Historial de consultas:");
        for (Consultas consulta : consultas) {
            System.out.println("Fecha: " + consulta.getFecha());
            System.out.println("Médico: " + consulta.getMedico().getNombre() + " " + consulta.getMedico().getApellido());
            System.out.println("Diagnóstico: " + consulta.getDiagnostico());
            System.out.println("Tratamiento: " + consulta.getTratamiento());
            System.out.println("--------------------------------------------------");
        }
    }
    }
}

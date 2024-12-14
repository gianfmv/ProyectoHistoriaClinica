/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.proyectoclinica;

import CGenerica.MantenimientoUsuario;
import Medico.Medico;
import Paciente.GestionaPaciente;
import Paciente.Paciente;
import Tablas.Usuarios;
import Usuario.Usuario;
import Utils.PersisteUsuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.io.IOException;
import java.util.ArrayList;
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
        while (!salir) {
            System.out.println("\n====== Menú Médico ======");
            System.out.println("1. Ver pacientes");
            System.out.println("2. Registrar consulta");
            System.out.println("3. Cerrar sesión");
            System.out.print("Seleccione una opción: ");
            int opcion = sc.nextInt();
            sc.nextLine();  // Limpiar el buffer

            switch (opcion) {
                case 1:
                    System.out.println("Ver pacientes");
                    // Lógica para mostrar pacientes
                    break;
                case 2:
                    System.out.println("Registrar consulta");
                    // Lógica para registrar una consulta
                    break;
                case 3:
                    System.out.println("Sesión cerrada.");
                    salir = true;
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }
        return salir;
    }

    // Menú paciente
    private static boolean mostrarMenuPaciente(Scanner sc, Usuarios pacienteLogueado) {
        boolean salir = false; // Variable para controlar la salida del menú paciente
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
                    // Lógica para mostrar historial del paciente
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
}

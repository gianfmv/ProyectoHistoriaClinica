/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CGenerica;

/**
 *
 * @author Gian Marrufo
 */
import Medico.Medico;
import Paciente.Paciente;
import Tablas.Usuarios;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.function.Predicate;

public class MantenimientoUsuario {

    private Repositorio<Usuarios> repositorioUsuario;
    private EntityManager em;

    public MantenimientoUsuario(EntityManager em) {
        this.em = em;
        this.repositorioUsuario = new Repositorio<>(em, Usuarios.class);
    }

    // Método de logueo por DNI y contraseña
   public Usuarios login(String dni, String password) {
        // Consulta simplificada con el campo 'tipo' que distingue entre Medico y Paciente
        TypedQuery<Usuarios> query = em.createQuery(
            "SELECT u FROM Usuario u WHERE u.dni = :dni AND u.password = :password", Usuarios.class);
        query.setParameter("dni", dni);
        query.setParameter("password", password);

        // Ejecutar la consulta y obtener el resultado
        Usuarios usuario = null;
        try {
            usuario = query.getSingleResult();
        } catch (Exception e) {
            // Si no se encuentra el usuario o ocurre algún error, el valor será null
            System.out.println("Usuario o contraseña incorrectos.");
        }

        return usuario;  // Puede ser un Medico o un Paciente, dependiendo del tipo
    }

    // Método para obtener todos los usuarios
    public List<Usuarios> obtenerTodosUsuarios() {
        return repositorioUsuario.obtenerTodos();
    }

    // Método para buscar un usuario por ID
    public Usuarios obtenerUsuarioPorId(Long id) {
        return repositorioUsuario.obtenerPorId(id);
    }

    // Método para registrar un nuevo usuario (Paciente o Médico)
    public void registrarUsuario(Usuarios usuario) {
        repositorioUsuario.guardar(usuario);
    }

    // Método para actualizar un usuario
    public void actualizarUsuario(Usuarios usuario) {
        repositorioUsuario.actualizar(usuario);
    }

    // Método para eliminar un usuario
    public void eliminarUsuario(Long id) {
        repositorioUsuario.eliminar(id);
    }
}
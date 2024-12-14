/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CGenerica;

/**
 *
 * @author Gian Marrufo
 */
import Tablas.Consultas;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class GestionaConsulta {

    private Repositorio<Consultas> repositorioConsulta;
    private EntityManager em;

    public GestionaConsulta(EntityManager em) {
        this.em = em;
        this.repositorioConsulta = new Repositorio<>(em, Consultas.class);
    }

    // Método para registrar una nueva consulta
    public void registrarConsulta(Consultas consulta) {
        repositorioConsulta.guardar(consulta);
    }

    // Método para obtener todas las consultas de un paciente
    public List<Consultas> obtenerConsultasPorPaciente(Long pacienteId) {
        String jpql = "SELECT c FROM Consulta c WHERE c.paciente.id = :pacienteId";
        Query query = em.createQuery(jpql);
        query.setParameter("pacienteId", pacienteId);
        return query.getResultList();
    }

    // Método para obtener todas las consultas de un médico
    public List<Consultas> obtenerConsultasPorMedico(Long medicoId) {
        String jpql = "SELECT c FROM Consulta c WHERE c.medico.id = :medicoId";
        Query query = em.createQuery(jpql);
        query.setParameter("medicoId", medicoId);
        return query.getResultList();
    }

    // Método para obtener una consulta por su ID
    public Consultas obtenerConsultaPorId(Long consultaId) {
        return repositorioConsulta.obtenerPorId(consultaId);
    }

    // Método para actualizar una consulta
    public void actualizarConsulta(Consultas consulta) {
        repositorioConsulta.actualizar(consulta);
    }

    // Método para eliminar una consulta
    public void eliminarConsulta(Long consultaId) {
        repositorioConsulta.eliminar(consultaId);
    }

    // Método para filtrar consultas con Predicate (utilizando Streams)
    public List<Consultas> filtrarConsultas(Predicate<Consultas> criterio) {
        // Obtener todas las consultas
        List<Consultas> consultas = repositorioConsulta.obtenerTodos();

        // Aplicar el filtro con el Predicate
        return consultas.stream()
                        .filter(criterio)  // Filtra las consultas con el criterio proporcionado
                        .collect(Collectors.toList());  // Devuelve una lista filtrada
    }

    // Métodos específicos de filtrado
    public List<Consultas> filtrarPorDiagnostico(String diagnostico) {
        return filtrarConsultas(c -> c.getDiagnostico() != null && c.getDiagnostico().contains(diagnostico));
    }

    public List<Consultas> filtrarPorTratamiento(String tratamiento) {
        return filtrarConsultas(c -> c.getTratamiento() != null && c.getTratamiento().contains(tratamiento));
    }

    public List<Consultas> filtrarPorFecha(java.util.Date fecha) {
        return filtrarConsultas(c -> c.getFecha().equals(fecha));
    }

    public List<Consultas> filtrarPorMedico(Long medicoId) {
        return filtrarConsultas(c -> c.getMedico().getId().equals(medicoId));
    }

    public List<Consultas> filtrarPorPaciente(Long pacienteId) {
        return filtrarConsultas(c -> c.getPaciente().getId().equals(pacienteId));
    }
}

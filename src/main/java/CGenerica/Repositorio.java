/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CGenerica;

/**
 *
 * @author Gian Marrufo
 */
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Repositorio<T> {

    private EntityManager em;
    private Class<T> type;

    public Repositorio(EntityManager em, Class<T> type) {
        this.em = em;
        this.type = type;
    }

    // consulta una entidad
    public List<T> obtenerTodos() {
        Query query = em.createQuery("SELECT e FROM " + type.getSimpleName() + " e");
        return query.getResultList();
    }

    // busqueda
    public T obtenerPorId(Long id) {
        return em.find(type, id);
    }

    // guardar objeto
    public void guardar(T entity) {
        em.getTransaction().begin();
        em.persist(entity);
        em.getTransaction().commit();
    }

    // actualizar objeto
    public void actualizar(T entity) {
        em.getTransaction().begin();
        em.merge(entity);
        em.getTransaction().commit();
    }

    // eliminar un objeto
    public void eliminar(Long id) {
        T entity = obtenerPorId(id);
        em.getTransaction().begin();
        em.remove(entity);
        em.getTransaction().commit();
    }

    // filtrar objetos usando Streams y Predicate
    public List<T> filtrar(Predicate<T> criterio) {
        List<T> lista = obtenerTodos();
        return lista.stream()
                    .filter(criterio)
                    .collect(Collectors.toList());
    }
}


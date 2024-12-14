/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Tablas;

/**
 *
 * @author Gian Marrufo
 */
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "consultas")
public class Consultas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate fecha;

    @ManyToOne
    @JoinColumn(name = "id_paciente", referencedColumnName = "id")
    private Usuarios paciente;

    @ManyToOne
    @JoinColumn(name = "id_medico", referencedColumnName = "id")
    private Usuarios medico;

    private String diagnostico;
    private String tratamiento;

    public Consultas() { }

    public Consultas(LocalDate fecha, Usuarios paciente, Usuarios medico, String diagnostico, String tratamiento) {
        this.fecha = fecha;
        this.paciente = paciente;
        this.medico = medico;
        this.diagnostico = diagnostico;
        this.tratamiento = tratamiento;
    }

    // Getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Usuarios getPaciente() {
        return paciente;
    }

    public void setPaciente(Usuarios paciente) {
        this.paciente = paciente;
    }

    public Usuarios getMedico() {
        return medico;
    }

    public void setMedico(Usuarios medico) {
        this.medico = medico;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(String tratamiento) {
        this.tratamiento = tratamiento;
    }

    @Override
    public String toString() {
        return "Consulta{" +
                "id=" + id +
                ", fecha=" + fecha +
                ", paciente=" + paciente.getNombre() +
                ", medico=" + medico.getNombre() +
                ", diagnostico='" + diagnostico + '\'' +
                ", tratamiento='" + tratamiento + '\'' +
                '}';
    }
}

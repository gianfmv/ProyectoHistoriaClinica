/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Interfaces_HClinica;

/**
 *Composición: HistoriaClinica depende de Paciente. Si el paciente es eliminado, su historia clínica también se elimina.
Asociación: La historia clínica se asocia a un Medico, pero la existencia del médico no depende de la historia clínica.
* 
 * @author Gian Marrufo
 */
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import Medico.Medico;
import Paciente.Paciente;
import java.util.ArrayList;
import java.util.List;

//Clase que implementa la interfaz IHistoriaClinica.
public class HistoriaClinica implements IHistoriaClinica{
    private Paciente paciente; // Composición: Historia clínica depende del paciente
    private Medico medico; //  La relación entre Medico y HistoriaClinica es una relación de agregación. Un médico puede realizar múltiples consultas sin que las historias clínicas sean dependientes de la existencia del médico.
    private LocalDate fechaConsulta;  
    private String diagnostico;
    private String tratamiento;
    private List<String> historiasClinicas; // Lista que guarda todas las consultas registradas     

    public HistoriaClinica(Paciente paciente, Medico medico, LocalDate fechaConsulta) {
        this.paciente = paciente;
        this.medico = medico;
        this.fechaConsulta = fechaConsulta;
        this.historiasClinicas = new ArrayList();
    }

    //Registra el diagnóstico y tratamiento en la historia clínica.
    @Override
    public void registrarAtencion(String diagnostico, String tratamiento) {
        this.diagnostico = diagnostico;
        this.tratamiento = tratamiento;
        
        // Guardar la información detallada en el historial de consultas.
        historiasClinicas.add(obtenerInformacionConsulta());
    }

    //Devuelve información detallada sobre la consulta
    @Override
    public String obtenerInformacionConsulta() {
        DateTimeFormatter formatear = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return "Paciente: " + paciente.getNombre() + "\n" +
               "Médico: " + medico.getNombre() + "\n" +
               "Fecha Consulta: " + fechaConsulta.format(formatear) + "\n" +
               "Diagnóstico: " + diagnostico + "\n" +
               "Tratamiento: " + tratamiento;
    }

}

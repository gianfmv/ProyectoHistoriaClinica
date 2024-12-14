/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces_HClinica;


/**
 *
 * @author Gian Marrufo
 */
public interface IHistoriaClinica {
    //Registra un diagnóstico y un tratamiento en la historia clínica.
    public void registrarAtencion(String diagnostico, String tratamiento);
    //Devuelve información sobre la consulta registrada.
    public String obtenerInformacionConsulta();
}

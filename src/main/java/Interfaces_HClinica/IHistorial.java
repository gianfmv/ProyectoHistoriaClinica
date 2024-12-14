/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces_HClinica;

import java.util.List;

/**
 *
 * @author Gian Marrufo
 */
public interface IHistorial {
     //Devuelve una lista de historias clínicas
    public List<IHistoriaClinica> obtenerHistoriasClinicas();
    //Agrega historia clínica
    public void agregarHistoriaClinica(IHistoriaClinica historia);
}

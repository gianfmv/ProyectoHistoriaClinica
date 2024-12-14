/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Encriptar;

import java.util.Base64;

/**
 *
 * @author Gian Marrufo
 */
public class EncriptadorBase64 implements IEncriptador {
   
    @Override
    public String Encriptar(String datoSinEncriptar) {
        return Base64.getEncoder().encodeToString(datoSinEncriptar.getBytes());
    }

    @Override
    public String Desencriptar(String datoEncriptado) {
        return new String(Base64.getDecoder().decode(datoEncriptado)); 
 
    }
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Encriptar;

/**
 *
 * @author Gian Marrufo
 */
public class EncriptadorXOR implements IEncriptador{

    private final char clave;

    public EncriptadorXOR(char clave) {
        this.clave = clave;
    }
    
    @Override
    public String Encriptar(String datoSinEncriptar) {
          StringBuilder encrypted = new StringBuilder();
        for (char c : datoSinEncriptar.toCharArray()) {
            encrypted.append((char) (c ^ clave));
        }
        return encrypted.toString();
    }

    @Override
    public String Desencriptar(String datoEncriptado) {
          return Encriptar(datoEncriptado);
    }
    
    
}

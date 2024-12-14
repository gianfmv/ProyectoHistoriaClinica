/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Encriptar;

/**
 *
 * @author Gian Marrufo
 */
public class EncriptadorCesar implements IEncriptador {

    private final int shift;
    
    public EncriptadorCesar(int shift) {
        this.shift = shift;
    }
    
    @Override
    public String Encriptar(String datoSinEncriptar) {
        StringBuilder encrypted = new StringBuilder();
        for (char c : datoSinEncriptar.toCharArray()) {
            encrypted.append((char) (c + shift));
        }
        return encrypted.toString(); 
    }

    @Override
    public String Desencriptar(String datoEncriptado) {
        StringBuilder decrypted = new StringBuilder();
        for (char c : datoEncriptado.toCharArray()) {
            decrypted.append((char) (c - shift));
        }
        return decrypted.toString();
    }
    
}

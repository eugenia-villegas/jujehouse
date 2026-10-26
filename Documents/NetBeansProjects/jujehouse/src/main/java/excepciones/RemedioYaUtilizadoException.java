/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package excepciones;
import modelo.Remedio;
import modelo.TipoRemedio;

/**
 *
 * @author Euge
 */
public class RemedioYaUtilizadoException extends Exception {
    public RemedioYaUtilizadoException(String mensaje) {
        super(mensaje);
    }
}

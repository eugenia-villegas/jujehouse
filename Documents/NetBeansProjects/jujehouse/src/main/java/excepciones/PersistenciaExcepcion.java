/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package excepciones;

/**
 *
 * @author Euge
 */
public class PersistenciaExcepcion extends Exception {
    public PersistenciaExcepcion(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}

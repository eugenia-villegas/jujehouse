/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Euge
 */

public class LuzMala extends Criatura {
    
    public LuzMala() {
        super("Luz Mala", 30, 2, 10);   
    }
    
    @Override
    public double getMultiplicador(TipoRemedio tipo) {
        switch(tipo) {
            case CUCHILLO:
                return 1.5;
            case REZO:
                return 1;
            case OFRENDA:
                return 0.5;
            default:
                return 1.0;
        }
    }
}
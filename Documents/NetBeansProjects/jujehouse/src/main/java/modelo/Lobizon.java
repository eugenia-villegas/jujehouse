/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Euge
 */
public class Lobizon extends Criatura {

    public Lobizon() {
        super("Lobizon", 60, 8, 50);
    }
    
    @Override
    public double getMultiplicador(TipoRemedio tipo) {
        switch(tipo) {
            case CUCHILLO:
                return 1.0;
            case REZO:
                return 1.5;
            case OFRENDA:
                return 0.5;
            default:
                return 1.0;
        }
    }
    
}

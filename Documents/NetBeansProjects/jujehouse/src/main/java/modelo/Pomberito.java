/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Euge
 */
public class Pomberito extends Criatura {

    public Pomberito() {
        super("Pomberito", 40, 4, 20);
    }
    
    @Override
    public double getMultiplicador(TipoRemedio tipo) {
        switch(tipo) {
            case CUCHILLO:
                return 0.5;
            case REZO:
                return 1;
            case OFRENDA:
                return 2.0;
            default:
                return 1.0;
        }
    }
}

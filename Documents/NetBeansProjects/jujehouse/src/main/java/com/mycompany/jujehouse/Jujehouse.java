/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.jujehouse;
import modelo.Remedio;
import modelo.TipoRemedio;
/**
 *
 * @author Euge
 */
public class Jujehouse {

    public static void main(String[] args) {
        Remedio cuchillo = new Remedio(TipoRemedio.CUCHILLO);
        Remedio rezo = new Remedio(TipoRemedio.REZO);
        Remedio ofrenda = new Remedio(TipoRemedio.OFRENDA);
        
        System.out.println("Los remedios son: " + cuchillo + ", " + rezo + ", " + ofrenda);
    }
}

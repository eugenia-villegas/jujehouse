/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.jujehouse;
import java.util.List;
import java.util.ArrayList;
import modelo.Remedio;
import modelo.TipoRemedio;
import modelo.Criatura;
import modelo.LuzMala;
import modelo.Pomberito;
import modelo.Lobizon;
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
        System.out.println("Detalle:");
        System.out.println("Cuchillo poder: " + cuchillo.getPoder());
        System.out.println("Cuchillo costo: $" + cuchillo.getCosto());
        System.out.println("Cuchillo efecto especial: " + cuchillo.getEfectoEspecial());
        System.out.println("Rezo poder: " + rezo.getPoder());
        System.out.println("Rezo costo: $" + rezo.getCosto());
        System.out.println("Rezo efecto especial: " + rezo.getEfectoEspecial());
        System.out.println("Ofrenda poder: " + ofrenda.getPoder());
        System.out.println("Ofrenda costo: $" + ofrenda.getCosto());
        System.out.println("Ofrenda efecto especial: " + ofrenda.getEfectoEspecial());
        
        List<Remedio>
        
        System.out.println("********************************************************");
        
        List<Criatura> listaCriatura = new ArrayList<Criatura> ();
        listaCriatura.add(new LuzMala());
        listaCriatura.add(new Pomberito());
        listaCriatura.add(new Lobizon());
        
        System.out.println("Criaturas y sus caracteristicas: ");
        
        for (int i = 0; i < listaCriatura.size(); i++) {
            
            System.out.println(listaCriatura.get(i));
            System.out.println("- Vida: " + listaCriatura.get(i).getVida());
            System.out.println("- Dano al Pueblo: " + listaCriatura.get(i).getDanoAlPueblo());
            System.out.println("- Recompensa: " + listaCriatura.get(i).getRecompensa());
        }
        
        
    }
}

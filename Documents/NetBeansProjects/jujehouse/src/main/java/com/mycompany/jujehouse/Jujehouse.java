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
        List<Remedio> listaRemedio = new ArrayList<Remedio> ();
        listaRemedio.add(new Remedio(TipoRemedio.CUCHILLO));
        listaRemedio.add(new Remedio(TipoRemedio.REZO));
        listaRemedio.add(new Remedio(TipoRemedio.OFRENDA));
        
        System.out.println("********************************************************");
        System.out.println("Remedios: ");
        for (Remedio listaRemedio1 : listaRemedio) {
            System.out.println(listaRemedio1.getTipo());
            System.out.println("- Poder: " + listaRemedio1.getPoder());
            System.out.println("- Costo: $" + listaRemedio1.getCosto());
            System.out.println("- Efecto especial? " + listaRemedio1.getEfectoEspecial());
        }
        
        System.out.println("********************************************************");
        
        List<Criatura> listaCriatura = new ArrayList<Criatura> ();
        listaCriatura.add(new LuzMala());
        listaCriatura.add(new Pomberito());
        listaCriatura.add(new Lobizon());        
        
        System.out.println("Criaturas y sus caracteristicas: ");
        
        for (Criatura  criatura : listaCriatura) {
            System.out.println(criatura.getNombre());
            System.out.println("- Vida: " + criatura.getVida());
            System.out.println("- Dano al Pueblo: " + criatura.getDanoAlPueblo());
            System.out.println("- Recompensa: " + criatura.getRecompensa());
            System.out.println("Multiplicador");
            for (Remedio remedio : listaRemedio) {
                System.out.println("- " + remedio.getTipo() + ": " + criatura.getMultiplicador(remedio.getTipo()));
            }
        }
        
        
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Euge
 */
public abstract class Criatura {
    private String nombre;
    private int vida;
    private int danoAlPueblo;
    private int recompensa;

    public Criatura(String nombre, int vida, int danoAlPueblo, int recompensa) {
        this.nombre = nombre;
        this.vida = vida;
        this.danoAlPueblo = danoAlPueblo;
        this.recompensa = recompensa;
    }
    
    public String getNombre() {
        return this.nombre;
    }
    
    public int getVida() {
        return this.vida;
    }
    
    public int getDanoAlPueblo() {
        return this.danoAlPueblo;
    }
    
    public int getRecompensa() {
        return this.recompensa;
    }
    
    public abstract double getMultiplicador(TipoRemedio tipo); 
    
    public boolean esNeutralizada(double danoRecibidoTotal) {
        return danoRecibidoTotal >= vida;
    }
    
    public double calcularDanoRecibido(Remedio remedio) {
        return remedio.getPoder() * getMultiplicador(remedio.getTipo());
    }
    
    public double calcularDanoTotal(List<Remedio> listaRemedio) {
        double danoTotal = 0;
        
        for (Remedio remedio : listaRemedio) {
            danoTotal = danoTotal + calcularDanoRecibido(remedio);
        }
        
        esNeutralizada(danoTotal);
        return danoTotal;
    }
    
    public int calcularDanoAlPueblo(List<Remedio> remedios) {
        double danoTotal = calcularDanoTotal(remedios);
        if (esNeutralizada(danoTotal)) {
            return 0;
        }
        for (Remedio remedio : remedios) {
            if (remedio.getEfectoEspecial()) {
                return this.danoAlPueblo / 2;
            }
        }
        return this.danoAlPueblo;
    }
    
    @Override 
    public String toString() {
        return getNombre();
    }
}

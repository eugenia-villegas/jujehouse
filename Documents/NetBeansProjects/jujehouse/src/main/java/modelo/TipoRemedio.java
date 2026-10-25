/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package modelo;

/**
 *
 * @author Euge
 */
public enum TipoRemedio {
    CUCHILLO("Cuchillo", 40, 30, false),
    REZO("Rezo", 30, 20, true),
    OFRENDA("Ofrenda", 20, 15, false);
    
    private final String nombre;
    private final int poder;
    private final int costo;
    private final boolean efectoEspecial;
    
    TipoRemedio(String nombre, int poder, int costo, boolean efectoEspecial) {
        this.nombre = nombre;
        this.poder = poder;
        this.costo = costo;
        this.efectoEspecial = efectoEspecial;
    }
    
    public String getNombre() {
        return this.nombre;
    }
    
    public int getPoder() {
        return this.poder;
    }
        
    public int getCosto() {
        return this.costo;
    }
    
    public boolean getEfectoEspecial() {
        return this.efectoEspecial;
    }
}

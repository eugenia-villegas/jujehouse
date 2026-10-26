/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Euge
 */
public class Remedio {
    private TipoRemedio tipo;
    private boolean usado = false;
    
    public Remedio(TipoRemedio tipo) {
        this.tipo = tipo;
    }
    
    public TipoRemedio getTipo() {
        return this.tipo;
    }
    
    public boolean isUsado() {
        return this.usado;
    }
    
    public void usar() {
        this.usado = true;
    }
    
    public void reiniciar() {
        this.usado = false;
    }
    
    public String getNombre() {
        return tipo.getNombre();
    } //remover??
    
    public int getPoder() {
        return tipo.getPoder();
    }
    
    public int getCosto() {
        return tipo.getCosto();
    }
    
    public boolean getEfectoEspecial() {
        return tipo.getEfectoEspecial();
    }
    
    @Override
    public String toString() {
        return tipo.getNombre();
    }
}

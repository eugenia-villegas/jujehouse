/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;
import excepciones.RemedioYaUtilizadoException;
import modelo.Criatura;
import modelo.Partida;
import modelo.Remedio;
import modelo.TipoRemedio;
import vista.VentanaJuego;

/**
 *
 * @author Euge
 */
public class ControladorJuego {
    private final Partida partida;
    private final VentanaJuego vista;

    public ControladorJuego() {
        this.partida = new Partida();
        this.vista = new VentanaJuego();
        this.vista.setControlador(this);
    }

    public void iniciar() {
        refrescar();
        vista.setVisible(true);
    }

    private void refrescar() {
        vista.actualizarEstado(partida.getOleadaActual(), partida.getTranquilidad(), partida.getPuntaje());
        vista.mostrarCriaturas(partida.getCriaturas());
    }

    public void asignarRemedio(TipoRemedio tipo) {
        Criatura criatura = vista.getCriaturaSeleccionada();
        if (criatura == null) {
            vista.mostrarMensaje("Elegí una criatura de la lista.");
            return;
        }
        for (Remedio remedio : partida.getRemedios()) {
            if (remedio.getTipo() == tipo) {
                try {
                    partida.asignar(criatura, remedio);
                    vista.agregarInforme("Asignaste " + remedio + " a " + criatura);
                } catch (RemedioYaUtilizadoException e) {
                    vista.mostrarMensaje(e.getMessage());
                }
            }
        }
    }

    public void resolverOleada() {
        String informe = partida.resolverOleada();
        vista.agregarInforme(informe);
        refrescar();
        if (partida.isTerminada()) {
            if (partida.isVictoria()) {
                vista.mostrarMensaje("¡Salvaste al pueblo! Puntaje: " + partida.getPuntaje());
            } else {
                vista.mostrarMensaje("El pueblo cayó. Puntaje: " + partida.getPuntaje());
            }
            vista.deshabilitarJuego();
        }
    }
    
}

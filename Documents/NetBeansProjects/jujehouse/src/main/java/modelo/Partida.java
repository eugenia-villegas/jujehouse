/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;
import excepciones.RemedioYaUtilizadoException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Euge
 */
public class Partida {
    
    public static final int TOTAL_OLEADAS = 3;

    private int tranquilidad = 10;
    private int puntaje = 0;
    private int oleadaActual = 1;
    private final List<Criatura> criaturas = new ArrayList<Criatura>();
    private final List<Remedio> remedios = new ArrayList<Remedio>();
    private final Map<Criatura, List<Remedio>> asignaciones = new HashMap<Criatura, List<Remedio>>();

    public Partida() {
        remedios.add(new Remedio(TipoRemedio.CUCHILLO));
        remedios.add(new Remedio(TipoRemedio.REZO));
        remedios.add(new Remedio(TipoRemedio.OFRENDA));
        cargarOleada();
    }

    private void cargarOleada() {
        criaturas.clear();
        asignaciones.clear();
        for (Remedio remedio : remedios) {
            remedio.reiniciar();
        }
        switch (oleadaActual) {
            case 1:
                criaturas.add(new LuzMala());
                criaturas.add(new LuzMala());
                break;
            case 2:
                criaturas.add(new LuzMala());
                criaturas.add(new LuzMala());
                criaturas.add(new Pomberito());
                break;
            case 3:
                criaturas.add(new Lobizon());
                criaturas.add(new Pomberito());
                criaturas.add(new LuzMala());
                break;
        }
        for (Criatura criatura : criaturas) {
            asignaciones.put(criatura, new ArrayList<Remedio>());
        }
    }

    public void asignar(Criatura criatura, Remedio remedio) throws RemedioYaUtilizadoException {
        remedio.usar();
        asignaciones.get(criatura).add(remedio);
    }

    public String resolverOleada() {
        String informe = "";
        for (Criatura criatura : criaturas) {
            List<Remedio> asignados = asignaciones.get(criatura);
            double dano = criatura.calcularDanoTotal(asignados);
            if (criatura.esNeutralizada(dano)) {
                puntaje += criatura.getRecompensa();
                informe += criatura.getNombre() + ": neutralizada\n";
            } else {
                int golpe = criatura.calcularDanoAlPueblo(asignados);
                tranquilidad -= golpe;
                informe += criatura.getNombre() + ": llegó al pueblo (-" + golpe + ")\n";
            }
        }
        oleadaActual++;
        if (!isTerminada()) {
            cargarOleada();
        }
        return informe;
    }

    public boolean isTerminada() {
        return tranquilidad <= 0 || oleadaActual > TOTAL_OLEADAS;
    }

    public boolean isVictoria() {
        return tranquilidad > 0 && oleadaActual > TOTAL_OLEADAS;
    }
    
    public int getTranquilidad() {
        return this.tranquilidad;
    }
    
    public int getPuntaje() {
        return this.puntaje;
    }
    
    public int getOleadaActual() {
        return this.oleadaActual;
    }
    
    public List<Criatura> getCriaturas() {
        return criaturas;
    }
    
    public List<Remedio> getRemedios() {
        return remedios;
    }
    
}

/**
 * 
 */
package com.ricardogarfe.renfe.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ricardo
 * 
 */
public class HorarioCercanias {

    private String linea;
    private String horaSalida;
    private List<TransbordoCercanias> transbordo = new ArrayList<TransbordoCercanias>();
    private String horaLlegada;
    private String duracion;
    private String codCivis;

    public String getLinea() {
        return linea;
    }

    public void setLinea(String linea) {
        this.linea = linea;
    }

    public String getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(String horaSalida) {
        this.horaSalida = horaSalida;
    }

    public List<TransbordoCercanias> getTransbordo() {
        return transbordo;
    }

    public void setTransbordo(List<TransbordoCercanias> transbordo) {
        this.transbordo = transbordo;
    }

    public String getHoraLlegada() {
        return horaLlegada;
    }

    public void setHoraLlegada(String horaLlegada) {
        this.horaLlegada = horaLlegada;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public String getCodCivis() {
        return codCivis;
    }

    public void setCodCivis(String codCivis) {
        this.codCivis = codCivis;
    }

}
